#include <stdio.h>
#include <string.h>
#include <stdlib.h>

#define BUFSIZE 100
#define TRUE 1

//struct tree_node can be reference simply as Node
typedef struct tree_node Node;

//struct definition of Node
struct tree_node {
    char* value;
    Node* leftNode;
    Node* rightNode;
};

Node* root = NULL;

/*-----------------------------------------------------------------------------
 * Function name:   print
 * Input:           pointer to a struct of type Node 
 * Output:          none
 * Result:          the string value of node is printed
 * Notes:           DO NOT EDIT THIS FUNCTION
 ----------------------------------------------------------------------------*/
void printNode(Node * node) {
    printf("%s\n", node->value);
}

/*-----------------------------------------------------------------------------
 * Function name:   compare
 * Input:           two char pointers 
 * Output:          0  if both strings are equal
 *                  <0 the first character that does not match has a 
 lower value in ptr1 than in ptr2
 >0 the first character that does not match has a 
 greater value in ptr1 than in ptr2
 * Notes:           USE THIS TO COMPARE STRINGS WHEN INSERTING/FINDING
 ----------------------------------------------------------------------------*/
int compare(char* a, char* b) {
    while (*a != '\0' || *b != '\0') { // Loop through the strings
	// Check if the strings have different length
	if ((a == '\0' && b != '\0') || (a != '\0' && b == '\0')) {
	return -1;
       } else if (*a > *b) { // Check if a is ranked greater than b
            return 1;
        } else if (*a < *b) { // Check if a is ranked lower than b
            return -1;
        }
        a++;
        b++;
    }
    return 0; // If no differences are detected, return 0 as default
}

/*-----------------------------------------------------------------------------
 * Function name:   insertRecursive
 * Input:           char pointer, current node pointer
 * Output:          current node pointer
 * Result:          a node having input as value is inserted into it's
 appropriate location in the tree
 ----------------------------------------------------------------------------*/
Node* insertRecursive(char *input, Node *curNode) {
    // Base case in case root is null
    if (curNode ==  NULL) {
        // Create a new node and set all the incapsulated variables to 
        // appropriate values
        curNode = (Node *)malloc(sizeof(Node));
        curNode->leftNode = (Node*)NULL;
        curNode->rightNode = (Node*)NULL;
        curNode->value = input;

    } else if (compare(input, curNode->value) > 0) { // if input is greater
        if (curNode->rightNode == NULL) { // Check if right node exist
            // If right node does not exist, make the node
            curNode->rightNode= (Node *)malloc(sizeof(Node));
            curNode->rightNode->leftNode = (Node*)NULL;
            curNode->rightNode->rightNode = (Node*)NULL;
            curNode->rightNode->value = input;
        } else {
            // If right node exist, keep going down the tree;
            insertRecursive(input, curNode->rightNode);
        }
    } else if (compare(input, curNode->value) < 0) { // if input is lesser
        if (curNode->leftNode == NULL) { // Check if left node exist
            // If left node does not exist, make the node
            curNode->leftNode= (Node *)malloc(sizeof(Node));
            curNode->leftNode->leftNode = (Node*)NULL;
            curNode->leftNode->rightNode = (Node*)NULL;
            curNode->leftNode->value = input;
        } else {
            // If left node exist, keep going down the tree;
            insertRecursive(input, curNode->leftNode);
        }
    }
    return curNode;
}

/*-----------------------------------------------------------------------------
 * Function name:   insert
 * Input:           char pointer
 * Output:          none
 * Result:          a node having input as value is inserted into it's
 appropriate location in the tree
 ----------------------------------------------------------------------------*/
void insert(char* input) {
    if(root == NULL) {
        root = insertRecursive(input, root);
    } else {
        insertRecursive(input, root);
    }
}

/*-----------------------------------------------------------------------------
 * Function name:   findRecursive
 * Input:           char pointer, current node pointer
 * Output:          none
 * Result:          Print out appropriate messages to stdout
 ----------------------------------------------------------------------------*/
void findRecursive(char* input, Node* curNode) {
    if (curNode == NULL) { // Check if end of tree is reached
        printf("NOT found: %s\n", input);
    } else if (compare(input, curNode->value) == 0) { // If value is found
        printf("Found: ");
        printNode(curNode);

    // Check if value is greater than current node's value
    } else if (compare(input, curNode->value) > 0) {
        // Move down right
        findRecursive(input, curNode->rightNode);
    } else { // Check if value is less than current node's value
        // Move down left
        findRecursive(input, curNode->leftNode);
    }
}

/*-----------------------------------------------------------------------------
 * Function name:   find
 * Input:           char pointer
 * Output:          none
 * Result:          see write-up
 ----------------------------------------------------------------------------*/
void find(char* input) {
    findRecursive(input, root);
}


/*-----------------------------------------------------------------------------
 * Function name:   traverseRecursive
 * Input:           Node pointer to current node
 * Output:          none
 * Result:          the nodes in the tree are printed in order of increasing
 *                  value
 ----------------------------------------------------------------------------*/
void traverseRecursive(Node* curNode) {
    if (curNode != NULL) {
        // Inorder tranverse
        traverseRecursive(curNode->leftNode);
        printNode(curNode); // Print out the value of the current Node
        traverseRecursive(curNode->rightNode);
    }
}

/*-----------------------------------------------------------------------------
 * Function name:   traverse
 * Input:           none
 * Output:          none
 * Result:          the nodes in the tree are printed in order of increasing
 *                  value
 ----------------------------------------------------------------------------*/
void traverse() {
    traverseRecursive(root); 
}

//DO NOT EDIT
void insertPrompt() {
    char * input = malloc(sizeof(char)*(BUFSIZE+1));
    printf("Enter a string to insert (max length 100): ");
    scanf("%s", input);
    insert(input);
}

//DO NOT EDIT
void lookupPrompt() {
    char * input = malloc(sizeof(char)*(BUFSIZE+1));
    printf("Enter a string to look up: ");
    scanf("%s", input);
    find(input);
}

//MUST MATCH STARTER CODE ON TURNIN 
int main() {

    char input[BUFSIZE];

    printf("Binary Search Tree!\n");

    while (!feof(stdin)) {

        printf("Select an Operation: (i)nsert, (l)ookup, (p)rint in order, (q)uit: ");
        scanf("%s", input);

        if (feof(stdin)) {
            break;
        }
        if (input[0]=='i') {
            insertPrompt();
        }
        else if (input[0]=='l') {   
            lookupPrompt();
        }
        else if (input[0]=='p') {
            traverse();
        }
        else if (input[0] == 'q') {
            break;
        }
        else {
            printf("Invalid option selected, try again\n"); 
        }
    }

    return 0;
}

