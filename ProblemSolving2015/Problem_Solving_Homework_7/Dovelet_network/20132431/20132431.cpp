#include <iostream>
#include <cstring>
#include <vector>

using namespace std;

int space[101][101];
vector < vector<int> > fcontainer;
vector < vector<int> > container;
int fcontainer_graph[101][101];
int container_graph[101][101];
int pred[101];
int color[101];
int discover[101];
int finishing[101];
int ordering[101];
int DFS_time=0;
int fcomponent_num=0;
int component_num=0;
int N;

enum {WHITE,GRAY,BLACK};

int next_node(int node_ptr)
{
	if (node_ptr>N) return -1;
	else return ordering[node_ptr];
}

int get_component_num(int cp_num,int end)
{
	for (int i=0;i<cp_num;i++)
	{
		for (int j=0;j<container[i].size();j++)
		{
			if (container[i][j]==end)
				return (i);
		}
	}
	return -1;
}

void DFS_Visit(int node)
{
	color[node]=GRAY;
	DFS_time=DFS_time+1;
	discover[node]=DFS_time;

	for (int i=1;i<=N;i++)
	{
		if (space[node][i])
		{
			if (color[i]==WHITE)
			{
				pred[i]=node;
				container[component_num-1].push_back(i);
				DFS_Visit(i);
			}
			else if (color[i]==BLACK)
			{
 				int compo_num=get_component_num(component_num-1,i);
 				if (compo_num!=-1)
	 				container_graph[component_num-1][compo_num]=1;
			}
		}
	}

	color[node]=BLACK;
	DFS_time=DFS_time+1;
	finishing[node]=DFS_time;
}

void DFS()
{
	int nx_node;
	for (int i=1;i<=N;i++)
	{
		color[i]=WHITE;
		pred[i]=0;
	}
	DFS_time=0;
	for (int i=1;(nx_node=next_node(i))!=-1;i++)
	{
		if (color[nx_node]==WHITE)
		{
			component_num++;
			container.push_back(vector<int>());
			container[component_num-1].push_back(nx_node);
			
			DFS_Visit(nx_node);
		}
	}
}

int indegree_check()
{
	int sum=0;
	int in_deg_zero=0;
	int out_deg_zero=0;
	int tmp_sum=0;
	int sum_check=0;

	for (int j=0;j<component_num;j++)
	{
		sum=0;	
		for (int i=0;i<component_num;i++)
			sum+=container_graph[i][j];
		
		if (sum==0)
		{
			for (int k=0;k<container[j].size();k++)
			{
				tmp_sum=0;
				for (int l=1;l<=N;l++)
					tmp_sum+=space[l][container[j][k]];

				if (tmp_sum==0)
				{
					sum_check=1;
					in_deg_zero++;
				}
			}
			if (sum_check==0)
				in_deg_zero++;

			sum_check=0;
		}
		else
		{
			for (int k=0;k<container[j].size();k++)
			{
				tmp_sum=0;
				for (int l=1;l<=N;l++)
					tmp_sum+=space[l][container[j][k]];

				if (tmp_sum==0)
				{
					sum_check=1;
					in_deg_zero++;
				}
			}
		}
	}
	return in_deg_zero;
}

int main(void)
{
	int tmp,max;
	int sum=0;
	int in_deg_zero=0;
	int out_deg_zero=0;
	int tmp_sum=0;
	int sum_check=0;

	cin>>N;

	for (int i=1;i<=N;i++)
	{
		while (true)
		{
			cin>>tmp;
			if (tmp==0)
				break;
			space[i][tmp]=1;
		}
		ordering[i]=i;
	}
	DFS();
	cout<<indegree_check()<<endl;
 
	for (int i=1;i<=N;i++)
	{
		for (int j=1;j<=i;j++)
		{
			int tmp=space[i][j];
			space[i][j]=space[j][i];
			space[j][i]=tmp;
		}
	}
	for (int i=1;i<=N;i++)
	{
		for (int j=1;j<i;j++)
		{
			if (finishing[j]<finishing[j+1])
			{
				int tmp=ordering[j+1];
				ordering[j+1]=ordering[j];
				ordering[j]=tmp;

				tmp=finishing[j+1];
				finishing[j+1]=finishing[j];
				finishing[j]=tmp;
			}
		}
	}
	memset(container_graph,0,sizeof(container_graph));
	container.erase(container.begin());
	container.resize(0);
	component_num=0;

	DFS();

	tmp_sum=0;
	sum_check=0;
	in_deg_zero=0;
	out_deg_zero=0;
	for (int i=0;i<component_num;i++)
	{
		sum=0;
		for (int j=0;j<component_num;j++)
			sum+=container_graph[i][j];
		if (sum==0)
		{
			for (int k=0;k<container[i].size();k++)
			{
				tmp_sum=0;
				for (int l=1;l<=N;l++)
					tmp_sum+=space[l][container[i][k]];
				if (tmp_sum==0)
				{
					sum_check=1;
					out_deg_zero++;
				}
			}
			if (sum_check==0)
				out_deg_zero++;
			sum_check=0;
		}
	}

	tmp_sum=0;
	sum_check=0;
	in_deg_zero=indegree_check();

	if (component_num==1)
		cout<<0<<endl;
	else
		cout<<((out_deg_zero>in_deg_zero)?out_deg_zero:in_deg_zero)<<endl;

	return 0;
}