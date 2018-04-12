/*
 * Filename: isort.s
 * Author: TODO
 * Userid: TODO
 * Description: PA2 Extra Credit. SPARC assembly code to be optimized by
 *              eliminating nops with existing useful instructions.
 *              Performs an insertion sort.
 *              Returns the difference between the largest and smallest
 *              values.
 * Date: TODO
 * Sources of Help: TODO
 */

	.global isort

	.section	".text"

isort:
	save	%sp, -96, %sp

	mov	1, %l0

	cmp	%l0, %i1
	bge	end
	nop

loop:

	mov	%l0, %l1

	cmp	%l1, %g0
	ble	end_inner
	nop

start_inner:

	sll	%l1, 2, %l2
	add	%i0, %l2, %l2
	ld	[%l2], %l3
	ld	[%l2 - 4], %l4

	cmp	%l3, %l4
	bge	end_inner
	nop

	st	%l4, [%l2]
	st	%l3, [%l2 - 4]

	add	%l1, -1, %l1

	cmp	%l1, %g0
	bg	start_inner
	nop

end_inner:

	inc	%l0

	cmp	%l0, %i1
	bl	loop
	nop

end:

	add	%i1, -1, %l0

	mov	%l0, %o0
	mov	4, %o1
	call	.mul
	nop

	add	%i0, %o0, %l0
	ld	[%l0], %l0

	ld	[%i0], %i0
	sub	%l0, %i0, %i0

	ret
	restore

