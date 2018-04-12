#ifndef ACTORGRAPH_HPP
/*
 * Name: Huize
 * Userid: cs100vbo
 * PA4
 * 
 * This defines the graph object which contains all the methods for traversing
 * the graph and related helper methods.
 */

#define ACTORGRAPH_HPP

#include <fstream>
#include <iostream>
#include <sstream>
#include <queue>
#include <fstream>
#include "ActorNode.hpp"

// Graph Class

class ActorGraph {
public:
  // Hash map for nodes
  std::unordered_map<std::string, ActorNode*> nodeSet;
  // Hash map for movies
  std::unordered_map<std::string, Movie*> movieSet;

  ActorGraph();
  ~ActorGraph();

  /**
   * Load the graph from a tab-delimited file of actor->movie relationships.
   *
   * in_filename - input filename
   * use_weighted_edges - if true, compute edge weights as
   * 1 + (2015 - movie_year), otherwise all edge weights will be 1
   *
   * return true if file was loaded successfully, false otherwise
   */
  bool loadFromFile(const char* in_filename);

  // Load the actors pair and print out related information
  bool traverse(const char *in_filename, 
    std::ofstream &out, bool weighted);

  // Search the graph and print out information
  std::string bfs(std::string actorAName, std::string actorBName,
    std::unordered_map<std::string, ActorNode*> &nodeSet);

  // Search the graph with edge weight
  std::string dijkstra(std::string actorAName, std::string actorBName);

  // Back track the node sequence to get a string
  std::string printRelationship(ActorNode *temp);

  // Union find method
  int unionFind(std::string actorA, std::string actorB);
  
  // BFS connection
  int bfsConnection(std::string actorA, std::string actorB);

  // Find check if the two movies are connected by an actor
  //bool findConnection(const std::string movieA, const std::string movieB);

  // Recover sentinel hash value
  /*
  std::string recoverSentinel
    (std::unordered_map<std::string, std::string> &dSet, 
    std::string target);
  */

  // finding the Actor Connections (Driver)
  void findActorConnections(char *fileName, std::ofstream &out, 
    bool unionFind);

  // convert the movie sets into actor sets
  void convertSet(std::unordered_map<std::string, ActorNode*> &actors,
    std::unordered_map<std::string, Movie*> &movies);

  // Make a vector from unordered_map
  void makeVector(std::unordered_map<std::string, ActorNode*> &map, 
    std::vector<std::string> &vec);

  // merge the related movie sets using set_union
  void unionize(std::unordered_map<std::string, 
    std::vector<std::string>> &map);

  // Find an element in a vector
  bool vectorFind(std::vector<std::string> &vec, std::string target);

  // Check if two vectors contain overlapse
  bool checkIntercect(std::vector<std::string> a, 
    std::vector<std::string> b);

  // Reset the graph node values
  void resetGraph();
};

#endif // ACTORGRAPH_HPP
