/*
 * Filename: testcreateMask.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function test the createMask function
 * Date: May 1st, 2016
 * Source of help: none
 */

#include "test.h"       /* For TEST() macro and stdio.h */
#include <stdlib.h>     /* For rand() function prototype */
#include <limits.h>     /* For LONG_MIN and LONG_MAX */
#include "pa2.h"        /* For rotate() function prototype */


// Function prototypes
void createMask( unsigned long keys[], unsigned char passphrase[],
                 unsigned long mask[] );
void refillPassphrase( unsigned char * passphrase );
void zeroFillPassphrase( unsigned char * passphrase );

// Testing function
void testcreateMask() {

    // Initialize variables
    unsigned long mask[2];
    unsigned long key[2];

    printf("Testing createMask()\n" );
 
    // Checking when 2 bytes are xored
    unsigned char passphrase[8] = { 'h','e', 'l', 'l', 'o', 'c', 's', 'e' };
    key[0] = 0x4F;
    key[1] = 0x34;
    createMask( key, passphrase, mask );
    TEST( mask[0] == 0x68656C23 && mask[1] == 0x6F637351 );
  
    // Checking when no bytes are xored
    refillPassphrase( passphrase );
    key[0] = 0;
    key[1] = 0;
    createMask( key, passphrase, mask );
    TEST( mask[0] == 0x68656C6C && mask[1] == 0x6F637365);

    // Checking when all nibbles are xored
    refillPassphrase( passphrase );
    key[0] = 0x243F4A9A;
    key[1] = 0x1B4C9D8E;
    createMask( key, passphrase, mask );
    TEST( mask[0] == 0x4C5A26F6 && mask[1] == 0x742FEEEB );

    // Checking when all bits are xored
    refillPassphrase( passphrase );
    key[0] = 0xFFFFFFFF;
    key[1] = 0xFFFFFFFF;
    createMask( key, passphrase, mask );
    TEST( mask[0] == 0x979A9393 && mask[1] == 0x909C8C9A );

    // Checking when 0 xoring with data
    zeroFillPassphrase( passphrase );
    key[0] = 0xABCDEF98;
    key[1] = 0x89FEDCBA;
    createMask( key, passphrase, mask );
    TEST( mask[0] == 0xABCDEF98 && mask[1] == 0x89FEDCBA );

    printf( "Finished running tests on createMask()\n" );

}

/*
 * Helper method that fill the passphrase with unsigned values of "hellocse"
 */
void refillPassphrase( unsigned char * passphrase ) {
    *passphrase = 0x68;
    ++passphrase;
    *passphrase = 0x65;
    ++passphrase;
    *passphrase = 0x6C;
    ++passphrase;
    *passphrase = 0x6C;
    ++passphrase;
    *passphrase = 0x6F;
    ++passphrase;
    *passphrase = 0x63;
    ++passphrase;
    *passphrase = 0x73;
    ++passphrase;
    *passphrase = 0x65;
}

/*
 * Helper method that fill the passphrase with all 0s
 */
void zeroFillPassphrase( unsigned char * passphrase ) {

    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;
    ++passphrase;
    *passphrase = 0;

}

int main() {
    testcreateMask();

    return 0;
}

