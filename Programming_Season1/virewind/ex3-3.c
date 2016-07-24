#include <stdio.h>
#include <string.h>
int main(void) {
	char *p[5]={"dog","elephant","monkey","rabbit","tiger"};
	int len[5]={0};
	int j=0,i=0,max=0,max_index=0;
	while(i<sizeof(p)/sizeof(char *)) {
		len[i]=strlen(p[i]);
		if (max<len[i]) {
			max=len[i];
			max_index=i;
		};
		i++;

	}
	printf("%s",p[max_index]);
	return 0;

}
