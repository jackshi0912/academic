/*
 * Filename: truncateHashKey.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function truncates the hashKey by extracting the lower
 * 	number of bits specified by numBits.
 * Date: May 16th, 2016
 * Source of Help: Handout
 */

	.global truncateHashKey

	.section ".text"

	INITIAL_NUM_OF_SIZ = 32
/*
 * Function name: truncateHashKey()
 * Function prototype: long truncateHashKey( long hashKey, long numBits )
 * Description: This function truncates the hashKey by extracting the lower
 * 		number of bits specified by numBits.
 * Parameter:
 *	arg1: long hashKey - %i0 The hashKey to be truncated
 *	arg2: long numBits - %i1 The number of bits to truncate
 *
 * Side Effects: None
 * Error Conditions: None
 * Return value The truncatedhashKey
 *
 * Registers Used:
 *	%l0 - Number of bits to remove
 */

truncateHashKey:

	save	%sp, -(92) & -8, %sp

	neg	%i1
	add	%i1, INITIAL_NUM_OF_SIZ, %l0

	sll	%i0, %l0, %i0
	srl	%i0, %l0, %i0

	ret
	restore
