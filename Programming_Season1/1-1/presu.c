#include <stdio.h>
int main(void) {
	int n,m;
	int input[9][9];
	for (n=0; n<9; n++) {
		for (m=0; m<9; m++) {
			scanf("%d", &input[n][m]);
		};
	};
	for (n=0; n<9; n++) {
		for (m=0; m<9; m++) {
			printf("%d", input[n][m]);
		};
	};
	return 0;
}
