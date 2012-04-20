(ns prjstats.factories
  (:use [clj-time.core :only [now plus days]])
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
