#include <stdio.h>
#define N 10
int main(void) {
	int grade[N]={10,20,30,40,50,60,70,80,90,100};
	int i;
#ifdef DEBUG 
	{
	for (i=0; i<10; i++) {
		printf("%d\n", grade[i]);
		}
	}
#endif
	return 0;
}
