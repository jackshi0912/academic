#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

printf "Compiling ... "
make all > /dev/null
printf "Done\n"
printf "Cleaning output directory ... "
rm -f ./testOutput/* > /dev/null
printf "Done\n\n"

InputArray=(./testInput/checkpoint0.txt ./testInput/checkpoint1.txt \
	./testInput/checkpoint2.txt ./testInput/checkpoint3.txt \
	./testInput/binary.dat ./testInput/doriangray.txt ./testInput/empty \
	./testInput/justA ./testInput/justABC \
	./testInput/lotsofprideandprejudice.txt ./testInput/nonascii \
	./testInput/nonascii2 ./testInput/skewed ./testInput/warandpeace.txt
	./testInput/kamasutra.txt ./testInput/lotsofA)

OutputEncodeArray=(./testOutput/checkpoint0.txt ./testOutput/checkpoint1.txt \
	./testOutput/checkpoint2.txt ./testOutput/checkpoint3.txt \
	./testOutput/binary.dat ./testOutput/doriangray.txt ./testOutput/empty \
	./testOutput/justA ./testOutput/justABC \
	./testOutput/lotsofprideandprejudice.txt ./testOutput/nonascii \
	./testOutput/nonascii2 ./testOutput/skewed ./testOutput/warandpeace.txt\
	./testOutput/kamasutra.txt ./testOutput/lotsofA)

OutputDecodeArray=(./testOutput/checkpoint0Decoded.txt \
	./testOutput/checkpoint1Decoded.txt ./testOutput/checkpoint2Decoded.txt \
	./testOutput/checkpoint3Decoded.txt ./testOutput/binaryDecoded.dat \
	./testOutput/doriangrayDecoded.txt ./testOutput/emptyDecoded \
	./testOutput/justADecoded ./testOutput/justABCDecoded \
	./testOutput/lotsofprideandprejudiceDecoded.txt \
	./testOutput/nonasciiDecoded ./testOutput/nonascii2Decoded \
	./testOutput/skewedDecoded ./testOutput/warandpeaceDecoded.txt \
	./testOutput/kamasutraDecoded.txt ./testOutput/lotsofADecoded)

i=5
	printf "Testing %s\t\t\t\t\t" ${InputArray[$i]}
	time ./compress ${InputArray[$i]} ${OutputEncodeArray[$i]}
	time ./uncompress ${OutputEncodeArray[$i]} ${OutputDecodeArray[$i]}

	if diff ${InputArray[$i]} ${OutputDecodeArray[i]} > /dev/null ; then
		printf "${GREEN}PASS${NC}\n"
	else 
		printf "${RED}Incorrect output${NC}\n"
	fi

printf "\nChecking memory leaks: compress \n\n"
valgrind --leak-check=yes ./compress ${InputArray[0]} ${OutputEncodeArray[0]}
printf "\n\nChecking memory leaks: uncompress \n\n"
valgrind --leak-check=yes ./uncompress ${OutputEncodeArray[0]} ${OutputDecodeArray[0]}

printf "\nCleaning up..."
make clean > /dev/null
printf "Done"
