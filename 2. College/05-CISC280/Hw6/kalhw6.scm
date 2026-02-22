;;; ---------------------------------------------------------
;;; Basic constants: 
(define WIDTH  50)
(define HEIGHT 160)
(define RADIUS 20)

(define DELTA 10)

(define X (quotient WIDTH 2))

;;; off-set : num -> num
(define (off-set y) (+ y DELTA (* 2 RADIUS)))

(define Y:RED (off-set (- RADIUS)))
(define Y:YEL (off-set Y:RED))
(define Y:GRN (off-set Y:YEL))

;;; A bulb is (make-bulb posn color)
;;CONSTRUCTOR
(define (make-bulb center color) (list center color))
;;SELECTORS
(define bulb-center car)
(define bulb-color  cadr)

;;; The draw library defines a structure POSN with
;;; CONSTRUCTOR (make-posn X Y) --> posn and
;;; SELECTORS (posn-x posn) --> X and (posn-y posn) --> Y

(define RED:BULB (make-bulb (make-posn X Y:RED) RED))
(define YEL:BULB (make-bulb (make-posn X Y:YEL) YELLOW))
(define GRN:BULB (make-bulb (make-posn X Y:GRN) GREEN))
;draws the initial state, and makes the drawing box.
(define (init!)
  (start WIDTH HEIGHT)
  (draw-solid-disk (bulb-center GRN:BULB) RADIUS (bulb-color GRN:BULB))
  (draw-solid-disk (bulb-center YEL:BULB) RADIUS (bulb-color YEL:BULB))
  (draw-solid-disk (bulb-center RED:BULB) RADIUS (bulb-color RED:BULB))
  (draw-solid-disk (bulb-center YEL:BULB) (- RADIUS 1) WHITE)
  (draw-solid-disk (bulb-center RED:BULB) (- RADIUS 1) WHITE)
  )

;this function takes the current bulb state and draws it.
(define (drawbulbs)
  (cond ((eq? (bulb-color bulb) (bulb-color GRN:BULB))
	 (draw-solid-disk (bulb-center GRN:BULB) 
			  RADIUS (bulb-color GRN:BULB))
	 (draw-solid-disk (bulb-center YEL:BULB) 
			  (- RADIUS 1) WHITE)
	 (draw-solid-disk (bulb-center RED:BULB) 
			  (- RADIUS 1) WHITE)
	 )
	((eq? (bulb-color bulb) (bulb-color YEL:BULB))
	 (draw-solid-disk (bulb-center GRN:BULB)
			  (- RADIUS 1) WHITE)
	 (draw-solid-disk (bulb-center YEL:BULB) 
			  RADIUS (bulb-color YEL:BULB))
	 (draw-solid-disk (bulb-center RED:BULB) 
			  (- RADIUS 1) WHITE))
	((eq? (bulb-color bulb) (bulb-color RED:BULB))
	 (draw-solid-disk (bulb-center GRN:BULB) 
			  (- RADIUS 1) WHITE)
	 (draw-solid-disk (bulb-center YEL:BULB) 
			  (- RADIUS 1) WHITE)
	 (draw-solid-disk (bulb-center RED:BULB) 
			  RADIUS (bulb-color RED:BULB)))
	 (else (error "failed to match color.")
	       (stop))))

;nextbulb set!'s the next bulb in the sequence.
(define (nextbulb!)
  (cond ((eq? (bulb-color bulb) (bulb-color GRN:BULB))
	 (set! bulb YEL:BULB))
        ((eq? (bulb-color bulb) (bulb-color YEL:BULB))
	 (set! bulb RED:BULB))
	((eq? (bulb-color bulb) (bulb-color RED:BULB))
	 (set! bulb GRN:BULB))
	(else (set! bulb GRN:BULB))))

;predefine the bulb in the global environment
(define bulb GRN:BULB)

;this is just a time waster so light doesn't scroll too fast
(define (donothing times)
  (if (< times 0)
      #t
      (donothing (- times 1))))
;this utilized nextbulb! and drawbulbs to set/show the next light in sequence.
(define (next!)
  (donothing 50000)
  (nextbulb!)
  (drawbulbs))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;       This is the file types.scm
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
(define false #f)
;;; The bottom level typing system

(define attach-tag cons)

(define (type-tag datum)
  (if (pair? datum)
      (car datum)
      (error "Bad typed datum -- TYPE-TAG" datum)))

(define (contents datum)
  (if (pair? datum)
      (cdr datum)
      (error "Bad typed datum -- CONTENTS" datum)))


;;; The apply-generic mechanism.  
;;;  Note that we don't deal with coercion here.

(define (apply-generic op . args)
  (let ((type-tags (map type-tag args)))
    (let ((proc (get op type-tags)))
      (if proc
	  (apply proc (map contents args))
	  (error "No method for the given types -- APPLY-GENERIC"
		 (list op type-tags))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;; Code for creating the table, YOU DON'T NEED TO WORRY ABOUT THIS.
;;; We will cover this material in Chapter 3.
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(define (make-table)
  (let ((local-table (list '*table*)))

    (define (lookup key-1 key-2)
      (let ((subtable (assoc key-1 (cdr local-table))))
        (if subtable
            (let ((record (assoc key-2 (cdr subtable))))
              (if record
                  (cdr record)
                  false))
            false)))

    (define (insert! key-1 key-2 value)
      (let ((subtable (assoc key-1 (cdr local-table))))
        (if subtable
            (let ((record (assoc key-2 (cdr subtable))))
              (if record
                  (set-cdr! record value)
                  (set-cdr! subtable
                            (cons (cons key-2 value)
                                  (cdr subtable)))))
            (set-cdr! local-table
                      (cons (list key-1
                                  (cons key-2 value))
                            (cdr local-table)))))
      'ok)

    (define (dispatch m)
      (cond ((eq? m 'lookup-proc) lookup)
            ((eq? m 'insert-proc!) insert!)
            (else (error "Unknown operation -- TABLE" m))))

    dispatch))


(define operation-table (make-table))
(define get (operation-table 'lookup-proc))
(define put (operation-table 'insert-proc!))

(define (install-wine-package)
  ;;internal proc's
  (define (price thing) (car thing))
  (define (name thing) (cadr thing))
  (define (type thing) (caddr thing))
  (define (sweetness thing) (cadddr thing))
  (define (make-food price name type sweetness)
    (list price name type sweetness))

;;Interface to the rest of the system
  (define (tag x) (attach-tag 'wine x))
  (put 'price '(wine) price)
  (put 'name '(wine) name)
  (put 'type '(wine) type)
  (put 'sweetness '(wine) sweetness)
  (put 'make-food 'wine
       (lambda (p n t s)
	 (tag (make-food p n t s))))
  'DONE)

(define (install-cheese-package)
  (define (price thing) (car thing))
  (define (name thing) (cadr thing))
  (define (type thing) (caddr thing))
  (define (make-food price name type)
    (list price name type))

;;Interface to the rest of the system
  (define (tag x) (attach-tag 'cheese x))
  (put 'price '(cheese) price)
  (put 'name '(cheese) name)
  (put 'type '(cheese) type)
  (put 'make-food 'cheese
       (lambda (p n t)
	 (tag (make-food p n t))))
  'DONE)
	
;; External interface for wine/cheese data types
(define (price thing) (apply-generic 'price thing))
(define (name thing) (apply-generic 'name thing))
(define (type thing) (apply-generic 'type thing))
(define (sweetness thing) (apply-generic 'sweetness thing))
(define (make-cheese price name type) ((get 'make-food 'cheese) price name type))
(define (make-wine price name type sweetness) ((get 'make-food 'wine) price name type sweetness))
;generic print function print-list.
(define (print-list foodlist)
  (display "name             price")
  (newline)
  (print-list-helper foodlist))
;this function allows us to print the headers name and price without 
;reprinting them every function call =)
(define (print-list-helper foodlist)
  (cond ((null? foodlist)
	 (newline))
	(else (display (name (car foodlist))) 
	      (display "        ")
	      (display (price (car foodlist))) 
	      (newline)
	      (print-list-helper (cdr foodlist)))))

;compatible? is not included in a class, due to the errors that result.
(define (compatible? thing1 thing2)
  (cond ((and (iswine? thing1) (iswine? thing2))
	 (if (eq? (sweetness thing1) (sweetness thing2))
	     #t
	     #f))
	((and (ischeese? thing1) (ischeese? thing2))
	 #t)
	((iswine? thing1)
	 (if (or (and (eq? (type thing1) 'white) (eq? (type thing2) 'mild))
		 (and (eq? (type thing1) 'red) (eq? (type thing2) 'blue)))
	     #t
	     #f))
	((iswine? thing2)
	 (if (or (and (eq? (type thing1) 'mild) (eq? (type thing2) 'white))
		 (and (eq? (type thing1) 'blue) (eq? (type thing2) 'red)))
	     #t
	     #f))))  

(define (ischeese? thing)
  (or (eq? (type thing) 'blue) (eq? (type thing) 'mild) 
      (eq? (type thing) 'smoked) (eq? (type thing) 'sharp)))

(define (iswine? thing)
  (or (eq? (type thing) 'red) (eq? (type thing) 'white)))
