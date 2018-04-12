/*
 * Filename: pa2.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function is the main driver for the program. It will first
 *              parse all of the command line arguments. If all arguments are 
 *              valid, it will create the 64-bit crypto mask and perform the 
 *              encryption/decryption from stdin. Otherwise, the appropriate
 *              error message will be printed.
 * Date: April 30, 2016
 * Souce of help: handouts, classnotes
 */

#include <stdlib.h>
#include <stdio.h>
#include "pa2.h"
#include "pa2Strings.h"

/*
 * Function name: main()
 * Function prototype: int main( int argc, char * argv[] );
 * Description: This function is the main driver for the program. It will first
 *              parse all of the command line arguments. If all arguments are 
 *              valid, it will create the 64-bit crypto mask and perform the 
 *              encryption/decryption from stdin. Otherwise, the appropriate
 *              error message will be printed.
 * Parameters:
 *      int argc - The number of command line arguments +1
 *      char * argv[] - The array of string commandline inputs
 *          - passphrase, key0, key1, rotateValue
 * Return value:
 *      Return EXIT_SUCESS if the program executed correctly
 *      Return EXIT_FAILURE if any errors were encountered in parsing the
 *      command line inputs.
 */

int main( int argc, char * argv[] ) {

    // Declare and initialize assist variables
    int errCount = 0;
    long errVal = 0;
    char errMsg[ BUFSIZ ];

    // Declare and initialize values needed for encryption
    unsigned char * passphrase = (unsigned char *) malloc( PASSPHRASE_SIZE );
    unsigned long keys[KEY_SIZE];
    long rotateValue;
    unsigned long mask[MASK_SIZE];

    --argc;

    (void) setvbuf( stdout, NULL, _IONBF, 0 );

    if ( argc != EXPECTED_ARGS ) { // Check if the number of argument is good
        // Print out error message to stderr
        fprintf( stderr, STR_USAGE, argv[0], MIN_ROTATE, MAX_ROTATE );
        return EXIT_FAILURE;
    }

    // Parsing passphrase
    // Check if the return value indicates any error
    if ( parsePassphrase( argv[PASSPHRASE_INDEX], passphrase ) != 0 ) {
        // Print out error message to stderr
        fprintf( stderr, STR_ERR_BAD_PASSPHRASE, PASSPHRASE_SIZE );
        ++errCount; // Increase error count
    }

    // Parsing the first key
    // Collect the return value from parseKey
    errVal =  parseKey( argv[KEY_ONE_INDEX], &keys[0] );

    if ( errVal == ERANGE_ERR ) { // Check the return value
        // Print out error message to stderr
        snprintf( errMsg, BUFSIZ, STR_ERR_CONVERTING, argv[KEY_ONE_INDEX],
        DEF_BASE );
        perror( errMsg ); // Print out error message
        ++errCount; // Increase error count
    } else if ( errVal == ENDPTR_ERR ) { // Check for endptr error
        // Print out error message to stderr
        fprintf( stderr, STR_ERR_NOTINT, argv[KEY_ONE_INDEX] );
        ++errCount;
    }
    
    // Parsing the second key
    errVal =  parseKey( argv[KEY_TWO_INDEX], &keys[1] );

    if ( errVal == ERANGE_ERR ) { // Check for return value
        snprintf( errMsg, BUFSIZ, STR_ERR_CONVERTING, argv[KEY_TWO_INDEX],
        DEF_BASE );
        perror( errMsg ); // Process error message
        ++errCount;
    } else if ( errVal == ENDPTR_ERR ) {
        // Print out error message
        fprintf( stderr, STR_ERR_NOTINT, argv[KEY_TWO_INDEX] );
        ++errCount;
    }
  
    // Parsing the rotate value
    // Collect the return value of parseRotateValue function
    errVal = parseRotateValue( argv[ROTATE_VALUE_INDEX], &rotateValue );
    
    // Analyze the return value
    if ( errVal == ERANGE_ERR ) {
        // Print out error message
        snprintf( errMsg, BUFSIZ, STR_ERR_CONVERTING, argv[ROTATE_VALUE_INDEX],
        DEF_BASE );
        perror( errMsg ); // Process the error message
        ++errCount;
    } else if ( errVal == ENDPTR_ERR ) {
        // Print out error message
        fprintf( stderr, STR_ERR_NOTINT, argv[ROTATE_VALUE_INDEX] );
        ++errCount;
    } else if ( errVal == BOUND_ERR ) {
        // Print out error message
        fprintf( stderr, STR_ERR_BAD_ROT, MIN_ROTATE, MAX_ROTATE );
        ++errCount;
    }

    // Check if the encryption process should continue

    if ( errCount != 0 ) { // Check if any error has occured
        // Print out error message
        fprintf( stderr, STR_ERR_NUM_ERRORS, errCount );
        return EXIT_FAILURE;
    }

    // Generating a mask
    createMask( keys, passphrase, mask );
    
    // Calling myCipher
    myCipher( stdin, mask, rotateValue );
    

    return EXIT_SUCCESS;
}
