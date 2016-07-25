#include <stdio.h>
int main(void)
{
	int N,i,tmp;
	int two=0,five=0,check=0;
	scanf("%d",&N);
	for (i=1;i<=N;i++)
	{
		tmp=i;
		while (1)
		{
			if ((tmp%2)!=0)
				break;
			two++;
			tmp=tmp/2;
		}
		tmp=i;
		while (1)
		{
			if ((tmp%5)!=0)
				break;
			five++;
			tmp=tmp/5;
		}
		while (two!=0 && five!=0)
		{
			check++;
			two--;
			five--;
		}
	}
	printf("%d",check);
	return 0;
}
