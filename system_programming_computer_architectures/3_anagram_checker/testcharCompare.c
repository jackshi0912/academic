/*
 * Filename: testcharCompare
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function is the unit test for the charCompare function
 * Date: May 16th, 2016
 * Source of help: none
 */
 
#include "test.h"
#include "pa3.h"

// Unit testing for charCompare
void testcharCompare() {
   
    // Initialize letters
    char a = 'a';
    char b = 'b';
    
    // Test a against b
    TEST( charCompare( &a, &b ) == -1 );
    // Test a against a
    TEST( charCompare( &a, &a ) == 0 );
    // Test b against a
    TEST( charCompare( &b, &a ) == 1 );

    // Initialize to non letter characters
    a = '(';
    b = '_';
    
    // Test a against b
    TEST( charCompare( &a, &b ) == -1 );
    // Test a against a
    TEST( charCompare( &a, &a ) == 0 );
    // Test b against a
    TEST( charCompare( &b, &a ) == 1 );

}

int main() {
    testcharCompare();
}
