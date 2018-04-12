/*
 * Filename: createMask.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This module creates the 64-bit crypto mask that will be used to
 * 		encrypt the data. The 64-bit mask will be stored in mask as an
 * 		arry of two 32-bit mask values. Create the mask by XORing the
 * 		passphrase with the keys. This will require loading the
 * 		appropriate values from keys and passphrase, and storing the
 * 		result in mask.
 * Date: Apirl 25th, 2016
 * Source of help: handouts
 */

	.global createMask	! Declares the symbl to be globally visible
	.section ".text"	! The text segment begins here
	
	INCRYPTION_BYTE = 4
/*
 * Function: createMask()
 * Function prototype: void createMask( unsigned long keys[], unsigned char
 * 	passphrase[], unsigned long mask[] );
 * Description: This function creates the 64-bit crypto mask that will be used to
 * 		encrypt the data. The 64-bit mask will be stored in mask as an
 * 		arry of two 32-bit mask values. Create the mask by XORing the
 * 		passphrase with the keys. This will require loading the
 * 		appropriate values from keys and passphrase, and storing the
 * 		result in mask.
 * Parameters:
 *	arg1: unsigned long keys[] - The keys to be XORed with the passphrase
 *	arg2: unsigned char passphrase[] - They passphrase to be XORed with key
 *	arg3: unsigned long mask[] - The mask generated as the result of XOR the
 *		keys and the passphrase.
 *
 * Side Effects: None
 * Error Condition: None
 * Return Value: None
 *
 * Registers Used:
 *	%l0 - xor value of the first mask
 *	%l3 - xor value of the second mask
 *	%l1 - 1 bytes of keys
 *	%l2 - 1 bytes of passphrasse
 */

createMask:
	save	%sp, -( 92 ) & -8, %sp
	

	ld	[%i0], %l1	! Load 4 byte of keys
	ld	[%i1], %l2	! Load 4 byte of passphrase
	xor	%l1, %l2, %l0	! XOR the key and passphrase and store the
				! result in the mask variable

	ld	[%i0 + INCRYPTION_BYTE], %l1	! Load 4 byte of keys
	ld	[%i1 + INCRYPTION_BYTE], %l2	! Load 4 byte of passphrase
	xor	%l1, %l2, %l3	! XOR the key and passphrase and store the
				! result in the mask variable

	st	%l0, [%i2]	! Store the mask in memory
	st	%l3, [%i2 + INCRYPTION_BYTE]	! Store the second part

	ret
	restore
