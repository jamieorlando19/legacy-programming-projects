;Jamie Orlando
;HOMEWORK #2
;CISC-280-010

;Returns the least significant digit
(define (units-digit n)
       (remainder n 10))

;Returns everything but the least significant digit
(define (all-but-units-digit n)
        (quotient n 10))

;#1
;Returns the amount of digits in the number (recursive)
(define decimal-length
  (lambda (n)
    (if (= (all-but-units-digit n) 0)
        1
        (+ (decimal-length (all-but-units-digit n)) 1))))

;#2
;Returns the ith digit of a number (iterative)
(define ith-digit
  (lambda (n i)
    (ith-digit-iter n i 0)))

;Iterative portion of previous function
(define ith-digit-iter
  (lambda (n i c)
    (if (= i c)
        (units-digit n)
        (ith-digit-iter (all-but-units-digit n) i (+ c 1)))))

;#3
;Returns the leading digit of a number
(define leading-digit
  (lambda (n)
    (ith-digit n (- (decimal-length n) 1))))

;#4
;Returns the occurances of a digit in a number (recursive)
(define occurances
  (lambda (d n)
    (cond ((and (= (units-digit n) 0) (= (all-but-units-digit n) 0)) 0)
          ((= (units-digit n) d) (+ (occurances d (all-but-units-digit n)) 1))
	  (else (occurances d (all-but-units-digit n))))))

;#5
;Returns the sum of the digits (recursive)
(define digit-sum
  (lambda (n)
    (if (= (all-but-units-digit n) 0)
        n
        (+ (digit-sum (all-but-units-digit n)) (units-digit n)))))

;#6
;Returns the sum of the digits of n until it is of length 1 (recursive)
(define digital-root
  (lambda (n)
    (if (= (decimal-length n) 1)
        n
        (digital-root (digit-sum n)))))

;#7
;When the function factorial is run, it goes into an infinite loop.
;This is because new-if will take things in parenthesis and evaluate them
;before putting it in its cond structure.  'If' is a special case which 
;wont do this however.

;#8
(define expt
  (lambda (x n)
    (expt-iter x n 1)))

(define expt-iter
  (lambda (x counter product)
    (if(= counter 0)
       product
       (expt-iter x
                  (- counter 1)
                  (* x product)))))

(define fast-expt
  (lambda (x n)
    (cond ((= n 0) 1)
          ((even? n) (square (fast-expt x (/ n 2))))
          (else (* x (fast-expt x (- n 1)))))))

(define (square x) (* x x))

(define even?
  (lambda (x)
    (= (remainder x 2) 0)))

