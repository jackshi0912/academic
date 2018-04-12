/* Filename: loadAF.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function populates the anagramInfo struct by reading the
 *              content of the .af file. This is done by making repetitive calls
 *              to loadAnagram until the end of file is reached.
 * Date: May. 16th, 2016
 * Source of help: handouts, tutor
 */
#include <stdlib.h>
#include "pa3.h"

/*
 * Function name: loadAF()
 * Function prototype:
 *    int loadAF( FILE * stream, struct anagramInfo * anagramInfo );
 * Description: This function populates the anagramInfo struct by reading the
 *              content of the .af file. This is done by making repetitive calls
 *              to loadAnagram until the end of file is reached.
 * Parameters:
 *    FILE *stream - The file stream containing the anagrams
 *    struct anagramInfo *anagramInfo - The pointer to the struct that is going
 *        to contain all the anagram information.
 */

int loadAF( FILE * stream, struct anagramInfo * anagramInfo ) {

  // Allocating memory for anagramInfo struct
  //  anagramInfo = (struct anagramInfo*) malloc( sizeof(struct anagramInfo) );
  struct anagram *retVal; // Declare a temporary pointer
  int loadStatus = 1; // Collector of the return value

  // Allocate memory for the array of anagrams within anagramInfo
  anagramInfo->anagramPtr = (struct anagram*) 
      malloc( sizeof( struct anagram ) );
  anagramInfo->numOfAnagrams = 0; // Reseet numOfAnagrams
  retVal = anagramInfo->anagramPtr; // Use a temp pointer

  while (loadStatus == 1) { // Process continues while there are lines to read

    // Enlarging the array of anagrams by one anagram
    retVal = realloc( anagramInfo->anagramPtr, 
    ( anagramInfo->numOfAnagrams + 1 ) * sizeof( struct anagram ) );

    if ( !retVal ) { // Check if the enlarging is successful
      for ( size_t i = 0; i < (size_t) ( anagramInfo->numOfAnagrams); i++ ){
        free( &(anagramInfo->anagramPtr[i]) ); // Deallocate memory in case of failure
      }
      return -1;
    }

    // set the array pointer inside anagramInfo
    anagramInfo->anagramPtr = retVal; 

    // Load a anagram
    loadStatus = loadAnagram( stream, 
        &(anagramInfo->anagramPtr[anagramInfo->numOfAnagrams]) );

    // Check the return value
    if (loadStatus == 0) { // End of file is reached
      return 0;

    } else if ( loadStatus == -1 ) { // Error occured
      for ( size_t i = 0; i < (size_t) (anagramInfo->numOfAnagrams); i++ ){
        free( &(anagramInfo->anagramPtr[i]) ); // Deallocate memory in case of failure
      }
      return -1;

    }

    ++(anagramInfo->numOfAnagrams); // Move to next anagram in array
  }

  return 0;
}
