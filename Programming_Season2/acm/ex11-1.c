#include <stdio.h>
int main(void)
{
	int N,i,sum,temp,n0=0,n1=1;
	scanf("%d", &N);
	i=0;
	while(1)
	{
		if (N==0)
		{
			sum=n0;
			break;
		}
		if (N==1)
		{
			sum=n1;
			break;
		}
		sum=n0+n1;
		temp=n1;
		n1=sum;
		n0=temp;
		i++;
		if (i==(N-1))
			break;
	}
	printf("%d",sum);
	return 0;
}
