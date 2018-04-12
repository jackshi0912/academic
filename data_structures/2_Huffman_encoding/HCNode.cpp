/**
 * Name: Huize Shi
 * Userid: cs100vbo
 * Date: August 12, 2016
 * PA 2: HCNode.cpp
 */

#include "HCNode.hpp"

/** Over loaded comparison operator for the max heap */
bool HCNode::operator<(const HCNode &other) {
  if (count != other.count) // Check if count match
    return (count > other.count); // Return opposit logic
  return symbol < other.symbol; // Else compare symbols
}

