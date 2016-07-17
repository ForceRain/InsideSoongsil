#include <iostream>

using namespace std;

int main(void)
{
	int N,start,destination;

	cin>>N>>start>>destination;
	
	int **w;
	w=new int*[N];
	for (int i=0;i<N;i++)
	{
		w[i]=new int[N];
	}

	for (int i=0;i<N;i++)
	{
		for (int j=0;j<N;j++)
		{
			cin>>w[i][j];
		}
	}

	int **D;
	D=new int*[N];

	for (int i=0;i<N;i++)
		D[i]=new int[N];

	for (int i=0;i<N;i++)
	{
		for (int j=0;j<N;j++)
			D[i][j]=w[i][j];
	}

	for (int k=0;k<N;k++)
	{
		for (int i=0;i<N;i++)
		{
			for (int j=0;j<N;j++)
			{
				if (D[i][j]<D[i][k]+D[k][j])
					D[i][j]=D[i][j];
				else
					D[i][j]=D[i][k]+D[k][j];
			}
		}
	}

	cout<<D[start-1][destination-1]<<endl;

	return 0;
}