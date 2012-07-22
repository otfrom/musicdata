(ns musicdata.core
  (:use [clojure.tools.logging :only (infof errorf)])
  (:require [clojure.string :as string]
            [clojure.java.io :as io]
            [clojure.data.json :as json]
            [clojure.data.csv :as csv]
            [clj-time.core :as time]
            [clj-time.coerce :as coerce]
            [quil.core :as quil]

            [incanter.core :as incanter]
            [incanter.stats :as stats]
            [incanter.charts :as charts]
            [incanter.io :as incanter-io]))

(defn slurp-csv [filename]
  (with-open [in-file (io/reader filename)]
    (doall
     (csv/read-csv in-file))))

(defn munge-csv [f filename]
  (with-open [in-file (io/reader filename)]
    (f (csv/read-csv in-file))))

(defn map-csv [f filename]
  (with-open [in-file (io/reader filename)]
    (map f (csv/read-csv in-file))))


(defn sum [nums]
  (reduce (fn [acc n] (+ acc (Integer/parseInt n))) 0 (filter #(not (= "" %)) nums)))

(defn mean [strings]
  (let [numbers (map #(Integer/parseInt %) (filter #(not (= "" %)) strings))
        total (reduce + numbers)
        count (float (count numbers))]
    (/ total count)))

(defn get-artists []
    (with-open [in-file (io/reader "./resources/words.csv")]
      (let [words (doall (csv/read-csv in-file))
            kw (map #(keyword %) (filter #(not (= "" %)) (first words)))
            good-rows (filter #(= (count kw) (count %)) (drop 1 words))
            bad-rows (filter #(not (= (count kw) (count %))) (drop 1 words))]
        (println "kw count " (count kw))
        (println "kw " kw)
        {:good (into #{} (map #(:Artist (zipmap kw %)) good-rows))
         :bad (into #{} (map #(:Artist (zipmap kw %)) bad-rows))})))

(defn artist-ratings [artist]
  (with-open [in-file (io/reader "./resources/words.csv")]
    (let [words (csv/read-csv in-file)
          kw (map #(keyword %) (filter #(not (= "" %)) (first words)))
          rows (drop 1 words)]
      (doall (filter (fn [row] (= artist (:Artist row))) (map #(zipmap kw %) rows))))))

;; (def test-data (slurp-csv "./resources/test.csv"))


;;(def training-data (incanter-io/read-dataset "./resources/train.csv" :header true))

;;(def user-data (incanter-io/read-dataset "./resources/users.csv" :header true))

;;(def word-data (incanter-io/read-dataset "./resources/words.csv" :header true))


(def non-rating-kws #{:OWN_ARTIST_MUSIC :User :LIKE_ARTIST :HEARD_OF :Artist})

(def rating-kws #{:Worldly :Calm :Warm :Classic :Dark :Talented :Confident :Heartfelt :Credible :Genius :Stylish :Annoying :Superstar :Playful :Intriguing :Sophisticated :Cheap :Relatable :Uplifting :Colourful :Passionate :Goodlyrics :Outspoken :Energetic :Fun :Outgoing :Cheesy :Free :Wholesome :Soulful :Unoriginal :Depressing :Noneofthese :Noisy :Relaxed :GoodLyrics :Sociable :Trendsetter :Distinctive :Irrelevant :Unattractive :Exciting :Catchy :Current :Thoughtful :Dated :Progressive :Laidback :Intrusive :Youthful :Arrogant :Uninspired :Cool :Superficial :Timeless :Unapproachable :Old :Boring :Popular :Serious :Wayout :Sensitive :Inspiring :Notauthentic :Background :Nostalgic :Over :Fake :Approachable :Legendary :Edgy :Original :Upbeat :Emotional :Beautiful :Rebellious :Pioneer :Authentic :Sexy :Aggressive :Mainstream :Iconic})

(def good #{:Classic :Talented :Confident :Heartfelt :Credible :Genius :Stylish :Intriguing :Sophisticated :Relatable :Uplifting :Passionate :Outspoken :Energetic :Fun :Outgoing :Wholesome :Trendsetter:Distinctive :Exciting :Catchy :Thoughtful :Popular :Legendary :Original :Upbeat :Emotional :Beautiful })

(def bad #{:Annoying :Cheap :Cheesy :Unoriginal :Depressing :Noisy :Irrelevant :Unattractive :Dated :Intrusive :Arrogant :Uninspired :Superficial :Unapproachable :Old :Boring :Over :Fake})

(comment :Worldly

:Calm
:Relaxed
:Laidback
:Aggressive

:Warm
:Cool

:Dark
:Serious
:Colourful
:Playful


:Nostalgic
:Timeless
:Current
:Youthful
:Edgy

:Goodlyrics
:Superstar

:Free

:GoodLyrics

:Sociable

:Progressive


:Sensitive
:Inspiring
:Background

:Approachable

:Rebellious
:Mainstream

:Notauthentic
:Authentic

:Pioneer
:Wayout

:Sexy

:Iconic

:Noneofthese
)
