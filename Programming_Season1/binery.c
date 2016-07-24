#include <stdio.h>
#include <stdlib.h>
#include <string.h>

int main(int argc, char *argv[]) {
	int i,n,j,temp,find,start,end,mid,num=0;
	if (argv[1]==NULL) {
		printf("입력 : ./a 입력할 숫자의 갯수\n");
		return 0;
	}
	i=atoi(argv[1]);
	int ary[i];
	printf("숫자들을 입력하세요 : ");
	for (n=0; n<i; n++) {
		scanf("%d", &ary[n]);
	}
	for (n=0; n<i-1; n++) {
		for (j=i-1; j>i; j--) {
			if (ary[j-1]>ary[j]) {
				temp=ary[j-1];
				ary[j-1]=ary[j];
				ary[j]=temp;
			}
		}
	}
	printf("찾을 숫자를 입력하세요 : ");
	scanf("%d", &find);
	start=0;
	end=i;
	while (1) {
		num++;
		mid=(start+end)/2;
		if (ary[mid]>find)
			end=mid;
		if (ary[mid]<find)
			start=mid;
		if (ary[mid]==find) {
			printf("찾으신 숫자는 %d 이고 %d번을 거쳐 발견했습니다!\n", find, num);
			return 0;
		}
	}
	return 0;
}
