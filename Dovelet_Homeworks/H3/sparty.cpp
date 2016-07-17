#include <iostream>
#include <vector>

using namespace std;

int W[1001][1001];
int dijk[1001][1001];

int out_nearest[1001];
int out_length[1001];
int in_nearest[1001];
int in_length[1001];

int main(void)
{
	int N,M,X;
	int A,B,T;
	cin>>N>>M>>X;
	
	for (int i=0;i<1001;i++)
	{
		for (int j=0;j<1001;j++)
			W[i][j]=100001;
	}

	for (int i=0;i<M;i++)
	{
		cin>>A>>B>>T;
		W[A][B]=T;
	}

	//0은 사용하지 않고 N까지 사용되게 한다.
	//floyd-warshall로 구성가능하나, time-limit에 문제생김(n^3)
	//dijkstra로 구성하는게 좋을듯.(n^2)
	//source를 X로 잡았을때 
	
	for (int i=1;i<=N;i++)
	{
		if (i!=X)
		{
			out_nearest[i]=X;
			out_length[i]=W[X][i];
		}
	}

	int vertex_near=X;
	int total_length=0;
	for (int i=1;i<=N;i++)
	{
		if (i!=X)
		{
			int minimum=20132431;
			for (int j=1;j<=N;j++)
			{
				if (j!=X)
				{
					if ((0<=out_length[j]) && (out_length[j]<=minimum))
					{
						minimum=out_length[j];
						vertex_near=j;
					}
				}
			}
	
			for (int k=1;k<=N;k++)
			{
				if (k!=X)
				{
					if (out_length[vertex_near]+W[vertex_near][k]<out_length[k])
					{
						out_length[k]=out_length[vertex_near]+W[vertex_near][k];
						out_nearest[k]=vertex_near;
					}
				}
			}

			dijk[X][vertex_near]=out_length[vertex_near];
			out_length[vertex_near]=-1;
		}
	}

	int temp;
	for (int i=0;i<=N;i++)
	{
		for (int j=i;j<=N;j++)
		{
			temp=W[i][j];
			W[i][j]=W[j][i];
			W[j][i]=temp;
		}
	}
	
	for (int i=1;i<=N;i++)
	{
		if (i!=X)
		{
			in_nearest[i]=X;
			in_length[i]=W[X][i];
		}
	}

	for (int i=1;i<=N;i++)
	{
		for (int j=1;j<=N;j++)
		{
			cout<<dijk[i][j]<<" ";
		}
		cout<<endl;
	}

	vertex_near=X;
	for (int i=1;i<=N;i++)
	{
		if (i!=X)
		{
			int minimum=20132431;
			for (int j=1;j<=N;j++)
			{
				if (j!=X)
				{
					if ((0<=in_length[j]) && (in_length[j]<=minimum))
					{
						minimum=in_length[j];
						vertex_near=j;
					}
				}
			}
	
			for (int k=1;k<=N;k++)
			{
				if (k!=X)
				{
					if (in_length[vertex_near]+W[vertex_near][k]<in_length[k])
					{
						in_length[k]=in_length[vertex_near]+W[vertex_near][k];
						in_nearest[k]=vertex_near;
					}
				}
			}
			dijk[vertex_near][X]=in_length[vertex_near];
			in_length[vertex_near]=-1;
		}
	}

	int max=-1;
	for (int i=0;i<=N;i++)
	{
		int sum=dijk[X][i]+dijk[i][X];
		if (max<sum)
			max=sum;
	}

	cout<<max<<endl;
	return 0;
}