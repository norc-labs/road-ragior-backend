(ns rage.main-impl
  (:use compojure.core
        [ring.middleware.params :only [wrap-params]]
        [ring.middleware.file-info :only [wrap-file-info]]
        [ring.middleware.json :only [wrap-json-params]]
        hiccup.core
        hiccup.page
        rage.mojio)
  (:require [compojure.route :as route]
            [cheshire.core :as json]
            [migae.urlfetch :as urlfetch]
            [clojure.tools.logging :as log]
            ))

(def mojio-app "fb96075e-fdbf-4b03-bd8e-f4aa6016ccb6")

(defroutes main-routes
  (GET "/" []
       ;; demo clojurescript
       (html5
        [:body
         [:p#clickable "Click me!"] ;; clickable: see core.cljs
         [:script {:src "js/goog/base.js" :type "text/javascript"}]
         [:script {:src "rage.js" :type "text/javascript"}]
         [:script {:type "text/javascript"}
          "goog.require('rage.core');"]]))

  (GET "/main/:user" [user]
       ;; (html5
       ;;  [:h1 (str "Howdy, " user)]))
    (str (format "<h1>Hello %s from rage.main-impl</h1>" user)
         "\n\n<a href='/'>home</a>"))

  (GET "/mojio/hello" []
       (let [res (urlfetch/fetch "https://api.moj.io/v1/" :async? false)]
         (html5
          [:body
           [:p "api token: " @*api-tok*]
           [:pre
            (slurp (:content res))
            ]])))
  (GET "/mojio/schemas" []
       (let [res (urlfetch/fetch "https://api.moj.io/v1/Schema/List")]
         (html5
          [:body
           [:p "API token: " @*api-tok*]
           [:pre
            (slurp (:content res))
            ]])))

  (GET "/mojio/schema/vehicle" []
       (let [res (urlfetch/fetch "https://api.moj.io/v1/Schema?entityType=Vehicle")]
         (log/warn res)
         (html5
          [:body
           [:p "api token: " @*api-tok*]
           [:pre
            (slurp (:content res))
            ]])))

  (GET "/mojio/auth" []
       (let [res (urlfetch/fetch
                  (str "https://api.moj.io/v1/login/" mojio-app "?secretKey=" mojio-test)
                  :method :post)
             j (slurp (:content res))
             edn (json/parse-string j true)]
         (do
           (log/warn "edn " edn)
           (swap! *api-tok* (fn [_] (:_id edn)))
           (html5
            [:body
             [:p "api token: "
              @*api-tok*
              ;; "_id" component is API token
              ]]))))

  (route/not-found "<h1>Page not found</h1>"))

(def main-handler
  (-> #'main-routes
      wrap-params
      wrap-json-params
      ))

