(ns prjstats.controller
  (:use prjstats.models 
        prjstats.templates 
        ring.util.response
        korma.core))

(defn index "Shows all projects" [req]
  (response (home-page (select projects))))

(defn project "Show a single project details" [req project-id]
  (let [project-id-as-integer (Integer/parseInt project-id)]
    (response (project-details (first (select projects (where {:id project-id-as-integer})))))))
