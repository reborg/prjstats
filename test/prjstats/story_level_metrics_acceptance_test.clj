(ns prjstats.story-level-metrics-acceptance-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use prjstats.factories)
  (:use [lobos.migrations :only [wipe-db]]))

(facts "it shows the number of stories done during the last iteration"
       (wipe-db)
       (let [a-project (give-me-a-project)
             some-metrics (give-me-metrics-for-project a-project)]
        (re-seq #"story_count" 
                (clojure.string/join 
                  (:body (project "/" (str (:id a-project)))))) 
         => (:metric_value some-metrics)))

;;(facts "details for a specific project"
;;  "all metrics are on the page for the specific project"
;;  (count (re-seq #"Metrics for" (join (:body (project "/" "1"))))) => 1)
