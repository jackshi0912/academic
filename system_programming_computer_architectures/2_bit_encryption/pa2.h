/*
 * Filename: pa2.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Constants and function prototypes used in PA2.
 * Date: April 30, 2016
 * Sources of Help: Given.
 */
#ifndef PA2_H
#define PA2_H

/*
 * Includes
 */
#include <stdio.h>    /* For type FILE */

/*
 * Constants
 */
#define EXPECTED_ARGS 4

/* Argument ranges/values */
#define PASSPHRASE_SIZE 8
#define KEY_SIZE 2
#define MASK_SIZE 2
#define MIN_ROTATE -63
#define MAX_ROTATE +63

/* Argument parsing */
#define DEF_BASE 0

/* Argument indices */
#define PASSPHRASE_INDEX 1
#define KEY_ONE_INDEX 2
#define KEY_TWO_INDEX 3 
#define ROTATE_VALUE_INDEX 4

/* Return values for indicating errors */
#define ENDPTR_ERR -1
#define ERANGE_ERR -2 
#define BOUND_ERR -3
#define LENGTH_ERR -4

/* Initialize each byte of passphrase with a recognizable value for debugging */
#define INIT_PASSPHRASE 0xA5

/* Local function prototypes for MYCIPHER (written in Assembly or C) */
void createMask( unsigned long keys[], unsigned char passphrase[],
                 unsigned long mask[] );
void myCipher( FILE * inFile, unsigned long mask[], long rotateValue );
long parseKey( char * str, unsigned long * key );
long parsePassphrase( char * str, unsigned char * passphrase );
long parseRotateValue( char * str, long * rotateValue );

/*
 * void rotate( unsigned long mask[], long rotateValue );
 * int isInRange( long minRange, long maxRange, long value, long exclusive );
 *
 * Used in assembly routines.
 * Commented out here to keep lint happy and as documentation.
 */

#endif /* PA2_H */
