/*
 * Filename: pa1.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: The main function of this program. It parse information from
 *              command line arguments. It examins the parsed input and decide
 *              if the arguments are legal for this program.
 * Date: April 9th, 2016
 * Source of help: handouts, www.tutorialspoint.com
 */

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include "pa1Strings.h"
#include "pa1.h"

/*
 * Function name: main()
 * Function prototype: int main( int argc, char *argv[] );
 * Description: The main function of this program. It parse information from
 *              command line arguments. It examins the parsed input and decide
 *              if the arguments are legal for this program.
 * Parameters:  int argc - The number of arguments being passed through
 *              char *argv[] - Command line arguments to be analyzed
 */
int main( int argc, char *argv[] )
{
    int sWidth; /* The width of the S drawing */
    int sChar; /* THe character to fill the S drawing */
    int fillerChar; /* The character to fill the empty space */ 
    int borderChar; /* The character used to construct boarder */

    /* Decrement the number of argument count to match the user input */
    if ( argc != EXPECTED_ARGS ) { /* Check if the argument count is valid */
        fprintf( stderr, STR_USAGE, argv[0], S_WIDTH_MIN, S_WIDTH_MAX, 
                ASCII_MIN, ASCII_MAX, ASCII_MIN, ASCII_MAX, ASCII_MIN, ASCII_MAX );
        return EXIT_FAILURE;
    }

    /* Parse the sWidth */
    sWidth = parseVal( argv[1], S_WIDTH_MIN, S_WIDTH_MAX, 
            STR_ERR_S_WIDTH_RANGE, 1 );

    /* Parse the sChar */
    sChar = parseVal( argv[SCHAR_IDX], ASCII_MIN, 
            ASCII_MAX, STR_ERR_S_CHAR_RANGE, 0 );

    /* Parse the fillerChar */
    fillerChar = parseVal( argv[FILLER_IDX], ASCII_MIN, ASCII_MAX,
            STR_ERR_FILLERCHAR_RANGE, 0 );

    if ( argv[SCHAR_IDX] == argv[FILLER_IDX] ){
        fprintf( stderr, STR_ERR_S_CHAR_FILLERCHAR_DIFF, 
                argv[SCHAR_IDX], argv[FILLER_IDX] );
    }

    /* Parse the boarderChar */
    borderChar = parseVal( argv[BORDER_IDX], ASCII_MIN, ASCII_MAX,
            STR_ERR_BORDERCHAR_RANGE, 0 );

    if ( argv[FILLER_IDX] == argv[BORDER_IDX] ){
        fprintf( stderr, STR_ERR_S_CHAR_BORDERCHAR_DIFF , 
                argv[SCHAR_IDX], argv[BORDER_IDX] );
    } 

    if ( sWidth != -1 && sChar != -1 && fillerChar != -1 && borderChar != -1 ) {
        drawS( sWidth, sChar, fillerChar, borderChar );
    }

    return EXIT_SUCCESS;
}

/*
 * Function name: parsVal()
 * Function prototype: int parseVal( char *argv, int min, int max, 
 *                     char *rangeStr, int testEven )
 * Description: This function use strtol() to extract the useful integer values
 * from a given string. It also output error messages when appropriate.
 * Parameters: char *argv - The input string to be parsed for integer
 *             int min - The lower bound for valid input
 *             int max - The upper bound for valid input
 *             char *rangeStr - The error message string
 *             int testEven - flag to test the value for eveness
 */
int parseVal( char *argv, int min, int max, char *rangeStr, int testEven ) {

    int outOfBound = 0; /* The out of bound flag to foce retunr -1 */
    char *endptr; /* The remaining string */
    errno = 0; /* Clear errno */

    /* Use strtol to extracted the int */
    int val = strtol( argv, &endptr, BASE); /* Use strtol() to parse int value */
    if ( errno != 0 ) { /* Check if an overflow occured during strtol */
        /* Create the error message */
        char errorMsg[ BUFSIZ ];
        snprintf( errorMsg, BUFSIZ, 
                STR_CONVERTING, argv, BASE );
        perror( errorMsg );
        return -1;

    } else if ( *endptr != '\0' ) { /* Check if the input contain any char */
        fprintf( stderr, STR_NOTINT, argv );
        return -1;
    }

    /* The error message constructed based on specifiers */
    char errMsg[ BUFSIZ ]; 

    /* Construct the error message based on specifiers of each error message */
    snprintf( errMsg, BUFSIZ, rangeStr, val, min, max );


    /* Check if the parsed value is in range */
    if ( !isInRange( min, max, val, INCLUSIVE ) ) {
        fprintf( stderr, rangeStr, val, min, max);
        outOfBound = -1;
    }

    /* Check if the test for Eveness is required and if the value is even */
    if ( testEven && !isEven( val ) ) {
        fprintf( stderr, STR_ERR_S_WIDTH_EVEN, val );
        return -1;
    }

    if ( outOfBound != 0 ) {
        return -1;
    }

    return val;
}

