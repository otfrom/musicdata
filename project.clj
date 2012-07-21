(defproject musicdata "0.1.0-SNAPSHOT"
  :description "Hacking for Music Data Hack Day"
  :url "http://github.com/otfrom/musicdata"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [quil "1.6.0"]
                 [incanter/incanter-core "1.3.0"]
                 [incanter/incanter-io "1.3.0"]
                 [incanter/incanter-charts "1.3.0"]
                 [overtone "0.7.1"]
                 [org.clojure/data.csv "0.1.2"]
                 [org.clojure/data.json "0.1.2"]
                 [org.clojure/tools.logging "0.2.3"]
                 [log4j/log4j "1.2.16"]]
  :plugins [[lein-swank "1.4.4"]])
