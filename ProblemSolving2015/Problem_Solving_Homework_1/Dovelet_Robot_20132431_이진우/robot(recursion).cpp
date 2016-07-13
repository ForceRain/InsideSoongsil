#include <iostream>

using namespace std;

char grid[10][10];
int Log[10][10];
int moving=0;
int totalC;
int totalR;
int last_start;
bool is_looping=false;

bool check_it_out(int curC,int curR)
{
	if ((curC<0) || (curC>=totalC) || (curR<0) || (curR>=totalR))
	{
		return true;
	}
	else
	{
		if (Log[curR][curC]!=0)
		{
			last_start=Log[curR][curC];
			return false;
		}
		Log[curR][curC]=++moving;
		if (grid[curR][curC]=='N')
			return check_it_out(curC,curR-1);
		else if (grid[curR][curC]=='S')
			return check_it_out(curC,curR+1);
		else if (grid[curR][curC]=='E')
			return check_it_out(curC+1,curR);
		else
			return check_it_out(curC-1,curR);
	}
}

int main(void)
{
	int start;
	cin>>totalR>>totalC>>start;

	for (int i=0;i<totalR;i++)
		cin>>grid[i];

	if (check_it_out(start-1,0))
		cout<<moving<<" step(s) to exit"<<endl;
	else
		cout<<last_start-1<<" step(s) before a loop of "<<moving-last_start+1<<" step(s)"<<endl;
		

	return 0;
}