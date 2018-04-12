/*
 * Filename: parsePassphrase.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Parse the passphrase from the command line arguments by checking
 * if str contains at least PASSPHRASE_SIZE characters. If it is at least this
 * long, copy just the first PASSPHRASE_SIZE character into the passphrase
 * output parameter.
 * Date: April 30, 2016
 */

#include <errno.h>
#include <stdlib.h>
#include "pa2.h"

/*
 * Function name parsePassphrase()
 * Function prototype: long parsePassphrase( char * str, unsigned char *
 * passphrase );
 * Describe: Parse the passphrase from the command line arguments by checking if
 *           str contains at least PASSPHRASE_SIZE characters. If it is at least
 *           this long, copy just the first PASSPHRASE_SIZE characters into the
 *           passphrase output parameter.
 * Parameters:
 *      char * str - The string to parse for passphrase
 *      unsigned char * passphrase - Pointer to the destination of the parsed
 *      string.
 * Return values:
 *      Return 0 if the program executed successfully.
 *      Return LENGTH_ERR if the str is shorter than minimum
 */

long parsePassphrase( char * str, unsigned char * passphrase ) {
    
    for ( int i = 0; i < PASSPHRASE_SIZE; i++ ) { // Loop through 8 bytes of
                                                  //data
        if ( *str == '\0' ) { // Check if the string is less than 8 bytes
            return  LENGTH_ERR;
        }
        // Copy the data
        *passphrase = *str;
        // Increase the pointer memory location
        ++passphrase;
        ++str;
    }

    return 0;
}
