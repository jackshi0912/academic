#ifndef TEST_CPP
#define TEST_CPP

#include <iostream>
#include <vector>
#include <fstream>
#include "DictionaryTrie.hpp"
#include "DictionaryBST.hpp"
#include "DictionaryHashtable.hpp"
#include "util.hpp"

using namespace std;

int main(int argc, char *argv[]) {
  // Initializing field
  ifstream in;
  in.open(argv[1]);

  if (!in.is_open()) {
    cerr << "File failed to open" << endl;
    return -1;
  }

  // Constructing dictionaries
  cout << endl << "Constructing Dictionary HashTable" << endl;
  DictionaryHashtable dh;
  cout << "Constructing Dictionary BST" << endl;
  DictionaryBST db;
  cout << "Constructing Dictionary Trie" << endl;
  DictionaryTrie dt;
  cout << "Construction Complete" << endl << endl;
  
  cout << "Loading Dictionary HashTable" << endl;
  Utils::load_dict(dh, in, 2000);
  in.clear();
  in.seekg(0, ios::beg);
  cout << "Loading Dictionary BST" << endl;
  Utils::load_dict(db, in, 2000);
  in.clear();
  in.seekg(0, ios::beg);
  cout << "Loading Dictionary Trie" << endl;
  Utils::load_dict(dt, in, 2000);
  in.clear();
  in.seekg(0, ios::beg);
  cout << "Loading Complete" << endl << endl;
 
  cout << "Testing Find" << endl;

  string line;
/*
  while (getline(in, line)) {
  //for (size_t i = 0; i < 10; i++) {
    //getline(in, line);
    if (in.eof()) break;
    line = line.substr(line.find(' ') + 1);
    if ((dh.find(line) == dh.find(line)) && (dh.find(line) == dt.find(line))) {
      continue;
    }
    cout << "find fail" << endl;
    return -1;
  }
  
  line = "!";

  if ((dh.find(line) != dh.find(line)) || (dh.find(line) != dt.find(line)))
    cout << "find fail" << endl;
  

  cout << "Find PASS" << endl << endl;
*/
  cout << "Test Autocomplete" << endl << endl;
  
  vector<string> complete = dt.predictCompletions("a", 10);
  for (size_t i = 0; i < complete.size(); ++i) {
    cout << complete[i] << endl;
  }
  return 0;
}

#endif
