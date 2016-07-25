#include <stdio.h>
int main(void)
{
	int a,b,c;
	int input,cycle,count=1;
	scanf("%d",&input);
	a=input/10;
	b=input%10;
	while(1)
	{
		c=a+b;
		cycle=10*b+(c%10);
		if (cycle==input)
			break;
		a=cycle/10;
		b=cycle%10;
		count++;
	}
	printf("%d",count);
	return 0;
}
