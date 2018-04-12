#!/bin/bash

RED='\033[0;31m'
GREEN='\033[0;32m'
NC='\033[0m'

printf "Compiling ... "
make clean >/dev/null
make all > /dev/null
printf "${GREEN}Done\n${NC}"

printf "\nTest unweighted\n"

	./pathfinder movie_casts.tsv u test_pairs.tsv \
	out_paths_unweighted_test.tsv

printf "Test weighted\n"

	./pathfinder movie_casts.tsv w test_pairs.tsv \
	out_paths_weighted_test.tsv


printf "Test connections Union\n"

	./actorconnections movie_casts.tsv test_pairs.tsv \
	connections_union_test.tsv "ufind"


printf "Test connections BFS\n"

	./actorconnections movie_casts.tsv test_pairs.tsv \
	connections_bfs_test.tsv "bfs"


if [ $? -eq 0 ]; then
	printf "Test ${GREEN}PASS${NC}\n"
else
	printf "Test ${RED}FAILED${NC}\n"
fi

make clean > /dev/null
