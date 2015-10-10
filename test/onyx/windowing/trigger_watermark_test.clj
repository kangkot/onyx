(ns onyx.windowing.trigger-watermark-test
  (:require [clojure.test :refer [deftest is]]
            [onyx.triggers.triggers-api :as api]
            [onyx.api]))

(deftest watermark-test
  (let [window {:window/id :collect-segments
                :window/task :identity
                :window/type :fixed
                :window/aggregation :onyx.windowing.aggregation/count
                :window/window-key :event-time
                :window/range [5 :minutes]}
        trigger {:trigger/window-id :collect-segments
                 :trigger/refinement :accumulating
                 :trigger/on :watermark
                 :trigger/sync ::no-op
                 :trigger/id :trigger-id}
        segment {:event-time (System/currentTimeMillis)}
        event {}]
    (is (api/trigger-fire? event trigger {:window window :segment segment
                                          :upper-extent (dec (System/currentTimeMillis))})))

  (let [window {:window/id :collect-segments
                :window/task :identity
                :window/type :fixed
                :window/aggregation :onyx.windowing.aggregation/count
                :window/window-key :event-time
                :window/range [5 :minutes]}
        trigger {:trigger/window-id :collect-segments
                 :trigger/refinement :accumulating
                 :trigger/on :watermark
                 :trigger/sync ::no-op
                 :trigger/id :trigger-id}
        segment {:event-time (System/currentTimeMillis)}
        event {}]
    (is
     (not
      (api/trigger-fire? event trigger {:window window :segment segment
                                        :upper-extent (inc (System/currentTimeMillis))})))))
