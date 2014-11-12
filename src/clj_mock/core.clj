(ns clj-mock.core)

(defn- append-call [call-atom fn-name args]
  (swap! call-atom conj {:fn-name fn-name :args args}))

(defn- name-matches? [pred call]
  (pred (:fn-name call)))

(defn- name-equals? [fn-name call]
  (= (:fn-name call) fn-name))

(defn- any-arg-matches? [pred call]
  (some pred (:args call)))

(defn- all-args-match? [pred call]
  (every? pred (:args call)))

(defn- has-arg-equal-to? [arg call]
  (any-arg-matches? (partial = arg) call))

(defn- nth-arg-matches? [n pred arg call]
  (pred (nth (:args call) n nil)))

(defn- nth-arg-equals? [n arg call]
  (nth-arg-matches? n (partial = arg) call))

(defn was-called? [fn-name call-list]
  (some (partial name-equals? fn-name) call-list))
