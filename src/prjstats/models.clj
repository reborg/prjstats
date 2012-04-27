;; how to use me?
;; (use 'korma.db 'korma.core 'prjstats.models)
;; (insert  projects (values {:id 1, :project_name "mps"}))
;; (delete projects (where {:id 1}))
;; (select projects)

(ns prjstats.models
  (:use korma.db korma.core))

(defdb prjstatsdb
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "prjstats"
   :user "admin"
   :password "admin"})

(defentity codemetrics)
(defentity projects (has-many codemetrics {:fk "project_id"}))
