#include <iostream>
#include <cstdio>
#include <vector>
#include <queue>

#define SOURCE 201
#define DEST 202
#define INF 0x7FFFFFFF

using namespace std;

int capacity[203][203]; 				// src=201, dest=202
int flow[203][203];
int residual_capacity[203][203];
bool has_next_path;

vector < pair<int,int> > path_finder()
{
	bool visited[203]={false};
	queue < pair < int, vector < pair<int,int> > > > node_container;
	pair < int, vector < pair<int,int> > > Node(SOURCE,vector < pair<int,int> >());
	node_container.push(Node);
	has_next_path=true;
	visited[SOURCE]=true;
	while (!node_container.empty())
	{
		Node=node_container.front();
		node_container.pop();
		vector < pair<int,int> > tmp=Node.second;

		if (Node.first==DEST)
			return Node.second;

		for (int i=0;i<203;i++)
		{
			if (residual_capacity[Node.first][i]!=0 && !visited[i])
			{
				tmp=Node.second;
				tmp.push_back(pair<int,int>(Node.first,i));
				node_container.push(pair < int, vector < pair<int,int> > >(i,tmp) );
				visited[i]=true;
			}
		}
	}
	has_next_path=false;
	return vector < pair<int,int> >();
}

int main(void)
{
	int N,tmp,from,to,cap,B,D;
	while (scanf("%d",&N)!=EOF)
	{
		for (int i=1;i<=N;i++)
		{
			cin>>tmp;
			capacity[2*i-1][2*i]=tmp;
			residual_capacity[2*i-1][2*i]=tmp;
		}
		cin>>tmp;
		for (int i=0;i<tmp;i++)
		{
			cin>>from>>to>>cap;
			capacity[from*2][to*2-1]=cap;
			residual_capacity[from*2][to*2-1]=cap;
		}

		cin>>B>>D;
		for (int i=0;i<B;i++)
		{
			cin>>tmp;
			capacity[SOURCE][2*tmp-1]=INF;
			residual_capacity[SOURCE][2*tmp-1]=INF;
		}

		for (int i=0;i<D;i++)
		{
			cin>>tmp;
			capacity[2*tmp][DEST]=INF;
			residual_capacity[2*tmp][DEST]=INF;
		}

		while (true)
		{
			vector < pair<int,int> > path=path_finder();

			if (!has_next_path)
				break;

			int resid_min=0x7FFFFFFF;
			int u,v;
			for (int i=0;i<path.size();i++)
			{
				u=path[i].first;
				v=path[i].second;
//				cout<<"ED : "<<u<<", "<<v<<endl;
				if (resid_min>residual_capacity[u][v])
					resid_min=residual_capacity[u][v];
			}

			for (int i=0;i<path.size();i++)
			{
				u=path[i].first;
				v=path[i].second;
				flow[u][v]=flow[u][v]+resid_min;
				residual_capacity[u][v]=capacity[u][v]-flow[u][v];
				flow[v][u]=-flow[u][v];
				residual_capacity[v][u]=capacity[v][u]-flow[v][u];
			}
		}

		int sum=0;
		for (int i=0;i<203;i++)
		{
			if (flow[SOURCE][i])
				sum+=flow[SOURCE][i];
		}
		cout<<sum<<endl;

		for (int i=0;i<203;i++)
			for (int j=0;j<203;j++)
			{
				residual_capacity[i][j]=0;
				capacity[i][j]=0;
				flow[i][j]=0;
			}
	}

	return 0;
}