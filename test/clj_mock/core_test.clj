(ns clj-mock.core-test
  (:require [clojure.test :refer :all]
            [clj-mock.core :as mock]))

; =============== Testing appending to atom ==============
(deftest append-call
  (testing "should add call to call-atom when method called"
    (let [call-list (atom [])]
      (#'mock/append-call call-list "function" ["arg1"])
      
      (is (= (count @call-list) 1))
      (is (= (:fn-name (first @call-list)) "function"))
      (is (= (:args (first @call-list)) ["arg1"]))))
  (testing "should keep adding calls in order"
    (let [call-list (atom[])]
      (#'mock/append-call call-list "function-1" [])
      (#'mock/append-call call-list "function-2" [])
      
      (is (= (count @call-list) 2))
      (is (= (:fn-name (first @call-list)) "function-1"))
      (is (= (:fn-name (second @call-list)) "function-2")))))

(deftest was-called?
  (testing "should return true if function was called previously"
    (let [call-list (atom [])]
      (#'mock/append-call call-list "function" [])
      (is (mock/was-called? "function" @call-list)))))
