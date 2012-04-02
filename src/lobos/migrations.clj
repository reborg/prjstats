;; How to use me?
;; (use 'lobos.core 'lobos.connectivity 'lobos.migration 'lobos.migrations 'lobos.schema)
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
  "migrate can always happen, with or without db tables, so if they are not
  there they are created. Drop instead fails if there are no tables (of course), 
  this is why I migrate first to be sure. The last migrate is the definitive one.
  At the end there should be an empty migrated up to date db."
  (try 
    (open-global prjstatsdb) 
    (catch Exception ex (println "Reusing connection" )))
  (migrate)
  (rollback)
  (migrate))

(defmigration add-projects-table
  (up [] (create prjstatsdb
                 (table :projects 
                        (integer :id :primary-key )
                        (varchar :project_name 100 :unique ))))
  (down [] (drop (table :projects ) :cascade)))

(defmigration add-codemetrics-table
  (up [] (create prjstatsdb
                 (table :codemetrics 
                        (integer :id :primary-key )
                        (varchar :metric_name 100)
                        (float :metric_value )
                        (timestamp :generated (default (now)))
                        (integer :project [:refer :projects :id] :not-null))))
  (down [] (drop (table :codemetrics ))))
