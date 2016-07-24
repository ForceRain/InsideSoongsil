#include <stdio.h>
#include <string.h>
#define N 100
int sentence(char str[], int num) {
	int count_num, right=0;
	if (num%2==0) {
		num=num-1;
		for(count_num=0; count_num<=(num/2); count_num++) {
			if (str[count_num]==str[num-count_num])
				right++;
		};
		if ((num/2)+1==right)
			return 1;
		else
			return 0;
	}
	else
		num=num-1;
		for(count_num=0; count_num<(num/2)+1; count_num++) {
			if (str[count_num]==str[num-count_num])
				right++;
		};
		if ((num/2)+1==right)
			return 1;
		else
			return 0;
	};
int main(void) {
	int a, len=0;
	char input[N];
	printf("문자열 입력 : ");
	scanf("%s", input);
	len=strlen(input);
	a=sentence(input,len);
	if (a==1)
		printf("%s는 회문입니다.", input);
	else
		printf("%s는 회문이 아닙니다.", input);
	return 0;
}
