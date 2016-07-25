#include <stdio.h>
void cal(char *, char *);

int main(void) 
{
	char express[21];
	char result[10];
	printf("수식을 입력하세요 : ");
	scanf("%20[^\n]", express);
	cal(express,result);
	printf("%s = %s\n", express, result);
	return 0;
}

void cal(char *express, char *result) 
{
	int opd1, opd2;
	char op;
	if (sscanf(express,"%d %c %d", &opd1, &op, &opd2)!=3) {
		sprintf(result,"결과 오류");
		return;
	}
	if (op=='+')
		sprintf(result,"%d", opd1+opd2);
	else if (op=='-')
		sprintf(result,"%d", opd1-opd2);
	else if (op=='*')
		sprintf(result,"%d", opd1*opd2);
	else if (op=='/')
		sprintf(result,"%d", opd1/opd2);
	else
		sprintf(result,"수식 오류");
}
