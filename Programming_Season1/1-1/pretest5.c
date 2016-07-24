#include <stdio.h>
void insert_num(int a[], int b, int c) {
	int k;
	for(k=9; k>=b; k--) {
		a[k]=a[k-1];                //a배열의 내용을 한칸씩 뒤로 밀기 위해 for문이용, 앞에 있던 값을 뒤에 입력
		};
	a[b]=c;							//입력받은 b의 값에 따라 c에다가 넣어준다.
	
	return;
}

int main(void) {
	int a[10]={1,2,3,4,5,6,7,8,9,0};
	int b, c;
	int i;
	for(i=0; i<10; i++) {
		printf("%d ", a[i]);
	};
	printf("\na배열의 '몇번째 위치'에 '얼마'를 입력하시겠습니까? : ");
	scanf("%d%d",&b,&c);
	b=b-1;							//배열의 위치는 0부터 시작하므로 
	insert_num(a,b,c);
	for(i=0; i<10; i++) {
		printf("%d ", a[i]);
	};
	return 0;
}


