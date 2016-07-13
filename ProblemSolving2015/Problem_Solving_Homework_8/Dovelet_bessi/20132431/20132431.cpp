#include <iostream>
#include <cstring>
#include <cstdio>

using namespace std;

int space[52][52];
bool visit[52][52];

int refine_input(char in)
{
	if (('A'<= in) && (in<='Z'))
		return in-'A';
	else
		return in-'a'+26;
}

int main(void)
{
	int P,weight;
	char from,to;

	for (int i=0;i<52;i++)
	{
		for (int j=0;j<52;j++)
			space[i][j]=10000000;
	}
	cin>>P;
	for (int i=0;i<P;i++)
	{
		cin>>from>>to>>weight;
		if (!visit[refine_input(from)][refine_input(to)])
		{
			space[refine_input(from)][refine_input(to)]=weight;
			space[refine_input(to)][refine_input(from)]=weight;
			visit[refine_input(from)][refine_input(to)]=true;
		}
		else
		{
			if (weight<space[refine_input(from)][refine_input(to)])
			{
				space[refine_input(from)][refine_input(to)]=weight;
				space[refine_input(to)][refine_input(from)]=weight;
			}
		}
	}

	for (int k=0;k<52;k++)
	{
		for (int i=0;i<52;i++)
		{
			for (int j=0;j<52;j++)
			{
				if (space[i][k]+space[k][j]<space[i][j])
				{
					space[i][j]=space[i][k]+space[k][j];
				}
			}
		}
	}

	int smallest=0x7FFFFFFF;
	int pos=-1;
	for (int i=0;i<25;i++)
	{
		if (space[i][refine_input('Z')]<smallest)
		{
			pos=i;
			smallest=space[i][refine_input('Z')];
		}
	}

	printf("%c %d\n",pos+'A',smallest);

	return 0;
}