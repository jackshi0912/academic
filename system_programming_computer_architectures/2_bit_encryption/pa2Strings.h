/*
 * Filename: pa2Strings.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Strings used in PA2 output.
 * Date: April 30, 2016
 * Sources of Help: Given.
 */

#ifndef PA2_STRINGS_H
#define PA2_STRINGS_H

#define STR_USAGE "\nUsage: %s passphrase key0 key1 rotateValue\n"\
      "    passphrase   (must be at least eight characters long)\n"\
      "    key0         (must be numeric; decimal, octal, or hexadecimal)\n"\
      "    key1         (must be numeric; decimal, octal, or hexadecimal)\n"\
      "    rotateValue  (must be a decimal value within the range [%+d - %+d])"\
      "\n\n"

#define STR_ERR_BAD_PASSPHRASE "\n\tPassphrase must be at least %d chars long\n"

#define STR_ERR_BAD_ROT "\n\tRotation value must be within the range of "\
                            "[%+d - %+d]\n"

#define STR_ERR_CONVERTING "\n\tConverting \"%s\" base \"%d\""

#define STR_ERR_NOTINT "\n\t\"%s\" is not an integer\n"

#define STR_ERR_NUM_ERRORS "\n\t--- Found %d error(s) ---\n\n"

#endif /* PA2_STRINGS_H */
