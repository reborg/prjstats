(ns prjstats.controller
  (:use prjstats.models 
        prjstats.templates 
        ring.util.response
        korma.core))

(defn index "Shows all projects" [req]
  (response (home-page (select projects))))

(defn project "Show a single project details" [req project-id]
  (let [project-id-as-integer (Integer/parseInt project-id)]
    (response (project-details (first (select projects (with codemetrics) (where {:id project-id-as-integer})))))))

(defn login "Handle login" [req]
  (let [params (:params req)]
    (if (empty? params)
      (response (login-template))
      (if (= (get params "username") (get params "password"))
        (assoc (redirect "/admin") :session {:username (get params "username")})
        (response (login-template "Invalid username or password"))))))

;; FIXME: really postgres no auto seq?
(defn codemetrics-next-id []
  (inc (count (select codemetrics))))

;; FIXME: the project attribute should come from the form because a selection
;; was made ahead about which project this metric is added to.
(defn codemetrics-add [attributes]
  (if-not (empty? attributes) 
    (insert codemetrics 
            (values 
              (assoc attributes 
                     :id (codemetrics-next-id) 
                     :project 1
                     "metric_value" (Double/parseDouble (attributes "metric_value")))))))

(defn admin "Admin console access" [req]
  (let [username (:username (:session req))
        params (:params req)]
    (if (nil? username)
      (redirect "/login")
      (do
        (locking System/out (println (str "params: " params)))
        (codemetrics-add params)
        (response (admin-template))))))

(defn logout "Handle logout request" [req]
  (assoc (redirect "/") :session nil))
