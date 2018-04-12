/*
 * Filename: parseRotateValue.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Descritpion: This function will parse the rotate value from string input by
 * 		using strtol. Once it is parsed it will store into the long 
 *		pointer passed into this function.
 * Date: Apirl 23, 2016
 * Source of Help: Handout, Lecture Notes
 */

	.global parseRotateValue

	.section ".data"

	ENDPTR_OFFSET = 4
	DEC_BASE = 10

/*
 * Function name: parseRotateValues
 * Function prototype: long parseRotateValues( char * str, long * rotateValue );
 * Descritpion: This function will parse the rotate value from string input by
 * 		using strtol. Once it is parsed it will store into the long 
 *		pointer passed into this function.
 * Parameters:
 *	arg 1: The incomming pointer to a char array to be analyzed for long
 *	arg 2: The long pointer to the outgoing value
 *
 * Side Effects: None
 * Error Condition: 
 * 	Number is too large to be successfully converted
 * 	Number contained invalid characters
 *	The number was outside of the required range
 *
 * Return Value:
 *	0 - Program is exited sucsessfuly
 *	ERANGE_ERR - If the number was too large to be successfully converted
 *	ENDPTR_ERR - If the number contained invalid characters
 *	BOUND_ERR - If the number was outside the required range
 * 
 * Registers Used:
 *	%l0 - errno
 *	%l1 - The converted long value of *arg1
 *	%l2 - The dereferenced value of MIN_ROTATE_G
 *	%l3 - The dereferenced value of MAX_ROTATE_G
 *	%l4 - The dereferenced value of endptr
 */

parseRotateValue:
	save	%sp, -( 92 + ENDPTR_OFFSET ) & -8, %sp	! Save caller's frame	

				! Set errno, min and max

	set	errno, %l0	! Set the address of errno to %l0
	st	%g0, [%l0]	! Dereference to set value to 0

	set	MIN_ROTATE_G, %l2	! Load the address of MIN_ROTATE_G
	ld	[%l2], %l2	! Dereference the value of MIN_ROTATE_G

	set	MAX_ROTATE_G, %l3	! Load the address of MAX_ROTATE_G
	ld	[%l3], %l3	! Dereference the value of MAX_ROTATE_G
	
				! Convert *arg1

	add	%fp, -ENDPTR_OFFSET, %l4
	st	%g0, [%l4]

	mov	%i0, %o0	! Stage arg1 for strtol
	add	%fp, -ENDPTR_OFFSET, %o1	! Stage endptr local var for strtol
	mov	DEC_BASE, %o2	! Stage the decimal base for strtol
	call	strtol		! Call strtol
	nop
	mov	%o0, %l1	! Move the converted value to %l1

				! Check if errno is set

	ld	[%l0], %l0	! Dereference errno to obtain the value
	cmp	%l0, %g0	! Compare errno with 0
	bne	tooLarge	! If not equal, branch to tooLarge
	nop

				! Check if the value is in range
	add	%fp, -ENDPTR_OFFSET, %l4
	ld	[%l4], %l4	! load dereferenced endptr

	ldub	[%l4], %l4

	cmp	%l4, %g0	! Check for null termination
	bne	endPtrErr	! branch to endPtrErr
	nop
	
	mov	%l2, %o0	! Stage MIN_ROTATE_G for isInRange
	mov	%l3, %o1	! Stage MAX_ROTATE_G for isInRange
	mov	%l1, %o2	! Stage the converted *arg1 value
	mov	%g0, %o3	! Stage 0 as the exclusive flag
	call	isInRange	! Call isInRange
	nop

	cmp	%o0, %g0	! Check if out of range
	be	outOfRange	! If not in range, branch to outOfRange
	nop

	st	%l1, [%i1]	! Store converted value to the arg2
				! dereferenced value
	
	mov	%g0, %i0	! stage 0 for return
	ret	! Return 0
	restore

outOfRange:

	set	BOUND_ERR_G, %i0	! Load the address of BOUND_ERR_G
	ld	[%i0], %i0	! Dereference the value of BOUND_ERR_G

	ret	! Return BOUND_ERR_G
	restore

tooLarge:

	set	ERANGE_ERR_G, %i0	! Load the address of ERANGE_ERR_G
	ld	[%i0], %i0	! Dereference the value of ERANGE_ERR_G

	ret	! Return ERANGE_ERR_G
	restore

endPtrErr:

	set	ENDPTR_ERR_G, %i0	! Load the address of ENDPTR_ERR_G
	ld	[%i0], %i0	! Dereference the value of ENDPTR_ERR_G

	ret	! Return ENDPTR_ERR_G
	restore
