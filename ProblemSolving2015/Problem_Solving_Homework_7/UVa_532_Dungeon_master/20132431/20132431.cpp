#include <iostream>
#include <cstdio>
#include <queue>
#include <cstring>

using namespace std;

char space[32][32][32];
bool passed[32][32][32];
bool output_check=false;
int total_level;
int total_x;
int total_y;
int min_time;

struct Node
{
	int level;
	int x;
	int y;
	int time;

	Node(int l,int y,int x,int time) : level(l), y(y), x(x), time(time) {}
};

void BFS(int level,int y,int x)
{
	min_time=0x7FFFFFFF;
	Node obj(level,y,x,0);
	queue<Node> container;
	container.push(obj);
	do
	{
		obj=container.front();
		container.pop();
		if (min_time>=obj.time)
		{
			if (space[obj.level][obj.y][obj.x]=='E')
			{
				if (min_time>obj.time)
					min_time=obj.time;
				output_check=true;
			}

			if ((obj.level<total_level-1) && (space[obj.level+1][obj.y][obj.x]!='#') && (!passed[obj.level+1][obj.y][obj.x]))
			{
				container.push(Node(obj.level+1,obj.y,obj.x,obj.time+1));
				passed[obj.level+1][obj.y][obj.x]=true;
			}
			if ((obj.level>0) && (space[obj.level-1][obj.y][obj.x]!='#') && (!passed[obj.level-1][obj.y][obj.x]))
			{
				container.push(Node(obj.level-1,obj.y,obj.x,obj.time+1));
				passed[obj.level-1][obj.y][obj.x]=true;
			}//up,down
			if ((obj.y>0) && (space[obj.level][obj.y-1][obj.x]!='#') && (!passed[obj.level][obj.y-1][obj.x]))
			{
				container.push(Node(obj.level,obj.y-1,obj.x,obj.time+1));
				passed[obj.level][obj.y-1][obj.x]=true;
			}
			if ((obj.y<total_y-1) && (space[obj.level][obj.y+1][obj.x]!='#') && (!passed[obj.level][obj.y+1][obj.x]))
			{
				container.push(Node(obj.level,obj.y+1,obj.x,obj.time+1));
				passed[obj.level][obj.y+1][obj.x]=true;
			}
			if ((obj.x>0) && (space[obj.level][obj.y][obj.x-1]!='#') && (!passed[obj.level][obj.y][obj.x-1]))
			{
				container.push(Node(obj.level,obj.y,obj.x-1,obj.time+1));
				passed[obj.level][obj.y][obj.x-1]=true;
			}
			if ((obj.x<total_x-1) && (space[obj.level][obj.y][obj.x+1]!='#') && (!passed[obj.level][obj.y][obj.x+1]))
			{
				container.push(Node(obj.level,obj.y,obj.x+1,obj.time+1));
				passed[obj.level][obj.y][obj.x+1]=true;
			}
		}
	}
	while (container.size()!=0);
}

int main(void)
{
	char input_buffer[100];
	int level,row,column;
	int str_len=0;
	int start_l,start_x,start_y;
	while (true)
	{
		memset(passed,false,sizeof(passed));
		output_check=false;
		gets(input_buffer);
		sscanf(input_buffer,"%d%d%d",&level,&row,&column);
		if ((level==0) && (row==0) && (column==0))
			break;

		total_y=row;
		total_x=column;
		total_level=level;
		for (int l=0;l<level;l++)
		{
			for (int i=0;i<row;i++)
			{
				gets(input_buffer);
				str_len=strlen(input_buffer);
				for (int j=0;j<str_len;j++)
				{
					space[l][i][j]=input_buffer[j];
					if (space[l][i][j]=='S')
					{
						start_l=l;
						start_y=i;
						start_x=j;
					}
				}
			}
			getchar();
		}
		BFS(start_l,start_y,start_x);
		if (output_check)
			cout<<"Escaped in "<<min_time<<" minute(s)."<<endl;
		else
			cout<<"Trapped!"<<endl;
	}
	return 0;
}