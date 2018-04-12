/*
 * Filename: pa1Strings.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Strings used in PA1 output
 * Date: April 16th, 2016
 * Sources of Help: handout
 */


#ifndef PA1_STRINGS_H 
#define PA1_STRINGS_H


#define STR_USAGE  "\nUsage: %s sWidth sChar fillerChar borderChar\n"\
       "    sWidth      (must be within the range of [%d - %d])\n"\
       "                (must be even)\n"\
       "    sChar       (must be an ASCII value within the range [%d - %d])\n"\
       "    fillerChar  (must be an ASCII value within the range [%d - %d])\n"\
       "                (must be different than sChar)\n"\
       "    borderChar  (must be an ASCII value within the range [%d - %d])\n"\
       "                (must be different than sChar)\n\n"
 
#define STR_ERR_S_WIDTH_RANGE "\n\tsWidth(%ld) must be within the"\
                              " range of [%d - %d]\n"


#define STR_ERR_S_WIDTH_EVEN "\n\tsWidth(%ld) must be even\n"

#define STR_ERR_S_CHAR_RANGE "\n\tsChar(%ld) must be an ASCII code"\
                             " in the range [%d - %d]\n"

#define STR_ERR_FILLERCHAR_RANGE "\n\tfillerChar(%ld) must be an "\
                                 "ASCII code in the range [%d - %d]\n"

#define STR_ERR_S_CHAR_FILLERCHAR_DIFF "\n\tsChar(%ld) and "\
                                       "fillerChar(%ld) must be "\
                                       "different\n"
	
#define STR_ERR_BORDERCHAR_RANGE "\n\tborderChar(%ld) must be an "\
                                 "ASCII code in the range [%d - %d]\n"
	
#define STR_ERR_S_CHAR_BORDERCHAR_DIFF "\n\tsChar(%ld) and "\
                                       "borderChar(%ld) must be different\n"

#define STR_CONVERTING "\n\tConverting \"%s\" base \"%d\""
	
#define STR_NOTINT "\n\t\"%s\" is not an integer\n"

#endif /* PA1_STRINGS_H */
