/*
 * Filename: testisInRange.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Unit test program to test the function isInRange.
 * Date: April 9th, 2016
 * Sources of Help: Handout
 */ 

#include <limits.h>	/* For LONG_MIN and LONG_MAX */
#include "pa1.h"	/* For isInRange() function prototype */
#include "test.h"	/* For TEST() macro and stdio.h */

/*
 * int isInRange( long minRange, long maxRange, long value, long exclusive );
 *
 * If exclusive is 0, check the inclusive range.
 * If exclusive is non-0, check the exclusive range.
 *
 * Returns -1 if minRange is greater than maxRange.
 * Returns 1 if value is within minRange and maxRange.
 * Returns 0 otherwise.
 */

void testisInRange( )
{
  (void) printf( "Testing isInRange()\n" );

  /* Test around 0 */
  TEST( isInRange( 0, 0, 0, EXCLUSIVE ) == 0 );
  TEST( isInRange( 0, 0, 0, INCLUSIVE ) == 1 );

  /* Test between the bounds */
  TEST( isInRange( -1, 1, 0, EXCLUSIVE ) == 1 );
  TEST( isInRange( -1, 1, 0, INCLUSIVE ) == 1 );

  /* Test for lowerbound for both inclusive and exclusive */
  TEST( isInRange( -5, 5, -5, INCLUSIVE ) == 1 );
  TEST( isInRange( -5, 5, -5, EXCLUSIVE ) == 0 );

  /* Test for upperbound for both inclusive and exclusive */
  TEST( isInRange( -5, 5, 5, INCLUSIVE ) == 1 );
  TEST( isInRange( -5, 5, 5, EXCLUSIVE ) == 0 );

  /* Test for value below lower bound */
  TEST( isInRange( -5, 5, -10, INCLUSIVE ) == 0 );
  TEST( isInRange( -5, 5, -10, EXCLUSIVE ) == 0 );
    
  /* Test for value above upper bound */
  TEST( isInRange( -5, 5, 10, INCLUSIVE ) == 0 );
  TEST( isInRange( -5, 5, 10, EXCLUSIVE ) == 0 );

  (void) printf( "Finished running tests on isInRange()\n" );
}

int main( )
{
    testisInRange( );
    return 0;
}
