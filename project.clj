(defproject hotpocket "0.2.0-SNAPSHOT"
  :description "A leiningen plugin to add git commit log as a resource file"
  :url "http://github.com/tramzee/hotpocket"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :deploy-repositories [["releases" :clojars]
                        ["snapshots" :clojars]]
  :eval-in-leiningen true)
