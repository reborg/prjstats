(ns lobos.migrations

  ;; exclude some clojure built-in symbols so we can use the lobos' symbols
  (:refer-clojure :exclude [alter drop bigint boolean char double float time])

  ;; use only defmigration macro from lobos
  (:use (lobos [migration :only [defmigration]]
          core
          schema)))
 
;;; Defines the database for lobos migrations
(def prjstatsdb
  {:classname "org.postgresql.Driver"
   :subprotocol "postgresql"
   :subname "prjstats"
   :user "admin"
   :password "admin"})

(defmigration add-projects-table
  ;; code be executed when migrating the schema "up" using "migrate"
  (up [] (create prjstatsdb
           (table :projects 
             (integer :id :primary-key )
             (varchar :project_name 100 :unique ))))
  ;; Code to be executed when migrating schema "down" using "rollback"
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
