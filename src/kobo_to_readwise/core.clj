(ns kobo-to-readwise.core
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [clojure.set :as set]
            [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.tools.logging :as log])
(:use [slingshot.slingshot :only [try+ throw+]]))
            

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     "/Users/andy.nortrup/Library/Application Support/Kobo/Kobo Desktop Edition/Kobo.sqlite"})
(def access-token "lfOsgmEUVPzmhZ3coth8Z7HZ2vleb8vJXjgwt6cMJkKW501Bre")

(def highlight-query "SELECT  Bookmark.Text, content.Title, content.Attribution, Bookmark.Annotation from content join bookmark where bookmark.volumeId = content.ContentId AND Bookmark.DateCreated > '2021-12-25'")

(defn get-highlights []
  (jdbc/query db [highlight-query]))

(defn transform-keys [highlights]
  (map #(as-> % highlight
            (set/rename-keys highlight {:annotation :note
                                        :attribution :author}))
       highlights))

(defn replace-nil-comments [highlights]
         (map (fn [m]
                (if (or 
                     (nil? (m :note))
                     (string/blank? (m :note)))
                  (dissoc m :note)
                  m)) highlights))
       


(defn highlights-to-json [highlights]
  (json/write-str {:highlights
                   (replace-nil-comments highlights)}))

(defn send-to-readwise [json-highlights]
  (try+ (client/post "https://readwise.io/api/v2/highlights/"
                     {:content-type :json
                      :headers {"Authorization" (string/join " " ["Token" access-token])}
                      :body json-highlights 
                      :throw-exceptions false})
        (catch [:status 400] {:keys [:headers :body]}
          (log/warn "400 Error" :headers :body))
        (catch Object _
          (log/error  :status :body "unexpected error")
          (throw+))))

(defn -main []
  (-> (get-highlights)
      (transform-keys)
      (highlights-to-json)
      (send-to-readwise)
      ))