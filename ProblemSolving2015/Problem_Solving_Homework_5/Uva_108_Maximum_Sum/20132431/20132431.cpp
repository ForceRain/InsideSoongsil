#include <iostream>
#include <cstdio>

using namespace std;

int N;
int space[101][101];
int DP[101][101];
int maximum=-1;

int main(void)
{
	cin>>N;
	int total=0,tmp;
	int i=1,j=1;

	while (total!=N*N)
	{
		scanf("%d",&tmp);
		space[i][j]=tmp;
		total++;
		i=total/N;
		j=total%N;
	}

	int sum=0;
	for (int i1=0;i1<N;i1++)
	{
		for (int j1=0;j1<N;j1++)
		{
			sum=0;
			for (int k=0;k<=i1;k++)
			{
				for (int l=0;l<=j1;l++)
				{
					sum+=space[k][l];
				}
			}
			DP[i1][j1]=sum;
		}
	}/*
	for (int i=0;i<N;i++)
	{
		for (int j=0;j<N;j++)
			cout<<DP[i][j]<<" ";
		cout<<endl;
	}*/
	for (int i2=0;i2<N;i2++)
	{
		for (int j2=0;j2<N;j2++)
		{
			for (int i1=1;i1<=i2;i1++)
			{
				for (int j1=1;j1<=j2;j1++)
				{
					if (maximum<DP[i2][j2]-DP[i1-1][j2]-DP[i2][j1-1]+DP[i1-1][j1-1])
						maximum=DP[i2][j2]-DP[i1-1][j2]-DP[i2][j1-1]+DP[i1-1][j1-1];
				}
			}
		}
	}	
	cout<<maximum<<endl;

	return 0;
}