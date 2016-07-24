#include <stdio.h>
#define N 100
int main(void) {
	char input[N];
	int num, cpr_num, right=0;
	printf("입력 : ");
	getchar();
	for(num=0; num<N; num++) {
		input[num]=getchar();
		if(input[num]=='\n') {
			input[num]='\0';
			break;
		};
	}
	if((num-1)%2==0) {
		for(cpr_num=0; num-1-cpr_num>=0; cpr_num++) {
			if(input[cpr_num]==input[(num-1)-cpr_num])
				right++;
		}
		if ((num-1)/2+1==right)
			printf("회문");
		else
			printf("회문 아님");
	}
	else{
		for(cpr_num=0; num-1-cpr_num>=0; cpr_num++) {
			if(input[cpr_num]==input[(num-1)-cpr_num])
				right++;
		}
	if ((num-1)/2==right)
		printf("회문");
	else
		printf("회문 아님");
	};
	return 0;
}
