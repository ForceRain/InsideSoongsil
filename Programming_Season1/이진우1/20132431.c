#include <stdio.h>
int main(void){
	int set[5][6] = {{1,2,3,4,5,6}, {10,15,25,35,40,42}, {1,9,11,22,23,25}, {9,28,31,34,35,36}, {1,9,23,28,33,35}};
	int input[6], bonus, i, j, n, u, count=0, s=0, x='X';
	int print[6]={x,x,x,x,x,x};                 //출력하는 print문에 대해서 모든 배열값에 x를 준다.
	printf("금주의 로또 번호를 입력하세요 -> ");
	scanf("%d%d%d%d%d%d", &input[0], &input[1], &input[2], &input[3], &input[4], &input[5]);
	printf("보너스 번호를 입력하세요 -> ");
	scanf("%d", &bonus);
	printf("\n홍길동님의 로또 당첨 결과는 다음과 같습니다.\n\n");
	for (n=0; n<5; n++, count=0, s=0){          //n세트에 대해서 큰 for문
		for (i=0; i<6; i++)
			for (j=0; j<6; j++)
				{if (set[n][i]==input[j])
					{print[i]=set[n][i];
						j=6;}                   //set배열과 input배열의 값을 비교해서 만약 같을 경우, set배열의 값을 print배열에 입력시키고 j에 값을 6을 준뒤 빠져 나온다.
		};
		
		for (u=0; u<6; u++)
			{if(print[u] != x)
				count +=1;};                    //input배열의 값을 증가시켜 가며 x가 아닌 것들과 비교 하며 x가 아닐때는 count값을 증가시킴.
		printf("%d세트 번호(", n+1);
		for (i=0; i<6; i++){
			printf("%2d ", set[n][i]);};         //출력할때 화살표 기준 왼쪽부분인 set배열을 출력.
		for (u=0; u<6; u++){
			if (set[n][u] == bonus)
				s = 1;};                        //만약 set배열에 bonus값과 같은 숫자가 있다면 s값을 1로 정한다.
		
		
		printf(") -> ");                        //출력
		for (i=0; i<6; i++){
			if (print[i]==x){
				if (set[n][i]==bonus && count==5)
					printf("%2d", bonus);
				else printf(" X ");}
			else printf(" %2d ", print[i]);};    //print배열의 값중 x인 것들 중에서 count값이 5이고 set배열의 값중 bonus와 같은 것이 있다면 그때 X대신 bonus값을 출력한다. 
		if (count<3)                            //print배열의 값이 x가 아닌 것들은 print배열의 값을 그대로를 출력한다.
			printf("(꽝)\n");
		if (count==3)
			printf("(5등)\n");
		if (count==4)
			printf("(4등)\n");
		if (count==5)
		{if (s==1)
			printf("(2등)\n");                  //count값이 5인 것들 중, s값이 1이면 5개를 맞추고 보너스 번호를 맞췄으므로 2등, s값이 0이면 보너스 번호를 못 맞췄으므로 3등.
			else if (s==0)
				printf("(3등)\n");};
		if (count==6)
			printf("(1등)\n");
		for (i=0; i<6; i++)                     //print배열의 값들을 모두 x로 초기화 시켜준다. 
			print[i]=x;
	};                                          //큰 for문에 의해 n이 5가 될때까지 이 작업을 수행한다.
	return 0;                                   
}
