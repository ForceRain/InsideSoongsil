#include <stdio.h>
struct person{
	int num;
	char name[40];
	char addr[40];
	char hobby[40];
	char favor[40];
};

int main(void) {
	FILE *ofp;
	struct person per;
	int i;
	ofp=fopen("my.bin","rb");
	if (ofp==NULL) {
		printf("열기 오류");
		return 0;
	};
	i=fread(&per,sizeof(struct person),1,ofp);
	while (i) {
		printf("%d %s %s %s %s", per.num,per.name,per.addr,per.hobby,per.favor);
		i=fread(&per,sizeof(struct person),1,ofp);
	};
	fclose(ofp);
	return 0;
}
