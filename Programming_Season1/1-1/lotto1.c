#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int lotto_num(void){
	int lotto[5][6], i=0, j=0, n, tmp;
	srand(time(NULL));
	for(i=0; i<5 ;i++)
			{for (j=0; j<6; j++)
				{lotto [i][j] = (rand()%45)+1;
		for (j=0; j<6; j++){
			for (n=6; n>j ; n--){
				if (lotto[i][n-1] > lotto[i][n]){ 
					tmp = lotto[i][n-1];
					lotto[i][n-1] = lotto[i][n];
					lotto[i][n] = tmp;};};};
					
					for (j=0; j<=4; j++)
					{
						if (lotto[i][j] == lotto[i][j+1])
							return 1;
						else 
							return 0;};};};
	}

	int main(void){
		int k,i,j,lotto[5][6];
		while (k)
		{k = lotto_num();
			if (k==0)
				k=0;
			else
				k=1;};

	for (i=0; i<5; i++)
	{printf("%d세트 : ", i+1); //각 세트별로 정렬된 lotto배열을 출력함.
		for (j=0; j<6; j++)
			printf(" %d,", lotto[i][j]);
		printf("\b \n");}; //줄 바꿈.
	return 0;
}

