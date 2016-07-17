#include <iostream>
#include <vector>

using namespace std;

int sudo_arr[9][9];
pair<int,int> data[9][9];
int last_x;
int last_y;
bool termination=false;

bool promising(int x,int y,int value)
{
	int arr_x=(x/3)*3;
	int arr_y=(y/3)*3;
	int tot_x;
	int tot_y;

	for (int i=0;i<9;i++)
	{
		tot_x=arr_x+(i%3);
		tot_y=arr_y+(i/3);
		if ((i!=y) && (sudo_arr[x][i]==value))
			return false;
		if ((i!=x) && sudo_arr[i][y]==value)
			return false;
		if ((tot_x!=x) && (tot_y!=y) && (sudo_arr[tot_x][tot_y]==value))
			return false;
	}
	
	return true;
}

void sudoku(int x,int y,int value)
{
	if (promising(x,y,value) && (!termination))
	{
		if ((last_x==x) && (last_y==y))
		{
			termination=true;
			for (int i=0;i<9;i++)
			{
				for (int j=0;j<9;j++)
				{
					cout<<sudo_arr[i][j];
				}
				cout<<endl;
			}
		}
		else
		{
			pair<int,int> set=data[x][y];
			for (int i=1;(i<=9) && (!termination);i++)
			{
				sudo_arr[set.first][set.second]=i;
				sudoku(set.first,set.second,i);
				sudo_arr[set.first][set.second]=0;
			}
		}
	}
	else
	{
		sudo_arr[x][y]=0;
	}
}

int main(void)
{
	char temp[9][10];
	for (int i=0;i<9;i++)
	{
		cin>>temp[i];
	}

	for (int i=0;i<9;i++)
	{
		for (int j=0;j<9;j++)
		{
			sudo_arr[i][j]=temp[i][j]-'0';
		}
	}
	
	int lx=-1,ly=-1;
	int bx=-1,by=-1;
	int start_x,start_y;
	for (int i=0;i<9;i++)
	{
		for (int j=0;j<9;j++)
		{
			if (sudo_arr[i][j]==0)
			{
				if (lx==-1)
				{
					start_x=i;start_y=j;
				}
				lx=i;ly=j;
				if (bx!=-1)
				{
					data[bx][by]=pair<int,int>(i,j);
				}
				bx=i;by=j;
			}
		}
	}
	last_x=lx;
	last_y=ly;

	for (int i=1;(i<=9) && (!termination);i++)
	{
		sudo_arr[start_x][start_y]=i;
		sudoku(start_x,start_y,i);
	}
	return 0;
}