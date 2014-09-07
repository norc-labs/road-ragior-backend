(ns rage.core)

(enable-console-print!)

(println "Hello howdy world!")

(defn handle-click []
  (js/alert "Hello there!"))

(def clickable (.getElementById js/document "clickable"))

(.addEventListener clickable "click" handle-click)

todo:
1.  register person/vehicle parings
2.  display current ridership status
3.  display speed curve for current (and past) trip(s)
4.  display road-rage metrics
