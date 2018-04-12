/*
 * Filename: printSegment.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Print a segment for a certain amount of times
 * Date: April 9th, 2016
 * Source of Help: Handout
 */

		.global printSegment	! Declares the smbol to be globally visible
		.section ".text"		! The text segment begins here

/*
 * Function name: printSegment()
 * Function prototype: void printSegment( long character, long amount );
 * Description: Prints a string of characters using the printChar method
 * Parameters:
 *		arg 1: long character -- The ASCII value of the character to be printed
 *		arg 2: long amount -- The amount of characters to print
 * Side Effects: None
 * Error Conditions: None
 * Return Value: None
 *
 * Registers Used:
 *		%i0 - arg1 -- The ASCII value of the character to be printed
 *		%i1 - arg2 -- The amount of time to print the character to stdout
 *
 */

printSegment:

	save	%sp, -96, %sp	! Save caller's window


	clr		%l0				! Clear the counter variable

	cmp		%l0, %i1		! Skip loop conditions
	bge		end_loop		! Skip to the end of the loop
	nop

loop:

	mov		%i0, %o0		! Stage the ASCII value to be printed as character
	call	printChar		! Calling printChar to print to stdout
	nop

	inc		%l0				! Increment counter variable
	cmp		%l0, %i1		! Next iteration condition
	bl		loop			! Branch to the start of loop
	nop

end_loop:

	ret						! Return at the end of function
	restore
