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
  (try 
    (open-global prjstatsdb) 
    (catch Exception ex (println (str "Connection already open:" ex))))
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
