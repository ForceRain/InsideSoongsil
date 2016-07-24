#include <stdio.h>
int main(void) {
	int i,n,k,j,right=0;
	int hong[5][6]={{1,2,3,4,5,6},{10,15,25,35,40,42},{1,9,11,22,23,25},{9,28,31,34,35,36},{1,9,23,28,33,35}};
	int answer[6]={0};
	int right_ans[5]={0};
	int bonus,bonus_right=0;
	printf("금주의 로또 번호를 입력하세요 -> ");
	for (i=0; i<6; i++){
		scanf("%d", &answer[i]);
	}
	printf("보너스 번호를 입력하세요 -> ");
	scanf("%d", &bonus);
	printf("\n홍길동님의 로또 당첨 결과는 다음과 같습니다.\n\n");
	for (i=0; i<5; i++) {
		for (j=0; j<6; j++) {
			for(k=0; k<6; k++) {
				if (answer[k]==hong[i][j]) {
					right++;
				}
			}
		}
		right_ans[i]=right;
		right=0;
	}
	for (i=0; i<5; i++) {
		bonus_right=0;
		printf("%d세트 번호 <", i+1);
		for (j=0; j<6; j++) {
			printf("%d ",hong[i][j]);
			if (j==5)
				printf("\b");
		}
		printf("> -> ");
		for (j=0; j<6; j++) {
			for (k=0; k<6; k++) {
				if (answer[k]==hong[i][j] && hong[i][j]!=bonus) {
					printf("%d ", hong[i][j]);
					break;
				}
				if (hong[i][j]==bonus && right_ans[i]==5) {
					printf("%d ", hong[i][j]);
					bonus_right=45;
					break;
				}
				if (answer[k]!=hong[i][j] && k==5)
					printf("X ");
			}
		}
		if (right_ans[i]<3)
			printf("<꽝>\n");
		if (right_ans[i]==3)
			printf("<5등>\n");
		if (right_ans[i]==4)
			printf("<4등>\n");
		if (right_ans[i]==5 && bonus_right!=45)
			printf("<3등>\n");
		if (right_ans[i]==5 && bonus_right==45)
			printf("<2등>\n");
		if (right_ans[i]==6)
			printf("<1등>\n");
	}
	return 0;
}
