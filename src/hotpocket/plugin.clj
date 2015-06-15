(ns hotpocket.plugin
  (:require [clj-time.core :as t]
            [clojure.edn :as edn]
            [clojure.java.io :as io]
            [clojure.java.shell :as shell]
            [clojure.pprint :as ppr :refer [pp pprint]]
            [clojure.string :as str]

            [leiningen.deploy :as d]
            [leiningen.hotpocket :as hp]

            [robert.hooke :as h]))



(defn- make-hotpocket
  [{:keys [name] :as project}]
  (let [r (shell/sh "git" "log"
                    "--pretty=%h %ad %s -- %an"
                    "--since" (str (t/minus (t/now) (t/days 10)))
                    "--date=iso")
        fname (str "resources/hotpocket/" name ".edn")
        git (into [] (for [[m hash dt c a] (re-seq #"(.*?) ((?:.*? ){2}.*?) (.*) -- (.*)" (:out r))]
                       {:hash hash :date dt :comment c :author a}))
        hotpocket {:git git :version (:version project)}]

    (io/make-parents fname)
    (spit fname (pr-str hotpocket))))

(defn add-hotpocket-resource
  [f & [project & _ :as args]]
  (make-hotpocket project)
  (apply f args))

(defn- add-hotpocket-resource-for-eb
  [f & [project subtask & _ :as args]]
  (when (= subtask "deploy")
    (make-hotpocket project))
  (apply f args))

(defn activate
  []
  (try
    (require 'leiningen.beanstalk)
    (when-let [beanstalk (find-var (symbol "leiningen.beanstalk" "beanstalk"))]
      (h/add-hook beanstalk #'add-hotpocket-resource-for-eb))
    (catch Exception _))
  (h/add-hook #'hp/hotpocket #'add-hotpocket-resource)
  (h/add-hook #'d/deploy #'add-hotpocket-resource))

(defn hooks [] (activate))
