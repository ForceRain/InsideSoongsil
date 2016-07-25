#include <stdio.h>
int main(void)
{
	int computer[100]={0};
	int N,i,input,refuse=0;
	scanf("%d",&N);
	int per[N];
	for (i=0;i<N;i++)
	{
		scanf("%d",&input);
		if (computer[input]==0)
			computer[input]=-1;
		else
			refuse++;
	}
	printf("%d",refuse);
	return 0;
}
