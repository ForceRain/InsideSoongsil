#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main(void) {
	int lotto[5][6]={0};
	int check[45]={0};
	int i=0,n=0,j=0,temp;
	srand(time(NULL));
	while (n<5) {
		for (i=0; i<45; i++) {
			check[i]=i+1;
		}
		for(i=0; i<6; i++) {
			temp=(rand()%45)+1;
			for (j=0; j<45; j++) {
				if (temp==j+1 && check[j]==-1) {
					i--;
					j=45;	
				}
				if (j+1==temp && check[j]!=-1) {
					lotto[n][i]=temp;
					check[j]=-1;
					j=45;
				}
			}
		}
		n++;
	}
	for (i=0; i<5; i++) {
		for (n=0; n<5; n++) {
			for (j=5; j>n; j--) {
				if (lotto[i][j-1]>lotto[i][j]) {
					temp=lotto[i][j-1];
					lotto[i][j-1]=lotto[i][j];
					lotto[i][j]=temp;
				}
			}
		}
	}
	for (i=0; i<5; i++) {
		printf("%d세트 : ", i+1);
		for (j=0; j<6; j++) {
			if (j==5)
				printf("%d\n", lotto[i][j]);
			else
				printf("%d, ", lotto[i][j]);
		}
	}
	return 0;
}
