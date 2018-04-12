/*
 * Filename: drawS.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: The function draws a S to stdout using printSegment and
 *				printChar with characters from user input.
 * Date: April 9th, 2016
 * Source of Help: Handout
 */

	.global drawS		! Declares the symbol to be globally visible
	.section ".data"	! The text segment begins here

fmt:
    .asciz "%d"

	.section ".text"	! The text segment begins here

    BASE = 10			! Base 10 for calculating number of digits
	DOUBLE = 2			! Doubling certain number and end loop condition

/*
 * Function name: drawS()
 * Function prototype: void drawS( long sWidth, long sChar, long fillerChar,
 *		long borderChar );
 * Description: The function draws a S to stdout using printSegment and
 *				printChar with characters acquired from user input.
 * Parameters:
 *		arg 1: long sWidth -- The width of the S to be printed
 *		arg 2: long sChar -- The characters to make the S out of
 *		arg 3: long fillerChar -- The characters to fill empty space
 *		arg 4: long borderChar -- The character to build the boarder out of
 *
 * Side Effects: None
 * Error Condition: None
 * Return Value: None
 *
 * Registers Used:
 *		%i0 - long sWidth -- The width of the S to be printed
 *		%i1 - long sChar -- The characters to make the S out of
 *		%i2 - long fillerChar -- The characters to fill empty space
 *		%i3 - long borderChar -- The character to build the boarder out of
 *
 *		%o0 - param 1 for pintSegment() and printChar -- The character to print
 *			- param 1 for numOfDigits() -- Input for digit counting
 *		%o1 - param 2 for printSegment() -- The number of characters per line
 *			- param 2 for numOfDigits() -- The base for counting digits
 *
 *		%l0 - counter variable for loops
 *		%l1 - borderWidth -- numOfDigits( sWidth, 10 )
 *		%l2 - borderWidth*2 -- For border segment printing
 *		%l3 - Length of the top, middle and bottom lines -- sWidth - 2
 */

drawS:
	save %sp, -96, %sp		! Save caller's window

	mov		%i0, %o0		! Stage the sWidth to be analized for digit counts
	mov		BASE, %o1		! Stage the constant 10 as base for digit counting
	call	numOfDigits		! Count the number of digits
	nop
	mov		%o0, %l1		! Store the result in register %l1

							! Start of drawing upper border

	mov		DOUBLE, %o0		! Stage constant 2 for multiplying
	mov		%l1, %o1		! Stage the digit count for multiplying
	call	.mul			! Multiply the two values using a sub-routine
    nop

	mov		%o0, %l2		! Store the result in %l2

	add		%l2, %i0, %l2	! Add the sWidth to get the length of the border
	
	clr		%l0;			! Clear the %l0 register for the loop counter variable

	cmp		%l0, %l1		! Precondition to skip the loop
	bge		end_top_border_loop		! If true, skip the loop
	nop

top_border_loop:

	mov		%i3, %o0		! Stage the border character for segment printing
	mov		%l2, %o1		! Stage the length of the border for the segment
							! printing
	call	printSegment	! Print the segment with a function call
	nop

	mov		BASE, %o0		! Stage '\n' ASCII value for printing
	call	printChar		! Print '\n'
	nop

	inc		%l0				! Increment the for loop counter variable
	cmp		%l0, %l1		! Compare to determin the end of the loop
	bl		top_border_loop	! Continue to next iteration
	nop

end_top_border_loop:

							! Start of drawing S

	clr		%l0;			! Clear the %l0 register for the loop counter variable

s_loop:

							! Print top, middle and bottom lines

	mov		%i3, %o0		! Stage  the borderChar for printSegment
	mov		%l1, %o1		! Stage  the border width for printSegment
	call	printSegment	! Print the segment to stdout
	nop

	mov		%i2, %o0		! Stage  the filler character for printChar
	call	printChar		! Print the segment to stdout
	nop

	sub		%i0, DOUBLE, %l2	! Calculate the length of the horizontal segments of
							! S by subtracting sWidth by 2

	mov		%i1, %o0		! Stage  the sChar for printSegment
	mov		%l2, %o1		! Stage  the horizontal segment length for printSegment
	call	printSegment	! Print the segment to stdout
	nop

	mov		%i2, %o0		! Stage  the filler character for printChar
	call	printChar		! Print the segment to stdout
	nop

	mov		%i3, %o0		! Stage  the borderChar for printSegment
	mov		%l1, %o1		! Stage  the border width for printSegment
	call	printSegment	! Print the segment to stdout
	nop

	mov		BASE, %o0		! Stage '\n' ASCII value for printing
	call	printChar		! Print '\n'
	nop
							! Do not continue if drawing bottom line

	cmp		%l0, DOUBLE		! Check if drawing last line
	be		end_s_loop		! break the loop if drawn last line
	nop

							! Fill between horizontal lines of S

	
	clr		%l3;			! Clear the %l3 register for the loop counter variable

	cmp		%l3, %l2		! Precondition to skip the loop
	bge		end_filler_loop	! If true, skip the loop
	nop

filler_loop:

	mov		%i3, %o0		! Stage borderChar for segment printing
	mov		%l1, %o1		! Stage borderWidth for segment printing
	call	printSegment	! Print the segment
	nop

							! Check for the top or bottom section

	cmp		%l0, %g0		! Check if working on the first section
	bne		not_section_0	! Skip to else block if the section is not 0
	nop
	mov		%i1, %o0		! Stage the sChar for printing
	call	printChar		! Print the sChar
	nop
	ba		end_not_section_0	! skip the else block
	nop

not_section_0:

	mov		%i2, %o0		! Stage the fillerChar for printing
	call	printChar		! Print the fillerChar
	nop

end_not_section_0:

							! Fill in the space between

	mov		%i2, %o0		! Stage the fillerChar for segment printing
	mov		%l2, %o1		! Stage the segment length for printing
	call	printSegment	! Print the line
	nop



							! Check for the top or bottom section

	cmp		%l0, %g0		! Check if working on the first section
	bne		not_section_0_1	! Skip to else block if the section is not 0
	nop
	mov		%i2, %o0		! Stage the sChar for printing
	call	printChar		! Print the sChar
	nop
	ba		end_not_section_0_1	! skip the else block
	nop

not_section_0_1:

	mov		%i1, %o0		! Stage the fillerChar for printing
	call	printChar		! Print the fillerChar
	nop

end_not_section_0_1:
	
	mov		%i3, %o0		! Stage borderChar for segment printing
	mov		%l1, %o1		! Stage borderWidth for segment printing
	call	printSegment	! Print the segment
	nop

	mov		10, %o0			! Stage '\n' ASCII value for printing
	call	printChar		! Print '\n'
	nop

							! Inner loop condition	

	inc		%l3				! Increment the for loop counter variable
	cmp		%l3, %l2		! Compare to determin the end of the loop
	bl		filler_loop		! Continue to next iteration
	nop

end_filler_loop:

							! Outer Loop conditions

	inc		%l0				! Increment the iteration counter
	cmp		%l0, 3			! Compare the iteration counter to constant 3
	bl		s_loop			! Continue iteration if the iteration is less than
							! the constant 3
	nop

end_s_loop:

							! Start of drawing bottom border

	clr		%l0;			! Clear the %l0 register for the loop counter variable

	cmp		%l0, %l1		! Precondition to skip the loop
	bge		end_bot_border_loop		! If true, skip the loop
	nop

	mov		2, %o0			! Stage constant 2 for multiplying
	mov		%l1, %o1		! Stage the digit count for multiplying
	call	.mul			! Multiply the two values using a sub-routine
	nop
	mov		%o0, %l2		! Store the result in %l2

	add		%l2, %i0, %l2	! Add the sWidth to get the length of the border

bot_border_loop:

	mov		%i3, %o0		! Stage the border character for segment printing
	mov		%l2, %o1		! Stage the length of the border for the segment
							! printing
	call	printSegment	! Print the segment with a function call
	nop

	mov		10, %o0			! Stage '\n' ASCII value for printing
	call	printChar		! Print '\n'
	nop

	inc		%l0				! Increment the for loop counter variable
	cmp		%l0, %l1		! Compare to determin the end of the loop
	bl		bot_border_loop	! Continue to next iteration
	nop

end_bot_border_loop:

	ret
	restore
