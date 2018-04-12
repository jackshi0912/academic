/*
 * Filename: findAnagram.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: The function prompts the user to enter a word to search for
 *              anagrams of. It then gets the word input from the user and
 *              prints any matching anagrams.
 * Date: May 16, 2016
 * Source of help: handouts, tutors
 */

#include <stdlib.h>
#include <string.h>
#include "pa3Strings.h"
#include "pa3.h"

/*
 * Function name: findAnagrams()
 * Function prototype: 
 *    int findAnagrams( const struct argInfo *argInfo, 
 *    const struct anagramInfo *anagramInfo );
 * Description: The function prompts the user to enter a word to search for
 *              anagrams of. It then gets the word input from the user and
 *              prints any matching anagrams.
 * Parameters:
 *    const struct argInfo* - The struct containing the argument information
 *    const struct anagramInfo* - Pointer to the struct that will contain the
 *        information on all the loaded anagrams.
 *
 * Return values:
 *    Return 0 if the program executed correctly
 *    Return -1 if the program encounters an error
 */

#define FAIL -1
#define SUCCESS 0

int findAnagrams( const struct argInfo *argInfo, const struct anagramInfo *
  anagramInfo ) {

  // Declare variables
  struct anagram *anagram; // anagram from user's input
  struct anagram *result; // The anagrams found
  int collision = 0; // Collision count
  int foundAnagrams = 0; // Check if at least one anagram is found
  char str[BUFSIZ]; // Declare a string buffer to read to
  char str1[BUFSIZ]; // LowercaseSorted str1
  char str2[BUFSIZ]; // LowercaseSorted str2

  (void) fprintf( stdout, STR_USER_PROMPT );
  
  while ( fgets( str, BUFSIZ, stdin ) != NULL ){ // Read a line into str

    str[ strlen(str) - 1 ] = '\0'; // Replace \n

    // Set memory for anagram
    anagram = (struct anagram*) malloc( sizeof( struct anagram ) );

    if ( !anagram ) { // If a null pointer is returned 
      return FAIL; // Failed
    }

    // Check if creating anagram is successful
    if ( createAnagram( str, anagram ) != 0 ) {
      return FAIL;
    }


    //fprintf( stderr, "Anagram created: %ld, %s\n", anagram->hashKey, anagram->word );

    // Check for hash bit flag
    if ( argInfo->flags & HASH_BITS_FLAG ) {
      // Truncate hash key
      anagram->hashKey = truncateHashKey( anagram->hashKey, argInfo->hashBits );
    }

    // Debug
    /*
    fprintf(stderr, "\nBsearch: HashKey used: %ld\n", anagram->hashKey);
    fprintf(stderr, "Bsearch: First Element of the array: %ld, %s\n",
    anagramInfo->anagramPtr->hashKey, anagramInfo->anagramPtr->word );
    fprintf(stderr, "Bsearch: Size of array: %d\n\n", 
    anagramInfo->numOfAnagrams );
    */
    // End debug

    // Debug
    /*
    for ( int i = 0; i < anagramInfo->numOfAnagrams; i++ ){
      fprintf( stderr, "%d, %s: %ld\n", i+1,
      anagramInfo->anagramPtr[i].word, anagramInfo->anagramPtr[i].hashKey );
    }

    fprintf( stderr, "First Element after sort: %d, %s: %ld\n", 1,
    anagramInfo->anagramPtr->word, anagramInfo->anagramPtr->hashKey );
    */
    // END DEBUG

    // Find the matching hashKey anagram
    result = (struct anagram*) bsearch( anagram, anagramInfo->anagramPtr,
    anagramInfo->numOfAnagrams - 1 , sizeof( struct anagram ), 
    hashKeyMemberCompare );

    if ( !result ) { // Check if a matching hashKey is found
      // If nothing is found, print out str
      (void) fprintf( stdout, STR_NO_ANAGRAMS ); 
      (void) fprintf( stdout, "\n%s", STR_USER_PROMPT );
      free( anagram ); // Free anagram memory to expect another round of input
      continue;
    }

    // Move the pointer to the left most matching hashKey
    while (strcmp( result->word, anagramInfo->anagramPtr->word) != 0 && 
    (result - 1)->hashKey == result->hashKey ) {
        result--;
    }

    // Iterate if there are matching words or matching hashKeys
    // Moving to from left to right
    while ( result->hashKey == anagram->hashKey ) {

      lowerCaseSortString( anagram->word, strlen( anagram->word ), str1 );
      lowerCaseSortString( result->word, strlen( result->word ), 
      str2 );

      // Check if both the word and hashKey match
      if( strcmp( str1, str2 ) == 0 ) {
        
        // If this is the first anagram found, print out found_anagram str
        if ( foundAnagrams == 0 ) {
          foundAnagrams++;
          (void) fprintf( stdout, STR_FOUND_ANAGRAMS );
        }
        // Print out the anagrams
        (void) fprintf( stdout, STR_ANAGRAM, (result++)->word );

      } else { // If only hashKey are the same
        ++collision; // Increment collision
        ++result; // Move result pointer to the right
      }
      
      // Check if the end of the array is reached
      if ( strcmp( result->word, 
      anagramInfo->anagramPtr[anagramInfo->numOfAnagrams - 1].word)
       == 0 ) {
        break;
      }
    }

    // If nothing is found, print out str
    if ( foundAnagrams == 0 ) {
      (void) fprintf( stdout, STR_NO_ANAGRAMS ); 
    }
    
    // Check if collision flag is set
    if ( argInfo->flags & COLLISION_FLAG) {
      (void) fprintf( stdout, STR_COLLISIONS, collision );
    }

    free( anagram ); // Free anagram memory to expect another round of input
    collision = 0; // Reset collision count
    foundAnagrams = 0; // Reset if at least one anagram is found
    
    (void) fprintf( stdout,"\n%s", STR_USER_PROMPT );
  } 


  if ( argInfo->flags & COLLISION_FLAG ) { // Check if the collision flag is set
    (void) fprintf( stdout, STR_COLLISIONS, collision ); // Print out collision
  }

  return SUCCESS;
}

