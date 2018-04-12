/*
 * Filename: isInRange.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Check if a incomming value is between min and max range
 * Date: April 9th, 2016
 * Source of Help: Handout
 */

	.global isInRange	! Declares the symbol to be globally visible so
						! we can call this function from other modules
	
	.section ".text"	! The text segment begins here

/*
 * Function name: isRange
 * Function prototype: int isInRange( long minRange, long maxRange, long value,
 * long exclusive)
 * Description: Check if the num is between the ranges
 * Parameters:
 * 		arg 1: long minRange -- The lower bound
 *		arg 2: long maxRange -- The higher bound
 *		arg 3: long value -- The value to be tested for range
 *		arg 4: long exclusive -- Decide if the value is within the range or out
 *		side of it
 * 
 * Side Effects: None
 * Error Condition: None
 * Return Value: Int represent if the value is withint our out side of the range
 *
 * Registers Used:
 * 		%i0 - param 1 -- The lower bound
 *		%i1 - param 2 -- The higher bound
 *		%i2 - param 3 -- The value to be tested for range
 *		%i3 - param 4 -- Decide if the value is within the range or out
 *		side of it
 */

isInRange:
	save	%sp, -96, %sp	! Save caller's window; if different than -96 then
							! comment on how the value was calculated

	cmp 	%i0, %i1		! Compare min to max
	bg 		minGTMax		! Go to error label
	nop

	cmp 	%i3, %g0		! Check if exclusive is zero
	be 		greaterThanLower	! If inclusive, check if value is greater than
							! or equal to lower 
	nop

	ba		greaterThanLowerEx	! if exclusive, check if value if greater than
								!lower bound
	nop

    mov %g0, %i0
    ret
    restore

greaterThanLower:
	cmp 	%i0, %i2		! Compare the min lower bound and input value
	ble 	lessThanUpper	! Branch out if the min lower bound is less than or
							! equal to the value
	nop

	mov 	%g0, %i0		! Move 0 to the return slot
	ret
	restore

lessThanUpper:
	cmp 	%i1, %i2		! Compare the max to the value
	bge 	valueIsValid	! Branch out if the max bound is greater or equal to
							! the value
	nop

	mov 	%g0, %i0		! Move 0 to the return slot
	ret
	restore

valueIsValid:

	mov 	1, %i0			! Move 1 to return register
	ret
	restore

greaterThanLowerEx:
	cmp 	%i0, %i2		! Compare the min lower bound and input value
	bl 	lessThanUpperEx		! Branch out if the min lower bound is less than
							! the value
	nop

	mov 	%g0, %i0		! Move 0 to the return slot
	ret
	restore

lessThanUpperEx:
	cmp 	%i1, %i2		! Compare the max to the value
	bg 	valueIsValid		! Branch out if the max bound is greater than
							! the value
	nop

	mov 	%g0, %i0		! Move 0 to the return slot
	ret
	restore

minGTMax:
	mov 	-1, %i0			! Return -1
	ret
	restore

