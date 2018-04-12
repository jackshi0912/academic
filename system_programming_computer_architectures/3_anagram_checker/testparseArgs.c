/*
 * Filename: testparseArgs.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Unit test program to test the function parseArgs.
 * Date: May 16, 2016
 * Sources of Help: none
 */ 

#include <string.h>
#include <stdlib.h>

#include "test.h"
#include "pa3.h"


/*
 * Unit Test for parseArgs.c
 *
 * int parseArgs(int argc, char * const argv[], struct argInfo * argInfo);
 * 
 * Parses the command line arguments using getopt() and sets the fields of the
 * argInfo struct accordingly.
 * 
 * Returns 0 on success, 1 on failure.
 */

void testparseArgs() {
  printf( "Testing parseArgs()\n" );

  /*
   * Test #1:
   * All flags are entered with no errors.
   */

  printf( "\nTest 1\n\n" );
  int argc = 6;
  char * argv[argc];

  /* Initialize command line arguments */
  argv[0] = "programName";
  argv[1] = "-f";
  argv[2] = "anagrams.af";
  argv[3] = "-c";
  argv[4] = "-b";
  argv[5] = "13";

  struct argInfo argInfo;

  /* Check return value from parseArgs (0 for success) */
  TEST( parseArgs( argc, argv, &argInfo ) == 0 );

  /* Check that the fields of argInfo were properly set */
  TEST( (argInfo.flags & COLLISION_FLAG) == COLLISION_FLAG );
  TEST( strncmp( argInfo.afFileName, "anagrams.af", BUFSIZ ) == 0 );
  TEST( argInfo.hashBits == 13 );


  /*
   * Test #2:
   * Flgas are entered with errors.
   */
  
  printf( "\nTest 2\n\n" );
  argc = 1;
  char * argv1[argc];

  /* Initialize command line arguments */
  argv1[0] = "programName";

  /* Check return value from parseArgs (0 for success) */
  TEST( parseArgs( argc, argv1, &argInfo ) == -1 );

  /* Check that the fields of argInfo were properly set */
  TEST( (argInfo.flags & COLLISION_FLAG) == COLLISION_FLAG );
  TEST( strncmp( argInfo.afFileName, "anagrams.af", BUFSIZ ) == 0 );

  /*
   * Test #3:
   * Set the argument of -b out side of range
   */

  printf( "\nTest 3\n\n" );
  argc = 6;
  char * argv2[argc];

  /* Initialize command line arguments */
  argv2[0] = "programName";
  argv2[1] = "-f";
  argv2[2] = "anagrams.af";
  argv2[3] = "-c";
  argv2[4] = "-b";
  argv2[5] = "0";

  /* Check return value from parseArgs (0 for success) */
  TEST( parseArgs( argc, argv2, &argInfo ) == -1 );

  /* Check that the fields of argInfo were properly set */
  TEST( (argInfo.flags & COLLISION_FLAG) == COLLISION_FLAG );
  TEST( strncmp( argInfo.afFileName, "anagrams.af", BUFSIZ ) == 0 );


  /*
   * Test #4:
   * Check with arg 5 being out side of upper bound
   * Other parameters are carried over
   */

  printf( "\nTest 4\n\n" );

  /* Check return value from parseArgs (0 for success) */
  argv2[5] = "33";
  TEST( parseArgs( argc, argv2, &argInfo ) == -1 );

  /* Check that the fields of argInfo were properly set */
  TEST( (argInfo.flags & COLLISION_FLAG) == COLLISION_FLAG );
  TEST( strncmp( argInfo.afFileName, "anagrams.af", BUFSIZ ) == 0 );


  /*
   * Test #5:
   * Check if letters are entered in place of numbers
   * Other parameters are carried over
   */

  printf( "\nTest 5\n\n" );

  /* Check return value from parseArgs (0 for success) */
  argv2[5] = "13abc";
  TEST( parseArgs( argc, argv2, &argInfo ) == -1 );

  /* Check that the fields of argInfo were properly set */
  TEST( (argInfo.flags & COLLISION_FLAG) == COLLISION_FLAG );
  TEST( strncmp( argInfo.afFileName, "anagrams.af", BUFSIZ ) == 0 );

  /*
   * Test #6:
   * Test values that are out of the upper bound
   * Other parameters are carried over
   */

  printf( "\nTest 6\n\n" );

  /* Check return value from parseArgs (0 for success) */
  argv2[5] = "9999999999999999999";
  TEST( parseArgs( argc, argv2, &argInfo ) == -1 );

  /* Check that the fields of argInfo were properly set */
  TEST( (argInfo.flags & COLLISION_FLAG) == COLLISION_FLAG );
  TEST( strncmp( argInfo.afFileName, "anagrams.af", BUFSIZ ) == 0 );

  printf( "Finished running tests on parseArgs()\n" );
}


int main() {
  testparseArgs();

  return 0;
}
