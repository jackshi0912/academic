/*
 * Filename: isEven.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Check if a incomming value is even
 * Date: Apirl 9th, 2016
 * Source of Help: Handout
 */


	.global isEven      ! Declares the symbol to be globally visible
	.section ".text"	! The start of the text segment
    
	EVEN_CONST = 2		! The constant base to evaluate a value's eveness

/*
 * Function name: isEven
 * Function prototype: int isEven( long value );
 * Description: Check if the num is even
 * Parameters:
 *      arg 1: long value -- The value to check if it's even
 * 
 * Side Effects: None
 * Error Condition: None
 * Return Value: Int represent if the input is even or odd
 * 
 * Registers Used
 *      %i0 - arg 1 -- The input to be test for eveness
            - return value -- 1 means even, 0 means odd
 *      %o0 - param 1 to .rem subroutine - The input
 *      %o1 - param 2 to .rem subroutine - The constant 2 to test eveness
 */
isEven:                 ! %i0 contains the long value to be tested for eveness
                        ! int isEven( long value );

	save %sp, -96, %sp

	mov %i0, %o0		! Move the input to o0 reg for modulous operator
	mov EVEN_CONST, %o1 ! Put 2 as the second modulous operand
	call .rem			! Mod the input by 2
    nop
	cmp %o0, %g0		! Check if the modulous result is 0
	bne notEven			! Branch out if the modulous result is not equal to 0
    nop
	mov 1, %i0			! Otherwise, move 1 to i0 for return
	ret                 ! Return the result
	restore             ! Restore in ret's delay slot

notEven:                ! Return 0 if the modulous result is not equal to 0
	mov %g0, %i0        ! Move 0 from global register to i0 for return
	ret                 ! Return the result
	restore             ! Restore in ret's delay slot
