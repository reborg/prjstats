;; Use me.
;; (use 'prjstats.templates)
;; (home-page)
(ns prjstats.templates
  (:use [net.cgrand.enlive-html]))
 
(deftemplate home-page "home.html" [projects]
  [:title] (content "PrjStats - The metrics aggregator")
  [:div.project] (clone-for [project projects]
                            [:span.project_name] (content (:project_name project))
                            [:div.metrics_count] (content (:project_name project))))

(deftemplate project-details "project.html" [project]
  [:title] (content "PrjStats - The metrics aggregator")
  [:div.project] (content (str "Metrics for " (project :project_name))))
