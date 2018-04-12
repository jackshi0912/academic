/* 
 * File:   pa4.hpp
 * Author: Acer
 *
 * Created on August 24, 2016, 12:20 PM
 * 
 * Constants used in this program
 */

#ifndef PA4_HPP
#define PA4_HPP

#define NAME_IDX 0
#define TITLE_IDX 1
#define YEAR_IDX 2
#define NUM_OF_COLUMNS_PAIR 2
#define NUM_OF_COLUMNS 3
#define ARG_COUNT 5
#define FILE_IDX 1
#define CONNECTION_PAIR_IDX 2
#define CONNECTION_OUTPUT_IDX 3
#define CONNECTION_OPTION_IDX 4
#define WEIGHT_OPTION_IDX 2
#define ACTOR_PAIR_IDX 3
#define OUTPUT_IDX 4
#define ACTOR_A_IDX 0
#define ACTOR_B_IDX 1
#define YEAR_OFFSET 2015

#define FILE_HEADER "(actor)--[movie#@year]-->(actor)--...\n"
#define CONNECTION_HEADER "Actor1\tActor2\tYear\n"
#define REL_NOT_FOUND "Relationship not found"
#define ACTOR_NOT_FOUND "Actors not found"
#define WEIGHT_ID "w"
#define UNION_ID "ufind"
#define BFS_ID "bfs"
#define NO_OVERLAP_YEAR 9999

#endif /* PA4_HPP */