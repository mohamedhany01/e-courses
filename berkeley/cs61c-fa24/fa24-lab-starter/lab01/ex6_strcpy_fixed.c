#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main() {
  // TODO: Create space to store the string "hello"
  // You may use your solution from a previous exercise;
  const int HELLO_SIZE = 6;
  char message[HELLO_SIZE];
  message[0] = 'h';
  message[1] = 'e';
  message[2] = 'l';
  message[3] = 'l';
  message[4] = 'o';
  message[5] = '\0';

  // Print out the value before we change message
  printf("Before copying: %s\n", message);

  // Creates another_string that contains a longer string
  char* long_message = "Here's a really long string";

  // TODO: Copy the string in long_message to message
  strncpy(message, long_message, HELLO_SIZE);

  // Print out the value after we change message
  printf("After copying: %s\n", message);

  return 0;
}
