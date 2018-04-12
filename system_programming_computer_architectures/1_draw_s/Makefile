#
# Makefile for CSE 30 -- PA1
# You should not need to change anything in this file.
#

HEADERS		= pa1.h pa1Strings.h
C_SRCS		= pa1.c
ASM_SRCS	= isEven.s printChar.s isInRange.s numOfDigits.s printSegment.s\
                  drawS.s
C_OBJS		= pa1.o
ASM_OBJS	= isEven.o printChar.o isInRange.o numOfDigits.o printSegment.o\
                  drawS.o
OBJS		= ${C_OBJS} ${ASM_OBJS}
EXE		= pa1


#
# Relevant man pages:
#
# man gcc
# man as
# man lint
#

GCC		= gcc
ASM		= $(GCC)
LINT		= lint

GCC_FLAGS	= -c -g -Wall -D__EXTENSIONS__ -std=c99
LINT_FLAGS1	= -c -err=warn
LINT_FLAGS2	= -u -err=warn
ASM_FLAGS	= -c -g
LD_FLAGS	= -g -Wall

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

${C_OBJS}:      ${HEADERS}


clean:
	@echo "Cleaning up project directory ..."
	/usr/bin/rm -f *.o $(EXE) *.ln core a.out \
	    testisInRange testisEven testnumOfDigits
	@echo ""
	@echo "Clean."

testisInRange:	test.h pa1.h isInRange.o testisInRange.o
	@echo "Compiling testisInRange.c"
	gcc -g -o testisInRange testisInRange.c isInRange.s
	@echo "Done."

runtestisInRange: testisInRange
	@echo "Running testisInRange"
	@./testisInRange

testisEven: test.h pa1.h isEven.o testisEven.o
	@echo "Compiling testisEven.c"
	gcc -g -o testisEven testisEven.c isEven.s
	@echo "Done."

runtestisEven: testisEven
	@echo "Running testisEven"
	@./testisEven

testnumOfDigits: test.h pa1.h numOfDigits.o testnumOfDigits.o isInRange.o
	@echo "Compiling testnumOfDigits.c"
	gcc -g -o testnumOfDigits testnumOfDigits.c numOfDigits.s isInRange.s
	@echo "Done."

runtestnumOfDigits: testnumOfDigits
	@echo "Running testnumOfDigits"
	@./testnumOfDigits

new:
	make clean
	make

