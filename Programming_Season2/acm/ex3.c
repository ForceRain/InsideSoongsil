#include <stdio.h>
int main(void)
{
	int N,M,tmp,temp,result;
	scanf("%d %d",&N,&M);
	int ary[N][M];
	for (tmp=0;tmp<N;tmp++)
	{
		for (temp=0;temp<M;temp++)
		{
			scanf("%d",&ary[tmp][temp]);
		}
	}
	scanf("%d",&result);
	int ary1[result];
	int comp[4];
	for (temp=0;temp<result;temp++)
	{
		int i=0,j=0,x=0,y=0;
		ary1[temp]=0;
		scanf("%d %d %d %d",&comp[0],&comp[1],&comp[2],&comp[3]);
		x=comp[2]-1;
		y=comp[3]-1;
		for (i=comp[0]-1;i<=x;i++)
		{
			for(j=comp[1]-1;j<=y;j++)
			{
				ary1[temp]+=ary[i][j];
			}
		}
	}
	for (temp=0;temp<result;temp++)
	{
		printf("%d ",ary1[temp]);
	}
	return 0;
}
