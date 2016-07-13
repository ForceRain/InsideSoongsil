#include <iostream>
#include <vector>
#include <set>
#include <algorithm>
#include <cstring>
#include <cstdio>

#define sqrt(x,y) (x)*(x)+(y)*(y) 

using namespace std;

int town_num;
vector< pair<int, pair<int,int> > > weight;  
vector< pair<int,int> > city_container;
vector< pair<int,int> > result;
bool visited[750][750];

struct Node
{
	int city;
	int height;
	Node *parent;
};

Node *container[750];

void Create(int i,Node *x)
{
	x->parent=x;
	x->city=i;
	x->height=0;
}

Node *Find(Node *x)
{
	while (x!=x->parent)
		x=x->parent;
	return x;
}

void Merge(Node *x,Node *y)
{
	Node *a=Find(x);
	Node *b=Find(y);

	if (a->height<=b->height)
	{
		if (a->height==b->height)
			b->height++;
		a->parent=b;
	}
	else
		b->parent=a;
}

bool Equal(Node *x,Node *y)
{
	return Find(x)==Find(y);
}

void organizate()
{
	int x,y;
	for (int i=0;i<town_num;i++)
	{
		x=city_container[i].first;
		y=city_container[i].second;
		for (int j=0;j<i;j++)
		{
			weight.push_back(pair<int, pair<int,int> >(sqrt(x-city_container[j].first,y-city_container[j].second),pair<int,int>(i,j)));
		}
	}
}

int main(void)
{
	int testcase,a,b;
	int connected_num;
	int node_num;
	cin>>testcase;

	getchar();
	for (int i=0;i<testcase;i++)
	{
		node_num=0;
		cin>>town_num;
		for (int j=0;j<town_num;j++)
		{
			cin>>a>>b;
			city_container.push_back(pair<int,int>(a,b));
			container[j]=new Node();
			Create(j+1,container[j]);
		}
		organizate();
		sort(weight.begin(),weight.end());
		
		cin>>connected_num;
		for (int j=0;j<connected_num;j++)
		{
			cin>>a>>b;
			if (!Equal(container[a-1],container[b-1]))
			{
				visited[a-1][b-1]=true;
				visited[b-1][a-1]=true;
				Merge(container[a-1],container[b-1]);
				node_num++;
			}
		}

		vector< pair<int, pair<int,int> > >::iterator it=weight.begin();
		while (node_num!=town_num-1)
		{
			pair<int,int> tmp=it->second;
			if (!visited[tmp.first][tmp.second])	
			{
				if (!Equal(container[tmp.first],container[tmp.second]))
				{
					Merge(container[tmp.first],container[tmp.second]);
					result.push_back(tmp);
					node_num++;
				}
			}
			it++;
		}
		if (result.size()==0)
			cout<<"No new highways need"<<endl;
		else
			for (int i=0;i<result.size();i++)
				cout<<result[i].first+1<<" "<<result[i].second+1<<endl;
		if (i!=testcase-1)
			cout<<endl;

		for (int i=0;i<town_num;i++)
			delete container[i];
		city_container.erase(city_container.begin(),city_container.end());
		memset(visited,0,sizeof(visited));
		weight.erase(weight.begin(),weight.end());
		result.erase(result.begin(),result.end());
	}
	

	return 0;
}