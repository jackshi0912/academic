#
# Makefile template for CSE 30 -- PA0
# You should not need to change anything in this file.
#

HEADERS		= pa0.h pa0Strings.h
C_SRCS		= pa0.c printDueDate.c
ASM_SRCS	= printWelcome.s squareNum.s
C_OBJS		= $(C_SRCS:.c=.o)
ASM_OBJS	= $(ASM_SRCS:.s=.o)
LINT_FILES	= $(C_SRCS:.c=.ln)
OBJS		= $(C_OBJS) $(ASM_OBJS)
EXE		= a.out


#
# Relevant man pages:
#
# man gcc
# man as
# man lint
#

GCC		= /usr/bin/gcc
ASM		= $(GCC)
LINT		= lint

GCC_FLAGS	= -c -g -Wall
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
# Have our project target be named whatever we specify above for EXE.
#

$(EXE):	$(OBJS)
	@echo "2nd phase lint on all C source files ..."
	$(LINT) $(LINT_FLAGS2) *.ln
	@echo ""
	@echo "Linking all object modules ..."
	$(GCC) $(LD_FLAGS) -o $(EXE) $(OBJS)
	@echo ""
	@echo "Done."

$(C_OBJS): $(HEADERS)

clean:
	@echo "Cleaning up project directory ..."
	/usr/bin/rm -f $(OBJS) $(EXE) $(LINT_FILES) core
	@echo ""
	@echo "Clean."
