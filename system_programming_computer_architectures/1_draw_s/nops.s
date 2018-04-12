/*
 * Filename: nops.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: PA1 Extra Credit. SPARC assembly code to be optimized by
 *              eliminating nops with existing useful instructions.
 * Date: April 16th, 2016
 * Sources of Help: Class Notes
 */


	.section	".rodata"
strfmt:
	.asciz  "%4d: %d\n"


	.global	nops
	.section	".text"
nops:
	save	%sp, -96, %sp

	mov	%i0, %l0	! num
	clr	%l1		! sum
	mov	%l1, %l2	! i

	cmp	%l2, %l0
	bge,a endloop
	mov	%l1, %i0

startloop:

	call	isEven
	mov	%l2, %o0

	cmp	%o0, %g0
	be,a isEvenElse
	mov	%l1, %o0

	ba	isEvenEnd
	add	%l1, %l2, %l1

isEvenElse:

	call	.mul
	mov	%l2, %o1

	mov	%o0, %l1
	sra	%l1, 1, %l1

isEvenEnd:

	inc	%l2

	set	strfmt, %o0
	mov	%l2, %o1
	call	printf
	mov	%l1, %o2

	cmp	%l2, %l0
	bl	startloop
	nop			! DO NOT FILL/REMOVE THIS NOP

	mov	%l1, %i0

endloop:

	ret
	restore

