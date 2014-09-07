(ns rage.main-impl
  (:use compojure.core
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.file-info :only [wrap-file-info]]
        rage.mojio)
  (:require [compojure.route :as route]))

(defroutes main-routes
  (GET "/main/:user" [user]
    (str (format "<h1>Howdy %s from rage.main-impl</h1>" user)
         "\n\n<a href='/'>home</a>"))

  (route/not-found "<h1>Page not found</h1>"))

(def main-handler
  (-> #'main-routes
      wrap-params
      wrap-file-info
      ))

