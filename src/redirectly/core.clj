(ns redirectly.core
  (:require [carica.core :refer [config clear-config-cache!]]
            [ring.util.response :as response]))

(def routes (config :routes))

(defn matches? [[slug route] uri]
  (when (= uri (str "/" (name slug)))
    route))

(defn status [route]
  (or (:status route) 307))

(defn url [route]
  (:to route))

(defmulti url (fn [{to :to}]
                  (if (vector? to)
                    (first to)
                    :url)))

(defmethod url :url [{to :to}] to)
(defmethod url :mmm [{[_ path] :to}] (str "//mmm.s-ol.nu" path))

(defn handler [req]
  (if-let [route (some #(matches? % (:uri req)) routes)]
    (response/redirect (url route) (status route))
    (-> (response/not-found (config :404))
        (response/content-type "text/html"))))

(defn destroy []
  (clear-config-cache!))
