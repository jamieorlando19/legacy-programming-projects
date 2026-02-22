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
	    (make-product (make-product (exponent exp)
					 (deriv (base exp) var))
			  (make-exponent (base exp) (- (exponent exp) 1))))
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

(define (base e) (cadr e))

(define (exponent e) (caddr e))


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

(define (make-exponent e1 e2)
  (cond ((=number? e2 0) 1)
	((=number? e2 1) e1)
	(else (list '** e1 e2))))

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

;finds the demands going upwards in the list, and sorts them.
(define (demands-going-up cf demands)
  (traverse (filter (>proc cf) demands)))

(define (demands-going-down cf demands)
  (flip (traverse (filter (<proc cf) demands))))

;this procedure traverses and sorts the list.
(define (traverse lst)
  (if (null? lst)
      null
      (traverse-helper (car lst) (traverse (cdr lst)))))

(define (traverse-helper x1 x2)
  (if (null? x2)
      (list x1)
      (cond ((< x1 (car x2))
	     (append (list x1) x2))
	    ((= x1 (car x2))
	     x1)
	    ((> x1 (car x2))
	    (append (car x2)(traverse-helper x1 (cdr x2))))
	    (else (error "This really shouldn't happen")))))

;this procedure will flip a list, so that the first ellement will
;be the last one, etc.
(define (flip lst)
  (if (null? lst)
      null
      (append (reverse (cdr lst)) (list (car lst)))))

;this is a procedure-returning version of >, implemented so it can be
;filtered on to the function. The same is true for the function <proc
;below, except with regards to < instead of >.
(define (>proc x)
  (lambda (y) (> y x)))

(define (<proc x)
  (lambda (y) (< y x)))

(define (down-controller cf uplist downlist)
  (if (null? downlist)
      (if (null? uplist)
	  cf
	  (car uplist))
	  (car downlist)))

(define (up-controller cf uplist downlist)
  (if (null? uplist)
      (if (null? downlist)
	  cf
	  (car downlist))
	  (car uplist)))
 
;Problem 3: the cannibal/missionary game
;constructors:
(define (make-state canleft canright misleft misright boat)
  (list canleft canright misleft misright boat))

(define *start-state* (make-state 3 0 3 0 0))

;selectors: (these get the field that corresponds to 
;their name from the list

(define (getcanleft state)
  (car state))

(define (getcanright state)
  (cadr state))

(define (getmisleft state)
  (caddr state))

(define (getmisright state)
  (cadddr state))

;note: boat 1=right, boat 0=left
(define (getboat state)
  (cadddr (cdr state)))
;end selectors

;nextstate1 is the state where 1 canibal takes the boat
(define (nextstate1 state)
  (if (= (getboat state) 1)
      (make-state (+ (getcanleft state) 1)
		  (- (getcanright state) 1)
		  (getmisleft state)
		  (getmisright state)
		  0)
      (make-state (- (getcanleft state) 1)
		  (+ (getcanright state) 1)
		  (getmisleft state)
		  (getmisright state)
		  1)))

;nextstate2 is the state where 2 canibals take the boat.
(define (nextstate2 state)
   (if (= (getboat state) 1)
      (make-state (+ (getcanleft state) 2)
		  (- (getcanright state) 2)
		  (getmisleft state)
		  (getmisright state)
		  0)
      (make-state (- (getcanleft state) 2)
		  (+ (getcanright state) 2)
		  (getmisleft state)
		  (getmisright state)
		  1)))

;nextstate3 is the state where 1 missionary takes the boat.
(define (nextstate3 state)
   (if (= (getboat state) 1)
      (make-state (getcanleft state)
		  (getcanright state)
		  (+ (getmisleft state) 1)
		  (- (getmisright state) 1)
		  0)
      (make-state (getcanleft state)
		  (getcanright state)
		  (- (getmisleft state) 1)
		  (+ (getmisright state) 1)
		  1)))

;nextstate4 is the state where 2 missionaries take the boat  
(define (nextstate4 state)
    (if (= (getboat state) 1)
      (make-state (getcanleft state)
		  (getcanright state)
		  (+ (getmisleft state) 2)
		  (- (getmisright state) 2)
		  0)
      (make-state (getcanleft state)
		  (getcanright state)
		  (- (getmisleft state) 2)
		  (+ (getmisright state) 2)
		  1)))

;nextstate5 is the state where 1 missionary and 1 canibal 
;take the boat
(define (nextstate5 state)
    (if (= (getboat state) 1)
      (make-state (+ (getcanleft state) 1)
		  (- (getcanright state) 1)
		  (+ (getmisleft state) 1)
		  (- (getmisright state) 1)
		  0)
      (make-state (- (getcanleft state) 1)
		  (+ (getcanright state) 1)
		  (- (getmisleft state) 1)
		  (+ (getmisright state) 1)
		  1)))

(define (successors state)
  (list (nextstate1 state)
	(nextstate2 state)
	(nextstate3 state)
	(nextstate4 state)
	(nextstate5 state)))

(define (legal? state)
  (and (> (getcanleft state) -1)
       (> (getcanright state) -1)
       (> (getmisleft state) -1)
       (> (getmisright state) -1)       
       (or (not (< (getmisleft state)(getcanleft state)))
	   (= (getmisleft state) 0))
       (or (not (< (getmisright state)(getcanright state)))
	   (= (getmisright state) 0))))

(define (final? state)
  (and (= (getmisleft state) 0) (= (getcanleft state) 0) 
       (= (getmisright state) 3) (= (getcanright state) 3)))

(define (all-successors statelist)
  (if (null? statelist)
      null
      (append (successors (car statelist))
	      (all-successors (cdr statelist)))))

(define (legal-states statelist)
  (if (null? statelist)
      null
      (if (legal? (car statelist))
	  (append (list (car statelist)) (legal-states (cdr statelist)))
	  (legal-states (cdr statelist)))))

(define (final-states statelist)
  (if (null? statelist)
      null
      (if (final? (car statelist))
	  (append (list (car statelist)) (final-states (cdr statelist)))
	  (final-states (cdr statelist)))))

(define (playgame)
  (gamehelper (list *start-state*)))

(define (gamehelper statelist)
  (cond 
    ((null? statelist) (display "no solution was found") null)
    ((null? (final-states statelist))
     (display ".")
     (gamehelper (legal-states (all-successors statelist))))
    (else (newline)
	  (display "found a solution:")
	  (car (final-states statelist)))))