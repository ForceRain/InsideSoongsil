#include <stdio.h>
#include <stdlib.h>
#include <time.h>
int main(void){
	int lotto[5][6], i=0, j=0, n, a, b, c, k, l, tmp;
	srand(time(NULL));
	for(i=0; i<5 ;){
		for (j=0; j<6; j++){
			lotto[i][j] = (rand()%45)+1;};
			for (a=0, b=5, c=0; b>0; b--)
			{if (lotto[i][a] == lotto[i][b])
				c=c+1;};
		if (c>0)
			i=i;
		else
			i=i+1;};

								
	for(i=0; i<5; i++){
		for (j=0; j<6; j++)
			for (n=6; n>j ; n--){
				                                      //각 세트별로 bubble 정렬을 시행한다.
				if (lotto[i][n-1] > lotto[i][n]){ 
					tmp = lotto[i][n-1];
					lotto[i][n-1] = lotto[i][n];
					lotto[i][n] = tmp;};};};
	for (i=0; i<5; i++)
	{printf("%d세트 : ", i+1);                        //각 세트별로 정렬된 lotto배열을 출력함.
		for (j=0; j<6; j++)
			printf(" %2d,", lotto[i][j]);
		printf("\b \n");};                            //줄 바꿈.
	return 0;
}

