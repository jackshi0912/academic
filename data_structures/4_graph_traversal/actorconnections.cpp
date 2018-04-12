/* 
 * File:   actorconnections.cpp
 * Author: JackShi
 * Userid: cs100vbo
 * Created on August 30, 2016, 3:10 PM
 * 
 * This is the driver for finding connections between two actors
 */


#include <cstring>
#include <fstream>
#include "pa4.hpp"
#include "ActorGraph.hpp"

using namespace std;

// Driver class for the actorconnections program

int main(int argc, char *argv[]) {

  // Check for argument count number
  if (argc != ARG_COUNT) {
    cerr << "Wrong number of argument" << endl;
    return -1;
  }

  ActorGraph graph; // Making a graph object
  // Load the graph
  if (!graph.loadFromFile(argv[FILE_IDX])) {
    cerr << "Load failed" << endl;
    return -1;
  }

  ofstream out;
  out.open(argv[CONNECTION_OUTPUT_IDX]);
  if (!out.is_open()) {
    cerr << "Problem writing to file" << endl;
    return -1;
  }

  // Connect the actors
  if (strcmp(argv[CONNECTION_OPTION_IDX], UNION_ID) == 0) {
    graph.findActorConnections(argv[CONNECTION_PAIR_IDX], out, true);
  } else if (strcmp(argv[CONNECTION_OPTION_IDX], BFS_ID) == 0) {
    graph.findActorConnections(argv[CONNECTION_PAIR_IDX], out, false);
  } else {
    cerr << "Invalid options" << endl;
  }

  out.close();

  return 0;
}
