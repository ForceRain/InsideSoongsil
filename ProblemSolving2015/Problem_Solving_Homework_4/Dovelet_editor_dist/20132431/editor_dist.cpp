#include <iostream>
#include <cstdio>
#include <cstring>
#include <vector>

using namespace std;

char wrong[100];
char original[100];
int DP[101][101];
int going[101][101];
int flag;			//0=NW, 1=N, 2=W
vector<char> wrong_c;
int times=0;

int my_min(int a,int b,int c)
{
	if (a<=b)
	{
		if (a<=c)
		{
			return a;
		}
		else
		{
			return c;
		}
	}
	else
	{
		if (b<=c)
		{
			return b;
		}
		else
		{
			return c;
		}
	}
}

void print_all()
{
	cout<<times++<<". ";
	for (int i=0;i<wrong_c.size();i++)
	{
		cout<<wrong_c[i];
	}
	cout<<endl;
}

void Print_it(int i,int j)
{
	if (i==0 || j==0)
	{
		if (i==0)
		{
			if (DP[i][j]>DP[i][j-1])
			{
				vector<char>::iterator it=wrong_c.begin();
				wrong_c.insert(it,original[j-1]);
				print_all();
			}
		}
		if (j==0)
		{
			if (DP[i][j]>DP[i-1][j])
			{
				vector<char>::iterator it=wrong_c.begin()+(i-1);
				wrong_c.erase(it);
				print_all();
			}
		}
		return;
	}
	else
	{		
		if (DP[i][j]>DP[i][j-1])
		{
			vector<char>::iterator it=wrong_c.begin()+i;
			wrong_c.insert(it,original[j-1]);
			print_all();
			Print_it(i,j-1);
		}
		else if (DP[i][j]>DP[i-1][j])
		{
			vector<char>::iterator it=wrong_c.begin()+(i-1);
			wrong_c.erase(it);
			print_all();
			Print_it(i-1,j);
		}
		else 
		{
			wrong_c[i-1]=original[j-1];

			if (DP[i][j]!=DP[i-1][j-1])
			{
				print_all();		
			}

			Print_it(i-1,j-1);
		}
	}
}

int main(void)
{
	cin>>wrong;
	cin>>original;

	DP[0][0]=0;

	int m=strlen(wrong);
	int n=strlen(original);

	for (int i=0;i<m;i++)
		wrong_c.push_back(wrong[i]);

	for (int i=1;i<=m;i++)
		DP[i][0]=i;
	for (int j=1;j<=n;j++)
		DP[0][j]=j;

	for (int i=1;i<=m;i++)
	{
		for (int j=1;j<=n;j++)
		{
			int val=(wrong[i-1]==original[j-1])?0:1;

			int tmp=my_min(DP[i-1][j-1]+val,DP[i-1][j]+1,DP[i][j-1]+1);
			DP[i][j]=tmp;
		}
	}

	cout<<DP[m][n]<<endl;
	print_all();
	Print_it(m,n);

	return 0;
}