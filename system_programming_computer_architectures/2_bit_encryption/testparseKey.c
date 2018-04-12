/*
 * Filename: testparseKey.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function is the unit test for the parseKey function
 * Date: May 1st, 2016
 * Source of help: none
 */

#include "test.h"
#include <stdlib.h>
#include "pa2.h"

// Unit test function for parseKey
void testparseKey() {

    unsigned long * key = (unsigned long *) malloc( sizeof( unsigned long) );
    long errMsg;

    // Test normal parsing
    char * str = "0xABCDEF";
    errMsg = parseKey(str, key);
    TEST( errMsg == 0 && *key == 0xABCDEF );

    // Test octal parsing
    str = "04473";
    errMsg = parseKey(str, key);
    TEST( errMsg == 0 && *key == 04473 );

    // Test decimal parsing
    str = "1234";
    errMsg = parseKey(str, key);
    TEST( errMsg == 0 && *key == 1234 );

    // Test empty string parsing
    str = "";
    errMsg = parseKey(str, key);
    TEST( errMsg == 0 && *key == 0 );

    // Test non-integer values
    str = "GDEF";
    errMsg = parseKey(str, key);
    TEST( errMsg == ENDPTR_ERR ); // Test if the error is caught

    // Test integer that is too large
    str = "0xFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF";
    errMsg = parseKey(str, key);
    TEST( errMsg == ERANGE_ERR ); // Check errno implementation
}

// main function to run the unit test
int main() {
    testparseKey();
    
    return 0;
}
