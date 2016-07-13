#include <iostream>
#include <vector>
#include <queue>
#include <cstdio>

using namespace std;

priority_queue< pair< pair<int,int>, int > > container;
int DP[21][10001];
vector < vector <pair<int,int> > > edge_container;

int main(void)
{
	int N,M,K;
	int from,to,weight;
	scanf("%d%d%d",&N,&M,&K);

	for (int i=0;i<=20;i++)
		for (int j=0;j<=10000;j++)
			DP[i][j]=0x20132431;

	for (int i=0;i<=N;i++)
		edge_container.push_back(vector<pair<int ,int> >());
	
	for (int i=0;i<M;i++)
	{
		scanf("%d%d%d",&from,&to,&weight);
		edge_container[from].push_back(pair<int,int>(-weight,to));
		edge_container[to].push_back(pair<int,int>(-weight,from));						// priority queue는 최대힙을 기본으로 하고 있으므로, 음수 처리해서 사용.
	}
	
	DP[0][1]=0;
	container.push(pair<pair<int,int>,int > (pair<int,int>(0,1),0));

	while (!container.empty())
	{
		pair< pair<int,int>,int > my_edge=container.top();
		container.pop();
		int new_node=my_edge.first.second;
		int new_weight=-my_edge.first.first;												// 음수로 들어있었으므로, '-'를 붙여서 가져온다.
		int k_num=my_edge.second;

		for (int k=0;k<edge_container[new_node].size();k++)
		{
			int temp_weight=-edge_container[new_node][k].first;
			int update_node=edge_container[new_node][k].second;
			if (temp_weight+new_weight<DP[k_num][update_node])									// 개량하지 않는 경우.
			{
				DP[k_num][update_node]=(temp_weight+new_weight);
				container.push(pair<pair<int,int>,int > (pair<int,int>(-(temp_weight+new_weight),update_node),k_num));
			}
			if ((k_num<K) &&(new_weight<DP[k_num+1][update_node]) )								// 개량하는 경우
			{
				DP[k_num+1][update_node]=new_weight;
				container.push(pair<pair<int,int>,int >(pair<int,int>(-new_weight,update_node),k_num+1));
			}
		}
	}

	printf("%d\n",DP[K][N]);

	return 0;
}
