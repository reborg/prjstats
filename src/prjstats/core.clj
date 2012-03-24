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
    [""] (delegate index)
    [id] (delegate project id)))
 
;;; start function for starting jetty
;;; http://stackoverflow.com/questions/2706044/how-do-i-stop-jetty-server-in-clojure
;;; (use 'prjstats.core) (.stop server)
;;; (use 'prjstats.core) (.start server)
(defn start [& port]
  (defonce server (run-jetty #'routes {:port (or port 8888) :join? false})))

;; run me with lein run (or in general lein run -m prjstats.core)
(defn -main [& port]
  (run-jetty #'routes {:port (or port 8888) :join? false}))
