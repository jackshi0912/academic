/*
 * Filename: pa3EC.h
 * Author: Huize Shi
 * Userid: cs30xapo
 * Description: Header file for pa3 including constants and function protoypes.
 * Date: May 16, 2016
 * Sources of Help: Given.
 */

#ifndef PA3_H
#define PA3_H

#include <stdio.h> /* For FILE type */

/* Arg parsing */
#define ARG_STR "f:b:ch"

#define HASH_BITS_FLAG  0x01
#define COLLISION_FLAG  0x02 
#define HELP_FLAG       0x04

#define CHAR_AF_FLAG        'f'
#define CHAR_HASH_BITS_FLAG 'b'
#define CHAR_COLLISION_FLAG 'c'
#define CHAR_HELP_FLAG      'h'

/* Hashing */
#define HASH_START_VAL 7
#define HASH_PRIME     31
#define MAX_HASH_BYTES 4

#define MIN_HASH_BITS  1
#define MAX_HASH_BITS  32

/* File mode: open a file in read mode */
#define FILE_READ_MODE  "r"    
#define FILE_EXTENSION ".af"

/* Pass to isInRange to check inclusive ranges */
#define INCLUSIVE 0
#define EXCLUSIVE 1

/* 
 * Enum Definitions 
 */
/* Usage printing */
enum usageMode { USAGE_SHORT, USAGE_LONG };


/* 
 * Struct Definitions 
 */
struct argInfo {
  long flags;
  const char * afFileName;
  long hashBits;
};

/* Create one anagram struct per word */
struct anagram {
  char * word;    /* Original word from dictionary */
  long hashKey;   /* Hashed value of lower-case sorted word */
};

/*
 * The anagramInfo struct holds info about the sorted array of struct anagram
 * generated from the database file
 */
struct anagramInfo {
  struct anagram * anagramPtr; /* Ptr to array of sorted struct anagram */
  int numOfAnagrams;           /* Number of anagrams in anagramPtr array */
};

/* 
 * C Function Prototypes 
 */
int parseArgs(int argc, char * const argv[], struct argInfo * argInfo);
int loadAF(FILE * stream, struct anagramInfo * anagramInfo);
int loadAnagram(FILE * stream, struct anagram * anagram);
int findAnagrams(const struct argInfo * argInfo,
                 const struct anagramInfo * angramInfo);
int createAnagram(const char * src, struct anagram * anagram);
void lowerCaseSortString(const char * src, int n, char * dest);
void usage(FILE * stream, enum usageMode u, const char * progName);

/* 
 * Assembly Function Prototypes 
 */
long hashString(const char *str);
long truncateHashKey(long hashKey, long numBits);
int charCompare(const void *p1, const void *p2);
int hashKeyMemberCompare(const void *p1, const void *p2);
int anagramCompare(const void *p1, const void *p2);
int isInRange(long minRange, long maxRange, long value, long exclusive);

#endif /* PA3_H */
