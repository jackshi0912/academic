/*
 * Filename: numOfDigits.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: The function counts the number of base digits in num. First check
 * 				to make sure if the base is within the range [2, 36], inclusive by calling is
 * 				In Range(). If base is not within the range, return -1. If num
 * 				is 0, return 0. Otherwise, return the number of digits in num.
 * Date: April 9th, 2016
 * Source of Help: Handout
 */

 	.global numOfDigits 	! Declares the symbol to be globally visible

	.section ".text"		! The text segment begins here

    LOWER_BOUND = 2         ! The lower bound for the base range
    UPPER_BOUND = 36        ! The upper bound for the base range

/*
 * Function name: numOfDigits()
 * Function prototype: int numOfDigits( long num, int base );
 * Description: The function counts the number of base digits in num. First check
 * 				to make sure if the base is within the range [2, 36], inclusive by calling is
 * 				In Range(). If base is not within the range, return -1. If num
 * 				is 0, return 0. Otherwise, return the number of digits in num.
 * Parameters:
 *		arg 1: The incomming num to be analyzed
 *		arg 2: The base to represent the incomming num.
 *
 * Side Effects: None
 * Error Condition: base is not within the range [2, 36] -> return -1
 * Return Value: the number of digits for the input represent in a defined base
 *
 * Registers Used:
 *		%i0 - param 1 -- The incomming num to be analyzed
 *		%i1 - param 2 -- The base to represent the incomming num.
 *
 *		%l0 - The current power value -- Multiply by base every loop
 *
 *		%o0 - param 1 for isInRange() -- Lower bound for base range
 *		%o1 - param 2 for isInRange() -- Upper bound for base range
 *		%o2 - param 3 for isInRange() -- TestValue bound
 *		%o3 - param 4 for isInRange() -- Exclusive flag
 */

numOfDigits:
	save 	%sp, -96, %sp	! Save caller's window; if defferent than -96 than
							! comment on how the value was calculated

	cmp 	%i0, %g0        ! Check if the input is zero
	be 		zeroInput       ! branch out to ZeroInput if it's the case
	nop

	cmp 	%i0, %g0		! Check if the input is neigative
	bg 		noInvertValue	! if there is not need to invert, branch to label
	nop

	mov		%i0, %o0		! Stage the value for multplication by -1
	mov		-1, %o1			! Stage -1 for inverting the value
	call	.mul			! Invert the value by multiply the value by -1
	nop
	mov		%o0, %i0		! Move the inversed value into the original input

noInvertValue:
	mov		LOWER_BOUND,%o0	! Lower bound for isInRange
	mov		UPPER_BOUND,%o1	! Upper bound for isInRange
	mov		%i1, %o2		! Value to test for range
	mov		%g0, %o3  		! Inclusive range parameter
	call	isInRange		! Function Call
	nop

	cmp		%o0, %g0		! Check if the value is in range
	be		baseOutOfRange	! Branch out to return -1 if base is out of range
	nop
	

	mov 	1, %i2			! Number of digit to start

	cmp 	%i1, %i0		! Check if the base (The power of current digit) is
							! greater than that of the value
	bg 		end				! End the loop if the power of the current digit is
							! greater than that of the value
	nop

	mov		%i1, %l0		! move the base into power slot

loop:

	add		%i2, 1, %i2		! increwment the digit count my 1
    nop


	mov		%i1, %o0		! Move the base ready to move up in power
	mov		%l0, %o1
	call	.mul			! Move the base for up in power
	nop

	mov		%o0, %l0		! move the squared power back to base slot

	cmp 	%l0, %i0		! Check if the base (The power of current digit) is
							! greater than that of the value
	ble  	loop			! End the loop if the power of the current digit is
							! greater than that of the value
	nop

end:
	mov 	%i2, %i0		! Stage the count of power for returning
	ret						! Return the count of digits
	restore

zeroInput:
	mov 	%g0, %i0		! If the input is zero, stage 0 for return
	ret						! return 0
	restore

baseOutOfRange:
	mov	-1, %i0			! Stage -1 for return
	ret						! Return -1
	restore
