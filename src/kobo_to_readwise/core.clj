(ns kobo-to-readwise.core
  (:require [clojure.java.jdbc :as jdbc]
            [clojure.data.json :as json]
            [clojure.set :as set]
            [clj-http.client :as client]
            [clojure.string :as string]
            [clojure.tools.logging :as log])
  (:use [slingshot.slingshot :only [try+ throw+]]))

(def macos-kobo-db (string/join (System/getProperty "file.separator")
                                                     [(System/getProperty "user.home")
                                                      "Library/Application Support/Kobo/Kobo Desktop Edition/Kobo.sqlite"]))

(def db
  {:classname   "org.sqlite.JDBC"
   :subprotocol "sqlite"
   :subname     macos-kobo-db})

(def default-key-file (string/join (System/getProperty "file.separator") [(System/getProperty "user.home") ".readwise_api"]))

(def highlight-query "SELECT  Bookmark.Text, content.Title, content.Attribution, Bookmark.Annotation from content join bookmark where bookmark.volumeId = content.ContentId")

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

(defn read-token []

  (if (.exists (clojure.java.io/file default-key-file))
    (slurp default-key-file)))

(defn send-to-readwise [json-highlights]
  (try+ (client/post "https://readwise.io/api/v2/highlights/"
                     {:content-type :json
                      :headers {"Authorization" (string/join " " ["Token" (read-token)])}
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
      (send-to-readwise)))