#include <iostream>
#include <cstdio>
#include <cstdlib>
#include <string>
#include <sstream>
#include <cstring>
#include <vector>

using namespace std;

double DP[2][201];
string stream;

void initialize()
{
	for (int i=0;i<2;i++)
	{
		for (int j=0;j<=200;j++)
		{
			DP[i][j]=0.0;
		}
	}
}

int main(void)
{
	double one_unit,P;
	int N;
	int M;
	int times=1;

	while (cin>>one_unit>>M)
	{
		vector< pair<int,double> > contain;
		initialize();
		contain.push_back(pair<int,double>(1,one_unit));

		for (int i=0;i<M;i++)
		{
			cin>>N>>P;
			contain.push_back(pair<int,double>(N,P));
		}
		
		cin.ignore();
		int total_size=contain.size();

		for (int j=1;j<=100;j++)
		{
			DP[0][j]=contain[0].second*j;
		}

		int bef_DP_indicator=0;
		for (int i=1;i<=total_size;i++)
		{
			int DP_indicator=i%2;		

			for (int j=1;j<=100;j++)
			{
				double minimum=100000000000.0;

				for (int k=1;k<=i;k++)
				{
					if (j-contain[k-1].first>=0)
					{
						if (DP[bef_DP_indicator][j]>DP[bef_DP_indicator][j-contain[k-1].first]+contain[k-1].second)
						{
							if (minimum>=DP[bef_DP_indicator][j-contain[k-1].first]+contain[k-1].second)
								minimum=DP[bef_DP_indicator][j-contain[k-1].first]+contain[k-1].second;
						}
						else
						{
							if (minimum>DP[bef_DP_indicator][j])
								minimum=DP[bef_DP_indicator][j];
						}

						if (minimum>DP[DP_indicator][j-contain[k-1].first]+contain[k-1].second)
							minimum=DP[DP_indicator][j-contain[k-1].first]+contain[k-1].second;
					}
					else
					{
						if (DP[bef_DP_indicator][j]>contain[k-1].second)
							minimum=contain[k-1].second;
						else
							minimum=DP[bef_DP_indicator][j];
					}
				}
				DP[DP_indicator][j]=minimum;
			}
			bef_DP_indicator=DP_indicator;
		}
		int idx;
		getline(cin,stream);
		stringstream obj(stream);
		cout<<"Case "<<times++<<":"<<endl;

		while (obj>>idx)
		{
			if (idx!=0)
				printf("Buy %d for $%.2lf\n",idx,(double)DP[contain.size()%2][idx]);
		}
	}

	return 0;
}
