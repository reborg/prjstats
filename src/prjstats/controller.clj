(ns prjstats.controller
  (:use prjstats.models prjstats.templates ring.util.response))

(defn index [req]
  (response (home-page)))
