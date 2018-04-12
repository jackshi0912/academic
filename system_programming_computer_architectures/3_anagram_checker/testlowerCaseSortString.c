/*
 * Filename: testlowerCaseSortString.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: This function tests the lowerCaseSortString function
 * Date: May 16th, 2016
 * Source of help: none
 */

#include "test.h"
#include "pa3.h"
#include "string.h"

// Function prototype declaration
void testlowerCaseSortString();

// Unit testing for lowerCaseSortString
void testlowerCaseSortString() {
   
    // Test #1: Mixed lower and upper case letter
    // Initialize initial values
    char * ini = "eBadC";
    char final[BUFSIZ];
    // Sorting the values
    lowerCaseSortString( ini, strlen( ini ), final );
    // Test if the values are in lowercase sorted order
    TEST( strcmp( final, "abcde" ) == 0 );

    // Test #2: Lower case only
    // Initialize initial values
    ini = "ebdcw";
    // Sorting the values
    lowerCaseSortString( ini, strlen( ini ), final );
    // Test if the values are in lowercase sorted order
    TEST( strcmp( final, "bcdew" ) == 0 );

    // Test #3 Lower case one letter
    // Initialize initial values
    ini = "e";
    char finalOneLetter[BUFSIZ];
    // Sorting the values
    lowerCaseSortString( ini, strlen( ini ), finalOneLetter );
    // Test if the values are in lowercase sorted order
    TEST( strcmp( finalOneLetter, "e" ) == 0 );

    // Test #4: Upper case only
    // Initialize initial values
    ini = "EBDCW";
    // Sorting the values
    lowerCaseSortString( ini, strlen( ini ), final );
    // Test if the values are in lowercase sorted order
    TEST( strcmp( final, "bcdew" ) == 0 );

    // Test #5: Upper case one letter
    // Initialize initial values
    ini = "A";
    // Sorting the values
    lowerCaseSortString( ini, strlen( ini ), finalOneLetter );
    // Test if the values are in lowercase sorted order
    TEST( strcmp( finalOneLetter, "a" ) == 0 );

    // Test #6: empty string
    // Initialize initial values
    ini = "";
    char finalEmpty[BUFSIZ];
    // Sorting the values
    lowerCaseSortString( ini, strlen( ini ), finalEmpty );
    // Test if the values are in lowercase sorted order
    TEST( strcmp( finalEmpty, "" ) == 0 );
}

int main() {
    testlowerCaseSortString();
    return 0;
}
