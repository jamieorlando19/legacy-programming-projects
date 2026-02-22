(define flip
  (let ((num 0))
    (lambda ()
      (begin (if (= num 0)
               (set! num 1)
               (set! num 0))
              num))))

(define (make-flip)
  (let ((num 0))
    (lambda ()
       (begin (if (= num 0)
                (set! num 1)
                (set! num 0))
               num))))

