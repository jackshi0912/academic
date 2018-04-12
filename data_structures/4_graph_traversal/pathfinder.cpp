/* 
 * File:   pathfinder.cpp
 * Author: JackShi
 * Userid: cs100vbo
 * Created on August 25, 2016, 9:41 PM
 */

#include <cstring>
#include <fstream>
#include "pa4.hpp"
#include "ActorGraph.hpp"

int main (int argc, char *argv[]) {
  // Check for argument count number
  if (argc != ARG_COUNT) {
    std::cerr << "Wrong number of argument" << std::endl;
    return -1;
  }
  
  ActorGraph graph; // Making a graph object
  // Load the graph
  if (!graph.loadFromFile(argv[FILE_IDX])) {
    std::cerr << "Load failed" << std::endl;
    return -1;
  }

  std::ofstream out;
  out.open(argv[OUTPUT_IDX]);
  if (!out.is_open()) {
    std::cerr << "Problem writing to file" << std:: endl;
    return -1;
  }

  // Traverse the graph
  if (!graph.traverse(argv[ACTOR_PAIR_IDX], out, 
    strcmp(argv[WEIGHT_OPTION_IDX], WEIGHT_ID) == 0)) {
    std::cerr << "Traversal failed" << std::endl;
    return -1;
  }

  out.close();

  return 0;
}