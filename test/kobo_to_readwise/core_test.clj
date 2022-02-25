(ns kobo-to-readwise.core-test
  (:require [clojure.test :refer :all]
            [kobo-to-readwise.core :refer :all]))

(def highlight-without-note {:text " I don’t have remnants in the same way that you do, or a plate inside my chest. I don’t know what my pieces were before they were me, and I don’t know what they’ll become after. All I have is right now, and at some point, I’ll just end, and I can’t predict when that will be, and—and if I don’t use this time for something, if I don’t make the absolute most of it, then I’ll have wasted something precious.” Dex rubbed their aching eyes. “Your kind, you chose death. You didn’t have to. You could live forever. But you chose this. You chose to be impermanent. People didn’t, and we spend our whole lives trying to come to grips with that.”\n", :title "A Psalm for the Wild-Built", :note nil, :author "Becky Chambers"})
(def highlight-with-note-removed {:text " I don’t have remnants in the same way that you do, or a plate inside my chest. I don’t know what my pieces were before they were me, and I don’t know what they’ll become after. All I have is right now, and at some point, I’ll just end, and I can’t predict when that will be, and—and if I don’t use this time for something, if I don’t make the absolute most of it, then I’ll have wasted something precious.” Dex rubbed their aching eyes. “Your kind, you chose death. You didn’t have to. You could live forever. But you chose this. You chose to be impermanent. People didn’t, and we spend our whole lives trying to come to grips with that.”\n", :title "A Psalm for the Wild-Built", :author "Becky Chambers"})
(def simple-note {:note "A Note"})
(def empty-note {:note ""})

(deftest remove-empty-note
  (is (= [{}] (replace-nil-comments [{:note nil}] )  ))
  (is (= [{}] (replace-nil-comments [empty-note])))
  (is (= [simple-note] (replace-nil-comments [simple-note])))
  (is (= [highlight-with-note-removed] (replace-nil-comments [highlight-without-note])))
  (is (= [highlight-with-note-removed simple-note](replace-nil-comments [highlight-without-note,
                             simple-note]))))

