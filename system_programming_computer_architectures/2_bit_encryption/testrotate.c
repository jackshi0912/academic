/*
 * Filename: testrotate.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Unit test program to test the function rotate.
 * Date: May 1st, 2016
 * Sources of Help: None
 */ 

#include "test.h"       /* For TEST() macro and stdio.h */
#include <stdlib.h>     /* For rand() function prototype */
#include <limits.h>     /* For LONG_MIN and LONG_MAX */
#include "pa2.h"        /* For rotate() function prototype */


/* Need to include the function prototype because it's commented out in pa2.h */
void rotate( unsigned long mask[], long rotateValue );

/*
 * Unit Test for rotate.s
 *
 * void rotate( unsigned long mask[], long rotateValue );
 *
 * Takes mask[0] as high order 32 bits and mask[1] as low order 32 bits
 * and simulates a 64-bit rotate within these two words.
 *
 * Lower 6 bits of rotateCnt is masked to keep rotate range 0-63.
 *
 * Positive rotateCnt - rotate right
 * Negative rotateCnt - rotate left
 */

void testrotate() {
  unsigned long mask[2];

  printf( "Testing rotate()\n" );

  /*
   * Test with rotateCnt of 0 - should be no change.
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, 0 );
  TEST( mask[0] == 0x01234567 && mask[1] == 0x89ABCDEF );

  /*
   * Test rotating left 1 bit.
   */
  mask[0] = 0x7FFFFFFF;
  mask[1] = 0x7FFFFFFF;

  rotate ( mask, -1 );
  TEST( mask[0] == 0xFFFFFFFE );
  TEST( mask[1] == 0xFFFFFFFE );

  /*
   * Test rotating right 1 bit.
   */
  mask[0] = 0xFFFFFFFE;
  mask[1] = 0xFFFFFFFE;

  rotate ( mask, 1 );
  TEST( mask[0] == 0x7FFFFFFF );
  TEST( mask[1] == 0x7FFFFFFF );


  /*
   * Test rotate left 1 nibble
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, -4 );
  TEST( mask[0] == 0x12345678 && mask[1] == 0x9ABCDEF0 );

  /*
   * Test rotate right 1 nibble
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, 4 );
  TEST( mask[0] == 0xF0123456 && mask[1] == 0x789ABCDE );

  /*
   * Test rotate left 1 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, -8 );
  TEST( mask[0] == 0x23456789 && mask[1] == 0xABCDEF01 );

  /*
   * Test rotate right 1 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, 8 );
  TEST( mask[0] == 0xEF012345 && mask[1] == 0x6789ABCD );

  /*
   * Test rotate left 4 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, -32 );
  TEST( mask[0] == 0x89ABCDEF && mask[1] == 0x01234567 );

  /*
   * Test rotate right 4 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, 32 );
  TEST( mask[0] == 0x89ABCDEF && mask[1] == 0x01234567 );

  /*
   * Test rotate left 8 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, -64 );
  TEST( mask[0] == 0x01234567 && mask[1] == 0x89ABCDEF );

  /*
   * Test rotate right 8 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, 64 );
  TEST( mask[0] == 0x01234567 && mask[1] == 0x89ABCDEF );

  /*
   * Test rotate left 9 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, -72 );
  TEST( mask[0] == 0x23456789 && mask[1] == 0xABCDEF01 );

  /*
   * Test rotate right 9 byte
   */
  mask[0] = 0x01234567;
  mask[1] = 0x89ABCDEF;

  rotate( mask, 72 );
  TEST( mask[0] == 0xEF012345 && mask[1] == 0x6789ABCD );

  printf( "Finished running tests on rotate()\n" );

}

int main() {
  testrotate();

  return 0;
}

