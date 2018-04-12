/*
 * ActorNode.cpp
 *
 *  Created on: Aug 23, 2016
 *      Author: JackShi
 * 
 * This file implements various elements that are used in the construction 
 * of graph nodes
 */

#include "ActorNode.hpp"

using namespace std;

// Node Constructor
ActorNode::ActorNode(string name = "") : distance(0), 
        prev(nullptr), done(0), name(name) {}

// Comparator class for priority queue
bool PQComparator::operator()(const ActorNode* a, const ActorNode* b) {
  return (a->distance > b->distance);
}

// Movie constructor
Movie::Movie(string title = "", 
  unsigned int year = 0) : title(title), year(year) {}

// Path Constructor
Path::Path(Movie *movie, ActorNode *node, int distance) : 
  movies(movie), node(node), distance(distance) {}