#include <stdio.h>
void my_reverse(int in[]) {
	int re;
	for (re=4; re>=0; re--) {
		printf("%d\n", in[re]);
	};
	return;
}

int main(void) {
	int in[5];
	int i;
	for (i=0; i<5; i++) {
		printf("숫자를 입력하세요 : ");
		scanf("%d", &in[i]);
	};
	my_reverse(in);
	return 0;
}
