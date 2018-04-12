/* Filename: pa1.h
 * Author: unknown (not modified by me)
 * Userid: cs30xapo
 * Description: The header file contains function prototypes and constants
 * Date: April 9th, 2016
 * Source of help: handouts
 */

#ifndef PA1_H
#define PA1_H

/* Local function prototypes for PA1 (written in Assembly or C) */

int isInRange( long minRange, long maxRange, long value, long exclusive );
void drawS( long sWidth, long sChar, long fillerChar, long borderChar );
int isEven( long value );
int parseVal( char *argv, int min, int max, char *rangeStr, int testEven );

#define EXPECTED_ARGS 5

#define BASE 10
#define S_WIDTH_MIN 4
#define S_WIDTH_MAX 1000

/* Pass to isInRange to check inclusive ranges */
#define INCLUSIVE 0
#define EXCLUSIVE 1

/*
 * See "man ascii" for ASCII char values
 */
#define ASCII_MIN 32     /* ' ' */
#define ASCII_MAX 126    /* '~' */

#define SCHAR_IDX 2      /* The argv index for sChar */
#define FILLER_IDX 3     /* The argv index for fillerChar */
#define BORDER_IDX 4     /* The argv index for borderChar */ 

/*
 * void printChar( char ch );
 * int numOfDigits( long num, int base );
 * void printSegment( long character, long amount);
 *
 * Only called from an Assembly routine. Not needed in any C routine.
 * Would get a lint message about function declared but not used.
 */

#endif /* PA1_H */
