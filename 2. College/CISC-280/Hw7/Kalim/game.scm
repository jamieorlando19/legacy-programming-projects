;;; ---------------------------------------------------------------------------
;;; Simple object system with inheritance
(define true #t)
(define false #f)

(define (ask object message . args)  ;; See page 104 to explain "."
  (let ((method (get-method object message)))
    (if (method? method)
	(apply method (cons object args))
	(error "No method" message (cadr method)))))

(define (get-method object message)
  (object message))

(define (no-method name)
  (list 'no-method name))

(define (method? x)
  (not (no-method? x)))

(define (no-method? x)
  (if (pair? x)
      (eq? (car x) 'no-method)
      false))

;Pre-defined symbols
(define stolen-cards '())
;;; ----------------------------------------------------------------------------
;;; Persons, places, and things will all be kinds of named objects

(define (make-named-object name)
  (lambda (message) 
    (cond ((eq? message 'name) (lambda (self) name))
	  (else (no-method name)))))

;helper procs for idcells, used in make-watcher
(define (makeidcell time id)
  (list time id))
(define (gettime idcell)
  (car idcell))
(define (getid idcell)
  (cadr idcell))
(define (eqcell? cell1 cell2)
  (and (eq? (gettime cell1) (gettime cell2))
       (eq? (getid cell1) (getid cell2))))

(define (make-watcher-object name)
  (let ((idlist '())
	(named-obj (make-named-object name)))
    (lambda (message)
      (cond ((eq? message 'inform) 
	     (lambda (self id)
	       (if (cellinlist? (makeidcell (current-time) id) idlist)
		   (report-stolen-id id)
		   (display "Legit ID, please continue."))
	       (set! idlist (append (list (makeidcell (current-time) id)) idlist))))
	    ((eq? message 'idlist)
	     (lambda (self)
	       idlist))
	    (else (get-method named-obj message))))))
	       
;;; Persons and things are mobile since their places can change

(define (make-mobile-object name location)
  (let ((named-obj (make-named-object name)))
    (lambda (message)
      (cond ((eq? message 'place)    (lambda (self) location))
	    ((eq? message 'install)
	     (lambda (self)
	       (ask location 'add-thing self)))	; Synchonize thing and place
	    ;; Following method should never be called by the user...
	    ;;  it is a system-internal method.
	    ;; See CHANGE-PLACE instead
	    ((eq? message 'set-place)
	     (lambda (self new-place)
	       (set! location new-place)
	       'place-set))
	    (else (get-method named-obj message))))))

(define (make&install-mobile-object name place)
  (let ((mobile-obj (make-mobile-object name place)))
    (ask mobile-obj 'install)
    mobile-obj))

;;; A thing is something that can be owned

(define (make-thing name birthplace)
  (let ((owner     'nobody)
	(mobile-obj (make-mobile-object name birthplace)))
    (lambda (message)
      (cond ((eq? message 'owner)    (lambda (self) owner))
	    ((eq? message 'ownable?) (lambda (self) true))
	    ((eq? message 'owned?)
	     (lambda (self)
	       (not (eq? owner 'nobody))))
	    ((eq? message 'birthplace) (lambda (self) birthplace))
	    ;; Following method should never be called by the user...
	    ;;  it is a system-internal method.
	    ;; See TAKE and LOSE instead.
	    ((eq? message 'set-owner)
	     (lambda (self new-owner)
	       (set! owner new-owner)
	       'owner-set))
	    (else (get-method mobile-obj message))))))

(define (make&install-thing name birthplace)	
  (let ((thing  (make-thing name birthplace)))
    (ask thing 'install)
    thing))

;;; Implementation of places

(define (make-place name)
  (let ((neighbor-map '())
	(things       '())
	(named-obj (make-named-object name)))
    (lambda (message)
      (cond ((eq? message 'things) (lambda (self) things))
	    ((eq? message 'neighbors)
	     (lambda (self) (map cdr neighbor-map)))
	    ((eq? message 'exits)
	     (lambda (self) (map car neighbor-map)))
	    ((eq? message 'neighbor-towards)
	     (lambda (self direction)
	       (let ((places (assq direction neighbor-map)))
		 (if places
		     (cdr places)
		     false))))
            ((eq? message 'add-neighbor)
             (lambda (self direction new-neighbor)
               (cond ((assq direction neighbor-map)
                      (display-message (list "Direction already assigned"
					      direction name))
		      false)
                     (else
                      (set! neighbor-map
                            (cons (cons direction new-neighbor) neighbor-map))
		      true))))
	    ((eq? message 'accept-person?)
	     (lambda (self person)
	       true))
 
	    ;; Following two methods should never be called by the user...
	    ;;  they are system-internal methods. See CHANGE-PLACE instead.
            ((eq? message 'add-thing)
             (lambda (self new-thing)
               (cond ((memq new-thing things)
                      (display-message (list (ask new-thing 'name)
					     "is already at" name))
		      false)
                     (else (set! things (cons new-thing things))
			   true))))
            ((eq? message 'del-thing)
             (lambda (self thing)
               (cond ((not (memq thing things))
                      (display-message (list (ask thing 'name)
					     "is not at" name))
		      false)
                     (else (set! things (delq thing things))	;; DELQ defined
			   true))))                             ;; below

            (else (get-method named-obj message))))))

;implementation of sd-card locked places, as per excercise 4
(define (make-card-locked-place name)
  (let ((place (make-place name)))
    (lambda (message)
      (cond ((eq? message 'accept-person?)
	     (lambda (self person)
	       (not (null? (filter  (lambda (o) (is-a o 'sd-card?)) (ask person 'possessions))))))
	    (else (get-method place message))))))

;implementation of student locked residences, as per bonus excercise 1
(define (make-student-residence name)
  (let ((place (make-place name))
	(card-list '()))
    (lambda (message)
      (cond ((eq? message 'card-list)
	     (lambda (self) card-list))
	    ((eq? message 'accept-person?)
	     (lambda (self person)
	       (let ((listids (map (lambda (o) (ask o 'id))
				  (filter (lambda (o) (is-a o 'sd-card?))
					  (ask person 'possessions)))))
	       (cond ((inlist? listids card-list)
		      (ask big-brother 'inform (inlist-id listids card-list))
		      true)
		     (else false)))))
	    ((eq? message 'register-card)
	     (lambda (self card)
	       (if (eq? self (ask card 'birthplace))
				  (set! card-list (append (list (ask card 'id)) card-list))
				  (display "failed to register:bad birthplace"))))
	    (else (get-method place message))))))

	       


;;; -----------------------------------------------------------------------
;;; Implementation of people

(define (make-person name birthplace threshold)
  (let ((possessions '())
	(mobile-obj  (make-mobile-object name birthplace)))
    (lambda (message)
      (cond ((eq? message 'person?)     (lambda (self) true))
	    ((eq? message 'possessions) (lambda (self) possessions))
	    ((eq? message 'list-possessions)
	     (lambda (self)
	       (ask self 'say
		    (cons "I have"
			  (if (null? possessions)
			      '("nothing")
			      (map (lambda (p) (ask p 'name))
				      possessions))))
	       possessions))
	    ((eq? message 'emote)
	     (lambda (self list-of-stuff)
	       (display-message
		(append (list "At" (ask (ask self 'place) 'name)
			      ":" name)
			(if (null? list-of-stuff)
			    '(just stands there, doing nothing.)
			    list-of-stuff)))))
	    ((eq? message 'say)
	     (lambda (self list-of-stuff)
	       (display-message
		 (append (list "At" (ask (ask self 'place) 'name)
			       ":"  name "says --")
			 (if (null? list-of-stuff)
			     '("Oh, nevermind.")
			     list-of-stuff)))
	       'said))
	    ((eq? message 'have-fit)
	     (lambda (self)
	       (ask self 'say '("Yaaaah! I am upset!"))
	       'I-feel-better-now))
	    ((eq? message 'look-around)
	     (lambda (self)
	       (let ((other-things
		       (map (lambda (thing) (ask thing 'name))
                               (delq self                       ;; DELQ
                                     (ask (ask self 'place)     ;; defined
                                          'things)))))          ;; below
                 (ask self 'say (cons "I see" (if (null? other-things)
						  '("nothing")
						  other-things)))
		 other-things)))

	    ((eq? message 'take)
	     (lambda (self thing)
	       (cond ((memq thing possessions)
		      (ask self 'say
			   (list "I already have" (ask thing 'name)))
		      true)
		     ((and (let ((things-at-place (ask (ask self 'place) 'things)))
			     (memq thing things-at-place))
			   (is-a thing 'ownable?))
		      (if (ask thing 'owned?)
			  (let ((owner (ask thing 'owner)))
			    (ask owner 'lose thing)
			    (ask owner 'have-fit))
			  'unowned)

		      (ask thing 'set-owner self)
		      (set! possessions (cons thing possessions))
		      (ask self 'say
			   (list "I take" (ask thing 'name)))
		      true)
		     (else
		      (display-message
		       (list "You cannot take" (ask thing 'name)))
		      false))))
	    ((eq? message 'lose)
	     (lambda (self thing)
	       (cond ((eq? self (ask thing 'owner))
		      (set! possessions (delq thing possessions)) ;; DELQ
		      (ask thing 'set-owner 'nobody)              ;; defined
		      (ask self 'say                              ;; below
			   (list "I lose" (ask thing 'name)))
		      true)
		     (else
		      (display-message (list name "does not own"
					     (ask thing 'name)))
		      false))))
	    ((eq? message 'move)
	     (lambda (self)
	       (cond ((= (random threshold) 0)
		      (ask self 'act)
		      true)
		     (else true))))
	    ((eq? message 'act)
	     (lambda (self)
	       (let ((new-place (random-neighbor (ask self 'place))))
		 (if new-place
		     (ask self 'move-to new-place)
		     false))))		; All dressed up and no place to go

	    ((eq? message 'move-to)
	     (lambda (self new-place)
	       (let ((old-place (ask self 'place)))
		 (cond ((eq? new-place old-place)
			(display-message (list name "is already at"
					       (ask new-place 'name)))
			false)
		       ((ask new-place 'accept-person? self)
			(change-place self new-place)
			(for-each (lambda (p) (change-place p new-place))
				  possessions)
			(display-message
			  (list name "moves from" (ask old-place 'name)
				     "to"         (ask new-place 'name)))
			(greet-people self (other-people-at-place self new-place))
			true)
		       (else
			(display-message (list name "can't move to"
					       (ask new-place 'name))))))))
	    ((eq? message 'go)
	     (lambda (self direction)
	       (let ((old-place (ask self 'place)))
		 (let ((new-place (ask old-place 'neighbor-towards direction)))
		   (cond (new-place
			  (ask self 'move-to new-place))
			 (else
			  (display-message (list "You cannot go" direction
						 "from" (ask old-place 'name)))
			  false))))))
	    ((eq? message 'install)
	     (lambda (self)
	       (add-to-clock-list self)
	       ((get-method mobile-obj 'install) self)))
	    (else (get-method mobile-obj message))))))
  
(define (make&install-person name birthplace threshold)
  (let ((person (make-person name birthplace threshold)))
    (ask person 'install)
    person))
;for teachers
(define (make-teacher name birthplace threshold)
  (let ((person (make-person name birthplace threshold)))
    (lambda (message)
      (cond ((eq? message 'act)
	     (lambda (self)
	       (let ((others (other-people-at-place self (ask self 'place))))
		 (if (not (null? others))
		     (ask self 'scold (pick-random others))
		     ((get-method person 'act) self)))))
	    ((eq? message 'scold)
	     (lambda (self person)
	       (ask self 'say '(You should be taking notes))
;temporarily	    (ask person 'name) "! Rot in jail until you learn."))
;disabled	  (go-to-jail person)
;for	       (ask self 'say
;playability    (list "They should have been taking notes."))
;purposes      (ask self 'emote '(shrugs nonchalantly))
	       '*cackle*))
	    (else (get-method person message))))))

(define (make&install-teacher name birthplace threshold)
  (let ((teacher  (make-teacher name birthplace threshold)))
    (ask teacher 'install)
    teacher))

(define (make-student name birthplace threshold)
  (let ((person (make-person name birthplace threshold)))
    (lambda (message)
      (cond ((eq? message 'act)
	     (lambda (self)	    
	       (cond ((not (null? (ask (ask self 'place) 'things)))
		      (let ((things-at-place 
			     (filter (lambda (o) (not (is-a o 'owned?)))
				     (filter (lambda (o) (is-a o 'ownable?))
					     (ask (ask self 'place) 'things)))))
			(if (not (null? things-at-place))
			    (ask self 'take (pick-random things-at-place))
			    ((get-method person 'act) self))))
		     (else
		      (cond ((not (null? (ask self 'possessions)))
			     (let ((target (pick-random (ask self 'possessions))))
			       (cond ((is-a target 'sd-card?)
				      (cond ((= (random 2) 0)
					     (copy-sd-card target)
					     ((get-method person 'act) self))
					    (else (ask self 'lose target))))
				     (else (ask self 'lose target)))))
			    (else (ask self 'emote '(sulks angrily, unable to 
							   take or drop anything.))
				  ((get-method person 'act) self)))))))
	    (else (get-method person message))))))

(define (make&install-student name birthplace threshold)
  (let ((student (make-student name birthplace threshold)))
    (ask student 'install)
    student))

;;; A troll is a kind of person (but not a kind person!)

(define (make-troll name birthplace threshold)
  (let ((person (make-person name birthplace threshold)))
    (lambda (message)
      (cond ((eq? message 'act)
	     (lambda (self)
	       (let ((others (other-people-at-place self (ask self 'place))))
		 (if (not (null? others))
		     (ask self 'eat-person (pick-random others))
		     ((get-method person 'act) self)))))
	    ((eq? message 'eat-person)
	     (lambda (self person)
	       (ask self 'say
		    (list "Growl.... I'm going to eat you,"
			  (ask person 'name)))
	       (go-to-heaven person)
	       (ask self 'say
		    (list "Chomp chomp." (ask person 'name)
			  "tastes yummy!"))
	       '*burp*))
	    (else (get-method person message))))))

(define (make&install-troll name birthplace threshold)
  (let ((troll  (make-troll name birthplace threshold)))
    (ask troll 'install)
    troll))

;ogres eat only people who are wandering around with stolen id cards.
(define (make-ogre name)
  (let ((person (make-person name dungeon 1)))
    (lambda (message)
      (cond ((eq? message 'act)
	     (lambda (self)
	       (let ((others (other-people-at-place self (ask self 'place)))
		     )
		 (let ((target (pick-random others)))
		 (if (not (null? others))
		     (cond ((stolen? (map (lambda (o) (ask o 'id))
				       (filter (lambda (o) (is-a o 'sd-card?))
					       (ask target 'possessions))))
			    (ask self 'eat-person target))
			   (else (ask self 'say (list "U r luhgit, " (ask target 'name) 
						      ", mi no eet u."))))
		     ((get-method person 'act) self))))))
	    ((eq? message 'eat-person)
	     (lambda (self person)
	       (ask self 'say
		    (list "Growl.... I'm going to eat you, "
			  (ask person 'name) ", you stinking cardthief!"))
	       (go-to-heaven person)
	       (ask self 'say
		    (list "Chomp chomp." (ask person 'name)
			  "tastes yummy!"))
	       '*burp*))
	    (else (get-method person message))))))

(define (make&install-ogre name)
  (let ((ogre  (make-ogre name)))
    (ask ogre 'install)
    ogre))

(define (go-to-jail person)
  (ask person 'say '("Ghack! Anything but Udel High Security Prison!!"))
  (ask person 'move-to gaol)
  'busted!)

(define (go-to-heaven person)
  (for-each (lambda (item) (ask person 'lose item))
	    (ask person 'possessions))
  (ask person 'say
       '("
                   Dulce et decorum est 
                   pro computatore mori!"
	 ))
  (ask person 'move-to heaven)
  (remove-from-clock-list person)
  'game-over-for-you-dude)

(define heaven (make-place 'heaven))		; The point of no return

;;; --------------------------------------------------------------------------
;;; Clock routines

(define *clock-list* '())
(define *the-time* 0)

(define (initialize-clock-list)
  (set! *clock-list* '())
  'initialized)

(define (add-to-clock-list person)
  (set! *clock-list* (cons person *clock-list*))
  'added)

(define (remove-from-clock-list person)
  (set! *clock-list* (delq person *clock-list*))  ;; DELQ defined below
  'removed)

(define (clock)
  (newline)
  (display "---Tick---")
  (set! *the-time* (+ *the-time* 1))
  (for-each (lambda (person) (ask person 'move))
	    *clock-list*)
  'tick-tock)
	     

(define (current-time)
  *the-time*)

(define (run-clock n)
  (cond ((zero? n) 'done)
	(else (clock)
	      (run-clock (- n 1)))))

;;; --------------------------------------------------------------------------
;;; Miscellaneous procedures

(define (is-a object property)
  (let ((method (get-method object property)))
    (if (method? method)
	(ask object property)
	false)))

(define (change-place mobile-object new-place)	; Since this bridges the gap
  (let ((old-place (ask mobile-object 'place))) ; between MOBILE-OBJECT and
    (ask mobile-object 'set-place new-place)	; PLACE, it is best it not
    (ask old-place 'del-thing mobile-object))	; be internal to either one.
  (ask new-place 'add-thing mobile-object)
  'place-changed)

(define (other-people-at-place person place)
  (filter (lambda (object)
	    (if (not (eq? object person))
		(is-a object 'person?)
		false))
	  (ask place 'things)))

(define (stolen? list)
  (inlist? list stolen-cards))
  
(define (report-stolen-id id)
  (set! stolen-cards (append (list id) stolen-cards))
  (let ((Enforcer (make&install-ogre 'Enforcer)))
    (ask Enforcer 'say '(Grrrrrrrrrrr..... I find theef dat stoled card en 'eet 'im!))
    ))

(define (report-stolen-card card)
  (report-stolen-id (ask card 'id)))
      
;for implementation of extra credit prob 1
;returns true if any ellement of list1 is in list2.
(define (inlist? list1 list2)
  (if (null? list1)
      #F
      (if (eltoflist? (car list1) list2)
	  #T
	  (inlist? (cdr list1) list2))))
;returns the id of the first ellement of list1 that
;is also in list2
(define (inlist-id list1 list2)
  (if (null? list1)
      null
      (if (eltoflist? (car list1) list2)
	  (car list1)
	  (inlist? (cdr list1) list2))))

      ;for implementing inlist.
(define (eltoflist? item list)
  (checkinlist? item list eq?))

;this function is much like eltoflist, 
;but works on cells.
(define (cellinlist? cell clist)
  (checkinlist? cell clist eqcell?))

;this is a broad function, for checking a list
;for an ellement, but allowing the user
;to choose which function is used.

(define (checkinlist? thing list eqfunct)
  (if (null? list)
      #F
      (if (eqfunct thing (car list))
	  #T
	  (checkinlist? thing (cdr list) eqfunct))))
  
(define (greet-people person people)
  (if (not (null? people))
      (ask person 'say
	   (cons "Hi"
		 (map (lambda (p) (ask p 'name))
			 people)))
      'sure-is-lonely-in-here))

(define (display-message list-of-stuff)
  (newline)
  (for-each (lambda (s) (display s) (display " "))
	    list-of-stuff)
  'message-displayed)

(define (random-neighbor place)
  (pick-random (ask place 'neighbors)))

(define (filter predicate lst)
  (cond ((null? lst) '())
	((predicate (car lst))
	 (cons (car lst) (filter predicate (cdr lst))))
	(else (filter predicate (cdr lst)))))

(define (pick-random lst)
  (if (null? lst)
      false
      (list-ref lst (random (length lst)))))  ;; See manual for LIST-REF

(define (delq item lst)
  (cond ((null? lst) '())
	((eq? item (car lst)) (delq item (cdr lst)))
	(else (cons (car lst) (delq item (cdr lst))))))

;;; -------------------------------------------------------------------
;;; Other interesting procedures

(define (make&install&reg-sd-card name birthplace id)
  (let ((card (make-sd-card name birthplace id)))
    (ask card 'install)
    (ask (ask card 'place) 'register-card card)
    card))

(define (make&reg-sd-card name birthplace id)
  (let ((card (make-sd-card name birthplace id)))
    (ask (ask card 'place) 'register-card card)))

(define (make&install-sd-card name birthplace id)
  (let ((card (make-sd-card name birthplace id)))
    (ask card 'install)
    card))

(define (make-sd-card name birthplace idnumber)
  (let ((id idnumber)
        (thing (make-thing name birthplace)))
    (lambda (message)
      (cond ((eq? message 'sd-card?) (lambda (self) true))
            ((eq? message 'id) (lambda (self) id))
	     (else (get-method thing message))))))


(define (copy-sd-card card)
  (let ((name   (symbol-append 'copy-of- (ask card 'name)))
	(place  (ask card 'place))
	(id     (ask card 'id)))
    (make&install-sd-card name place id)))

(define  (symbol-append sym1 sym2)
  (let* ((str1 (symbol->string sym1))
	 (str2 (symbol->string sym2)))
    (string->symbol (string-append str1 str2))))
    


;;;============================================================================
;;; You can extend this file to make more stuff part of your world.
;;;============================================================================

;;;============================================================================
;;; *CAVEAT* To avoid retyping long strings of commands,   
;;;          you should create little scripts at the  
;;;          end of this file to make the game evolve as you work through it.  
;;;          [See the bottom of this file for an example.]                     
;;;============================================================================

(initialize-clock-list)

;; Here we define the places in our world...
;;------------------------------------------
(define Greenhouse     (make-place 'Greenhouse))
(define dungeon        (make-place 'dungeon))
(define Smith-Hall     (make-place 'Smith-Hall))
(define computer-lab   (make-place 'computer-lab))
(define Trabant-Center (make-place 'Trabant-Center))
(define keith-office   (make-place 'keith-office))
(define errol-office   (make-place 'errol-office))
(define dormitory      (make-student-residence 'dormitory))
(define laird-campus (make-place 'laird-campus))
(define pencader-halls (make-student-residence 'pencader-halls))
(define pencader-dining (make-card-locked-place 'pencader-dining))
(define christiana-west (make-student-residence 'christiana-west))
(define christiana-east (make-student-residence 'christiana-east))
(define christiana-com (make-card-locked-place 'christiana-com))
(define heaven       (make-place 'heaven))	; The point of no return
(define gaol (make-place 'gaol))   
;; One-way paths connect individual places in the world.
;;------------------------------------------------------

(define (can-go from direction to)
  (ask from 'add-neighbor direction to))

(define (can-go-both-ways from direction reverse-direction to)
  (can-go from direction to)
  (can-go to reverse-direction from))

(can-go gaol 'up Smith-Hall)
(can-go-both-ways Smith-Hall 'up  'down  computer-lab)
(can-go-both-ways Smith-Hall 'north 'south Trabant-Center)
(can-go-both-ways Smith-Hall 'east  'west  Greenhouse)
(can-go-both-ways Smith-Hall 'west  'east  errol-office)
(can-go-both-ways Greenhouse 'up    'down  keith-office)
(can-go-both-ways dormitory  'west  'east  Trabant-Center)
(can-go-both-ways Trabant-Center 'north 'south laird-campus)
(can-go-both-ways laird-campus 'east 'west pencader-halls)
(can-go-both-ways laird-campus 'up 'down pencader-dining)
(can-go-both-ways laird-campus 'north 'south christiana-com)
(can-go-both-ways christiana-com 'west 'east christiana-west)
(can-go-both-ways christiana-com 'east 'west christiana-east)

(can-go dungeon      'up    Trabant-Center)

;; The important critters in our world...
;;---------------------------------------
(define big-brother (make-watcher-object 'big-brother))
(define errol   (make&install-teacher 'errol   errol-office 3))
(define keith   (make&install-teacher 'keith   keith-office 2))
(define stud1 (make&install-student 'stud1 dormitory 2))
(define stud2 (make&install-student 'stud2 pencader-halls 2))
(define stud3 (make&install-student 'stud3 christiana-east 2))
(define stud4 (make&install-student 'stud4 christiana-west 2))
(define stud5 (make&install-student 'stud5 pencader-dining 2))
(define stud6 (make&install-student 'stud6 computer-lab 2))
(define bread (make&install-thing 'bread-of-unusual-softness pencader-dining))
;grendel tends to kill my ogres, and has thus been 
;stricken from these realms.
;(define grendel (make&install-troll  'grendel dungeon      4))

(define dorm-card
  (make&install&reg-sd-card 'dorm-card dormitory '745-765-1234))
(define penc-card
  (make&install&reg-sd-card 'penc-card pencader-halls '432-32-4123))
(define east-card 
  (make&install&reg-sd-card 'east-card christiana-east '213-43-3724))
(define west-card
  (make&install&reg-sd-card 'west-card christiana-west '132-98-8987))
(define keith-card
  (make&install-sd-card 'keith-card keith-office '888-12-3456))
(define errol-card
  (make&install-sd-card 'errol-card errol-office '888-98-7654))
(define spare-card
  (make&install-sd-card 'spare-card Smith-Hall '123-45-6789))
;; The beginning of an ever-expanding game script
;;------------------------------------------------

(define kalim (make&install-person 'kalim laird-campus 999))
(define kcard (make&install-sd-card 'kcard laird-campus '111-34-1416))
(define late-homework (make&install-thing 'late-homework dormitory))

(define (play-game)
  (ask kalim 'take kcard)
  (ask errol 'take errol-card)
  (ask keith 'take keith-card))

;; ...now whenever you re-load this file, you can bring things up to
;; date by invoking PLAY-GAME.
