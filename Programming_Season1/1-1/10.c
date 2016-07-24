#include <stdio.h>

int main(void)
{
	int i = 0, j = 0, k = 0, 
		num[5][6] = {{1,2,3,4,5,6}, {10,15,25,35,40,42},
		             {1,9,11,22,23,25},{9,28,31,34,35,36},
				     {1,9,23,28,33,35}},
	real_num[5][6] = {{1,2,3,4,5,6}, {10,15,25,35,40,42},
		             {1,9,11,22,23,25},{9,28,31,34,35,36},
					 {1,9,23,28,33,35}},
	wee[6], bonus, count[5] = {}, b_count[5] = {0};

	printf("금주의 로또 번호를 입력하세요 -> ");
	scanf("%d %d %d %d %d %d", &wee[0], &wee[1], &wee[2], &wee[3],
			&wee[4], &wee[5]);
	printf("보너스 번호를 입력하세요 -> ");
	scanf("%d", &bonus);
	printf("\n홍길동님의 로또 당첨 결과는 다음과 같습니다.\n\n");

	for(i = 0; i < 5; i++){
		for(j = 0; j < 6; j++)
			for(k = 0; k < 6; k++){
				if(num[i][j] != wee[k])
					num[i][j] = 0;
			}
	}

	for(i = 0; i < 5; i++){
		for(j = 0; j < 6; j++)
			for(k = 0; k < 6; k++){
				if(real_num[i][j] == wee[k]){
					num[i][j] = real_num[i][j];
					count[i]++;
				}
				if(real_num[i][j] == bonus){
					num[i][j] = real_num[i][j];
					b_count[i]++;
				}
				
			}
	}

	

	for(i = 1; i < 6; i++){
		printf("%d세트 번호<%d %d %d %d %d %d> -> %d %d %d %d %d %d ",
				i, real_num[i-1][0], real_num[i-1][1], real_num[i-1][2],
				real_num[i-1][3], real_num[i-1][4], real_num[i-1][5],
				num[i-1][0], num[i-1][1], num[i-1][2], num[i-1][3],
				num[i-1][4], num[i-1][5]);
		switch(count[i-1]){
			case 3 :
				printf("<5등>\n");
				break;
			case 4 :
				printf("<4등>\n");
				break;
			case 5 :
				if(b_count[i] == 0){
					printf("<3등>\n");
					break;
				}
				else{
					printf("<2등>\n");
					break;
				}
			case 6 :
				printf("<1등>\n");
				break;
			default :
				printf("<꽝>\n");
		}
	}
	return 0;
}

   
	

