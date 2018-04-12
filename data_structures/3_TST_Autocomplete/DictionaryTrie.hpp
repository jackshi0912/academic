/* Name: Huize Shi
 * Userid: cs100vbo
 * Date: August 19, 2016
 * PA3: DictionaryTrie.hpp
 * The DictionaryTrie class
 */

#ifndef DICTIONARY_TRIE_HPP
#define DICTIONARY_TRIE_HPP

#include <vector>
#include <string>
#include <queue>

#define ALPHABET_SIZ 27
#define HASH_OFFSET 97

class TrieNode; // Forward class declaration

// Custom comapre class for priority queue of pointers
class CompareFreq {
public:
  bool operator()(TrieNode *first, TrieNode *second) const;
};

class CompareMaxFreq {
public:
  bool operator()(TrieNode *first, TrieNode *second) const;
};

// TrieNode class definition
class TrieNode {

  public:
    /** Constructor that could potentially obtain a key */
    TrieNode(std::string key = "", int frequency = 0);

    std::string key; // Key string
    unsigned int maxFrequency;
    unsigned int frequency; // Frequency of the word
    TrieNode *children[ALPHABET_SIZ]; // Array of pointer to children
    bool end;
};

/**
 *  The class for a dictionary ADT, implemented as a trie
 *  You may implement this class as either a mulit-way trie
 *  or a ternary search trie, but you must use one or the other.
 *
 */
class DictionaryTrie
{
  public:

    /* Create a new Dictionary that uses a Trie back end */
    DictionaryTrie();

    /* Insert a word with its frequency into the dictionary.
     * Return true if the word was inserted, and false if it
     * was not (i.e. it was already in the dictionary or it was
     * invalid (empty string) */
    bool insert(std::string key, unsigned int freq);

    /* Return true if word is in the dictionary, and false otherwise */
    bool find(std::string key) const;
   
    /* Obtain the node based on the string key */
    TrieNode *getNode(std::string key) const;

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
    std::vector<std::string>
      predictCompletions(std::string prefix, unsigned int num_completions);

    /** Perform BFS on the subtree */
    void BFS(TrieNode* root, std::priority_queue<TrieNode*, 
    std::vector<TrieNode*>, CompareFreq> &completions_queue);

  /** Destructor */
  ~DictionaryTrie();
  /** Recursive tree deleting method */
  void memDelete(TrieNode *curNode);

  private:
  TrieNode *root;
};


#endif // DICTIONARY_TRIE_HPP
