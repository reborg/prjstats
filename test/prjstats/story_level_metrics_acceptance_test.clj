(ns prjstats.story-level-metrics-acceptance-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use prjstats.factories)
  (:use [lobos.migrations :only [wipe-db]]))

(defn page-contains [page value]
  (re-seq (re-pattern value) (clojure.string/join (:body page))))

;;  => projects/1
;;  last iteration story metrics:
;;  number of stories done 2
;;  unfinished stories 4
;;  story points executed 4
;;  given a project 1 with 4 stories done
;;  when I ask for project number 1 details
;;  then I want to see 4 for the number of stories done
(facts "it shows the number of stories done during the last iteration"
       (wipe-db)
       (let [a-project-id (str (:id (give-me-a-project-with-metric "stories-done" 44)))]
        (page-contains (project "/" a-project-id) "stories-done") => truthy
        (page-contains (project "/" a-project-id) "44") => truthy))

;;(facts "details for a specific project"
;;  "all metrics are on the page for the specific project"
;;  (count (re-seq #"Metrics for" (join (:body (project "/" "1"))))) => 1)
