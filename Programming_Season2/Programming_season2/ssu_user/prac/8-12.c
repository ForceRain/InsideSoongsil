#include <stdio.h>
int main(void) {
	char *p="C is fun", *q="C is fun";
	if(p==q) 
		printf("같은 메모리에 하나만 저장됩니다. \n");
	else
		printf("다른 메모리에 따로 저장됩니다. \n");
	return 0;

}
