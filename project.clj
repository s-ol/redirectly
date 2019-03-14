(defproject redirectly "0.1.0-SNAPSHOT"
  :description "mini redirect service"
  :url "https://s-ol.nu/redirectly"
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler redirectly.core/handler
         :destroy redirectly.core/destroy}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [sonian/carica "1.2.2"]
                 [ring/ring-core "1.7.1"]
                 [ring/ring-jetty-adapter "1.7.1"]
                 [ring-logger "1.0.1"]])
