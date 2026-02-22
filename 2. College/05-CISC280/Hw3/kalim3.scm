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
  (lambda (my-hand opponent-up-card)
    (newline)
    (display "Opponent up card ")
    (display opponent-up-card)
    (newline) 
    (display "Your Total: ")
    (display (hand-total my-hand))
    (user-says-y?)))

(define user-says-y?
  (lambda () (mred:get-choice "" "Hit Me" "Stay" "Hit?")))

;stop-at is a primitive strategy, (yet better at blackjack 
;than louis), which decides to hit only if the total of its
;hand is less than the input "stop"

(define (stop-at stop)
  (lambda (my-hand opponent-up-card)
    (< (hand-total my-hand) stop)))

;test-strategy runs times # of times, and determines, and 
;then returns, the number of times player-strat beats 
;the house-strat

(define test-strategy
  (lambda (player-strat house-strat times)
    (if (= times 0)
	0
	(+ (twenty-one player-strat house-strat) 
	   (test-strategy player-strat house-strat 
			  (- times 1))))))

;watch-player adds to strat inputed and causes it
;to display the information it's using to make its decisions.
(define watch-player 
  (lambda (strat)
    (lambda (my-hand opponent-hand)
    (newline)
    (display "Opponent up card ")
    (display opponent-hand)
    (newline) 
    (display "Your Total: ")
    (display (hand-total my-hand))
    (strat my-hand opponent-hand))))

;this is louis' strategy... he's not very good at blackjack.
;the exact strat is explained in the hw description.
(define louis
  (lambda (my-hand opponent-hand)
    (cond ((< (hand-total my-hand) 12) #T)
	  ((= (hand-total my-hand) 12) (if (< opponent-hand 4) 
					  #T
					  #F))
	  ((> (hand-total my-hand) 16) #F)
	  ((= (hand-total my-hand) 16) (if (= opponent-hand 10)
					  #T
					  #F))
	  (else (if (> opponent-hand 6)
		    #T
		    #F)))))
;this returns true if and only if both strat1 and strat2 
;would decide to hit.		
(define both
  (lambda (strat1 strat2)
    (lambda (my-hand opponent-hand)
      (if (strat1 my-hand opponent-hand) 
	  (if (strat2 my-hand opponent-hand)
	      #T
	      #F)
	  #F))))
              
	 
	   
  