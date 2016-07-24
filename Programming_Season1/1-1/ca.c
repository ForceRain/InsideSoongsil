#include <stdio.h>
int main(void)
{
	int i,o;
	printf("알파벳을 입력하세요 :");
	scanf("%c", &i);
	if((int)'a'<i && i<'z' || (int)'A'<i && i<'Z')
	{if ((int)'a'<i && i<'z')
		{o=i+32;
		printf("입력된 알파벳 : %c -> %c", i, o);}
		else
		{o=i-32;
				printf("입력된 알파벳 : %c -> %c", i, o);};}
	else
		printf("프로그램을 종료합니다.");
	return 0;
}


