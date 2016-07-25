#include <stdio.h>
#include <time.h>
int main(void) {
	struct tm *t;
	time_t now;
	now=time(NULL);
	t=localtime(&now);

}
