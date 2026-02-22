;;; Scheme code for Twenty-One Simulator [HW3]

(define twenty-one
  (lambda (player-strategy house-strategy)
    (let ((house-initial-hand (make-new-hand (deal))))
      (let ((player-hand
	     (play-hand player-strategy
			(make-new-hand (deal))
			(hand-up-card house-initial-hand))))
	(if (> (hand-total player-hand) 21)
	    0                                ; ``bust'': player loses
	    (let ((house-hand 
		   (play-hand house-strategy
			      house-initial-hand
			      (hand-up-card player-hand))))
	      (cond ((> (hand-total house-hand) 21)
		     1)                      ; ``bust'': house loses
		    ((> (hand-total player-hand)
			(hand-total house-hand))
		     1)                      ; house loses
		    (else 0))))))))           ; player loses

(define play-hand 
  (lambda (strategy my-hand opponent-up-card)
    (cond ((> (hand-total my-hand) 21) my-hand) ; I lose... give up
	  ((strategy my-hand opponent-up-card) ; hit?
	   (play-hand strategy
		      (hand-add-card my-hand (deal))
		      opponent-up-card))
	  (else my-hand))))                ; stay


(define deal
  (lambda ()
    (+ 1 (random 10))))

(define make-new-hand
  (lambda (first-card)
    (make-hand first-card first-card)))

(define make-hand
  (lambda (up-card total)
    (cons up-card total)))

(define hand-up-card
  (lambda (hand)
    (car hand)))

(define hand-total
  (lambda (hand)
    (cdr hand)))

(define hand-add-card
  (lambda (hand new-card)
    (make-hand (hand-up-card hand)
	       (+ new-card (hand-total hand)))))

(define hit?
  (lambda (your-hand opponent-up-card)
    (newline)
    (display "Opponent up card ")
    (display opponent-up-card)
    (newline)
    (display "Your Total: ")
    (display (hand-total your-hand))
    (user-says-y-v100?)))

(define user-says-y-v52?
  (lambda () (mred:get-choice "" "Hit Me" "Stay" "Hit?")))

(define user-says-y-v100?
  (lambda () (= (car (get-choices-from-user 
		      "Choose One" 
		      #f
		      '("Hit Me" "Stay")
		      #f
		      '(0)
		      '(single)))
                0)))

(define stupid-strategy
  (lambda (my-hand opponent-up-card)
    (> opponent-up-card 5)))

(define stop-at-16
  (lambda (my-hand opponent-up-card)
    (< (hand-total my-hand) 16)))

(define stop-at
  (lambda (stop)
     (lambda (my-hand opponent-up-card)
         (< (hand-total my-hand) stop))))

(define test-strategy
  (lambda (player-strategy house-strategy count)
    (if (= count 0)
        0
        (+ (twenty-one player-strategy house-strategy) 
           (test-strategy player-strategy house-strategy (- count 1))))))

(define watch-player 
  (lambda (strategy)
    (lambda (my-hand opponent-hand)
    (newline)
    (display "Opponent up card ")
    (display opponent-hand)
    (newline) 
    (display "Your Total: ")
    (display (hand-total my-hand))
    (strategy my-hand opponent-hand))))

(define louis
  (lambda (my-hand opponent-up-card)
    (cond ((< (hand-total my-hand) 12)     #T)
          ((> (hand-total my-hand) 16)     #F)
          ((= (hand-total my-hand) 12) 
              (if (< opponent-up-card 4)   #T
				           #F))
          ((= (hand-total my-hand) 16) 
              (if (= opponent-up-card 10)  #T
				           #F))
          (else 
               (if (> opponent-up-card 6)  #T
		                           #F)))))

(define both
  (lambda (strategy-1 strategy-2)
    (lambda (my-hand opponent-hand)
      (if (strategy-1 my-hand opponent-hand) 
	  (if (strategy-2 my-hand opponent-hand)
	      #T
	      #F)
	  #F))))
          