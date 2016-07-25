#include <stdio.h>
struct sub{
	int num;
	int grade[];
};
int main(void)
{
	printf("%d",sizeof(struct sub));
	return 0;
}
