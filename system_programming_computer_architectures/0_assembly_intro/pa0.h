/**
 * Filename: pa0.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Function prototypes and definitions for PA0
 * Date: March 31, 2016
 * Source of Help: Handout
 */

#ifndef PA0_H
#define PA0_H

#define EXPECTED_ARGS 1
#define DECIMAL 10

/* User-defined types used in PA0 */

struct DueDate {
    const char *month;
    unsigned int day;
    unsigned int year;
};

/* Function prototypes for the C and assembly routines used in PA0 */

void printDueDate( const struct DueDate *dueDate);
void printWelcome( const char *string );

int squareNum( int num );

#endif /* PA0_H */
