/*
 * Filename: testhashString.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Unit testing for the hashString function
 *    The tests are conducted by comparing the result with that of the official
 *    algorithm from the handout.
 * Date: May 16th, 2016
 * Source of help: none
 */

#include "test.h"
#include "pa3.h"
#include <string.h>

// Unit testing
long hashStringGenerator( const char * str );

void testhashString() {

    char * str = "ABCDE"; // Basic testcase
    TEST( hashStringGenerator(str) == hashString(str) );

    str = ""; // Empty string
    TEST( hashStringGenerator(str) == hashString(str) );

    str = "FjIoE"; // Mixed case
    TEST( hashStringGenerator(str) == hashString(str) );

    str = "flsak"; // Lower case
    TEST( hashStringGenerator(str) == hashString(str) );

    str = "a"; // Single letter
    TEST( hashStringGenerator(str) == hashString(str) );

}

// Main function of the testing program
int main () {
    testhashString();
    return 0;
}

// Reference hashString algorithm
// Provided by the handout
long hashStringGenerator( const char * str) {
    long hash = HASH_START_VAL;
    int strLen = strlen(str);
    for( int i = 0; i < strLen; i++ ) {
      hash = hash * HASH_PRIME + str[i];
    }
    return hash;
}
