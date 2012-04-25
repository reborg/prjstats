(ns prjstats.factories
  (:use [clj-time.core :only [now plus days]])
  (:use korma.db korma.core)
  (:use [prjstats.models :only (codemetrics projects)])
  (:use [clj-factory.core :only [deffactory defseq factory fseq]]))

(defseq :project_id [n] n)
(defseq :project_name [n] (str "project " n))

(deffactory :project
  {:id (fseq :project_id)
   :project_name   (fseq :project_name)})

(defseq :codemetrics_id [n] n)
(defseq :metric_name [n] (str "metric-name-" n))
(defseq :metric_value [n] (* 1.2 n))
(defseq :timestamp [n] (java.sql.Date. (.getTime (java.util.Date.))))

(deffactory :codemetrics
  {:id (fseq :codemetrics_id)
   :metric_name (fseq :metric_name)
   :metric_value (fseq :metric_value)
   :generated (fseq :timestamp)})

(deffactory :generic-metric
  {:id (fseq :codemetrics_id)
   :generated (fseq :timestamp)})

(defn give-me-a-project
  "generate a sample project by incrementing the current id" []
  (insert projects (values (factory :project))))

(defn give-me-a-project-with-metrics
  "creates a project and related metrics" []
  (insert codemetrics 
          (values (conj 
                    (factory :codemetrics) 
                    {:project (:id (give-me-a-project))}))))

(defn give-me-a-project-with-metric [name value]
  "creates a project with a specific metric" []
  (insert codemetrics 
          (values (conj 
                    (conj 
                      (factory :generic-metric 4) 
                      {:metric_name name :metric_value value})
                    {:project (:id (give-me-a-project))}))))

(defn give-me-metrics-for-project
  "creates a sample set of metrics for a project" [project]
  (insert codemetrics 
          (values (conj 
                    (factory :codemetrics) 
                    {:project (:id project)}))))

