#include <stdio.h>
int isSolution(int input[9][9]) {
	int num, n, m, k=0, l=0, check_sum=0;
	for (n=0; n<9; n++) {
		for (m=0; m<9; m++) {
			if (input[n][m]>9 || input[n][m]<1)
				return 0;
			check_sum+=input[n][m];
		};
		if (check_sum!=45)
			return 0;
		check_sum=0;
	};
	for (m=0; m<9; m++) {
		for (n=0; n<9; n++) {
			if (input[n][m]>9 || input[n][m]<1)
				return 0;
			check_sum+=input[n][m];
		};
		if (check_sum!=45)
			return 0;
		check_sum=0;
	};

	for (num=0; num<9; num++) {
		for (n=k; n<k+3; n++) {
			for (m=l; m<l+3; m++) {
				if (input[n][m]>9 || input[n][m]<1)
					return 0;
				check_sum+=input[n][m];
						};
					};
			if (check_sum!=45)
				return 0;
			check_sum=0;
			if (l==6) {
				k=k+3;
				l=0;
				}
			else if (l<6)
				l=l+3;
		};
	return 1;
}
void makeSudoku(void) {
	int answer[9][9]={{5,3,4,6,7,8,9,1,2}, {6,7,2,1,9,5,3,4,8}, {1,9,8,3,4,2,5,6,7}, {8,5,9,7,6,1,4,2,3}, {4,2,6,8,5,3,7,9,1}, {7,1,3,9,2,4,8,5,6}, {9,6,1,5,3,7,2,8,4}, {2,8,7,4,1,9,6,3,5}, {3,4,5,2,8,6,1,7,9}};
	:
}
int main(void) {
	int n, m, ans=0, go;
	int input[9][9];
	printf("스도쿠 하자!!! \n");
	printf("1. 스도쿠 맞는지 확인   2. 스도쿠 문제풀기\n");
	scanf("%d", &go);
	switch (go) {
		case 1 : {
					getchar();
					printf("스도쿠 해를 입력 하세요 : \n");
					for (n=0; n<9; n++) {
						for (m=0; m<9; m++) {
							scanf("%d", &input[n][m]);
							};
						};
		ans=isSolution(input);
		if (ans)
			printf("올바른 답입니다.");
		else
			printf("틀린 답입니다.");
				 };
				 break;
		case 2 : {
					 makeSudoku();
				 };
				 break;
	};
	return 0;
}
