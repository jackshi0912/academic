/* Name: Huize Shi
 * Userid: cs100vbo
 * Date: August 19, 2016
 * PA3: DictionaryTrie.cpp
 * The implementation of DictionaryTrie and TrieNode class
 */

#include "util.hpp"
#include "DictionaryTrie.hpp"

/* Create a new Dictionary that uses a Trie back end */
DictionaryTrie::DictionaryTrie(){
  root = new TrieNode();
}

/* Insert a word with its frequency into the dictionary.
 * Return true if the word was inserted, and false if it
 * was not (i.e. it was already in the dictionary or it was
 * invalid (empty string) */
bool DictionaryTrie::insert(std::string key, unsigned int freq) {
  int letterHash;
  // Check if the string is empty
  if (!key.length()) {
    return false;
  }
  
  // DEBUG: 
  // std::cerr << "Inserting: " << key << ": " << freq << std::endl;
  
  TrieNode *curNode = root; // Iteration node pointer

  for (size_t i = 0; i < key.length(); i++) { // Traverse down the tree
    // Obtain letter hash value
    // DEBUG:
    // std::cerr << "CurNode: " << curNode->key << std::endl;
    if (key.at(i) == ' ') {
      letterHash = ALPHABET_SIZ-1;
    } else {
      letterHash = static_cast<unsigned char>(key.at(i)) - HASH_OFFSET;
    }

    // Check if hashvalue is out of the scope
    if (letterHash >= ALPHABET_SIZ || letterHash < 0)
      return false;

    // Check if the child exist
    if (!curNode->children[letterHash]) {
      // Make new link
      curNode->end = false;
      curNode->children[letterHash] = new TrieNode();
      // DEBUG:
      // std::cerr << "Making new link for " << key.at(i) << std::endl;
    }
    curNode->maxFrequency = 
      (freq > curNode->maxFrequency)?freq:curNode->maxFrequency;
    // Traverse down the trie
    curNode = curNode->children[letterHash];
  }
  // Node already exist
  if (curNode->frequency) {
    return false;
  }

  // Make the node an end node
  curNode->frequency = freq;
  curNode->maxFrequency = 
    (freq > curNode->maxFrequency)?freq:curNode->maxFrequency;
  curNode->key = key;
  // Check if it is an end node
  curNode->end = true;

  for (size_t i = 0; i < ALPHABET_SIZ; i++) {
    if(curNode->children[i]) {
      curNode->end = false;
    }
  }
  return true;

  // DEBUG:
  // std::cerr << "End Node: " << curNode->key 
  // << ": " << curNode->frequency << std::endl;
}

/* Return true if word is in the dictionary, and false otherwise */
bool DictionaryTrie::find(std::string key) const {
  // Check if the string is empty
  if (!key.length()) {
    return false;
  }

  // DEBUG: 
  // std::cerr << "Finding " << key << std::endl;

  unsigned int letterHash;
  // Iteration node pointer
  TrieNode *curNode = root; 
  for (size_t i = 0; i < key.length(); i++) {
    if (key.at(i) == ' ') {
      letterHash = ALPHABET_SIZ-1;
    } else {
      letterHash = static_cast<unsigned char>(key.at(i)) - HASH_OFFSET;
    }

    if (letterHash >= ALPHABET_SIZ || letterHash < 0) {
      return false;
    }

    // DEBUG:
    // std::cerr << key.at(i) << " is hashed to: " << letterHash << std::endl;

    // Check if the child exist
    if (!curNode->children[letterHash]) {
      // DEBUG:
      // std::cerr << "No such node" << std::endl;
      return false;
    }
    // Traverse down the trie
    curNode = curNode->children[letterHash];
  }
  // Checking if end Node has the same key
  // DEBUG: 
  // std::cerr << "EndNode: " << curNode->key << " : " 
  // << curNode->frequency << std::endl;
  return curNode->key == key;
}

/* Return the Node with a given key */
TrieNode *DictionaryTrie::getNode(std::string key) const {
  // Check if the string is empty
  if (!key.length()) {
    return 0;
  }
  unsigned int letterHash;
  // Iteration node pointer
  TrieNode *curNode = root; 
  for (size_t i = 0; i < key.length(); i++) {
    if (key.at(i) == ' ') {
      letterHash = ALPHABET_SIZ-1;
    } else {
      letterHash = static_cast<unsigned char>(key.at(i)) - HASH_OFFSET;
    }

    // Check if the hash key is out of scope
    if (letterHash >= ALPHABET_SIZ || letterHash < 0) {
      return 0;
    }

    // Check if the child exist
    if (!curNode->children[letterHash]) {
      return 0;
    }
    // Traverse down the trie
    curNode = curNode->children[letterHash];
  }
  // Return the node
  return curNode;
}

/* Return up to num_completions of the most frequent completions
 * of the prefix, such that the completions are words in the dictionary.
 * These completions should be listed from most frequent to least.
 * If there are fewer than num_completions legal completions, this
 * function returns a vector with as many completions as possible.
 * If no completions exist, then the function returns a vector of size 0.
 * The prefix itself might be included in the returned words if the prefix
 * is a word (and is among the num_completions most frequent completions
 * of the prefix)
 */
std::vector<std::string> DictionaryTrie::predictCompletions(std::string prefix,
  unsigned int num_completions) {

  TrieNode *curNode = getNode(prefix); // Prefix Node
  std::vector<std::string> complete; // Vector of auto complete

  // Check if curNode is null
  if (!curNode){
    return complete;
  }

  
  std::priority_queue<TrieNode*, std::vector<TrieNode*>, 
    CompareMaxFreq> subTrees; // Order subtrees in order of MaxFreq

  std::priority_queue<TrieNode*, std::vector<TrieNode*>, 
    CompareFreq> completion_queue; // Order completion in order of freqency
  
  // Push prefix onto the priority queue if it's a word
  if (curNode->frequency) completion_queue.push(curNode);
  
  // Push children of curNode onto the subtree priority queue
  
  for (size_t i = 0; i < ALPHABET_SIZ; i++) {
    if(curNode->children[i]) 
      subTrees.push(curNode->children[i]);
  }

  // Loop through all subtrees
  for (size_t i = 0; i < subTrees.size(); i++) {
    BFS(subTrees.top(), completion_queue); // Get a subtree
    subTrees.pop();
    while (!completion_queue.empty()) { // Process a subtree
      // Check if the top frequency is less than the next tree's max frequency
      if (completion_queue.top()->frequency < subTrees.top()->maxFrequency) {
        break; // Process the next tree
      }
      complete.push_back(completion_queue.top()->key); // Add key to vector
      completion_queue.pop();
      if (complete.size() == num_completions) { // If the vector is big enough
        // DEBUG:
        // std::cerr << "DONE" << std::endl;
        return complete;
      }
    }
  }

  // DEBUG:
  //std::cerr << "OUT Of VALUES" << std::endl;
  return complete;
}

/** Breadth First Search */
void DictionaryTrie::BFS(TrieNode* root, std::priority_queue<TrieNode*, 
  std::vector<TrieNode*>, CompareFreq> &completion_queue) {
    
    // DEBUG:
    // std::cerr << "BFS" << std::endl;

    std::queue<TrieNode*> temp; // Container for BFS
    temp.push(root); // pushing node onto queue
    while (!temp.empty()) { // BFS loop
      if (temp.front()->frequency) { // Check if the node is a word
        completion_queue.push(temp.front()); // Enlisting the front node
        // DEBUG:
        // std::cerr << "Pusing: " << temp.front()->key << std::endl;
      }

      if (!temp.front()->end) { // If not a end node child
        for (size_t i = 0; i < ALPHABET_SIZ; ++i) { // Adding all the children
          if (temp.front()->children[i]) {
            temp.push(temp.front()->children[i]);
          }
        }
      }
      temp.pop();
    }
  }

// Destructor for the tree
DictionaryTrie::~DictionaryTrie() {
  memDelete(root);
}

// Post order traversal resursivly delete tree
void DictionaryTrie::memDelete(TrieNode *curNode) {
  if (!curNode)
    return;
  for (size_t i = 0; i < ALPHABET_SIZ; ++i) {
    if (curNode->children[i])
      memDelete(curNode->children[i]);
  }
  delete curNode;
}

// TrieNode implementations

/* Constructor for the TrieNode */
TrieNode::TrieNode(std::string key, int frequency)
  :key(key), maxFrequency(frequency), frequency(frequency), children(), 
  end(false) {}

// Compare function () operator overload for priority queue comparison
bool CompareFreq::operator()(TrieNode *first, TrieNode *second) const {
  if (first->frequency != second->frequency) {
    return first->frequency < second->frequency; // Return larger frequency
  }
  return first->key < second->key; // Use key to break ties
}

// Compare function () operator overload for priority queue comparison
bool CompareMaxFreq::operator()(TrieNode *first, TrieNode *second) const {
  if (first->maxFrequency != second->maxFrequency) {
    return first->maxFrequency < second->maxFrequency; // Return larger frequency
  }
  return first->key < second->key; // Use key to break ties
}
