/*
 * Filename: pa0.c Author: Huize Shi Userid: cs30xapo 
 * Description: Example C program for pa0. It calls a couple of functions 
 *              written in assembly and C as examples of function calls and 
 *              parameter passing in C and SPARC assembly (also known as the 
 *              language's and architecture's calling conventions).
 * Date: March 31, 2016
 * Sources of Help: Handout
 */

/*
 * Header file included here.
 * Std C Lib header file first, then local headers.
 */

/* Standard C Library headers use angle brackets <> */
#include <stdlib.h>
#include <stdio.h>

/* Local headers use double quotes "" */
#include "pa0.h"
#include "pa0Strings.h"

/*
 * Function name: main()
 * Function prototype: int main( int argc, char (argv[] );
 *                     or 
 *                     int main( void ); if no command line processing.
 * Description: C main driver which calls C and SPARC assembly routines to
 *              print a greeting, assignment due date, and the square of the
 *              number entered at the command line.
 * Parameters: int containing the number to square
 * Side Effects: Outputs greeting, assignment due date, and square of a number
 * Error Conditions: None.
 * Return Value: EXIT_FAILURE indicating failed execution, and EXIT_SUCCESS
 * indicating successful execution.
 */

int main( int argc, char *argv[] ) {
    struct DueDate dueDate;     /* Structure to hold assignment due date */
    char *endptr;               /* Used by strtol() for error checking */
    int argVal = 0;             /* Value to square from command line */

    /*
     * We decrement argc because the name of the program is the first
     * argument which we do not want to count.
     */
    --argc;

    if ( argc != EXPECTED_ARGS ) {
        /* Error message are printed to stderr */
        (void) fprintf (
                stderr,
                argc < EXPECTED_ARGS ? STR_ERR_MISSING_ARG : STR_ERR_EXTRA_ARG,
                argv[0] );
        (void) fprintf( stderr, STR_ERR_USAGE, argv[0] );
        
        return EXIT_FAILURE;
    }

    /*
     * ---> See: man -s3c strtol <---
     *  strtol() offers error checking/handling. In PA0, we won't be ckecking
     *  for errors with strtol (), but you will need to do this in future
     *  assignments.
     */

    /*
     * Here we would set serrno to 0 because no standard C library function ever
     * set errno to 0.
     */
    argVal = strtol ( argv[1], &endptr, DECIMAL );

    /*
     * Here is where we would check for errors:
     * 1. If errno is no longer 0, an error occured with strtol().
     * 2. If endptr doesn't point to the null character, the string couldn't be
     * converted to an int.
     */

    dueDate.day    = 5;
    dueDate.year   = 2016;
    dueDate.month  = "April";

    /*
     * Print the greeting message
     */
    printWelcome( STR_WELCOME_QUARTER );

    /*
     * print assignment due date
     * printDueDate() take the address of the DueDate struct
     * (a pointer to the DueDate struct)
     */
    printDueDate( &dueDate );

    /*
     * Now square the input and print the result
     */
    (void) printf( STR_SQUARENUM, argVal, squareNum( argVal ) );

    return 0;

