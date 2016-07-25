#include <stdio.h>
#include <string.h>
#define N 100
int main(void) {
	char name[N];
	printf("입력 : ");
	scanf("%s", name);
	printf("strlen(name) : %d", strlen(name));
	return 0;
}
