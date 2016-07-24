#include <stdio.h>
int isSolution(int input[9][9]) {
	int k,i,j,l,m,sum,count,incount;
	//행끼리 비교
	for(k=0; k<9; k++) {
		for(i=0; i<9; i++) {
			for(j=0; j<9; j++) {
				if(input[k][i]==input[k][j])
					return 0;
			};
		};
	};
	//열끼리 비교
	for(k=0; k<9; k++) {
		for(i=0; i<9; i++) {
			for (j=0; j<9; j++) {
				if(input[i][k]==input[j][k])
					return 0;
			};
		};
	};
	//상자안에 비교
	int box[9][3][3];
	static int j=0, i=0;

	for(k=0; k<9; k++) {
		for(; j<l; j++) {
			for(; i<:9; i++) {

	return 1;
}

int main(void) {
	int input[9][9];
	int re,i,j;
	printf("1부터 9사이의 스도쿠 해를 입력 하세요 : \n");
	for(i=0; i<9; i++) {
		for(j=0; j<9; j++) {
			scanf("%d", &input[i][j]);
		};
	};
	re=isSolution(input);
	if(re)
		printf("올바른 답입니다.\n");
	else
		printf("틀린 답입니다\n");
	return 0;
}
