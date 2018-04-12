/*
 * Filename: testisEven.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Unit test program to test the function isEven
 * Date: April 9th, 2016
 * Source of Help: Handout
 */

#include "pa1.h"    /* For isEven() function prototype */
#include "test.h"   /* For TEST() macro and stdio.h */

/*
 * int isEven( long value );
 *
 * Check if the the incomming value is even
 *
 * Return 1 if the value is even.
 * Return 0 if the value is odd.
 */

void testisEven( )
{
    (void) printf( "Testing isEven()\n" );

    /* Test 0 */
    TEST( isEven( 0 ) == 1 ); 

    /* Test positive even */
    TEST( isEven( 2 ) == 1 ); 

    /* Test positive odd */
    TEST( isEven( 3 ) == 0 ); 

    /* Test neigative even */
    TEST( isEven( -2 ) == 1 ); 

    /* Test neigative odd */
    TEST( isEven( -3 ) == 0 ); 

    (void) printf( "Finished running test on isEven()\n" );
}

int main()
{
    testisEven();
    return 0;
}
