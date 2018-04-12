/*
 * Filename: parseArgs.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Parse the argument and read the flags. This function examins if
 *              all the neccesary arguments are in place for each flag. The
 *              program then stores all the information into a struct.
 * Date: May 16th, 2016
 * Source of Help: handouts
 */

#include <stdlib.h>
#include <strings.h>
#include <stdio.h>
#include <errno.h>
#include "pa3.h"
#include "pa3Strings.h"

#define BASE10 10
#define SUCCESS 0
#define FAIL -1

/*
 * Function name parseArgs()
 * Function prototype: int parseArgs( int argc, char * const argv[], struct
 *    argInfo * argInfo);
 * Description: Parse the argument and read the flags. This function examins if
 *              all the neccesary arguments are in place for each flag. The
 *              program then stores all the information into a struct.
 * Parameters:
 *    int argc - The number of arguments passed from main
 *    char * const argv[] - Argument values passed from main
 *    struct argInfo *argInfo - Pointer to the struct where all the argument
 *        values are going to be stored.
 * Return value:
 *    Return 0 indicating success
 *    Return -1 indicating failure
 */

int parseArgs( int argc, char * const argv[], struct argInfo * argInfo ) {

    // Declaring variables
    long hashBits;
    char *ptr;
    char errMsg[ BUFSIZ ];
    int fflag = 0;
    int c;

    // Clearing the struct
    argInfo->flags = 0;
    argInfo->hashBits = 0;

    while( ( c = getopt( argc, argv, ARG_STR ) ) != -1 ) { // Parse flags

        switch( c ) {   // Check flag value

            case CHAR_HELP_FLAG: // Check for help flag
                argInfo->flags = argInfo->flags | HELP_FLAG;
                optind = 1;
                return SUCCESS;

            case CHAR_AF_FLAG: // Check for file flag
                fflag = 1;
                argInfo->afFileName = optarg;
                break;

            case CHAR_HASH_BITS_FLAG: // Check for hash bit flag
                argInfo->flags = argInfo->flags | HASH_BITS_FLAG;
                hashBits = strtol( optarg, &ptr, BASE10 );
                
                if ( errno != 0 ) { // Check for too large input
                    (void) snprintf( errMsg, BUFSIZ, STR_ERR_CONVERTING, optarg,
                    BASE10 );
                    perror( errMsg ); // Print out error message
                    optind = 1;
                    return FAIL;
                }

                if ( *ptr != '\0' ) { // Check for invalid character
                    (void) fprintf( stderr, STR_ERR_NOTINT, optarg );
                    optind = 1;
                    return FAIL;
                }

                if ( !isInRange( MIN_HASH_BITS, MAX_HASH_BITS, hashBits,
                INCLUSIVE) ) { // Check if the value is in range
                    (void) fprintf( stderr, STR_ERR_BAD_HASH_BITS, 
                    MIN_HASH_BITS, MAX_HASH_BITS ); // Output error to stderr
                    optind = 1;
                    return FAIL;
                }
               
                //  Setting the hashbit after all the potential errors are
                //  checked
                argInfo->hashBits = hashBits;
                break;

            case CHAR_COLLISION_FLAG: // Check for collision flag
                argInfo->flags = argInfo->flags | COLLISION_FLAG;
                break;

        }
        
    }
    
    if ( fflag == 0 ) { // Check if -f flag is parsed
        optind = 1;
        return FAIL;
    }

    if ( optind != argc ) { // Check for left over operand
        (void) fprintf( stderr, STR_ERR_EXTRA_ARGS, argv[ optind ] );
        optind = 1;
        return FAIL;
    }

    optind = 1;
    return SUCCESS;
}
