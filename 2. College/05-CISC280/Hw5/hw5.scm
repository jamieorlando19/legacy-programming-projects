(define (deriv exp var)
  (cond ((number? exp) 0)
        ((variable? exp)
         (if (same-variable? exp var) 1 0))
        ((sum? exp)
         (make-sum (deriv (addend exp) var)
                   (deriv (augend exp) var)))
        ((product? exp)
         (make-sum
           (make-product (multiplier exp)
                         (deriv (multiplicand exp) var))
           (make-product (deriv (multiplier exp) var)
                         (multiplicand exp))))
        ((exponent? exp)
         (make-product (exponent exp)
                       (make-product (deriv (base exp) var)
                                     (make-exponentiation (base exp) 
                                                          (- (exponent exp) 1)))))
        (else
         (error "unknown expression type -- DERIV" exp))))

;; representing algebraic expressions

(define (variable? x) (symbol? x))

(define (same-variable? v1 v2)
  (and (variable? v1) (variable? v2) (eq? v1 v2)))

;;;Old def (define (make-sum a1 a2) (list '+ a1 a2))

;;; Old Def (define (make-product m1 m2) (list '* m1 m2))

(define (sum? x)
  (and (pair? x) (eq? (car x) '+)))

(define (addend s) (cadr s))

(define (augend s) (caddr s))

(define (product? x)
  (and (pair? x) (eq? (car x) '*)))

(define (multiplier p) (cadr p))

(define (multiplicand p) (caddr p))

(define (exponent? x)
  (and (pair? x) (eq? (car x) '**)))

(define (base p) (cadr p))

(define (exponent p) (caddr p))

;: (deriv '(+ x 3) 'x)
;: (deriv '(* x y) 'x)
;: (deriv '(* (* x y) (+ x 3)) 'x)


;; With simplification

(define (make-sum a1 a2)
  (cond ((=number? a1 0) a2)
        ((=number? a2 0) a1)
        ((and (number? a1) (number? a2)) (+ a1 a2))
        (else (list '+ a1 a2))))

(define (=number? exp num)
  (and (number? exp) (= exp num)))

(define (make-product m1 m2)
  (cond ((or (=number? m1 0) (=number? m2 0)) 0)
        ((=number? m1 1) m2)
        ((=number? m2 1) m1)
        ((and (number? m1) (number? m2)) (* m1 m2))
        (else (list '* m1 m2))))

(define (make-exponentiation e1 e2)
  (cond ((or (=number? e1 0) (=number? e2 0)) 0)
        ((or (=number? e1 1) (=number? e2 1)) e1)
        ((and (number? e1) (number? e2)) (expt e1 e2))
        (else (list '** e1 e2))))

(define (expt b n)
  (if (= n 0)
      1
      (* b (expt b (- n 1)))))
                                          
                                        

;;;---------------------------------------------
;;; Elevator Function Template
;;; controller : ['up | 'down] number list-of-numbers --> number
(define (controller dir cf demands)
  (cond ((eq? dir 'down)
	 (down-controller cf 
			  (demands-going-up cf demands)
			  (demands-going-down cf demands)))
	((eq? dir 'up)
	 (up-controller cf
			(demands-going-up cf demands)
			(demands-going-down cf demands)))))

(define (down-controller cf demands-up demands-down)
  (cond ((and (null? demands-down) (null? demands-up)) cf)
        ((null? demands-down) (up-controller cf demands-up demands-down))
        (else (find-greatest demands-down))))
        

(define (up-controller cf demands-up demands-down)
  (cond ((and (null? demands-down) (null? demands-up)) cf)
        ((null? demands-up) (down-controller cf demands-up demands-down))
        (else (find-least demands-up))))

(define (demands-going-up cf demands)
  (filter (lambda(x) (> x cf)) demands))

(define (demands-going-down cf demands)
  (filter (lambda(x) (< x cf)) demands))

(define (filter predicate sequence)
  (cond ((null? sequence) null)
        ((predicate (car sequence))
         (cons (car sequence)
               (filter predicate (cdr sequence))))
        (else (filter predicate (cdr sequence)))))

(define (find-least l)
  (find-least-help (car l) l))

(define (find-least-help num l)
  (if (null? l)
      num
      (if ( < (car l) num)
          (find-least-help (car l) (cdr l))
          (find-least-help num (cdr l)))))

(define (find-greatest l)
  (find-greatest-help (car l) l))

(define (find-greatest-help num l)
  (if (null? l)
      num
      (if ( > (car l) num)
          (find-greatest-help (car l) (cdr l))
          (find-greatest-help num (cdr l)))))

(define (append list1 list2)
  (if (null? list1)
      list2
      (cons (car list1)
            (append (cdr list1) list2))))

;;;==========================================================


;;Constructor
(define (make-state canleft canright misleft misright boat)
  (list canleft canright misleft misright boat))

;Selectors

(define (get-canleft state)
  (car state))

(define (get-canright state)
  (cadr state))

(define (get-misleft state)
  (caddr state))

(define (get-misright state)
  (cadddr state))

;1=right 0=left
(define (get-boat state)
  (cadr (cdddr state)))

;Initial State
(define *start-state* (make-state 3 0 3 0 0))

(define (successors state)
  (list (new-state1 state)
	(new-state2 state)
	(new-state3 state)
	(new-state4 state)
	(new-state5 state)))

;New State 1: 1 cannibal takes the boat
(define (new-state1 state)
  (if (= (get-boat state) 1)
      (make-state (+ (get-canleft state) 1)
		  (- (get-canright state) 1)
		  (get-misleft state)
		  (get-misright state)
		  0)
      (make-state (- (get-canleft state) 1)
		  (+ (get-canright state) 1)
		  (get-misleft state)
		  (get-misright state)
		  1)))

;New State 2: 2 cannibals take the boat
(define (new-state2 state)
   (if (= (get-boat state) 1)
       (make-state (+ (get-canleft state) 2)
		   (- (get-canright state) 2)
		   (get-misleft state)
		   (get-misright state)
		   0)
       (make-state (- (get-canleft state) 2)
		  (+ (get-canright state) 2)
		  (get-misleft state)
		  (get-misright state)
		  1)))

;New State 3: 1 missionary takes the boat
(define (new-state3 state)
   (if (= (get-boat state) 1)
      (make-state (get-canleft state)
		  (get-canright state)
		  (+ (get-misleft state) 1)
		  (- (get-misright state) 1)
		  0)
      (make-state (get-canleft state)
		  (get-canright state)
		  (- (get-misleft state) 1)
		  (+ (get-misright state) 1)
		  1)))

;New State 4: 2 missionaries take the boat  
(define (new-state4 state)
    (if (= (get-boat state) 1)
      (make-state (get-canleft state)
		  (get-canright state)
		  (+ (get-misleft state) 2)
		  (- (get-misright state) 2)
		  0)
      (make-state (get-canleft state)
		  (get-canright state)
		  (- (get-misleft state) 2)
		  (+ (get-misright state) 2)
		  1)))

;New State 5: 1 cannibal and 1 missionary take the boat 
(define (new-state5 state)
    (if (= (get-boat state) 1)
      (make-state (+ (get-canleft state) 1)
		  (- (get-canright state) 1)
		  (+ (get-misleft state) 1)
		  (- (get-misright state) 1)
		  0)
      (make-state (- (get-canleft state) 1)
		  (+ (get-canright state) 1)
		  (- (get-misleft state) 1)
		  (+ (get-misright state) 1)
		  1)))

(define (all-successors state-list)
  (if (null? state-list)
      null
      (append (successors (car state-list))
	      (all-successors (cdr state-list)))))

(define (legal? state)
  (and (> (get-canleft state) -1)
       (> (get-canright state) -1)
       (> (get-misleft state) -1)
       (> (get-misright state) -1)       
       (or (not (< (get-misleft state)(get-canleft state)))
	   (= (get-misleft state) 0))
       (or (not (< (get-misright state)(get-canright state)))
	   (= (get-misright state) 0))))

(define (legal-states state-list)
  (if (null? state-list)
      null
      (if (legal? (car state-list))
	  (append (list (car state-list)) (legal-states (cdr state-list)))
	  (legal-states (cdr state-list)))))

(define (final? state)
  (and (= (get-misleft state) 0) (= (get-canleft state) 0) 
       (= (get-misright state) 3) (= (get-canright state) 3)))

(define (final-states state-list)
  (if (null? state-list)
      null
      (if (final? (car state-list))
	  (append (list (car state-list)) (final-states (cdr state-list)))
	  (final-states (cdr state-list)))))

(define (Missionaries-and-Cannibals)
  (MC-helper (list *start-state*)))

(define (MC-helper state-list)
  (cond ((null? state-list) (display "Whoops") null)
        ((null? (final-states state-list)) 
            (display ".")
            (MC-helper (legal-states (all-successors state-list))))
    (else (newline)
	  (display "Solution is:")
	  (car (final-states state-list)))))
