/*
 * ActorNode.hpp
 *
 *  Created on: Aug 23, 2016
 *      Author: JackShi
 * 
 * This contains various classes that are member objects of the graph and 
 * each other. These include graph nodes and paths, etc.
 */

#ifndef ACTORNODE_HPP_
#define ACTORNODE_HPP_

#include <unordered_map>
#include <string>
#include <functional>

class Path;
class Movie;

// Node in the graph
class ActorNode {
public:
  // Dijkstra's algorithm elements
  int distance;
  Path *prev;
  bool done;
  // Actor elements
  std::string name;
  // Link to movies
  std::unordered_map<std::string, Movie*> movies;
  ActorNode(std::string name);
};

// Compare the node pointers for Dijkstra priority queue
class PQComparator {
public:
  bool operator()(const ActorNode *a, const ActorNode *b);
};

// Path Class

class Path {
public:
  Movie *movies;
  ActorNode* node;
  int distance;
  Path(Movie *movie, ActorNode *node, int distance);
};

// Movie Class

class Movie {
public:
  std::string title;
  int year;
  // Link to actors
  std::unordered_map<std::string, ActorNode*> actors;
  Movie(std::string title, unsigned int year);
};

#endif /* ACTORNODE_HPP_ */
