#include <stdio.h>
int main(void){
	unsigned f;
	int i;
	i=f=1;
	while(i<=5){
		f*=i;
		i++;
	}
	printf("5!=%u\n", f);
	return 0;
}

