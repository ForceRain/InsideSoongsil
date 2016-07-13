#include <iostream>
#include <vector>
#include <stack>

#define SOURCE 101
#define DEST 102
#define INF 0x0FFFFFFF

using namespace std;

int N,M;
vector < pair<int,int> > woori;			//pair <int,int> = pair <# of pigs, who got all?>
vector<int> edges[103];

int flow[103][103];
int capacity[103][103];				// src=101, dest=102
int residual_capacity[103][103];
bool has_next_path=true;

vector <pair <int,int> > path_finder()
{
	bool visited[103]={false};
	stack< pair<int, vector< pair<int,int> > > > edge_container;					// stack을 이용해서 DFS로 구현.
	pair<int,vector< pair<int,int> > > node(SOURCE,vector< pair<int,int> >());
	
	visited[SOURCE]=true;
	edge_container.push(node);
	has_next_path=true;
	do
	{
		node=edge_container.top();
		edge_container.pop();
		vector< pair<int,int> > tmp_con=node.second;

		if (node.first==DEST)
			return tmp_con;

		for (int i=1;i<103;i++)
		{
			if (residual_capacity[node.first][i]!=0 && !visited[i])
			{
				tmp_con=node.second;
				tmp_con.push_back(pair<int,int>(node.first,i));
				edge_container.push(pair<int,vector< pair<int,int> > >(i,tmp_con));
				visited[i]=true;
			}
		}
	}
	while (!edge_container.empty());
	has_next_path=false;
	return vector< pair<int,int> >();
}

int main(void)
{
	int tmp,temp;
	int capacity_sum=0;
	cin>>N>>M;

	for (int i=0;i<N;i++)
	{
		cin>>tmp;
		woori.push_back(pair<int,int>(tmp,0));
	}

	for (int j=1;j<=M;j++)
	{
		cin>>tmp;
		capacity_sum=0;
		for (int k=0;k<tmp;k++)
		{
			cin>>temp;
			if (woori[temp-1].first==0)
			{
				capacity[j][woori[temp-1].second]=INF;
				residual_capacity[j][woori[temp-1].second]=INF;
			}
			else
			{
				capacity_sum+=woori[temp-1].first;
				woori[temp-1].first=0;
				woori[temp-1].second=j;
			}
		}
		capacity[j][DEST]=capacity_sum;
		residual_capacity[j][DEST]=capacity_sum;
		cin>>tmp;
		capacity[SOURCE][j]=tmp;
		residual_capacity[SOURCE][j]=tmp;
	}

	while (true)
	{
		vector <pair<int,int> > container=path_finder();
		if (!has_next_path)
			break;

		int resid=0x7FFFFFFF;
		int u,v;
		for (int j=0;j<container.size();j++)
		{
			u=container[j].first;
			v=container[j].second;
			if (resid>residual_capacity[u][v])
				resid=residual_capacity[u][v];
		}

		for (int j=0;j<container.size();j++)
		{
			u=container[j].first;
			v=container[j].second;
			flow[u][v]=flow[u][v]+resid;
			residual_capacity[u][v]=capacity[u][v]-flow[u][v];
			flow[v][u]=-flow[u][v];
			residual_capacity[v][u]=capacity[v][u]-flow[v][u];
		}
	}

	long long output=0;
	for (int i=0;i<103;i++)
		if (flow[SOURCE][i])
			output+=flow[SOURCE][i];

	cout<<output<<endl;

	return 0;
}