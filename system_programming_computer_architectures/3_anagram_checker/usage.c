/* 
 * Filename: usage.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function prints out usage statements based on the type that
 *    is needed: long version or short version.
 * Date: May 16, 2016
 * Source of help: none
 */

#include <stdio.h>
#include "pa3.h"
#include "pa3Strings.h"

/*
 * Function name: usage()
 * Function prototype:
 *    void usage( FILE *stream, enum usageMode u, const char *progName );
 * Description: This function prints out usage statements based on the type that
 *    is needed: long version or short version.
 * Parameters:
 *    FILE *stream - The stream to write to
 *    enum usageMode - The mode that determins the version of usage to print
 *    const char *progName - The name of the program
 * Return value: none
 */

void usage( FILE *stream, enum usageMode u, const char *progName ) {

  if ( u == USAGE_SHORT ) { // Check if the usage is short
    // Print out short usage
    (void) fprintf( stream, STR_USAGE_SHORT, progName, progName );
  } else if ( u == USAGE_LONG ) { // Check if the long version is needed
    // Print out long usage
    (void) fprintf( stream, STR_USAGE_LONG, progName, MIN_HASH_BITS, MAX_HASH_BITS );
  }

}
