(defproject prjstats "0.0.1-SNAPSHOT"
  :description "PrjStats a simple project metrics aggregator and visualizer"
  :dependencies [[org.clojure/clojure "1.3.0"]
                 [ring "1.0.1"]
                 [net.cgrand/moustache "1.1.0"]
                 [lobos "1.0.0-SNAPSHOT"]
                 [korma "0.2.1"]
                 [enlive "1.0.0"]
                 [postgresql "9.1-901.jdbc4"]
                 [clj-yaml "0.3.1"]]
  :dev-dependencies [[midje "1.3.1"] 
                     [lein-midje "1.0.8"]]
  :main prjstats.core)
