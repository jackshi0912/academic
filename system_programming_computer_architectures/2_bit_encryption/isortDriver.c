/*
 * Filename: isortDriver.c
 * Author: TODO
 * Userid: TODO
 * Description: PA2 Extra Credit. Driver for isort.s function.
 *              Code below should not be modified.
 * Date: TODO
 * Sources of Help: TODO
 */

#include <stdio.h>
#include <stdlib.h>

#define ARRAY_SIZE 420
#define MAX 12345
#define COLS 10
#define FMT "%5d "
#define SEED 17

int isort( int array[], int len );

/*
 * Randomly populates an array of ints and calls isort() to perform
 * an insertion sort. isort() returns the difference between the largest
 * and smallest values in the array.
 */

int main() {

  int i;
  int array[ARRAY_SIZE];

  srand( SEED );

  for ( i = 0; i < ARRAY_SIZE; i++ ) {
    array[i] = rand() % MAX;
  }

  int result = isort( array, ARRAY_SIZE );

  for ( i = 0; i < ARRAY_SIZE; i++ ) {
    printf( FMT, array[i] );
    if ( (i % COLS) == (COLS - 1) ) {
      printf( "\n" );
    }
  }

  printf( "\nMax - Min = %d\n", result );

  return 0;
}

