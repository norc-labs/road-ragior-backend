(ns rage.main
    (:gen-class :extends javax.servlet.http.HttpServlet)
    (:require [rage.main-impl :as impl]
              [ring.util.servlet :as ring]))

;; (def -service (ring/make-service-method impl/main-handler))

(defn -service
  [this rqst resp]
    (let [request-map  (ring/build-request-map rqst)
          response-map (impl/main-handler request-map)]
    (when response-map
      (ring/update-servlet-response resp response-map))))
