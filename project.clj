(defproject rage "0.1.0-SNAPSHOT"
  :description "road ragior - road rage diagnostics"
  :min-lein-version "2.0"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :url "http://example.com/FIXME"

  :migae {:sdk "rage"
          :devlog "devserver.log" ;; for 'lein migae run' stdout/stderr
          :id "hackzthon-winner"
          ;; GAE version ID
          ;; using '-' prefix on version nbr forces user to customize
          :version  {:dev "0-1-0"
                     :test "-0-1-0"
                     :prod "-0-1-0"}
          :filters [{:filter "reload_filter"
                     :ns "rage.reload-filter"
                     :class "rage.reload_filter"}]
          :servlets [{:servlet "main",
                      :src "rage/main.clj"
                      :ns "rage.main",
                      :class "rage.main",
                      :filters [{:filter "reload_filter"}]
                      :services [{:service "main" :url-pattern  "/*"}
                                              ]}
                     ;; {:servlet "user",
                     ;;  :src "rage/user_servlet.clj"
                     ;;  :ns "rage.user-servlet",
                     ;;  :class "rage.user_servlet",
                     ;;  :filters [{:filter "reload_filter"}]
                     ;;  :services [{:service "prefs" :url-pattern  "/user/prefs"}
                     ;;                          {:service "login" :url-pattern  "/user/login"}
                     ;;                          ]}
                     ]
          ;; :security [{:url-pattern "/*"
          ;;            :web-resource-name "foo"
          ;;            :role-name "*"}]
          :war "war"
          :display-name "rage"
          :welcome "index.html"
          :threads true,
          :sessions true,
          :logging [:jul :slf4j]
          ;; static-files: html, css, js, etc.
          :statics {:src "src/main/public"
                    :dest ""
                    :include {:pattern "public/**"
                              ;; :expire "5d"
                              }
                    ;; :exclude {:pattern "foo/**"}
                    }
          ;; resources: img, etc. - use lein default
          :resources {:src "src/main/resource"
                      :dest ""
                      :include {:pattern "public/**"
                                ;; :expire "5d"
                                }
                      ;; :exclude {:pattern "bar/**"}
                      }
          }
  :aot ["rage.main" #".*filter"]
  :resource-paths ["src/"]
  :web-inf "war/WEB-INF"
  :source-paths ["src/clj"]
  :compile-path "war/WEB-INF/classes"
  :target-path "war/WEB-INF/lib"
  :libdir-path "war/WEB-INF/lib"
  :jar-exclusions [#".*impl*" #"^WEB-INF/appengine-generated.*$"]
  :clean-targets [:web-inf]
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2311"]
                 [compojure "1.1.8"]
                 [ring "1.3.1"]
                 [clj-http "1.0.0"] ;; http client lib
                 [hiccup "1.0.5"]
                 [ring/ring-json "0.3.1"]
                 [cheshire "5.3.1"] ;; json parse
                 ;; [ring/ring-servlet "1.2.0"]
		 ;; [ring/ring-core "1.3.1"]
                 ;; [migae/migae-env "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-blobstore "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-channel "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-datastore "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-images "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-mail "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-memcache "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-taskqueues "0.1.0-SNAPSHOT"]
                 [migae/urlfetch "0.1.0-SNAPSHOT"]
                 ;; [migae/migae-user "0.1.0-SNAPSHOT"]
                 [log4j "1.2.17" :exclusions [javax.mail/mail
                                              javax.jms/jms
                                              com.sun.jdmk/jmxtools
                                              com.sun.jmx/jmxri]]
                 [org.slf4j/slf4j-log4j12 "1.6.6"]
                 [org.clojure/tools.logging "0.2.3"]]

  :cljsbuild {
    :builds [{:id "rage"
              :source-paths ["src/cljs"]
              :compiler {
                :output-to "war/rage.js"
                :output-dir "war/js/"
                :optimizations :none
                :source-map true}}]}

  :plugins [[lein-ring "0.8.11"]
            ;; [lein-cljsbuild "1.0.3"]]
            [lein-cljsbuild "1.0.4-SNAPSHOT"]]

  :ring {:handler webapp.core/app}

  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring-mock "0.1.5"]]}})


