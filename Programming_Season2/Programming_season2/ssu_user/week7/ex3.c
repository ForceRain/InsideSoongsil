#include <stdio.h>
struct as{
	int oh;
	int no;
};
int main(void) {
	struct as p={1,1}, q={2,2};
	printf("복사전 p의 멤버 : %d %d", p.oh, p.no);
	printf("복사전 q의 멤버 : %d %d", q.oh, q.no);
	p=q;
	printf("복사후 p의 멤버 : %d %d", p.oh, p.no);
	printf("복사후 q의 멤버 : %d %d", q.oh, q.no);
	if (p==q)
		printf("등호 계산 가능\n");
	else
		printf("등호 계산 불가능\n");
	return 0;
}
