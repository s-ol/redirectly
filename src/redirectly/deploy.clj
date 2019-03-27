(ns redirectly.deploy
  (:gen-class)
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [redirectly.core :refer [handler]]))

(defn -main [& args]
  (run-jetty handler {:port 3000}))
