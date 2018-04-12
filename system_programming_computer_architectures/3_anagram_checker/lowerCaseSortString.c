/* 
 * Filename: lowerCaseSortString.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function takes in an pointer to a string and change the
 *              elements of the array to a sorted and lower case version. This
 *              result is then copied to populate the pointer passed in as the
 *              destination.
 * Date: May, 16th, 2016
 * Source of help: handouts
 */

#include <string.h>
#include <stdlib.h>
#include <ctype.h>
#include <stdio.h>
#include "pa3.h"

/*
 * Function name: lowerCaseSortString
 * Function prototype: 
 *    void lowerCaseSortString( const char * src, int n, char *dest );
 * Description: This function takes in an pointer to a string and change the
 *              elements of the array to a sorted and lower case version. This
 *              result is then copied to populate the pointer passed in as the
 *              destination.
 * Parameters:
 *    arg1: const char *src - %i0 The incomming pointer to a string to be sorted
 *    arg2: int n - The length of the array without the null termination
 *    arg3: char *dest - The pointer to the destination memory block
 * Return value: none
 */
void lowerCaseSortString( const char * src, int n, char * dest ) {

    (void) strncpy( dest, src, n ); // Copy the source data to destination

    for ( int i = 0; i < n; i++ ) {
        dest[i] = tolower( dest[i] ); // Change all char at dest to lower case
    }

    qsort( dest, n, 1, charCompare ); // Quick sort the destination characters
    dest[n] = '\0'; // Adding on the null termination

}
