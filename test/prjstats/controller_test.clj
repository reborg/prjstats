(ns prjstats.controller-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use [clojure.string :only (join)]))

(facts "showing the list of projects"
  "all projects available are on the page"
  (count (re-seq #"project_name" (join (:body (index "req"))))) => 4)

(facts "details for a specific project"
  "all metrics are on the page for the specific project"
  (count (re-seq #"Metrics for" (join (:body (project "/" "1"))))) => 1)
