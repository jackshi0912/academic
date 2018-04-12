/*
 * Filename: printChar.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Print a character onto stdout. Called from printSegment.
 * Date: April 9th, 2016
 * Sources of Help: Handout
 */

		.global printChar	! Declares the symbol to be globally visible
		.section ".data"	! The data segment begins here

fmt:
		.asciz "%c"

		.section ".text"	! The text section begins here

/*
 * Function name: printChar()
 * Function prototype: void printChar( char ch );
 * Description: Prints to stdout a single character
 * Parameters:
 *		arg 1: char ch -- The character to be printed
 * Side Effects: None
 * Error Conditions: None
 * Return Value: None
 *
 * Registers Used:
 *		%i0 - arg 1 -- The character passed in to this function
 *
 *		%o0 - param 1 for printf() -- format string
 *		%o1 - param 2 for printf() -- copy of arg 1 being passed in
 */

printChar:
	save 	%sp, -96, %sp	! Save caller's window

	set 	fmt, %o0		! Stage the format string for printf
	mov		%i0, %o1		! Stage the input character for printf
	call	printf			! Make function call to print to stdout
	nop

	ret						! Return from subroutine
	restore					! Restore caller's window

