#include <stdlib.h>
#include "test.h"
#include "pa3.h"

void testCompareFunctions() {
  struct anagram *a = (struct anagram *) malloc( sizeof( struct anagram ) );
  struct anagram *b = (struct anagram *) malloc( sizeof( struct anagram ) );

  fprintf( stderr, "Test1\n" );
  a->word = "apple";
  a->hashKey = 100;
  b->word = "beta";
  b->hashKey = 120;
  TEST( hashKeyMemberCompare( a, b ) == -1 );

  fprintf( stderr, "Test2\n" );
  a->hashKey = 120;
  b->hashKey = 100;
  TEST( hashKeyMemberCompare( a, b ) == 1 );

  fprintf( stderr, "Test3\n" );
  a->hashKey = 100;
  b->hashKey = 100;
  TEST( hashKeyMemberCompare( a, b ) == 0 );

  fprintf( stderr, "Test2.1\n" );
  a->hashKey = 100;
  b->hashKey = 120;
  TEST( anagramCompare( a, b ) == -1 );

  fprintf( stderr, "Test2.2\n" );
  a->hashKey = 120;
  b->hashKey = 100;
  TEST( anagramCompare( a, b ) == 1 );

  fprintf( stderr, "Test2.3\n" );
  a->hashKey = 100;
  b->hashKey = 100;
  TEST( anagramCompare( a, b ) == -1 );

  fprintf( stderr, "Test2.4\n" );
  a->word = "theta";
  a->hashKey = 100;
  b->word = "beta";
  b->hashKey = 100;
  TEST( anagramCompare( a, b ) == 1 );

  fprintf( stderr, "Test2.5\n" );
  a->word = "beta";
  a->hashKey = 100;
  b->word = "beta";
  b->hashKey = 100;
  TEST( anagramCompare( a, b ) == 0 );
}

void main() {
  testCompareFunctions();
}
