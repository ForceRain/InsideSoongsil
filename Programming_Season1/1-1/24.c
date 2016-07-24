#include <stdio.h>
int main(void){
	int a[5][7], i, j, k;
	k=0;
	printf("Su Mo Tu We Th Fr Sa\n");
	for(i=0;i<5;i++)
		for(j=0; j<7;j++,k++)
			a[i][j]=k-1;
	a[0][0]=a[0][1]=a[4][4]=a[4][5]=a[4][6]=0;
	for (i=0; i<5; i++){
		for (j=0; j<7; j++){
			if(a[i][j]==0)
				printf("%2c%c", ' ', ' ');
			else
				printf(" %2d", a[i][j]);
		};
		printf("\n");
	}
	return 0;
}


