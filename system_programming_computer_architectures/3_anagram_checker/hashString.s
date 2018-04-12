/*
 * Filename: hashString.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function generates a hashcode for a given string
 */

	.global hashString

	.section ".text"

/*
 * Function name: hashString()
 * Function prototype: long hashString( const char * str );
 * Description: This function generates a hashcode for a given string
 * Parameter:
 *	arg1: const char *ptr - %i0 The pointer to a string
 *
 * Side Effects: None
 * Error Conditions: None
 * Return Value hash code for the string
 *
 * Registers Used:
 *	%l0 - hash value
 *	%l1 - strlen( str );
 *	%l2 - HASH_PRIME
 *	%l3 - Loop iterator
 *	%l4 - value of char within the string
 */

hashString:
	
	save	%sp, -(92) & -8, %sp

	! Load and dereference the HASH_START_VAL constant
	set	HashStartVal, %l0
	ld	[%l0], %l0

	! Use strlen to get string length
	mov	%i0, %o0
	call	strlen
	nop
	mov	%o0, %l1	! Store the length in %l1

	! Obtain the value of the HASH_PRIME constant
	set	HashPrime, %l2
	ld	[%l2], %l2

	clr	%l3	! Clear the iteration counter
	
	cmp	%l3, %l1	! Precondition to skip the loop
	bge	end
	nop

loop:
	ldub	[%i0], %l4	! Load a character of the string

	! hash * HASH_PRIME
	mov	%l0, %o0	
	mov	%l2, %o1
	call	.mul
	nop
	
	add	%o0, %l4, %l0	! Get the new hash value
	
	inc	%i0	! Increment the string pointer address
	inc	%l3	! i++
	cmp	%l3, %l1	! Precondition to skip the loop
	bl	loop
	nop

end:

	! Return the hash value
	mov	%l0, %i0

	ret
	restore
