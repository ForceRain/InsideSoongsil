#include <iostream>
#include <vector>

using namespace std;

typedef struct _data
{
	int level;
	vector<int> path;
	int bound;
}Data;

typedef struct _node
{
	int x;
	int y;
}Node;

int W[11][11];
int minimum_length=20132431;
int q_ptr=0;
int data_num;
bool isEmpty=false;
Data q[12];

int bound(Data v)
{
	int sum=0;
	int s=0,e=0;
	int check[12]={0};
	check[0]++;
	for (int i=0;i<v.path.size();i++)
	{
		e=v.path[i];
		sum+=W[s][e];
		check[e]++;
		s=e;
	}

	int kMin=20132431;
	for (int i=0;i<data_num;i++)
	{
		if ((!check[i]))
		{
			if (kMin>W[e][i])
				kMin=W[e][i];
		}
	}
	sum+=kMin;

	check[1]=0;
	for (int j=0;j<data_num;j++)
	{
		int tMin=20132431;
		for (int i=0;i<=data_num;i++)
		{
			if (!check[i])
			{
				if (tMin>W[e][i])
					tMin=W[e][i];
			}
		}
		sum+=tMin;
	}
	return sum;
}

int length(Data u)
{
	int sum=0;
	int s=0,e;
	for (int i=0;i<u.path.size();i++)
	{
		e=u.path[i];
		sum+=W[s][e];
		s=e;
	}
	return sum;
}
 
void adjust(int root)
{
	int e=q[1].bound;
	int j;
	for (j=2*root;j<=q_ptr;j*=2)
	{
		if ((j<q_ptr) && (q[j].bound>q[j+1].bound))
			j++;
		if (e<=q[j].bound)
			break;
		q[j/2]=q[j];
	}
	q[j/2].bound=e;
}

void insert(Data v)
{
	q_ptr++;
	q[q_ptr]=v;

	for (int i=q_ptr/2;i>0;i--)
	{
		adjust(i);
	}
}

int absolute(int input)
{
	if (input<0)
		return -input;
	else
		return input;
}

Data remove()
{
	Data output=q[1];
	Data tmp=q[1];
	q[1]=q[q_ptr];
	q[q_ptr]=tmp;
	q_ptr--;

	for (int i=q_ptr/2;i>0;i--)
	{
		adjust(i);
	}
	return output;
}

bool qEmpty()
{
	if (q_ptr==0)
		return true;
	else
		return false;
}

void TSP()
{
	Data u,v;

	v.level=0;
	v.path.push_back(0);
	v.bound=bound(v);
	insert(v);
	
	while (!qEmpty())
	{
		v=remove();
		if (v.bound<minimum_length)
		{
			u.level=v.level+1;
			int check[12]={0};
			for (int i=0;i<v.path.size();i++)
			{
				check[v.path[i]]++;
			}

			for (int i=1;i<data_num;i++)
			{
				if (!check[i])
				{
					u.path=v.path;
					u.path.push_back(i);

					if (u.level>=data_num-2)
					{
						int k;
						for (k=0;k<data_num;k++)
						{
							if ((!check[k]) && (k!=i))
								break;
						}

						u.path.push_back(k);
						u.path.push_back(0);
						if (length(u)<minimum_length)
						{
							minimum_length=length(u);
						}
					}
					else
					{
						u.bound=bound(u);
						if (u.bound<minimum_length)
							insert(u);
					}
				}
			}
		}//End of if
	}//End of while

}

int main(void)
{
	vector<Node> container;
	int X,Y;
	cin>>X>>Y;
	int curX,curY;
	cin>>curX>>curY;

	Node start;
	start.x=curX;
	start.y=curY;
	container.push_back(start);

	int N;
	cin>>N;
	data_num=N;
	data_num++;

	int bX,bY;
	for (int i=0;i<N;i++)
	{
		cin>>bX>>bY;
		Node obj;
		obj.x=bX;
		obj.y=bY;
		container.push_back(obj);
	}

	for (int i=0;i<N+1;i++)
	{
		Node obj1=container[i];
		for (int j=0;j<N+1;j++)
		{
			Node obj2=container[j];
			W[i][j]=absolute(obj1.x-obj2.x)+absolute(obj1.y-obj2.y);
		}
	}

	TSP();
	cout<<"The shortest path has length "<<minimum_length<<endl;

	return 0;
}