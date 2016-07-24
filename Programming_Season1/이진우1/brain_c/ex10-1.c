#include <stdio.h>
int main(void) {
	int a,b,temp;
	int *ap,*bp;
	ap=&a;
	bp=&b;
	printf("두 수를 입력하세요 : ");
	scanf("%d%d", &a,&b);
	temp=*ap;
	*ap=*bp;
	*bp=temp;
	printf("바뀐 두 수는 %d %d입니다.",a,b);
	return 0;
}
