/*
 * Name: Huize Shi
 * Userid: cs100vbo
 * PA r
 * ActorGraph.cpp
 * 
 * This is the implementation of the ActorGraph class
 * It contains methods for graph search and other helper methods
 */

#include "ActorGraph.hpp"
#include "pa4.hpp"
#include <limits>
#include <queue>
#include <algorithm>

using namespace std;

// ActorGraph Constructor

ActorGraph::ActorGraph() {
}

// Load the actorNode relationships from file

bool ActorGraph::loadFromFile(const char* in_filename) {
  ifstream infile(in_filename); // Initialize the file stream
  bool have_header = false; // Check if the header is processed

  // keep reading lines until the end of file is reached
  while (infile) { // Building the movie set
    string s; // Line of input
    if (!getline(infile, s)) break; // get the next line

    if (!have_header) { // Check header
      // skip the header
      have_header = true;
      continue;
    }

    istringstream ss(s);
    vector <string> record; // Line information
    while (ss) { // Getting a line of input
      string next;
      // get the next string before hitting a tab character and put it in 'next'
      if (!getline(ss, next, '\t')) break;
      record.push_back(next); // Obtain a field of data
    }

    if (record.size() != NUM_OF_COLUMNS) { // Check column count
      // We should have exactly 3 columns
      continue;
    }

    // Processing the line of input
    string actor_name(record[NAME_IDX]);
    string movie_title(record[TITLE_IDX]);
    int movie_year = stoi(record[YEAR_IDX]);

    // Building an movie
    Movie *tempMovie = new Movie(movie_title, movie_year);
    // Building an actor
    ActorNode *tempActor = new ActorNode(actor_name);

    // Link the movie with actor (double link)
    tempActor->movies.insert(make_pair(movie_title +
            to_string(movie_year), tempMovie));
    tempMovie->actors.insert(make_pair(actor_name, tempActor));

    // Load ActorNode into hash table
    if (nodeSet.find(actor_name) == nodeSet.end()) { // Node not found
      nodeSet.insert(make_pair(actor_name, tempActor)); // Add new actor
    } else { // Actor node found
      delete tempActor;
      // Add movie to the actor
      nodeSet[actor_name]->movies.insert(
              make_pair(tempMovie->title + to_string(tempMovie->year), tempMovie));
      // Link the movie to the actor
      tempMovie->actors[actor_name] = nodeSet[actor_name];
    }
  }

  // Link the movies back to actors
  // Loop through the actors in the hash table
  for (auto actorIt = nodeSet.begin(); actorIt != nodeSet.end(); actorIt++) {
    // Loop through the movies
    for (auto movieIt = actorIt->second->movies.begin();
            movieIt != actorIt->second->movies.end(); movieIt++) {
      // Check if the movie is already in the set
      if (movieSet.find(movieIt->first) != movieSet.end()) {
        // Delete old movie
        delete movieIt->second;
        // Simply link the existing movie to actor
        movieSet.find(movieIt->first)->second
                ->actors.insert(make_pair(actorIt->first, actorIt->second));
        // Link the actor to the movie
        actorIt->second->movies[movieIt->first] = movieSet[movieIt->first];
      } else { // Enter the new movie into the set
        movieSet.insert(make_pair(movieIt->second->title +
                to_string(movieIt->second->year), movieIt->second));
      }
    }
  }
  // Check file loading status
  if (!infile.eof()) {
    cerr << "Failed to read " << in_filename << "!\n";
    return false;
  }
  infile.close();
  return true;
}

// Method to load pairs

bool ActorGraph::traverse(const char *in_filename,
        ofstream &out, bool weighted) {

  ifstream infile(in_filename); // Initialize the file stream

  bool have_header = false; // Check if the header is processed
  string output = FILE_HEADER;

  // keep reading lines until the end of file is reached
  while (infile) {
    string s; // Line of input
    if (!getline(infile, s)) break; // get the next line

    if (!have_header) { // Check header
      // skip the header
      have_header = true;
      continue;
    }

    istringstream ss(s);
    vector <string> record; // Line information

    while (ss) { // Getting a line of input
      string next;
      // get the next string before hitting a tab character and put it in 'next'
      if (!getline(ss, next, '\t')) break;
      record.push_back(next); // Obtain a field of data
    }

    if (record.size() != NUM_OF_COLUMNS_PAIR) { // Check column count
      // we should have exactly 2 columns
      continue;
    }

    // Processing the line of input
    if (nodeSet.find(record[ACTOR_A_IDX]) != nodeSet.end() &&
            nodeSet.find(record[ACTOR_B_IDX]) != nodeSet.end()) {
      if (!weighted) {
        output += bfs(record[ACTOR_A_IDX], record[ACTOR_B_IDX], nodeSet);
      } else {
        output += dijkstra(record[ACTOR_A_IDX], record[ACTOR_B_IDX]);
      }
      if (!infile.eof()) {
        output += "\n";
      }
    } else {
      cerr << "Pair not found in the set" << endl;
    }
  }

  if (!infile.eof()) {
    //cerr << "Failed to read pairs " << in_filename << "!\n";
    return false;
  }
  infile.close();
  out << output;
  return true;
}

// BFS to establish connection between nodes
string ActorGraph::bfs(string actorAName, string actorBName,
        unordered_map<string, ActorNode*> &nodeSetIn) {
  // DEBUG: 
  //cerr << endl << "Begin to search: " << actorAName << " - " << actorBName
  //  << endl;
  // Prepare the queue
  queue<ActorNode*> storage; // Storage queue for BFS
  resetGraph(); // Reset graph values before searching
  if (nodeSetIn.find(actorAName) == nodeSetIn.end() ||
          nodeSetIn.find(actorBName) == nodeSetIn.end()) {
    // DEBUG: 
    // cerr << "Search not found" << endl;
    return ACTOR_NOT_FOUND;
  }

  ActorNode *actorA = nodeSetIn.find(actorAName)->second;

  // Set first starting node value
  actorA->distance = 0;
  actorA->done = 1;
  storage.push(actorA); // Push the starting node into the queue
  // BFS main loop
  // DEBUG: 
  // cerr << "Entering BFS" << endl;
  while (!storage.empty()) {
    // Dequeue a element
    ActorNode *temp = storage.front();
    storage.pop();
    // Push children onto storage
    // DEBUG: 
    // cerr << "Processing: " << temp->name << endl;
    for (auto movieIt = temp->movies.begin(); movieIt != temp->movies.end();
            movieIt++) { // Loop through movies related to this actors
      // Loop through all the other actors in the movie
      for (auto actorIt = movieIt->second->actors.begin();
              actorIt != movieIt->second->actors.end(); actorIt++) {
        // Check if the related actor is the same as the original
        if (actorIt->second->name == temp->name) {
          continue;
        }
        // Check if the actor is visited
        if (!actorIt->second->done) {
          // DEBUG: 
          // cerr << actorIt->second->name << " - ";
          actorIt->second->done = 1;
          actorIt->second->distance = temp->distance + 1;
          actorIt->second->prev
                  = new Path(movieIt->second, temp, temp->distance);
          storage.push(actorIt->second); // Push the actor onto the queue
        } else {
          // DEBUG: 
          //cerr << actorIt->second->name << "(visited) ";
        }
      }
      // DEBUG
      // cerr << endl;
    }

    if (temp->name == actorBName) { // Check if search should end
      // cerr << "Sucess" << endl;
      return printRelationship(temp);
    }
  }
  // cerr << "not Found" << endl;
  return REL_NOT_FOUND;
}

// Dijkstra's algorithm

string ActorGraph::dijkstra(string actorAName, string actorBName) {
  resetGraph();
  // Find the two actors from the set
  ActorNode *actorA = nodeSet[actorAName];
  actorA->distance = 0; // Preparing actor A for enqueue
  priority_queue<ActorNode*, vector<ActorNode*>, PQComparator> pq;
  pq.push(actorA); // Push first node onto the queue
  while (!pq.empty()) {
    ActorNode *temp = pq.top(); // Obtain the top element
    pq.pop();
    if (!temp->done) {
      temp->done = 1;
      // Loop through temp's neighbors
      // Loop through temp's movies
      for (auto movieIt = temp->movies.begin(); movieIt != temp->movies.end();
              movieIt++) {
        // Loop through each actors in those movies
        for (auto actorIt = movieIt->second->actors.begin();
                actorIt != movieIt->second->actors.end(); actorIt++) {
          int edge_distance = temp->distance + 1 // Getting edge distance
                  + (YEAR_OFFSET - movieIt->second->year);
          // Check if distance is good
          if (edge_distance < actorIt->second->distance) {
            actorIt->second->distance = edge_distance;
            actorIt->second->prev // Link the node to the past
                    = new Path(movieIt->second, temp, edge_distance);
            pq.push(actorIt->second);
          }
        }
      }
    }
    if (temp->name == actorBName) {
      return printRelationship(temp);
    }
  }
  return REL_NOT_FOUND;
}

// Printing method for the relationship strings
string ActorGraph::printRelationship(ActorNode *temp) {
  string output;
  // Back tracking and printing out the path
  while (temp->prev != nullptr) {
    output = "--[" + temp->prev->movies->title + "#@"
            + to_string(temp->prev->movies->year) + "]-->" + "("
            + temp->name + ")" + output;
    temp = temp->prev->node; // Traverse up the node
  }
  // Print out the original
  output = "(" + temp->name + ")" + output;
  return output;
}

// Union find for establishing a year of connection
int ActorGraph::unionFind(string actorA, string actorB) {
  // find earliest and latest movie year
  int earliestYear = movieSet.begin()->second->year;
  int latestYear = movieSet.begin()->second->year;
  unordered_map<string, vector < string>> disjointedSet;

  // Finding the max and min years
  for (auto it = movieSet.begin(); it != movieSet.end(); it++) {
    if (it->second->year < earliestYear)
      earliestYear = it->second->year;
    if (it->second->year > latestYear)
      latestYear = it->second->year;
  }

  // Loop through the years
  for (int years = earliestYear; years <= latestYear; years++) {
    // Adding movies into the set
    for (auto movieIt = movieSet.begin();
            movieIt != movieSet.end(); movieIt++) {
      if (movieIt->second->year == years) { // Adding movies in certain year
        vector<string> actorList;
        makeVector(movieIt->second->actors, actorList);
        // Add a vector of actor names to the set
        disjointedSet.insert(make_pair(movieIt->first, actorList));
        unionize(disjointedSet);
      }
    }
    // Check if the movie is enough
    for (auto it = disjointedSet.begin(); it != disjointedSet.end(); it++) {
      if (vectorFind(it->second, actorA) && vectorFind(it->second, actorB)) {
        return years;
      }
    }
  }
  
  return NO_OVERLAP_YEAR;
}

// Find a certain element in a vector
bool ActorGraph::vectorFind(vector<string> &vec, string target) {
  for (auto it = vec.begin(); it != vec.end(); it++) {
    if (*it == target) {
      return true;
    }
  }
  return false;
}

// Make a vector from unordered Map
void ActorGraph::makeVector(unordered_map<string, ActorNode*> &map,
        vector<string> &vec) {
  for (auto it = map.begin(); it != map.end(); it++) {
    vec.push_back(it->first);
  }
  // Sort the vectors for set_union
  sort(vec.begin(), vec.end());
}

// Unionize the sets
void ActorGraph::unionize(unordered_map<string, vector<string> > &map) {

  // Loop through the movies
  // Unionize the map
  for (auto it = map.begin(); it != map.end(); it++) {
    for (auto it2 = map.begin(); it2 != map.end(); it2++) {
      if (it->first == it2->first) { // Check if they are the same movie
        continue;
      }
      if (checkIntercect(it->second, it2->second)) { // Check for intersection
        // Making a temp vector container large enough for both
        vector<string> temp(it->second.size() + it2->second.size());
        vector<string>::iterator tempIt; // Temp iterator for join set
        // Make union set
        tempIt = set_union(it->second.begin(), it->second.end(),
                it2->second.begin(), it2->second.end(), temp.begin());
        temp.resize(tempIt - temp.begin()); // Resize the vector
        // Insert the newly unionized vector
        map.emplace(it->first + it2->first, temp);
        // Erase old vectors
        map.erase(it->first);
        map.erase(it2->first);
        it = map.begin();
        it2 = map.begin();
      }
    }
  }
}

// Check if two vectors contain the same element
bool ActorGraph::checkIntercect(vector<string> a, vector<string> b) {
  for (auto aIt = a.begin(); aIt != a.end(); aIt++) {
    for (auto bIt = b.begin(); bIt != b.end(); bIt++) {
      if (*aIt == *bIt) {
        return true;
      }
    }
  }
  return false;
}

// Driver for the BFS method of establishing connections
int ActorGraph::bfsConnection(std::string actorA, std::string actorB) {
  // cerr << "processing: " << actorA << " - " << actorB << endl;
  // find earliest and latest movie year
  int earliestYear = movieSet.begin()->second->year;
  int latestYear = movieSet.begin()->second->year;
  // Loop through movies to find the year bounds
  for (auto it = movieSet.begin(); it != movieSet.end(); it++) {
    if (it->second->year < earliestYear)
      earliestYear = it->second->year;
    if (it->second->year > latestYear)
      latestYear = it->second->year;
  }
  unordered_map<string, Movie*> yearlyMovieSet;
  // Loop through the years
  for (int years = earliestYear; years <= latestYear; years++) {
    // Loop through the movies in that year and add it to the set
    for (auto it = movieSet.begin(); it != movieSet.end(); it++) {
      if (it->second->year == years) {
        yearlyMovieSet.insert(make_pair(it->first, it->second));
      }
    }
    // Check if a connection is found by means of bfs
    unordered_map<string, ActorNode*> tempActorSet;
    convertSet(tempActorSet, yearlyMovieSet);
    if (bfs(actorA, actorB, tempActorSet) != REL_NOT_FOUND
      && bfs(actorA, actorB, tempActorSet) != ACTOR_NOT_FOUND) {
      return years;
    }
  }
  return NO_OVERLAP_YEAR;
}

// Onvert the movie set to actor set
void ActorGraph::convertSet(unordered_map<string, ActorNode*> &actors,
        unordered_map<string, Movie*> &movieSet) {
  // Looping through every actor in every movie to make a map of actors
  for (auto it = movieSet.begin(); it != movieSet.end(); it++) {
    for (auto actorIt = it->second->actors.begin(); actorIt != it->second->
            actors.end(); actorIt++) {
      // If actor does not exist in the map
      if (actors.find(actorIt->first) == actors.end()) {
        // Insert actor into the hash map
        actors.insert(make_pair(actorIt->first, actorIt->second));
      }
    }
  }
}

// Driver method
void ActorGraph::findActorConnections(char *in_filename,
        ofstream &out, bool unionFindOpt = true) {
  ifstream infile(in_filename); // Initialize the file stream
  // Check if the file is successfully opened
  if (!infile.is_open()) {
    cerr << "Problem opening the file" << endl;
    return;
  }
  // Read the file
  bool have_header = false; // Check if the header is processed

  out << CONNECTION_HEADER; // Write header

  // keep reading lines until the end of file is reached
  while (infile) { // Building the movie set
    string s; // Line of input
    if (!getline(infile, s)) break; // get the next line

    if (!have_header) { // Check header
      // skip the header
      have_header = true;
      continue;
    }

    istringstream ss(s);
    vector <string> record; // Line information
    while (ss) { // Getting a line of input
      string next;
      // get the next string before hitting a tab character and put it in 'next'
      if (!getline(ss, next, '\t')) break;
      record.push_back(next); // Obtain a field of data
    }

    if (record.size() != NUM_OF_COLUMNS_PAIR) { // Check column count
      // We should have exactly 3 columns
      continue;
    }

    // Processing the line of input
    string actorA(record[ACTOR_A_IDX]);
    string actorB(record[ACTOR_B_IDX]);


    // Depends on the options, use different method of approach
    if (unionFindOpt) {
      out << actorA << "\t" << actorB << "\t"
              << to_string(bfsConnection(actorA, actorB));
    } else {
      out << actorA << "\t" << actorB << "\t"
              << to_string(bfsConnection(actorA, actorB));
    }
    if (!infile.eof()) {
      out << "\n";
    }
  }

  infile.close();
}

// Reset the graph node values

void ActorGraph::resetGraph() {
  for (auto it = nodeSet.begin(); it != nodeSet.end(); it++) {
    // Reset node information
    it->second->distance = numeric_limits<int>::max();
    it->second->done = 0;
    // Delete path information
    if (it->second->prev) {
      delete it->second->prev;
    }
    it->second->prev = nullptr;
  }
}

// Destructor for ActorGraph

ActorGraph::~ActorGraph() {
  // Delete actor hash table elements
  for (auto actorIt = nodeSet.begin(); actorIt != nodeSet.end(); actorIt++) {
    delete actorIt->second;
  }
  // Delete movie hash table elements
  for (auto movieIt = movieSet.begin(); movieIt != movieSet.end(); movieIt++) {
    delete movieIt->second;
  }
}
