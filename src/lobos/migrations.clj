;; How to use me?
;; (use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.migrations)
;; (open-global prjstatsdb)
;; (migrate)
(ns lobos.migrations
  (:refer-clojure :exclude [alter drop bigint boolean char double float time])
  (:use (lobos 
          [migration :only [defmigration]] 
          [connectivity :only [open-global]] 
          core 
          schema)))

(def prjstatsdb
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "prjstats"
   :user "admin"
   :password "admin"})

(defn wipe []
  (open-global prjstatsdb)
  (reset))

(defmigration add-projects-table
  (up [] (create prjstatsdb
                 (table :projects 
                        (integer :id :primary-key )
                        (varchar :project_name 100 :unique ))))
  (down [] (drop (table :projects ))))

(defmigration add-codemetrics-table
  (up [] (create prjstatsdb
                 (table :codemetrics 
                        (integer :id :primary-key )
                        (varchar :metric_name 100)
                        (float :metric_value )
                        (timestamp :generated (default (now)))
                        (integer :project [:refer :projects :id] :not-null))))
  (down [] (drop (table :codemetrics ))))
