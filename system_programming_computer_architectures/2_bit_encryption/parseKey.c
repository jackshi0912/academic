/*
 * Filename: parseKey.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This module will be used to parse the second and third command
 *              line arguments, key0 and key1. Parse the key passed in as str 
 *              by converting the string to an unsigned long. The user can enter
 *              these values in decimal octal, or hexadecimal.
 * Date: April 23rd, 2016
 * Source of help: handouts
 */

#include <errno.h>
#include <stdlib.h>
#include "pa2.h"

/*
 * Function name: parseKey()
 * Function prototype: long parseKey( char * str, unsigned long * key );
 * Description: This function will be used to parse the second and third command
 *              line arguments, key0 and key1. Parse the key passed in as str 
 *              by converting the string to an unsigned long. The user can enter
 *              these values in decimal octal, or hexadecimal.
 * Parameters: char * str - The string to be parsed into an unsigned long
 *             unsigned long * key - The pointer to the resulting key
 * Return value:
 *      Return 0 if the program exited correctly
 *      Return ERANGE_ERR if the value is too large to parse
 *      Return ENDPTR_ERR if the string contains unparsable characters
 */

long parseKey( char * str, unsigned long * key ) {
    
    errno = 0; // Reset errno

    char *endptr; // Declares the endptr variable

    *key = strtoul( str, &endptr, DEF_BASE ); // Collect the key value

    if ( errno != 0 ) { // Check errno
        return ERANGE_ERR;
    } else if ( *endptr != '\0' ) { // Check if the entire string is parsed
        return ENDPTR_ERR;
    }

    return 0;
}

