/*
 * Filename: pa3Strings.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Strings used in PA3 output.
 * Date: May 17, 2016
 * Sources of Help: Given.
 */

#ifndef PA3_STRINGS_H
#define PA3_STRINGS_H

/* Usage strings */
#define STR_USAGE_LONG "\nUsage: %s -f anagramsFile [-b hashBits] [-c] | "\
  "[-h]\n"\
  "Interactive anagram search.\n"\
  "  -f <anagramsFile>  File with .af extension to read anagrams from.\n"\
  "  -b <hashBits>      Number of bits to extract from the hash value;\n"\
  "                     Must be a decimal value within the range [%d - %d].\n"\
  "  -c                 Display the number of hash key collisions.\n"\
  "  -h                 Print the long usage.\n"\
  "\n\n"

#define STR_USAGE_SHORT "\nUsage: %s -f anagramsFile [-b hashBits] [-c] | "\
  "[-h]\nTry '%s -h' for more information.\n\n"


/* Interactive prompt strings */
#define STR_USER_PROMPT "\nEnter a word to search for anagrams [^D to exit]: "
#define STR_NO_ANAGRAMS "No anagrams found."
#define STR_FOUND_ANAGRAMS "Anagram(s) are:"
#define STR_COLLISIONS "\nNumber of collisions: %d"
#define STR_ANAGRAM " %s"


/* Error strings */
#define STR_ERR_AF_INVALID "\n\tError opening anagrams file; invalid file.\n"
#define STR_ERR_AF_PERMISSION "\n\tError opening anagrams file; permission "\
  "denied.\n"
#define STR_ERR_AF_EXTENSION "\n\tError opening anagrams file; must have .af "\
  "extension.\n"
#define STR_ERR_AF_LOAD "\n\tCould not load anagrams file. Memory limit likely"\
  " exceeded.\n"
#define STR_ERR_EXTRA_ARGS "\n\tExtra operand '%s'.\n"
#define STR_ERR_CREATE_ANAGRAM "\n\tCould not build struct anagram. Memory "\
  "limit likely reached.\n"
#define STR_ERR_BAD_HASH_BITS "\n\tNumber of hash bits must be within the "\
  "range of [%d - %d].\n"
#define STR_ERR_CONVERTING "\n\tConverting \"%s\" base \"%d\""
#define STR_ERR_NOTINT "\n\t\"%s\" is not an integer.\n"

#endif /* PA3_STRINGS_H */
