(ns prjstats.core
  (:use ring.adapter.jetty
        ring.middleware.resource
        ring.middleware.file
        ring.util.response
        net.cgrand.moustache
        prjstats.controller))
 
;; Routes definition
(def routes
  (app
    (wrap-file "resources/public")
    [""] (delegate index)))
 
;;; start function for starting jetty
;;; http://stackoverflow.com/questions/2706044/how-do-i-stop-jetty-server-in-clojure
;;; (.stop server) after starting
(defn start [& port]
  (defonce server (run-jetty #'routes {:port (or port 8888) :join? false})))
