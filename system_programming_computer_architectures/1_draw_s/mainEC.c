/*
 * Filename: mainEC.c
 * Author: TODO
 * Userid: TODO
 * Description: PA1 Extra Credit. Driver for nops.s function.
 *              Code below should not be modified.
 * Date: TODO
 * Sources of Help: TODO
 */

#include <stdio.h>
#include <stdlib.h>
#include <errno.h>

long nops( long num ); /* Function prototype for nops() */

#define BASE 10

int main(int argc, char* argv[]) {

  if (argc != 2) {
    (void) fprintf(stderr, "Usage:\t%s num\n", argv[0]);
    (void) fprintf(stderr, "\tnum - integer to calculate secret number for\n");
    exit(1);
  }

  char *endptr;

  errno = 0;
  long num = strtol(argv[1], &endptr, BASE);
  if (errno) {
    (void) fprintf(stderr, "Must enter a valid integer argument\n");
    exit(1);
  }

  long secret = nops(num);

  (void) printf("The secret number for %ld is %ld\n", num, secret);

  return 0;
}

