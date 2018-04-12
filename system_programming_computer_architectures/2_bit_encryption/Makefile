#
# Makefile for CSE 30 -- PA2
# You should not need to change anything in this file.
#

HEADERS		= pa2.h pa2Strings.h
C_SRCS		= pa2.c parseKey.c parsePassphrase.c pa2Globals.c
ASM_SRCS	= createMask.s myCipher.s parseRotateValue.s rotate.s \
		  isInRange.s

C_OBJS		= pa2.o parseKey.o parsePassphrase.o pa2Globals.o
ASM_OBJS	= createMask.o myCipher.o parseRotateValue.o rotate.o \
		  isInRange.o

TEST_BINS	= testcreateMask testmyCipher testparseKey \
		  testparsePassphrase testparseRotateValue testrotate


OBJS		= ${C_OBJS} ${ASM_OBJS}
EXE		= pa2


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

${C_OBJS}:      ${HEADERS}

clean:
	@echo "Cleaning up project directory ..."
	/usr/bin/rm -f *.o $(EXE) *.ln core a.out ${TEST_BINS}
	@echo ""
	@echo "Clean."

# --- Test for createMask ---
testcreateMask: test.h ${HEADERS} createMask.o testcreateMask.o
	@echo "Compiling testcreateMask.c"
	gcc -g -o testcreateMask testcreateMask.o createMask.o 
	@echo "Done."

runtestcreateMask: testcreateMask
	@echo "Running testcreateMask"
	@./testcreateMask

# --- Test for myCipher ---
testmyCipher: test.h ${HEADERS} myCipher.o testmyCipher.o rotate.o
	@echo "Compiling testmyCipher.c"
	gcc -g -o testmyCipher testmyCipher.o myCipher.o pa2Globals.c rotate.o
	@echo "Done."

runtestmyCipher: testmyCipher
	@echo "Running testmyCipher"
	@./testmyCipher

# --- Test for parseKey ---
testparseKey: test.h ${HEADERS} parseKey.o testparseKey.o
	@echo "Compiling testparseKey.c"
	gcc -g -o testparseKey testparseKey.o parseKey.o
	@echo "Done."

runtestparseKey: testparseKey
	@echo "Running testparseKey"
	@./testparseKey

# --- Test for parsePassphrase ---
testparsePassphrase: test.h ${HEADERS} parsePassphrase.o testparsePassphrase.o
	@echo "Compiling testparsePassphrase.c"
	gcc -g -o testparsePassphrase testparsePassphrase.o parsePassphrase.o 
	@echo "Done."

runtestparsePassphrase: testparsePassphrase
	@echo "Running testparsePassphrase"
	@./testparsePassphrase

# --- Test for parseRotateValue ---
testparseRotateValue: test.h ${HEADERS} parseRotateValue.o \
		      testparseRotateValue.o isInRange.o
	@echo "Compiling testrotate.c"
	gcc -g -o testparseRotateValue testparseRotateValue.o \
	parseRotateValue.o pa2Globals.c isInRange.o
	@echo "Done."

runtestparseRotateValue: testparseRotateValue
	@echo "Running testparseRotateValue"
	@./testparseRotateValue

# --- Test for rotate ---
testrotate: test.h ${HEADERS} rotate.o testrotate.o
	@echo "Compiling testrotate.c"
	gcc -g -o testrotate testrotate.o rotate.o
	@echo "Done."

runtestrotate: testrotate
	@echo "Running testrotate"
	@./testrotate

new:
	make clean
	make 
