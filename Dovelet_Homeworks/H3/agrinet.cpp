#include <iostream>
#include <vector>

using namespace std;

int village[100][100];
int nearest[100];
int dis[100];

int main(void)
{
	int N;
	int nearVertex;
	vector<int> F;
	cin>>N;

	for (int i=0;i<N;i++)
	{
		for (int j=0;j<N;j++)
		{
			cin>>village[i][j];
		}
	}

	for (int i=1;i<N;i++)
	{
		nearest[i]=0;
		dis[i]=village[0][i];			//첫번째 값은 0번째
	}

	for (int i=1;i<N;i++)
	{
		int minimum=100001;
		for (int j=1;j<N;j++)
		{
			if ((0<=dis[j]) && (dis[j]<=minimum))
			{
				minimum=dis[j];
				nearVertex=j;
			}
		}
		
		int edge=village[nearVertex][nearest[nearVertex]];
		F.push_back(edge);
		dis[nearVertex]=-1;

		for (int k=1;k<N;k++)
		{
			if (village[k][nearVertex]<dis[k])
			{
				nearest[k]=nearVertex;
				dis[k]=village[k][nearVertex];
			}
		}
	}

	int total=0;
	for (int i=0;i<F.size();i++)
	{
		total+=F[i];
	}
	cout<<total<<endl;
	return 0;
}