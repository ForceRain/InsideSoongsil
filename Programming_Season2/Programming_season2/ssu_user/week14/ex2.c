#include <stdio.h>
int main(void)
{
	printf("컴파일 날짜 : %s \n",__DATE__);
	printf("현재 파일명 : %s \n",__FILE__);
	printf("현재 라인 : %d \n", __LINE__);
	printf("현재 컴파일러의 표준 준수 여부 : %d\n", __STDC__);
	printf("컴파일 시각 : %s\n",__TIME__);
	return 0;
}
