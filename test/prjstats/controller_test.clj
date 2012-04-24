(ns prjstats.controller-test
  (:use midje.sweet)
  (:use prjstats.controller)
  (:use [lobos.migrations :only (wipe-db)])
  (:use [clojure.string :only (join)]))

;; FIXME: expecting the controller test to verify return status codes, redirects and in general correct routes are followed, and correct views renedered. It also protect the admin area and stuff.
