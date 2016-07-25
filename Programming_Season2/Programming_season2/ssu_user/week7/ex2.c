#include <stdio.h>
#define WON 1
#define DOLLAR 0
union won_or_dollar{
	int won;
	float dollar;
};

struct product{
	char name[20];
	_Bool WorD;
	union won_or_dollar price;
};

int main(void) {
	const struct product elec[6]={[0].name="PMP",[0].WorD=WON,[0].price.won=600000,[1].name="MP3",[1].WorD=WON,[1].price.won=153000,[2].name="SmartPhone",[2].WorD=DOLLAR,[2].price.dollar=520.40,[3].name="TV",[3].WorD=DOLLAR,[3].price.dollar=430.20,[4].name="Navigator",[4].WorD=WON,[4].price.won=350000, [5].name="Laptop",[5].WorD=DOLLAR,[5].price.dollar=1950.00};
	int i=0, money=0, change=0;
	float temp;
	printf("보유금액과 환율을 입력하세요.\n");
	printf("보유금액 : ");
	scanf("%d", &money);
	printf("환율 : ");
	scanf("%d", &change);
	printf("구매 가능한 제품은 다음과 같습니다.\n");
	for (i=0; i<6; i++) {
		if (elec[i].WorD)
		{
			if (elec[i].price.won<=money)
				printf("%-20s %d원\n", elec[i].name, elec[i].price.won);
		}
		else
		{
			if ((elec[i].price.dollar)*change<=money) {
				temp=elec[i].price.dollar*change;
				printf("%-20s %d원\n", elec[i].name, (int)temp);
			}
		}
	}
	return 0;
}
