(ns swagger-service.core
  (:require [reagent.core :as reagent :refer [atom]]
            [ajax.core :refer [GET]])
  (:require-macros [secretary.core :refer [defroute]]))

(defn fetch-links! [links link-count]
  (GET "/api/cat-links"
    {:params {:link-count link-count}
     :handler #(reset! links %)}))

(defn home-page []
  (let [links
        (atom nil)]
    (fetch-links! links 20)
    (fn []
      [:div
       (for [link @links]
         [:img {:src link}])])))

(defn mount-components []
  (reagent/render-component [home-page] (.getElementById js/document "app")))

(defn init! []
  (mount-components))
