(ns prjstats.story-level-metrics-acceptance-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use korma.db korma.core)
  (:use [prjstats.models :only (codemetrics projects)])
  (:use prjstats.factories)
  (:use [clj-factory.core :only [factory]])
  (:use [lobos.migrations :only [wipe-db]]))

(defn give-me-a-project
  "generate a sample project by incrementing the current id" []
  (insert projects (values (factory :project))))

(defn give-me-a-project-with-metrics
  "creates a project and related metrics" []
  (insert codemetrics 
          (values (conj 
                    (factory :codemetrics) 
                    {:project (:id (give-me-a-project))}))))

(defn give-me-metrics-for-project
  "creates a sample set of metrics for a project" [project]
  (insert codemetrics 
          (values (conj 
                    (factory :codemetrics) 
                    {:project (:id project)}))))

(facts "it shows the number of stories done during the last iteration"
       (wipe-db)
       (let [a-project (give-me-a-project)
             some-metrics (give-me-metrics-for-project a-project)]
        (re-seq #"story_count" (clojure.string/join (:body (project "/" (str (:id a-project)))))) => (:metric_value some-metrics)))


;;(facts "details for a specific project"
;;  "all metrics are on the page for the specific project"
;;  (count (re-seq #"Metrics for" (join (:body (project "/" "1"))))) => 1)
