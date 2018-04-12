#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

printf "Compiling ... "
make clean >/dev/null
make test > /dev/null
printf "${GREEN}Done\n${NC}"

printf "\nTesting Insert and Find\n "

	./test ./dictionary/shuffled_freq_dict.txt

if [ $? -eq 0 ]; then
	printf "Test ${GREEN}PASS${NC}\n"
else
	printf "Test ${RED}FAILED${NC}\n"
fi

make clean > /dev/null
