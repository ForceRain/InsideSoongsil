#include "stack.h"
int main(void)
{
	char	str[] = "Stack Test!";
	int	 i;
	reset();			// 스택 초기화
	printf("문자열 : %s\n", str);
	//  스택에 문자열 push
	for (i = 0; str[i] != '\0'; ++i)
		if (!full())
			push(str[i]);
	printf("역 문자열 : ");
	// 스택에서 문자열 pop
	while (!empty())
		putchar(pop());
	putchar('\n');
	return 0;
}

