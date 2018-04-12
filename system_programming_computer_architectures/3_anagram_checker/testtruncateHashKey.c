/*
 * Filename: testtruncateHashKey.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function unit test truncateHashKey function

 */
#include "pa3.h"
#include "test.h"

void testtruncateHashKey() {

    long hashkey = 0xCDEF; // Initialiize variable

    // Test truncate 2 bits
    TEST( truncateHashKey( hashkey, 2 ) == 0x3 );
    // Test truncate 4 bits
    TEST( truncateHashKey( hashkey, 4 ) == 0xF );
    // Test truncate 6 bits
    TEST( truncateHashKey( hashkey, 6 ) == 0x2F );
    // Test truncate 12 bits
    TEST( truncateHashKey( hashkey, 12 ) == 0xDEF );
    // Test truncate 16 bits
    TEST( truncateHashKey( hashkey, 16 ) == 0xCDEF );

    // Test null input
    long nullHashKey = 0;
    TEST( truncateHashKey( nullHashKey, 12 ) == 0 );

}

// Main method
int main() {
    // Call unit test
    testtruncateHashKey();
    return 0;
}
