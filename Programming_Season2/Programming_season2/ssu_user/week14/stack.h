#include <stdio.h>
#include <stdbool.h>
#define MAX_LEN 100
#define EMPTY -1
#define FULL  (MAX_LEN-1)
typedef struct stack {
	char    s[MAX_LEN];
	int top;
} stack;
void reset(void);
void push(char c);
char pop(void);
char top(void);
bool empty(void);
bool full(void);


