/* Name: Huize Shi
 * Userid: cs100vbo
 * Date: August 19, 2016
 * PA3: benchdict.cpp
 * Benchmark the performance of the three data structures
 */


#include <iostream>
#include <fstream>
#include "DictionaryTrie.hpp"
#include "DictionaryBST.hpp"
#include "DictionaryHashtable.hpp"
#include "util.hpp"

#define NUM_OF_ARGS 5
#define MIN_SIZE 1
#define STEP_SIZE 2
#define NUM_STEP 3
#define DICT_DIR 4
#define DEC_BASE 10
#define AVER_NUM 200

using namespace std;

int main(int argc, char *argv[]) {

  // Check for correct input
  if (argc != NUM_OF_ARGS ) {
    cerr << "Wrong number of arguments\n";
    return -1;
  }

  ifstream in; // Initialize ifstream
  in.open(argv[DICT_DIR]);

  // Check for file open status
  if (!in.is_open()) {
    cerr << "Error opening the file" << endl;
    return -1;
  }

  // Parse the command line argumentss
  int minSize = stoi(argv[MIN_SIZE], nullptr, DEC_BASE);
  int stepSize = stoi(argv[STEP_SIZE], nullptr, DEC_BASE);
  int numStep = stoi(argv[NUM_STEP], nullptr, DEC_BASE);
  long long time;
  Timer tm;

  // Testing BST
  cout << endl << "Dictionary BST" << endl;
  for (size_t i = 0; i < (unsigned int)numStep; i++) {
    DictionaryBST *db = new DictionaryBST();
    time = 0;
    Utils::load_dict(*db, in, minSize + i * stepSize); // Load dictionary
    tm.begin_timer(); // Set timmer
    // Run 200 times for average result
    for (size_t j = 0; j < AVER_NUM; j++) {
      if(db->find("oasjdfosgfoashgoij")) {
        cerr << "Problem: Word Found" << endl;
        return -1;
      }
    }
    time = tm.end_timer()/AVER_NUM; // End timer
    // Output result
    cout << minSize + i * stepSize << "\t" << time << endl;
    // Reset
    in.clear();
    in.seekg(0, ios::beg);
    delete db;
  }

  cout << endl;

  // Testing Hash Table
  cout << endl << "Dictionary Hashtable" << endl;
  for (size_t i = 0; i < (unsigned int)numStep; i++) {
    DictionaryHashtable *dh = new DictionaryHashtable();
    time = 0;
    Utils::load_dict(*dh, in, minSize + i * stepSize); // Load dictionary
    tm.begin_timer(); // Set timmer
    // Run 200 times for average result
    for (size_t j = 0; j < AVER_NUM; j++) {
      if(dh->find("oasjdfosgfoashgoij")) {
        cerr << "Problem: Word Found" << endl;
        return -1;
      }
    }
    time = tm.end_timer()/AVER_NUM; // End timer
    // Output result
    cout << minSize + i * stepSize << "\t" << time << endl;
    // Reset
    in.clear();
    in.seekg(0, ios::beg);
    delete dh;
  }

  cout << endl;

  // Testing Trie
  cout << endl << "Dictionary Trie" << endl;
  for (size_t i = 0; i < (unsigned int)numStep; i++) {
    DictionaryTrie *dt = new DictionaryTrie();
    time = 0;
    Utils::load_dict(*dt, in, minSize + i * stepSize); // Load dictionary
    tm.begin_timer(); // Set timmer
    // Run 200 times for average result
    for (size_t j = 0; j < AVER_NUM; j++) {
      if(dt->find("oasjdfosgfoashgoij")) {
        cerr << "Problem: Word Found" << endl;
        return -1;
      }
    }
    time = tm.end_timer()/AVER_NUM; // End timer
    // Output result
    cout << minSize + i * stepSize << "\t" << time << endl;
    // Reset
    in.clear();
    in.seekg(0, ios::beg);
    delete dt;
  }
}
