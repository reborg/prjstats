;; how to use me?
;; (use 'korma.db 'korma.core 'prjstats.models)
;; (insert  projects (values {:id 1, :project_name "mps"}))
;; (delete projects (where {:id 1}))
;; (select projects)

(ns prjstats.models
  (:use korma.db korma.core))

(defentity codemetrics)
(defentity projects (has-many codemetrics))
