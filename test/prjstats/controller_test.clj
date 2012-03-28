(ns prjstats.controller-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use [clojure.string :only (join)]))

(facts "showing the list of projects"
  "all projects available are on the page"
  (count (re-seq #"project_name" (join (:body (index "req"))))) => 4)

;;(facts "about the main page"
;;  "Bare request produces prompt"
;;  (handler {:uri "/" :request-method :get})
;;  => (contains {:status 200
;;                :body #"add two numbers"})
;;
;;  "Valid numbers produce sum"
;;  (handler {:uri "/" :request-method :post :params {"a" "1" "b" "2"}})
;;  => (contains {:status 200
;;                :body #"1 \+ 2 = 3"})
;;
;;  "Non-numbers produce error message"
;;  (handler {:uri "/" :request-method :post :params {"a" "2" "b" "f"}})
;;  => (contains {:status 200
;;                :body #"those are not both numbers"}))
;;
;;(fact "Other pages produce redirect"
;;  (handler {:uri "/anything" :request-method :get})
;;  => (contains {:status 302,
;;                :headers (contains {"Location" "/"})}))
