/*
 * Filename: testparseRotateValue.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function is the unit test for parseRotateValue function
 * Date: May 1st, 2016
 * Source of help: none
 */
#include "test.h"
#include <stdlib.h>
#include "pa2.h"

// Unit testing function
void testparseRotateValue() {

    // Initialize variables
    long * key = (long *) malloc( sizeof( long) );
    long errMsg;

    // Parsing 0
    char * str = "0";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == 0 && *key == 0 );

    // Parsing empty string
    str = "";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == 0 && *key == 0 );

    // Parsing the lower bound
    str = "-63";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == 0 && *key == -63 );
    
    // Parsing the upper bound
    str = "63";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == 0 && *key == 63 );

    // Parsing a value that is too large
    str = "999999999999999999";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == ERANGE_ERR); // Check errno emplementation
    
    // Parsing a non-int value
    str = "TESTTEST";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == ENDPTR_ERR); // CHeck if endptr is properly set

    // Parsing value lower than lower bound
    str = "-64";
    errMsg = parseRotateValue(str, key);
    TEST( errMsg == BOUND_ERR); // Check if error is caught

    // Parsing value greater than upper bound
    str = "64";
    errMsg = parseRotateValue(str, key); // Check if error is caught
    TEST( errMsg == BOUND_ERR);
}

// The main function used to execute the unit test
int main() {

    testparseRotateValue();
    
    return 0;
}
