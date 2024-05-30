#!/usr/bin/env bb

(require '[cheshire.core :as json])                         ;; optional
(require '[babashka.http-client :as http])

; Load list of components - maybe filter by type of codebase?
(defn load-components []
  (->
    (http/get "https://poetrydb.org/title/Ozymandias/lines.json")
    :body
    (json/parse-string true)
    :args)
  )

(def example {:components [
                           {:name "firstname", :key "firstkey"}
                           {:name "secondname", :key "secondkey"}
                           ]})

(defn create-query-from-components [components] (->>
                                                  (:components components)
                                                  (map (fn [component] (str (:key component) "_" (:name component))))
                                                  (clojure.string/join ",")
                                                  )
  )

(defn query-code-cov [services] (->
                                  (http/get "" {:query-params {:something services}})
                                  ))

(defn echo [] (-> (http/get "https://postman-echo.com/get" {:query-params {:q ["clojure" "curl"], :contents [{:name "name1" :key "key1"}, {:name "name1" :key "key1"}, {:all (str "a," "b," "c")}]}})
                  :body (json/parse-string true) :args :contents
                  ))

(defn poetry [] (-> (http/get "https://poetrydb.org/title/Ozymandias/lines.json") :body (json/parse-string true)))
