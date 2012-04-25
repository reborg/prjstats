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

(def h2-settings
  {:classname   "org.h2.Driver"
   :subprotocol "h2:file"
   :subname     (str (System/getProperty "user.dir") "/" "prjstats")
   :user        "sa"
   :password    "" })

(defmigration add-projects-table
  (up [] (create prjstatsdb
                 (table :projects 
                        (integer :id :primary-key )
                        (varchar :project_name 100 :unique ))))
  (down [] (drop (table :projects )) (println "dropping table projects")))

(defmigration add-codemetrics-table
  (up [] (create prjstatsdb
                 (table :codemetrics 
                        (integer :id :primary-key )
                        (varchar :metric_name 100)
                        (float :metric_value )
                        (timestamp :generated)
                        (integer :project_id [:refer :projects :id] :not-null))))
  (down [] (drop (table :codemetrics )) (println "dropping table codemetrics")))

(defn wipe-db []
  "Couldn't make rollback/migrate to work properly at each test run.
  only way found was to do the following manually"
  (try 
    (open-global prjstatsdb) 
    (catch Exception ex (println "Reusing connection" )))
  (try 
    (drop (table :codemetrics ))
    (catch Exception ex (println "Table codemetrics does not exist. No need to drop again." )))
  (try 
    (drop (table :projects) :cascade)
    (catch Exception ex (println "Table projects does not exist. No need to drop again." )))
  (try 
    (drop (table :lobos_migrations))
    (catch Exception ex (println "Table lobos_migrations does not exist. No need to drop again." )))
  (nil? (migrate)))
