/*
 * Filename: printDueDate.c
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Example C routine to print out the due date infor
 *              (month day, year) using a pointer to a structure.
 *              Called from main()
 * Date: March 31, 2016
 * Sources of Help: Handout
 */

#include <stdio.h>
#include "pa0Strings.h"
#include "pa0.h"

/*
 * Function name: printDueDate()
 * Function prototype:
 *   void printDueDate( const struct DueDate *dueDate );
 * Description: Prints to stdout the month, day, and year members of a
 *              struct DueDate node.
 * Parameters:
 *      arg1: struct DueDate *dueDate -- pointer to the
 *            struct DueDate node.
 * Side Effects: Outputs the due date of the assignment
 * Error Conditions: Checks to make sure arg1 is not NULL.
 *                   No checks to ensure struct data are correct type/values.
 * Retrun Value: None.
 */

void printDueDate( const struct DueDate *dueDate ) {
    if ( dueDate != NULL )
        (void) printf( STR_DUEDATE, dueDate->month, dueDate->day, dueDate->year );
}
