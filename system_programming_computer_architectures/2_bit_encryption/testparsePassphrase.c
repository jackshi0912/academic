/*
 * Filename: testparsePassphrase.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function is the unit test for parsePassphrase function
 * Date May 1st, 2016
 * Source of help: none
 */
#include "test.h"
#include <stdlib.h>
#include "pa2.h"

// Unit testing function prototype
long parsePassphrase( char * str, unsigned char * passphrase );

// Unit testing function for testparsePassphrase
void testparsePassphrase () {

    // Initialize variables
    char * str = "ababababab";
    unsigned char * passphrase = (unsigned char *) malloc ( 8 );
    long rtval;

    rtval = parsePassphrase( str, passphrase); // Collect return value

    TEST(rtval == 0);
    for ( int i = 0; i < 8; i++ ){
        if ( i % 2 == 0) {
            TEST(*passphrase == 0x61);
        } else {
            TEST(*passphrase == 0x62);
        }
        ++passphrase;
    }
}

int main() {
    testparsePassphrase();
}
