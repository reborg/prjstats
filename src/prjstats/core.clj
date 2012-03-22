(ns prjstats.core
  (:use ring.adapter.jetty
        ring.middleware.resource
        ring.util.response
        net.cgrand.moustache))
 
;;; A simple handler to show send some response to the client.
(defn index
  [req]
  (response "Welcome to PrjStats!!! Dude."))
 
;; Routes definition
(def routes
  (app
    [""] index))
 
;;; start function for starting jetty
;;; http://stackoverflow.com/questions/2706044/how-do-i-stop-jetty-server-in-clojure
;;; (.stop server) after starting
(defn start [& port]
  (defonce server (run-jetty #'routes {:port (or port 8888) :join? false})))
