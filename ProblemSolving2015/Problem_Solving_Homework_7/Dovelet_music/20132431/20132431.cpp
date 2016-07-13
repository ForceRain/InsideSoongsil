#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int w[1001][1001];
int order[1001];
int visit[1001];
vector< pair<int, vector<int> > > container;
vector<int> result;

int main(void)
{
	int N,M;
	cin>>N>>M;

	int times;
	for (int i=0;i<M;i++)
	{
		cin>>times;
		for (int j=0;j<times;j++)
		{
			cin>>order[j];
		}
		for (int j=1;j<times;j++)
		{
			w[order[j-1]][order[j]]=1;
		}
	}

	for (int i=1;i<=N;i++)
	{
		int sum=0;
		pair< int,vector<int> > temp;
		for (int j=1;j<=N;j++)
		{
			if (w[j][i]==1)
				sum++;
		}
		temp.first=sum;
		for (int j=1;j<=N;j++)
		{
			if (w[i][j]==1)
				temp.second.push_back(j);
		}
		
		container.push_back(temp);
	}
	//초기화 부분
	int top=-1;
	for (int i=0;i<N;i++)
	{
		if (container[i].first==0)
		{
			visit[i]=top;
			top=i;
		}
	}

	bool no=false;
	for (int i=0;i<N;i++)
	{
		if (top==-1)
		{
			cout<<"0"<<endl;
			no=true;
			break;
		}
		int ptr=top;
		top=visit[top];
		result.push_back(ptr+1);

		int siz=container[ptr].second.size();
		for (int k=0;k<siz;k++)
		{
			int target=container[ptr].second[k]-1;
			container[target].first--;

			if (container[target].first==0)
			{
				visit[target]=top;
				top=target;
			}
		}
	}

	if (!no)
	{
		for (int i=0;i<result.size();i++)
			cout<<result[i]<<endl;
	}

	return 0;
}