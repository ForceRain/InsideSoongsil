#include <stdio.h>
struct status{
	unsigned b1:1, b2:1, b3:1, b4:1;
};
int main(void) {
	int i;
	struct status p;
	p.b1=1;
	p.b2=0;
	p.b3=1;
	p.b4=1;
	if (p.b1==1)
		printf("광박\n");
	if (p.b2==1)
		printf("피박\n");
	if (p.b3==1)
		printf("흔들\n");
	if (p.b4==1)
		printf("투고\n");
	return 0;
}
