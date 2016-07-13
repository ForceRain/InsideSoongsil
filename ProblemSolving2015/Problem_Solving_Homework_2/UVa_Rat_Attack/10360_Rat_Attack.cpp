#include <iostream>
#include <cstring>
#include <cstdio>

using namespace std;

int rat_house[1026][1026];
int maxX;
int maxY;
int d;
int maxRat;

void init_rats(int x,int y,int rats)
{
	int left_start=((x-d)>=0?(x-d):0);
	int upper_start((y-d)>=0?(y-d):0);
	int right_end=((x+d)<1024?(x+d):1023);
	int lower_end=((y+d)<1024?(y+d):1023);

	for (int i=left_start;i<=right_end;i++)
	{
		for (int j=upper_start;j<=lower_end;j++)
		{
			rat_house[i][j]+=rats;
			if (maxRat<rat_house[i][j])
			{
				maxX=i+1;
				maxY=j+1;

				maxRat=rat_house[i][j];
			}
		}
	}
}

int main(void)
{
	int test_case;
	int n;
	int x,y,rats;
	cin>>test_case;

	for (int i=0;i<test_case;i++)
	{
		maxX=0;
		maxY=0;
		maxRat=0;
		memset(rat_house,0,sizeof(rat_house));

		cin>>d;
		cin>>n;
		for (int j=0;j<n;j++)
		{
			cin>>x>>y>>rats;
			x--;y--;

			init_rats(x,y,rats);
		}
		/*
		for (int i=0;i<10;i++)
		{
			for (int j=0;j<10;j++)
			{
				printf("%3d ",rat_house[i][j]);
			}
			cout<<endl;
		}
		*/
		cout<<maxX<<" "<<maxY<<" "<<maxRat<<endl;
	}


	return 0;
}