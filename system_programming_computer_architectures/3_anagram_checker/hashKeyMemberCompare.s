/* 
 * Filename: hashKeyMemberCompare.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function compares the hashKey member of two pointers to
 * 		struct anagram. This is used in binary sort.
 * Date: May 16, 2016
 * Source of help: handout
 */

	.global	hashKeyMemberCompare

	.section	".text"

/*
 * Function name: hashKeyMemberCompare()
 * Function prototype:
 *	int hashKeyMemberCompare( const void *p1, const void *p2 );
 * Description: This function compares the hashKey member of two pointers to
 * 		struct anagram. This is used in binary sort.
 * Parameters:
 *	arg1: const void *p1 - %i0 The pointer to struct anagram one
 *	arg2: const void *p1 - %i1 The pointer to struct anagram two
 *
 * Side Effects: None
 * Error Conditions: None
 * Return Value: int presenting which struct is greater
 *	1 is returned if the first struct is considered greater than second
 *	-1 is returned if the first struct is less than the second
 *	0 is returned if both struct have the same hashKey value
 *
 * Registers used: 
 *	%l0 - Constant value for key offset
 */

hashKeyMemberCompare:

	save	%sp, -(92) & -8, %sp
	
	set	AnagramStructHashKeyOffset, %l0	! Get the offset
	ld	[%l0], %l0	! Dereference offset to get value

	ld	[%i0 + %l0], %i0	! Access the value of p1->hashKey
	ld	[%i1 + %l0], %i1	! Access the value of p2->hashKey

	cmp	%i0, %i1	! Compare the hashKey values
	bg	greater	! Branch to return 1
	nop

	bl	less	! Use same condition code to branch to return -1
	nop

	mov	%g0, %i0	! Stage 0 for return

	ret	! Return 0
	restore

greater:
	mov	1, %i0

	ret	! Return 1
	restore

less:
	mov	-1, %i0

	ret	! Return -1
	restore
