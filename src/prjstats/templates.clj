;; Use me.
;; (use 'prjstats.templates)
;; (home-page)
(ns prjstats.templates
  (:use [net.cgrand.enlive-html]))
 
(deftemplate home-page "home.html" []
  [:title] (content "PrjStats - The metrics aggregator"))
