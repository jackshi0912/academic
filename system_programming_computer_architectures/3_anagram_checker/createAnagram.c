/* 
 * Filename: createAnagram.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function will create a new anagram by populating the passed
 *    in struct anagram.
 * Date: May 16, 2016
 * Source of help: handouts, tutor
 */

#include <stdlib.h>
#include <string.h>
#include <errno.h>
#include "pa3.h"

#define FAIL -1
#define SUCCESS 0

/* 
 * Function name: createAnagram()
 * Function prototype:
 *    int createAnagram( const char *str, struct anagram *anagram );
 * Description: This function will create a new anagram by populating the passed
 *    in struct anagram.
 * Parameters:
 *    const char *str - A string containing the word to construct the anagram
 *    struct anagram *anagram - The struct anagram to be populated with data
 * Return value:
 *    Return 0 if the program executed correctly
 *    Return -1 if an error is encountered
 */

int createAnagram( const char *str, struct anagram *anagram ) {

  char lowerStr[BUFSIZ];
  errno = 0; // Set errno to 0
  // Allocate memory for word
  anagram->word = (char *) malloc( strlen( str ) + 1 );

  if ( !(anagram->word) || errno != 0 ) { // Check if the malloc is successful
    return FAIL;
  }

  // Copy the string to the word in struct
  (void) strncpy( anagram->word, str, strlen( str ) + 1 );
  // Get the lower case sorted string
  lowerCaseSortString( str, strlen( str ), lowerStr );
  
  // Debug
  /*
  fprintf( stderr, "\nLowerCase: %s\n", lowerStr );
  fprintf( stderr, "HashValue: %ld\n\n", hashString( lowerStr ) );
  */
  // End debug

  // Set the hashkey to the result of hashString
  anagram->hashKey = hashString( lowerStr );
  
  return SUCCESS;
}
