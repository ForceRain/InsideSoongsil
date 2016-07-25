#include <stdio.h>
int main(void) {
	char name[10];
	int num,age,money,i=0, check;
	check=scanf("%s %d %d %d",name,&num,&age,&money);
	while (check!=EOF) {
		i++;
		printf("%d %s %d %d %d\n",i,name,num,age,money);
		check=scanf("%s %d %d %d",name,&num,&age,&money);
	}
	return 0;
}
