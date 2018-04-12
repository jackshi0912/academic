/*
 * Filename: pa0Strings.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: String used in PA0 output
 * Date: March 31, 2016
 * Sources of Help: Handout
 */

#ifndef PA0_STRINGS_H
#define PA0_STRINGS_H

/*
 * Normal output messages
 */
#define STR_WELCOME_QUARTER "Spring 2016"
#define STR_DUEDATE         "PA0 is due on %s %d, %d\n"
#define STR_SQUARENUM       "The square of %d is %d\n"

/*
 * Error messages
 */
#define STR_ERR_USAGE       "Usage: %s [INTEGER]\n Integer value to square\n"
#define STR_ERR_MISSING_ARG "%s: missing integer arg\n"
#define STR_ERR_EXTRA_ARG   "%s: too many arguments\n"

#endif /* PA0_STRINGS_H */
