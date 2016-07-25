#include <stdio.h>
int main(int argc, char *argv[]){
	int i,sum=0;
	for (i=0; i<3; i++) {
		sum+=atoi(argv[i+1]);
	}
	printf("평균은 %d입니다.", sum/3);
	return 0;
}
