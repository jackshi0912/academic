/* 
 * Filename: anagramCompare.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Check if one struct anagram should proceed the other. This is
 * 		evaluated first by comparing their hashKey value. If those do
 * 		not separate them, the content of their words are compared.
 * Date: May 16, 2016
 * Source of help: none
 */


	.global	anagramCompare

	.section ".text"

/*
 * Function name: anagramCompare()
 * Function prototype: int anagramCompare( const void *p1, const void *p2 );
 * Description: Check if one struct anagram should proceed the other. This is
 * 		evaluated first by comparing their hashKey value. If those do
 * 		not separate them, the content of their words are compared.
 * Parameters:
 *	arg1: const void *p1 - %i0 The pointer to the first struct anagram
 *	arg2: const void *p2 - %i1 The pointer to the second struct anagram
 *
 * Side Effects: None
 * Error Condition: None
 * Return Value:
 *	Int representing the precedence of the first anagram relative to the
 *	second
 *	1 is returned if the first one is greater than the second
 *	-1 is returned if the first one is less than the second
 *	0 is returned if both have exactly the same struct members
 *
 * Registers used: %l0 - word offset for the struct anagram
 */

anagramCompare:

	save	%sp, -92 & -8, %sp

	! Use hashKeyMemberCompare to first compare the hash keys
	mov	%i0, %o0
	mov	%i1, %o1
	call	hashKeyMemberCompare
	nop

	cmp	%o0, %g0	! If the hashKeys are the same
	be	strcompare	! Move on to compare the strings
	nop

	! If the hash keys are different, return the hashKey comparison
	mov	%o0, %i0
	ret
	restore

strcompare:

	set	AnagramStructWordOffset, %l0	! Load the label for the const
	ld	[%l0], %l0	! Dereference to get the value of constant

	ld	[%i0 + %l0], %o0	! Dereference the word for struct 1
	ld	[%i1 + %l0], %o1	! Dereference the word for struct 2

	call	strcmp
	nop

	cmp	%o0, %g0	! Check if the value is greater than zero
	bg	greaterThanZero	! Branch to return 1
	nop

	bl	lessThanZero	! use same cc, if less, branch to return -1
	nop

	mov	%g0, %i0	! Stage the result of strcmp for return
	ret	! Return the result of strcmp
	restore

lessThanZero:

	mov	-1, %i0	! Stage -1 for return
	ret	! Return -1
	restore

greaterThanZero:

	mov	1, %i0	! Stage 1 for return
	ret	! Return 1
	restore
