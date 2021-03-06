;;;; Engine - client.clj
;;;; Copyright (C) 2012  Alexander Kahl <e-user@fsfe.org>
;;;; This file is part of Engine.
;;;; Engine is free software; you can redistribute it and/or modify it
;;;; under the terms of the GNU Affero General Public License as
;;;; published by the Free Software Foundation; either version 3 of the
;;;; License, or (at your option) any later version.
;;;;
;;;; Engine is distributed in the hope that it will be useful,
;;;; but WITHOUT ANY WARRANTY; without even the implied warranty of
;;;; MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
;;;; GNU General Public License for more details.
;;;;
;;;; You should have received a copy of the GNU General Public License
;;;; along with this program.  If not, see <http://www.gnu.org/licenses/>.
(ns engine.client
  (:use [engine.core :only (defdispatcher)]
        engine.client.util
        hiccup.page
        [cssgen :only (rule)])
  (:require [engine.client.splash :as splash])
  (:import (java.util Date Locale TimeZone)
           java.text.SimpleDateFormat))

(let [handler
      (unchanged-handler
       "text/html; charset=utf-8"
       (html5 {:lang "en"}
              [:head
               [:title "Engine"]
               [:meta {:charset "utf-8"}]
               [:meta {:http-equiv "X-UA-Compatible" :content "chrome=1"}]
               [:link {:rel "shortcut icon" :href "client/gears-32x32.png"}]
               (apply include-css "client/html5reset.css" "client/main.css" "/client/jquery-ui.css" "/client/jquery.pnotify.css" splash/cssfiles)
               [:script {:data-main "client/main" :src "client/require-jquery.js"}]]
              [:body
               [:div {:id "content"} [:pre {:id "editor"}]]
               [:div {:id "meta"} [:pre {:id "minibuffer"}]]
               splash/html]))]
  (defdispatcher "/"
    (fn [request]
      (assoc (handler request) :session (:session request)))))

(defcss "/client/main.css"
  (rule "body" :margin 0 :position "absolute" :top 0 :left 0 :right 0 :bottom 0 :overflow "hidden" :font-size "12px"
        css-display-box (css-box "orient" "vertical")
        (rule ".ui-widget" :font-family "Quantico")
        (rule ".ui-pnotify-text" :font-family "Inconsolata" :font-size "0.8em"))
  (rule "#content" (css-box "flex" 1) css-display-box) (rule "#content > *" (css-box "flex" 1))
  (rule ".hbox" (css-box "flex" 1) css-display-box (css-box "orient" "horizontal")) (rule ".hbox > *" :width "50%")
  (rule ".vbox" (css-box "flex" 1) css-display-box (css-box "orient" "vertical")) (rule ".vbox > *" :height "50%")
  (rule "#meta" :height "20px")
  (rule "#editor" :position "relative" :margin 0)
  (rule "#minibuffer" :position "relative" :margin 0 :height "100%"))
