(ns prjstats.controller-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use [lobos.migrations :only (wipe-db)])
  (:use [clojure.string :only (join)]))

(background 
   (wipe-db) => true)

(facts "page display blank if no projects"
  "there are no projects stored"
  (wipe-db)
  (count (re-seq #"project_name" (join (:body (index "req"))))) => 0)

;;(facts "showing the list of projects"
;;  "all projects available are on the page"
;;  (count (re-seq #"project_name" (join (:body (index "req"))))) => 4)
;;  
;;(facts "details for a specific project"
;;  "all metrics are on the page for the specific project"
;;  (count (re-seq #"Metrics for" (join (:body (project "/" "1"))))) => 1)
