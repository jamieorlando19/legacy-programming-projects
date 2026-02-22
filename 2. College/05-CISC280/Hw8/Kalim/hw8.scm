;Kalim Oldziey, homework#8

;;;---------------------------------
;;; Note: this is the "correct" definition.
;;;(define (parallel-execute . args)
;;;  (map thread args))

;;; This definition makes it much more likely that the threads are interleaved in processing.
(define (parallel-execute . args)
  (map (lambda (thread) (thread-weight thread 1)) (map thread args)))

(define (make-serializer)
  (let ((mutex (make-mutex)))
    (lambda (p)
      (define (serialized-p . args)
	(mutex 'acquire)
	(let ((value (apply p args)))
	  (mutex 'release)
	  value))
      serialized-p)))

(define (make-mutex)
  (let ((cell (make-semaphore 1)))
    (define (the-mutex m)
      (cond ((eq? m 'acquire)
	     (semaphore-wait cell))
	    ((eq? m 'release)
	     (semaphore-post cell))))
    the-mutex))

;;;------------------------------------------------

(define *SEATS* 100)
;Make-Ticket-Seller Modified for question 1

(define (make-ticket-seller)
  (let ((total-tickets-sold 0)
	(customer-order 0))
    (define (sell)      
      (cond ((not (> *SEATS* 0))
	     ((print-tickets-sold total-tickets-sold)))
	    ((not (> customer-order 0))
	     (sleep (random 2))
	     (set! customer-order (+ 1 (random 3)))
	     (sell))
	    (((can-sell? customer-order))
	     (set! total-tickets-sold (+ customer-order total-tickets-sold))
	     (set! customer-order 0)
	     (sell))
	    (else 
	     (set! customer-order 0)
	     (sell))))
    sell))
;can-sell? necessary for implementation of new 
;make-ticket-seller procedure. Checks to see if
;customer-order is legitimate w/ regards to the
;current number of seats remaining.

(define (can-sell? customer-order)
  (sell-serializer (lambda ()
	     (cond ((> customer-order *SEATS*)
		    #f)
		   (else (set! *SEATS* (- *SEATS* customer-order)))))))
;fixed print-tickets by adding
;serializer "display-serializer"
(define (print-tickets-sold n)
  (display-serializer (lambda ()
			(display "I sold ")
			(display n)
			(display " tickets")
			(newline))))

		      
(define (test) 
  (parallel-execute (make-ticket-seller) 
		    (make-ticket-seller) 
		    (make-ticket-seller) 
		    (make-ticket-seller) 
		    (make-ticket-seller) 
		    (make-ticket-seller) 
		    (make-ticket-seller)
		    (make-ticket-seller)
		    (make-ticket-seller)
		    (make-ticket-seller)))

;defines 2 serializers so that the parrellel execution
;of the functions commences properly.

(define sell-serializer (make-serializer))
(define display-serializer (make-serializer))
;;;--------------------------------------------------------
;;; STREAMS

(define (stream-car stream) (car stream))
(define (stream-cdr stream) (force (cdr stream)))
(define-macro cons-stream 
  (lambda (a b) `(cons ,a (delay ,b))))
(define stream-null? null?)
(define the-empty-stream null)

(define (stream-ref s n)
  (if (= n 0)
      (stream-car s)
      (stream-ref (stream-cdr s) (- n 1))))

(define (stream-map proc s)
  (if (stream-null? s)
      the-empty-stream
      (cons-stream (proc (stream-car s))
                   (stream-map proc (stream-cdr s)))))

(define (stream-for-each proc s)
  (if (stream-null? s)
      'done
      (begin (proc (stream-car s))
             (stream-for-each proc (stream-cdr s)))))

(define (stream-enumerate-interval low high)
  (if (> low high)
      the-empty-stream
      (cons-stream
       low
       (stream-enumerate-interval (+ low 1) high))))

(define (stream-filter pred stream)
  (cond ((stream-null? stream) the-empty-stream)
        ((pred (stream-car stream))
         (cons-stream (stream-car stream)
                      (stream-filter pred
                                     (stream-cdr stream))))
        (else (stream-filter pred (stream-cdr stream)))))

;;; Printing STREAMS
(define (display-stream s)
  (stream-for-each display-line s))

(define (display-line x)
  (newline)
  (display x))

(define display-stream-2
  (let ()
    (define (iter stream)
      (if (stream-null? stream)
	  (display "]")
	  (begin (display (stream-car stream))
		 (display " ")
		 (iter (stream-cdr stream)))))
    (lambda (stream)
      (display "[")
      (iter stream))))

;;Useful for looking at finite amounts of infinite streams
;;Print the first n elements of the stream s.
;;One version prints on one line, one on separate lines

(define (stream-print-n s n)
  (if (> n 0)
      (begin (display (stream-car s))
             (display ",")
             (stream-print-n (stream-cdr s) (- n 1)))))

(define (stream-print-n-seperate s n)
  (if (> n 0)
      (begin (newline)
	     (display (stream-car s))
             (stream-print-n-seperate (stream-cdr s) (- n 1)))))

;;; Some useful testing functions

(define (tty-stream) (cons-stream (read) (tty-stream)))


	
;Question 2b
(define (list->stream list)
  (if (not (null? list))
      (cons-stream (car list) (list->stream (cdr list)))
      null))

;Question 4
(define (remove-duplicates stream)
  (if (stream-null? stream)
      the-empty-stream
      (let ((1stelt (stream-car stream)))
	(cons-stream 1stelt
		     (stream-filter (lambda (x) (not (eq? x 1stelt))) 
				    (remove-duplicates (stream-cdr stream)))))))

;Question 5
;calls proc with all the first elt's of the streams, then again with
;all the 2nd elt's of the streams, etc...

(define (stream-map-new proc . argstreams)
  (if (stream-null? (car argstreams))
      the-empty-stream
      (cons-stream
       (apply proc (map stream-car argstreams))
       (apply stream-map-new (cons proc (map stream-cdr argstreams))))))

