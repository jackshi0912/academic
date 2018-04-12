/* Filename: pa3EC.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function is the main driver of the program. This function
 *              will be delegating a majority of the functionality to the other
 *              functions. Whenever an error is encountered, immediately print
 *              the appropriate error message, print the short usage statement,
 *              and return, indicating failure.
 * Date: May. 16, 2016
 * Source of help: handout, tutor
 */

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <errno.h>
#include "pa3Strings.h"
#include "pa3EC.h"

/*
 * Function name: main()
 * Function prototype: int main( int argc, char *argv[] );
 * Description: This function is the main driver of the program. This function
 *              will be delegating a majority of the functionality to the other
 *              functions. Whenever an error is encountered, immediately print
 *              the appropriate error message, print the short usage statement,
 *              and return, indicating failure.
 * Parameters:
 *    int argc - The number of command line arguments + 1
 *    char *argv[] - The values of each command line arguments
 * Return value:
 *    Return EXIT_SUCCESS if the program executed correctly
 *    Return EXIT_FAILURE upon encountering an error
 */

int main( int argc, char *argv[] ) {

  // Declare variables
  // Allocate memory for struct anagramInfo
  struct anagramInfo *anagramInfo;
  struct argInfo * argInfo = ( struct argInfo*) 
    malloc( sizeof( struct argInfo ) );
  FILE *fp;
  errno = 0; // Reset errno

  // Check if the parseArg is successful
  if ( parseArgs( argc, argv, argInfo ) != 0 ) {
    usage( stderr, USAGE_SHORT, argv[0] ); // Print usage to stderr
    free(argInfo); // Free memory
    return EXIT_FAILURE; 
  }
  // Check if the helpflag is set
  if ( HELP_FLAG & argInfo->flags ) {
    usage( stdout, USAGE_LONG, argv[0] ); // Print long usage to stdout
    free(argInfo); // Free memory
    return EXIT_SUCCESS;
  }
  // Check if the extension is there
  if( !strrchr( argInfo->afFileName, '.' ) 
  || strcmp( strrchr( argInfo->afFileName, '.' ), FILE_EXTENSION ) != 0 ) {
    (void) fprintf( stderr, STR_ERR_AF_EXTENSION ); // Print out err string
    usage( stderr, USAGE_SHORT, argv[0] ); // Print usage to stderr
    free(argInfo); // Free memory
    return EXIT_FAILURE;
  }

  // parseArgs is successful
  // Open file for read-only
  fp = fopen( argInfo->afFileName, "r" );
  
  if ( errno == EACCES ) { // Checking for permission error
    (void) fprintf( stderr, STR_ERR_AF_PERMISSION ); // Print out errMsg
    usage( stderr, USAGE_SHORT, argv[0] ); // Print usage to stderr
    free(argInfo); // Free memory
    return EXIT_FAILURE;
  }

  if ( errno != 0 ) { // Check if the file is invalid
    (void) fprintf( stderr, STR_ERR_AF_INVALID ); // Print out errMsg
    usage( stderr, USAGE_SHORT, argv[0] ); // Print usage to stderr
    free(argInfo); // Free memory
    return EXIT_FAILURE;
  }

  // Set aside memory for anagramInfo
  anagramInfo = ( struct anagramInfo* ) malloc( sizeof( struct anagramInfo ) );
  
  // Check if loading file is successful
  if ( loadAF( fp, anagramInfo ) != 0 ) {
    (void) fprintf( stderr, STR_ERR_AF_LOAD ); // Print out load errMsg
    usage( stderr, USAGE_SHORT, argv[0] ); // Print usage to stderr
    free(argInfo); // Free memory
    free(anagramInfo); // Free memory
    return EXIT_FAILURE;
  }
  
  // Truncate the hashKey of the anagrams in the array if -b flag is set
  if ( argInfo->flags & HASH_BITS_FLAG ) {
    for ( size_t i = 0; i < (size_t) anagramInfo->numOfAnagrams; i++ ) {
      anagramInfo->anagramPtr[i].hashKey = truncateHashKey(
      anagramInfo->anagramPtr[i].hashKey, argInfo->hashBits );
    }
  }

  // Sort the anagramInfo->anagramPtr
  qsort( anagramInfo->anagramPtr, anagramInfo->numOfAnagrams, sizeof(struct
  anagram ), anagramCompare );

  // DEBUG Code
  /*
  for ( int i = 0; i < anagramInfo->numOfAnagrams; i++ ){
    fprintf( stderr, "%d, %s: %ld\n", i+1,
    anagramInfo->anagramPtr[i].word, anagramInfo->anagramPtr[i].hashKey );
  }

    fprintf( stderr, "First Element after sort: %d, %s: %ld\n", 1,
    anagramInfo->anagramPtr->word, anagramInfo->anagramPtr->hashKey );
  */
  // END DEBUG

  // Loading .af file is successful
  // Find anagram status

  if ( findAnagrams( argInfo, anagramInfo ) != 0 ) {
    (void) fprintf( stderr, STR_ERR_CREATE_ANAGRAM ); // Print out errMsg
    usage( stderr, USAGE_SHORT, argv[0] ); // Print usage to stderr
    free(argInfo); // Free memory
    free(anagramInfo); // Free memory
    return EXIT_FAILURE;
  }

  free(argInfo); // Free memory
  free(anagramInfo); // Free memory
  return EXIT_SUCCESS;

}

