#include <stdio.h>
int main(void){
	int set[5][6] = {{1,2,3,4,5,6}, {10,15,25,35,40,42}, {1,9,11,22,23,25}, {9,28,31,34,35,36}, {1,9,23,28,33,35}};
	int input[6], bonus, i, j, tmp, n, u, count=0, sign, x='X';
	int print[6]={x,x,x,x,x,x};
	printf("금주의 로또 번호를 입력하세요 -> ");
	scanf("%d%d%d%d%d%d", &input[0], &input[1], &input[2], &input[3], &input[4], &input[5]);
	printf("보너스 번호를 입력하세요 -> ");
	scanf("%d", &bonus);
	printf("\n홍길동님의 로또 당첨 결과는 다음과 같습니다.\n\n");

	for (i=0; i<5 ; i++)
		{for (j=5; j>i; j--)
			if (input[j-1] > input[j]){
				tmp = input[j-1];
				input[j-1] = input[j];
				input[j] = tmp;
			};
		};                              //bubble sort

	for (n=0; n<5; n++, count=0){
		for (i=0; i<6; i++)
			for (j=0; j<6; j++)
				{if (set[n][i]==input[j])
					{print[i]=set[n][i];
						j=6;}
		};
		
		for (u=0; u<6; u++)
			{if(print[u] != x)
				count +=1;};            //input배열의 값을 증가시켜 가며 x가 아닌 것들과 비교 하며 count값을 증가시킴.
		printf("%d세트 번호(", n+1);
		for (i=0; i<6; i++){
			printf("%d ", set[n][i]);};
		printf(") -> ");
		for (i=0; i<6; i++){
			if (print[i]==x){
				if (set[n][i]==bonus && count==5)
					printf("%d", bonus);
				else printf(" X");}
			else printf(" %d ", print[i]);};
		if (count<3)
			printf("(꽝)\n");
		if (count==3)
			printf("(5등)\n");
		if (count==4)
			printf("(4등)\n");
		for (u=0; u<6; u++){
			if (set[n][u]==bonus && count==5)
				{printf("(3등)\n");
					u=6;}
			else if (count==5)
				{printf("(2등)\n");
					u=6;}
		}
		if (count==6)
			printf("(1등)\n");
		for (i=0; i<6; i++)
			print[i]=x;
	};
	return 0;
}
			


		

	
