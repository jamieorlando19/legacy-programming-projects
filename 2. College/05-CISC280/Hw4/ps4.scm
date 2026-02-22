;;;SECTION 2.1.4

(define (add-interval x y)
  (make-interval (+ (lower-bound x) (lower-bound y))
                 (+ (upper-bound x) (upper-bound y))))

(define (mul-interval x y)
  (let ((p1 (* (lower-bound x) (lower-bound y)))
        (p2 (* (lower-bound x) (upper-bound y)))
        (p3 (* (upper-bound x) (lower-bound y)))
        (p4 (* (upper-bound x) (upper-bound y))))
    (make-interval (min p1 p2 p3 p4)
                   (max p1 p2 p3 p4))))

(define (div-interval x y)
  (if (or (= 0 (lower-bound y)) (= 0 (upper-bound y)))
      (error ("Divide by 0"))
      (mul-interval x (make-interval (/ 1.0 (upper-bound y))
                                     (/ 1.0 (lower-bound y))))))

;; EXERCISE 2.7

(define (make-interval a b) (list a b))

(define (lower-bound x) (car x))
(define (upper-bound x) (cadr x))


;;;SECTION 2.1.4 again

(define (make-center-width c w)
  (make-interval (- c w) (+ c w)))

(define (center i)
  (/ (+ (lower-bound i) (upper-bound i)) 2))

(define (width i)
  (/ (- (upper-bound i) (lower-bound i)) 2))

;; parallel resistors

(define (par1 r1 r2)
  (div-interval (mul-interval r1 r2)
                (add-interval r1 r2)))

(define (par2 r1 r2)
  (let ((one (make-interval 1 1))) 
    (div-interval one
                  (add-interval (div-interval one r1)
                                (div-interval one r2)))))

(define (print-in-bounds x)
  (display "[")
  (display (lower-bound x))
  (display ", ")
  (display (upper-bound x))
  (display "]"))

(define (sub-interval x y)
  (make-interval (abs (- (lower-bound x) (lower-bound y)))
                 (abs (- (upper-bound x) (upper-bound y)))))

(define (print-int-center-percent x)
  (let ((mid (+ (/ ( - (upper-bound x) (lower-bound x)) 2.0) (lower-bound x))))
    (let ((per (* (- 1.0 (/ mid (upper-bound x))) 100)))
       (display "[")
       (display mid)
       (display " +- ")
       (display per)
       (display "%]"))))

;Iterative
(define (reverse list1)
  (define (reverse-iter new old)
    (if (null? old)
        new
        (reverse-iter (append (list(car old)) new) (cdr old))))
    (reverse-iter null list1))     
  
;Recursive
(define (reversed list1)
 (if (null? list1)
      null
      (append (reversed (cdr list1)) (list (car list1)))))

(define (palinedromep list1)
  (equal? list1 (reverse list1)))

(define (count-down-from n)
  (if (< n 1)
      null
      (append (list n) (count-down-from(- n 1)))))

(define (count-up-from n)
  (reverse(count-down-from n)))
  

(define (apply-all proclist input)
  (define (apply-all-2 procs in new)
     (if (null? procs)
         new
        (apply-all-2 (cdr procs) in (append new (list ((car procs) in))))))
  (apply-all-2 proclist input null))

(define (filter p s)
  (cond ((null? s) null)
        ((p (car s))
         (cons (car s)
               (filter p (cdr s))))
        (else (filter p (cdr s)))))

;Uses filter command from pg 115
(define (same-parity first . remaining)
  (if (odd? first)
       (append (list first) (filter odd? remaining))
       (append (list first) (filter even? remaining))))

(define (for-each proc items)
     (cond ((not (null? items))  (proc (car items))
                                               (for-each proc (cdr items)))))

(define (deep-reverse input)
  (cond ((null? input) null)
        ((not (pair? input)) (list input))
        (else 
             (reverse input)
             (append (deep-reverse (cdr input)) 
                     (deep-reverse (car input))))))

(define (fringe x)
  (cond ((null? x) null)
        ((not (pair? x)) (list x))
        (else (append (fringe (car x))
                      (fringe (cdr x))))))
  

