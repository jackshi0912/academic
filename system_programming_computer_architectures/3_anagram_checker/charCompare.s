/*
 * Filename: charCompare.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function compares two characters and determin which one is
 * 	ranked greater than the other or if they are the same. This is done by
 * 	comparing the character's ascii value.
 * Date: may. 16, 2016
 * Source of help: handout
 */

 	.global charCompare

	.section ".text"


/*
 * Function name: charCompare()
 * Function prototype: int charCompare( const void * p1, const void * p2 );
 * Description: This function compares two characters and determin which one is
 * 	ranked greater than the other or if they are the same. This is done by
 * 	comparing the character's ascii value.
 * Parameter:
 *	arg1: const void *p1 - %i0 The first character to be compared
 *	arg2: const void *p2 - %i1 The second character to be compared
 *
 * Side Effects: none
 * Error Condition: none
 * Return Value: none
 *
 * Registers Used:
 * 	
 */

charCompare:
	
	! Dereference pointers
	ldub	[%o0], %o0
	ldub	[%o1], %o1
	cmp	%o0, %o1	! Compare the two characters

	bl	less	! If first char is smaller, move to return -1
	nop

	bg	greater	! If the first char is larger, move to return 1
	nop

	mov	%g0, %o0	! The two values are the same, return 0
	retl
	nop

less:
	mov	-1, %o0	! The first value is less, return -1
	retl
	nop

greater:
	mov	1, %o0	! The first value is greater, return 1
	retl
	nop
