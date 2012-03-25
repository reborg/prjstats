(ns prjstats.controller
  (:use prjstats.models 
        prjstats.templates 
        ring.util.response
        korma.core))

(defn index "Shows all projects" [req]
  (response (home-page (select projects))))

(defn project "Show a single project details" [req project-id]
  (let [project-id-as-integer (Integer/parseInt project-id)]
    (response (project-details (first (select projects (where {:id project-id-as-integer})))))))

(defn login "Handle login" [req]
  (let [params (:params req)]
    (if (empty? params)
      (response (login-template))
      (if (= (get params "username") (get params "password"))
        (assoc (redirect "/admin") :session {:username (get params "username")})
        (response (login-template "Invalid username or password"))))))

(defn admin "Admin console access" [req]
  (let [username (:username (:session req))]
    (if (nil? username)
      (redirect "/login")
      (response (admin-template)))))

(defn logout "Handle logout request" [req]
  (assoc (redirect "/") :session nil))
