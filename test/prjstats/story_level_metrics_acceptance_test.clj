(ns prjstats.story-level-metrics-acceptance-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use korma.db korma.core)
  (:use [prjstats.models :only (codemetrics projects)])
  (:use prjstats.factories)
  (:use [clj-factory.core :only [factory]])
  (:use [lobos.migrations :only [wipe-db]]))

(defn load-story-level-metrics
  "creates a project and related story level metrics" []
  (insert codemetrics 
          (values (conj 
                    (factory :codemetrics) 
                    {:project (:id (insert projects (values (factory :project))))}))))

(facts "story level metrics for specific project are shown"
       (wipe-db)
       (load-story-level-metrics)
       (count (re-seq #"project_name" (join (:body (index "req"))))) => 1)
