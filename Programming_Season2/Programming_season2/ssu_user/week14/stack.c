#include "stack.h"
static stack s;
void reset(void)
{
	s.top = EMPTY;
}
void push(char c)
{
	s.top++;
	s.s[s.top] = c;
}
char pop(void)
{
	return (s.s[s.top--]);
}
char top(void)
{
	return (s.s[s.top]);
}
bool empty(void)
{
	return (s.top == EMPTY);
}
bool full(void)
{
	return (s.top == FULL);
}

