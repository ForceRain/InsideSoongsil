#include <stdio.h>
int change(int *a[3][3], int n, int *cursor) {
	int x,y,temp;
	x=(n-1)/3;							//입력받은 값이 input_num이므로 이것을 이용, 이동할 위치의 행과 열을 계산한다
	y=(n-1)%3;
	if ((*a[x][y]==*(cursor+1) && n!=4 && n!=7) || (*a[x][y]==*(cursor-1) && n!=3 && n!=6) || *a[x][y]==*(cursor+3) || *a[x][y]==*(cursor-3) ){
		temp=*a[x][y];				//행과 열을 이동할때의 조건을 생각한다. 이동할 위치의 주소값이 +1,-1이거나 위, 아래로 바뀌는 것이면 가능하나
		*a[x][y]=*cursor;			//그중에서도 3번위치나 4번 위치에 있을 경우 +1되거나 -1되는 경우는 제외해야 하므로 조건식에 적어준다
		*cursor=temp;				//이 모든 조건을 충족 시켰을 경우 값을 서로 바꿔준다.
		}
	else
		return 0;					//충족되지 않았을 경우 잘못된 입력이므로 0을 리턴해서 pass값에 입력시킨다.
	return 1;						//충족되었으므로 pass값에 1을 준다.
} 
void check(int *a[3][3], int b[][3],int *out_key) {
	int i,j,num=0;					
	for (i=0; i<3; i++) {
		for (j=0; j<3; j++) {
			if (*a[i][j]==b[i][j])	//a배열은 현재 move배열이므로 맞춰야 하는 배열이다. 그래서 이미 답이 들어있는 ans_three배열(여기서는 b배열)	
				num++;				//두 배열을 이용해서 서로의 값을 비교한다. 만약 같다면 num값을 증가시킨다.
		};							
	};
	if (num==9)
		*out_key=0;					//만약 num값이 9가 되면 모두 제대로 된 위치에 온 것이므로 out_key의 값에 0을 입력시키고 메인 함수의 
	return;							//while문을 빠져나온다.
};
int main(void) {
	int ans[9]={1,2,3,4,5,6,7,8,9};								//주소값을 넣기 위한 배열
	int copy[9]={1,2,3,4,5,6,7,8,0};										
	int ans_three[3][3]={{1,2,3},{4,5,6},{7,8,0}};
	int *move[3][3];
	int *cursor;
	int m=0,i,j,temp,key,input_num,out_key=1,pass;
	srand(time(NULL));
	for (i=0; i<3;i++) {
		for (j=0; j<3; j++) {
			move[i][j]=&ans[m];
			m++;												//답의 배열의 주소값을 move배열에 입력시킨다.
		};
	};															//1차원 배열의 주소값을 3곱3의 포인터 배열에 입력
	for (i=0; i<3; i++) {
		for (j=0; j<3; j++) {
			while (key) {										//뽑을 난수들이 있는 배열인 copy배열을 이용해서
				temp=rand()%9;									//난수 생성기를 이용, 하나를 뽑아서 그값이 copy배열의 -1이 아닐 경우 그값을 move에 입력 시킨다.
				if (copy[temp]!=-1) {							//만약  copy배열의 값이 -1이 나온다면 계속해서 난수를 뽑아낸다.
					*move[i][j]=temp;
					copy[temp]=-1;
					key=0;
				};
			};
		key=1;
		};
	};															
	pass=1;														//pass값이 1이면 정상적으로 입력된것, 0이면 잘못된 입력임
	while (out_key) {      										//out_key가 0이 될때까지 반복 
		for (i=0; i<3; i++) {
			for (j=0; j<3; j++) {
				printf("%d ", *move[i][j]);
				if (*move[i][j]==0)
					cursor=move[i][j];							//0이 있는 위치의 주소값을 cursor라는 포인터변수에 입력시킨다.
			};
			printf("\n");
		};														
		printf("enter position : ");
		scanf("%d", &input_num);								//input_num은 이동할 위치
		if (0<input_num && input_num<10) {						//입력받을 값이 1~9의 값이므로 이외의 경우는 다시 입력받게 한다.
			if (pass==1) {										//pass가 1이면 계속 해서 진행
				pass=change(move,input_num,cursor);
			};
			if (pass==0) {										//pass가 0이면 0의 위치 기준으로 상하좌우가 아니므로 다시 입력받는다
				while (1) {
					printf("re-enter position : ");
					scanf("%d", &input_num);
					pass=change(move,input_num,cursor);
					if (pass==1)
						break;									//그리고 입력받은 값이 제대로 되었을경우 pass값이 1이 되므로 while문을 빠져나간다.
				};
			};
		}
		else if (input_num==-1)									//입력 받은 값이 -1이면 프로그램을 종료한다.
			return 0;
		else 
			printf("잘못된 입력입니다.\n");
	check(move,ans_three,&out_key);								//조작가능한 move배열과 ans_three배열을 이용해서 답이 맞는지 확인, out_key의 주소를 주어 while의
	}; 															//탈출여부를 정한다.								
	return 0;
}
