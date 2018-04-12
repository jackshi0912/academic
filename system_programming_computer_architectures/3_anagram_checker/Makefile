#
# Makefile for CSE 30 -- PA3
# You should not need to change anything in this file.
#

HEADERS		= pa3.h pa3Strings.h test.h

C_SRCS		= pa3.c parseArgs.c createAnagram.c loadAF.c loadAnagram.c \
		  lowerCaseSortString.c usage.c findAnagrams.c pa3Globals.c

C_SRCS_EC	= pa3EC.c parseArgs.c createAnagram.c loadAF.c loadAnagram.c \
		  lowerCaseSortString.c usage.c findAnagramsEC.c pa3Globals.c \
		  printHashStatsEC.c

ASM_SRCS	= isInRange.s charCompare.s hashString.s hashKeyMemberCompare.s\
		  anagramCompare.s truncateHashKey.s


C_OBJS		= $(C_SRCS:.c=.o)
C_OBJS_EC 	= $(C_SRCS_EC:.c=.o)
ASM_OBJS	= $(ASM_SRCS:.s=.o)


TEST_BINS	= testtruncateHashKey testhashString testcharCompare \
		  testlowerCaseSortString testparseArgs


OBJS		= ${C_OBJS} ${ASM_OBJS}
OBJS_EC 	= ${ASM_OBJS} ${C_OBJS_EC}

EXE		= pa3
EXE_EC 		= pa3EC


GCC		= gcc
ASM		= $(GCC)
LINT		= lint


GCC_FLAGS	= -c -g -W -Wall -D__EXTENSIONS__ -std=c99
LINT_FLAGS1	= -c -err=warn
LINT_FLAGS2	= -u -err=warn
ASM_FLAGS	= -c -g
LD_FLAGS	= -g -W -Wall


#
# Standard rules
#

.s.o:
	@echo "Assembling each assembly source file separately ..."
	$(ASM) $(ASM_FLAGS) $<
	@echo ""

.c.o:
	@echo "Linting each C source file separately ..."
	$(LINT) $(LINT_FLAGS1) $<
	@echo ""
	@echo "Compiling each C source file separately ..."
	$(GCC) $(GCC_FLAGS) $<
	@echo ""

#
# Simply have our project target be a single default $(EXE) executable.
#

$(EXE):	$(OBJS)
	@echo "2nd phase lint on all C source files ..."
	$(LINT) $(LINT_FLAGS2) *.ln
	@echo ""
	@echo "Linking all object modules ..."
	$(GCC) -o $(EXE) $(LD_FLAGS) $(OBJS)
	@echo ""
	@echo "Done."

#
# Simply have our project target be a single default $(EXE) executable.
#

$(EXE_EC):	$(OBJS_EC)
	@echo "2nd phase lint on all C source files ..."
	$(LINT) $(LINT_FLAGS2) *.ln
	@echo ""
	@echo "Linking all object modules ..."
	$(GCC) -o $(EXE_EC) $(LD_FLAGS) $(OBJS_EC)
	@echo ""
	@echo "Done."

${C_OBJS}:      ${HEADERS}

clean:
	@echo "Cleaning up project directory ..."
	/usr/bin/rm -f *.o $(EXE) $(EXE_EC) *.ln core a.out ${TEST_BINS}
	@echo ""
	@echo "Clean."

# --- Test for truncateHashKey ---
testtruncateHashKey: ${HEADERS} truncateHashKey.o testtruncateHashKey.o
	@echo "Compiling testtruncateHashKey.c"
	gcc -g -o testtruncateHashKey testtruncateHashKey.o truncateHashKey.o 
	@echo "Done."

# --- Test for hashString ---
testhashString: ${HEADERS} hashString.o testhashString.o
	@echo "Compiling testhashString.c"
	gcc -g -o testhashString testhashString.o hashString.o pa3Globals.c
	@echo "Done."

# --- Test for charCompare ---
testcharCompare: ${HEADERS} charCompare.o testcharCompare.o
	@echo "Compiling testcharCompare.c"
	gcc -g -o testcharCompare testcharCompare.o charCompare.o
	@echo "Done."

# --- Test for lowerCaseSortString ---
testlowerCaseSortString: ${HEADERS} lowerCaseSortString.o charCompare.o\
			 testlowerCaseSortString.o
	@echo "Compiling testlowerCaseSortString.c"
	gcc -g -o testlowerCaseSortString testlowerCaseSortString.o\
	 lowerCaseSortString.o charCompare.o 
	@echo "Done."

# --- Test for parseArgs ---
testparseArgs: ${HEADERS} parseArgs.o testparseArgs.o isInRange.o
	@echo "Compiling testparseArgs.c"
	gcc -g -o testparseArgs testparseArgs.o parseArgs.o isInRange.o
	@echo "Done."

new:
	make clean
	make 

testCompareFunctions:
	rm testCompareFunctions
	gcc -o testCompareFunctions testCompareFunctions.c anagramCompare.s\
			  hashKeyMemberCompare.s pa3Globals.c
	@echo "Done"
