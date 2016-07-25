#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#define N 20
int main(void) {
	char *word[10];
	char input[N];
	int i;
	for (i=0; i<10; i++) {
		printf("%d word input : ", i+1);
		scanf("%s", input);
		word[i]=(char *)malloc(strlen(input)+1);
		strcpy(word[i],input);
	}
	for (i=0; i<10; i++) {
		printf("%d word : %s\n", i+1, word[i]);
	}
	return 0;
}
