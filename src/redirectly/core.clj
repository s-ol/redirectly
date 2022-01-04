(ns redirectly.core
  (:require [carica.core :refer [config clear-config-cache!]]
            [ring.util.response :as response]
            [clojure.string :refer [join ends-with?]]))

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
(defmethod url :mmm [{[_ path & rest] :to}] (str "//mmm.s-ol.nu" path "/" (join ":" rest)))
(defmethod url :klaus [{[_ repo & rest] :to}] (str "//git.s-ol.nu/" repo "/" (join "/" rest)))

(defn handler [req]
  (if-let [route (some #(matches? % (:uri req)) routes)]
    (response/redirect (url route) (status route))
    (-> (response/not-found (config :404))
        (response/content-type "text/html"))))

(defn destroy []
  (clear-config-cache!))
