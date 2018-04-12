#include "pa1.h"
#include "test.h"

void printSegment( long character, long amount );
void printChar( char character );

void main( int argc, char *argv[] ) {

    (void) printf( "Testing printSegment\n" );

    printSegment(124, 12);
    printChar(10);
    printSegment(124, 12);
    printChar(10);
    printSegment(124, 12);
    printChar(10);
    (void) printf( "Dont testing printSegment\n" );

}
