(defproject kobo-to-readwise "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [org.clojure/java.jdbc "0.7.8"]
                 [org.xerial/sqlite-jdbc "3.23.1"]
                 [org.clojure/data.json "2.4.0"]
                 [org.clojure/tools.logging "1.2.4"]
                 [clj-http "3.12.3"]
                 [slingshot "0.12.2"]]
  :repl-options {:init-ns kobo-to-readwise.core}
  :main kobo-to-readwise.core)
