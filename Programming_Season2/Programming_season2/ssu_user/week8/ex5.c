#include <stdio.h>
struct profile{
	char name[10];
   	unsigned pay: 27;
	unsigned ID : 17, age : 7;
};
int main(void) {
	struct profile person[7];
	int i;
	unsigned ID=0, age=0, pay=0;
	for (i=0; i<7; i++) {
		printf("이름을 입력하세요 : ");
		scanf("%s", person[i].name);
		printf("ID를 입력하세요 : ");
		scanf("%u", &ID);
		printf("나이를 입력하세요 : ");
		scanf("%u", &age);
		printf("연봉을 입력하세요 : ");
		scanf("%u", &pay);
		person[i].ID=ID;
		person[i].pay=pay;
		person[i].age=age;
	}
	printf("이름     ID      나이      연봉\n");
	for (i=0; i<7; i++) {
		printf("%10s %10u %10u %10u\n", person[i].name, person[i].ID, person[i].age, person[i].pay);
	}
	return 0;
}
