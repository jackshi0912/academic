#!/bin/bash

printf "Cleaning up: *.dvi *.aux *.swp *.log *.synctex.gz ... "
find . \( -name "*.dvi" -o -name "*.aux" -o -name "*.swp" -o -name "*.log" \
	-o -name "*.synctex.gz" \) -type f -delete
printf "Done\n"

