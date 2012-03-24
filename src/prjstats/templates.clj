;; Use me.
;; (use 'prjstats.templates)
;; (home-page)
(ns prjstats.templates
  (:use [net.cgrand.enlive-html]))
 
(deftemplate home-page "home.html" [projects]
  [:title] (content "PrjStats - The metrics aggregator")
  [:div.project] (clone-for [project projects]
                            [:a.project_name] (do-> 
                                                (set-attr :href (str "/" (:id project))) 
                                                (content (:project_name project)))
                            [:div.metrics_count] (content (:project_name project))))

(deftemplate project-details "project.html" [project]
  [:title] (content "PrjStats - The metrics aggregator")
  [:div.project] (content (str "Metrics for " (project :project_name))))

(deftemplate login-template "login.html" [& msg]
  [:div#error] (if (nil? msg)
                 (set-attr :style "display:none")
                 (do->
                   (remove-attr :style)
                   (content msg))))
