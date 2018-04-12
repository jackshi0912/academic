/*
 * Filename: rotate.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function will rotate the bits in the 64-bit crypto mask.
 * 	The rotate Value indicates how many bits to rotate by. A negative 
 *	rotateValue indicates the bits will be rotated left, and a positive 
 *	rotateValue indicates the bits will be rotated right. You should perform
 *	the rotation one bit at a time. After performing the rotation, store the
 *	rotated mask back into the mask parameter.
 * Date: April 26, 2016
 * Source of Help: Handout, Lecture Notes
 */

	.global rotate

	.section ".text"

	SHIFT_PRESERVE_BIT = 31
	WORD_BYTE_OFFSET = 4

/*
 * Function name: rotate()
 * Function prototype: void rotate( unsigned long mask[], long rotateValue )
 * Description: This function will rotate the bits in the 64-bit crypto mask.
 * 	The rotate Value indicates how many bits to rotate by. A negative 
 *	rotateValue indicates the bits will be rotated left, and a positive 
 *	rotateValue indicates the bits will be rotated right. You should perform
 *	the rotation one bit at a time. After performing the rotation, store the
 *	rotated mask back into the mask parameter.
 * 
 * Parameters:
 *	arg 1: The mask to rotate
 *	arg 2: The number of bits to rotate
 * 
 * Side Effects: None
 * Error Condition: None
 * Return Value: None
 *
 * Registers Used:
 *	%l0 - The byte value of mask
 *	%l1 - The bit preserved from the left word
 *	%l2 - The bit preserved from the right word
 *	%l3 - The iteration counter
 *	%l4 - The address of bit to preserve
 */

rotate:
 	save	%sp, -( 92 )& -8, %sp

	clr	%l4	! Clear the address of the preserved byte
	mov	1, %l4	! Move 1 into the address of the preserved byte
	clr	%l3	! clear the iteration counter

	cmp	%i1, %g0	! Check if the rotate value is neigative
	bl	rotateLeft	! If rotate value is neigative, rotate left
	nop

	be	end	! If the value is 0, end
	nop

rotateRight:
				! Rotate right
	ld	[%i0], %l0	! Load the first word of mask
	and	%l0, %l4, %l1	! Preserve the right most bit
	sll	%l1, SHIFT_PRESERVE_BIT, %l1	! Move the preserved bit to the
						! left most bit
	srl	%l0, 1, %l0			! Shift the the first word by
						! one bit
	st	%l0, [%i0]			! Store the shifted value

	ld	[%i0 + WORD_BYTE_OFFSET], %l0	! Load the second word of mask
	and	%l0, %l4, %l2			! Preserve the right most bit
	sll	%l2, SHIFT_PRESERVE_BIT, %l2	! Move the preserved bit to the
						! left most bit
	srl	%l0, 1, %l0			! Shift the the first word by
						! one bit
	st	%l0, [%i0 + WORD_BYTE_OFFSET]	! Store the shifted value

				! Restore the preserved bit
	
	ld	[%i0], %l0	! Load the first word of mask
	or	%l0, %l2, %l0	! restore the preserved bit for the first word

	st	%l0, [%i0]	! Store the rotated word
	
	ld	[%i0 + WORD_BYTE_OFFSET], %l0	! Load the second word of mask
	or	%l0, %l1, %l0	! restore the preserved bit for the second word

	st	%l0, [%i0 + WORD_BYTE_OFFSET]	! Store the rotated word

	inc	%l3	! Increment the iteration counter
	cmp	%l3, %i1	! Check if the iteration rounds meets the rotate
				! value
	bl	rotateRight	! If not, keep on executing loop
	nop

	ba	end
	nop


rotateLeft:

				! Rotate left
	ld	[%i0], %l0	! Load the first word of mask

	mov	1, %l4
	sll	%l4, SHIFT_PRESERVE_BIT, %l4	! Set the addresss for the
						! left most bit

	and	%l0, %l4, %l1			! Preserve the left most bit
	srl	%l1, SHIFT_PRESERVE_BIT, %l1	! Move the preserved bit to the
						! right most bit
	sll	%l0, 1, %l0			! Shift the the first word by
						! one bit
	st	%l0, [%i0]			! Store the shifted value

	ld	[%i0 + WORD_BYTE_OFFSET], %l0	! Load the second word of mask
	and	%l0, %l4, %l2			! Preserve the left most bit
	srl	%l2, SHIFT_PRESERVE_BIT, %l2	! Move the preserved bit to the
						! left most bit
	sll	%l0, 1, %l0			! Shift the the first word by
						! one bit
	st	%l0, [%i0 + WORD_BYTE_OFFSET]	! Store the shifted value

				! Restore the preserved bit
	
	ld	[%i0], %l0	! Load the first word of mask
	or	%l0, %l2, %l0	! restore the preserved bit for the first word
	st	%l0, [%i0]	! Store the rotated word
	
	ld	[%i0 + WORD_BYTE_OFFSET], %l0	! Load the second word of mask
	or	%l0, %l1, %l0	! restore the preserved bit for the second word
	st	%l0, [%i0 + WORD_BYTE_OFFSET]	! Store the rotated word

	inc	%i1	! Increment the iteration counter
	cmp	%i1, %g0	! Check if the goal has met
	bl	rotateLeft	! If yes, keep iterating
	nop

end:

	mov	%g0, %i0	! Clear the return register
	ret
	restore
