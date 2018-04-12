/*
 * Filename: loadAnagrams.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function will load a single anagram from the stream and
 *              populate the fields of the passed in anagramInfo struct. The 
 *              array within the anagram struct will be allocated on the heap.
 * Date: May. 16, 2016
 * Source of help: handouts
 */

#include <stdlib.h>
#include <string.h>
#include "pa3.h"


/* 
 * Function name: loadAnagram()
 * Function prototype: 
 *      int loadAnagram( FILE * stream, struct anagram * anagram )
 * Description: This function will load a single anagram from the stream and
 *              populate the fields of the passed in anagramInfo struct. The 
 *              array within the anagram struct will be allocated on the heap.
 * Parameters:
 *      FILE *stream - The file stream to read from
 *      struct anagram *anagram - Pointer to an anagram that is to be populated
 * Return value:
 *      Return 1 when the anagram is loaded successfully
 *      Return -1 when the loading fails
 *      Return 0 if the end of file is reached
 */

#define BASE 10
#define FAIL -1
#define SUCCESS 1
#define END 0

int loadAnagram( FILE * stream, struct anagram * anagram ) {

  // Declare variables
  char str[BUFSIZ];
  int key;
  // char keyStr[MAX_HASH_BYTES];

  // Loading the hashkey into anagram
  if( !fread( &key, MAX_HASH_BYTES, 1,  stream ) ) {
    return END;
  }

  // Copy the hashkey value to the struct
  (void) memcpy( &(anagram->hashKey), &key, MAX_HASH_BYTES );

  // Check if there is a line left
  if ( !fgets( str, BUFSIZ, stream ) ) {
    return END;
  }

  // Allocate memory for struct member
  anagram->word = (char*) malloc( strlen(str) + 1 );
  
  if ( !anagram->word ) { // Check if there are memory avaliable
    return FAIL;
  }

  // Copy the string with null termination
  (void) memcpy( anagram->word, str, strlen(str) + 1 );

  return SUCCESS;

}
