/*
 * Filename: myCipher.s
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function encrypt/decrypt the user input by using a 64 bit
 * 		mask. The mask is first rotated then xored with the data that
 * 		calls for encryption.
 * Date: May, 1st, 2016
 * Source of Help: Handout, Notes, Tutor
 */

	.global myCipher

	.section ".text"
	
	BUFSIZ = 1024
	CHAR_SIZE = 1
	ENCRYPTION_LENGTH = 8
	WORD_LENGTH = 4

/*
 * Function name: myCipher()
 * Function prototype: void myCipher( FILE * inFile, unsigned long mask[], long
 * rotateValue );
 * Description: This function encrypt/decrypt the user input by using a 64 bit
 * 		mask. The mask is first rotated then xored with the data that
 * 		calls for encryption.
 * Parameter:
 *	arg1: FILE * inFile - the stream to read from
 *	arg2: unsigned long mask[] - The mask to encrypt the data
 *	arg3: long rotateValue - The value to rotate the mask
 *
 * Side Effects: None
 * Error Conditions: None
 * Return Value: None
 *
 * Registers Used:
 * 	%l0 - The address to the block buffer
 *	%l1 - The number of bytes left of the block
 *	%l2 - One byte block content (4 bytes in the main loop)
 *	%l3 - One byte mask value (4 bytes in the main loop)
 *	%l4 - Byte count
 *	%l5 - The number of bytes recieved
 */

myCipher:

	save	%sp, -( 92 + BUFSIZ ) & -8, %sp

	add	%fp, -BUFSIZ, %l0	! Set %0 to be the pointer to buffer
	clr	%l4	! Clear Byte count

	! Use fopen to make %i0 the pointer to file io stream

	mov	%l0, %o0	! Stage the pointer to buffer for fread
	mov	CHAR_SIZE, %o1	! Stage the char byte size for fread
	mov	BUFSIZ, %o2	! Stage the buffer size for freed
	mov	%i0, %o3	! Stage the file pointer for fread

	call	fread	! Call the fread function
	nop

	mov	%o0, %l1	! Set l2 to the size of the input
	mov	%l1, %l5	! Store the size of input for fwrite

	! Rotate the mask
	mov	%i1, %o0	! Stage the mask pointer for rotate
	mov	%i2, %o1	! Stage the rotate value for rotate
	call	rotate		! Call rotate for the mask
	nop

loop:
	
	! XOR the mask and the content 8 byte at a time

	ld	[%l0], %l2	! Load 4 bytes of content
	ld	[%i1], %l3	! Load 4 bytes of mask value
	xor	%l2, %l3, %l2	! Store the xored result in %l2
	st	%l2, [%l0]	! Store the result in stack memory

	ld	[%l0 + WORD_LENGTH], %l2	! Load 4 bytes of content
	ld	[%i1 + WORD_LENGTH], %l3	! Load 4 bytes of mask value
	xor	%l2, %l3, %l2	! Store the xored result in %l2
	st	%l2, [%l0 + WORD_LENGTH]	! Store the result in memory

	add	%l0, ENCRYPTION_LENGTH, %l0	! Increase the memory address
	sub	%l1, ENCRYPTION_LENGTH, %l1	! Decrease the number of data

	! Check if all data are encrypted
	cmp	%l1, %g0	! Compare the num of data left with 0
	be	done
	nop

	! Branch back to loop if there are still more than 8 byte left
	cmp	%l1, ENCRYPTION_LENGTH	! Check if there are more than 8 byte
	bge	loop
	nop

lessThanEightByte:

	ldub	[%l0], %l2	! Load a byte of data
	ldub	[%i1 + %l4], %l3	! load a byte of mask

	xor	%l2, %l3, %l2	! Encrypt a byte of data
	stb	%l2, [%i0]	! Store the data to the buffer block

	inc	%l4	! Increment the byte count
	cmp	%l4, ENCRYPTION_LENGTH	! Compare the byte count to the mask
					! length
	bge	clearByteCount	! If the byte count is the same as mask length,
	nop			! proceed to clear it

continue:

	add	%l0, CHAR_SIZE, %l0	! Increment the buffer address by a byte
	sub	%l1, CHAR_SIZE, %l1	! Subtract the byte left


	cmp	%l1, %g0	! Compare the byte left with zero
	bg	lessThanEightByte	! Branch back to loop if still data left


done:

	add	%fp, -BUFSIZ, %l0	! Restore %0 to be the 
					!pointer to the buffer

	set	standardOut, %o3	! Load the stdout pointer to %o3
	ld	[%o3], %o3	! Dereference the pointer to stdout

	mov	%l0, %o0	! Stage the pointer to buffer for fwrite
	mov	CHAR_SIZE, %o1	! Stage the char byte size for fwrite
	mov	%l5, %o2	! Stage the buffer size for fwrite

	call	fwrite	! Call the fwrite function
	nop

	ret
	restore

clearByteCount:

	clr	%l4	! Clear the byte count

	ba continue	! Go back to the byte wise xor
	nop

