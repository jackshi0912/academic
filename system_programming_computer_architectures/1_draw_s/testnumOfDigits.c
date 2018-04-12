/*
 * filename: testnumOfDigits.c
 * Author: Huize Shi
 * Userid: cs3-xapo
 * Description: Unit test program to test the function numOfDigits
 * Date: April, 9th, 2016
 * Source of Help: Handout
 */

#include "pa1.h"    /* For numOfDigits() function prototype */
#include "test.h"   /* For TEST() macro and stdio.h */

int numOfDigits( long num, int base );

/*
 * int numOfDigits( long num, int base );
 *
 * Count the number of digits for the input with a given base
 *
 * Return -1 if the base is not within the range of [2, 36]
 * Return 0 if the input is 0
 * Return the number of digits for the input with a given base
 */

void testnumOfDigits( )
{
    (void) printf( "Testing numOfDigits" );

    /* Test 0 input */
    TEST( numOfDigits( 0, 0 ) == 0 );

    /* Test bases for power of 2 for positive input values */
    TEST( numOfDigits(1, 2 ) == 1 );
    TEST( numOfDigits(3, 2 ) == 2 );
    TEST( numOfDigits(5, 2 ) == 3 );

    TEST( numOfDigits(1, 4 ) == 1 );
    TEST( numOfDigits(5, 4 ) == 2 );
    TEST( numOfDigits(17, 4 ) == 3 );

    TEST( numOfDigits(1, 8 ) == 1 );
    TEST( numOfDigits(9, 8 ) == 2 );
    TEST( numOfDigits(65, 8 ) == 3 );

    TEST( numOfDigits(1, 16 ) == 1 );
    TEST( numOfDigits(17, 16 ) == 2 );
    TEST( numOfDigits(257, 16 ) == 3 );

    TEST( numOfDigits(1, 32 ) == 1 );
    TEST( numOfDigits(33, 32 ) == 2 );
    TEST( numOfDigits(1025, 32 ) == 3 );

    /* Test bases for power of 2 for neigative input values */
    TEST( numOfDigits(-1, 2 ) == 1 );
    TEST( numOfDigits(-3, 2 ) == 2 );
    TEST( numOfDigits(-5, 2 ) == 3 );

    TEST( numOfDigits(-1, 4 ) == 1 );
    TEST( numOfDigits(-5, 4 ) == 2 );
    TEST( numOfDigits(-17, 4 ) == 3 );
    
    TEST( numOfDigits(-1, 8 ) == 1 );
    TEST( numOfDigits(-9, 8 ) == 2 );
    TEST( numOfDigits(-65, 8 ) == 3 );

    TEST( numOfDigits(-1, 16 ) == 1 );
    TEST( numOfDigits(-17, 16 ) == 2 );
    TEST( numOfDigits(-257, 16 ) == 3 );

    TEST( numOfDigits(-1, 32 ) == 1 );
    TEST( numOfDigits(-33, 32 ) == 2 );
    TEST( numOfDigits(-1025, 32 ) == 3 );

    /* Test others */

    /* Power of 10 */
    TEST( numOfDigits(2483, 10 ) == 4 );
    TEST( numOfDigits(100000, 10 ) == 6 );

    /* Power of 5 */
    TEST( numOfDigits(26, 5 ) == 3 );
    TEST( numOfDigits(5, 5 ) == 2 );


    /* Lower upper base bound */
    TEST( numOfDigits(64, 2 ) == 7 );
    TEST( numOfDigits(37, 36 ) == 2 );
    

    (void) printf( "Finished running test on numOfDigits()\n" );
}

int main()
{
    testnumOfDigits();
    return 0;
}
