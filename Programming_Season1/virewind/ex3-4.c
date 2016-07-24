#include <stdio.h>
void print_all(char **p,int i) {
	int k=0;
	for (k=0; k<i; k++) {
		printf("%s\n", p[k]);
	}
	return ;
}
int main(void) {
	int i=0;
	char *p[]={"dog", "elephant", "monkey", "rabbit", "tiger"};
	i=sizeof(p)/sizeof(char *);
	print_all(p,i);
	return 0;
}
