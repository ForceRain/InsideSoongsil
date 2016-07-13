#include <iostream>
#include <cstdio>

using namespace std;

int dis[102][102];
int station[102];
int DP[102][102];
int avail[102][102];

int main(void)
{
	int max_running,N;
	cin>>max_running;
	cin>>N;
	for (int i=0;i<=N;i++)
	{
		int tmp;
		cin>>tmp;
		dis[i][i+1]=tmp;
	}

	for (int i=1;i<=N;i++)
		cin>>station[i];
	for (int i=0;i<=N+1;i++)
	{
		for (int j=i+2;j<=N+1;j++)
		{
			dis[i][j]=dis[i][j-1]+dis[j-1][j];
		}
	}
	//dis배열에는 i에서j까지 가는 누적 거리의 합이 담겨있음.
	for (int diagonal=0;diagonal<=N+1;diagonal++)
	{
		for (int i=0;i<=(N+1)-diagonal;i++)
		{
			int j=i+diagonal;

			if (dis[i][j]<=max_running)	//한방에 갈 수 있는 거리였다면
			{
				//cout<<"i : "<<i<<", j : "<<j<<endl;
				DP[i][j]=0;
				avail[i][j]=max_running-dis[i][j];
			}
			else
			{
				int minimum=20132431;
				int min_avail=20132431;
				for (int k=i;k<=j-1;k++)
				{
					if (avail[i][k]+avail[k][j]<max_running)
					{
						if (minimum>DP[i][k]+DP[k][j]+station[k])
						{
							minimum=DP[i][k]+DP[k][j]+station[k];
						}
						min_avail=max_running-dis[k][j];
					}//가능한 거리들의 합이 짧을 경우
					else
					{
						min_avail=avail[i][k]+avail[k][j]-max_running;
					}//가능한 거리들의 합이 갈수 있을 정도로 충분히 클 경우 
				}
				DP[i][j]=minimum;
				avail[i][j]=min_avail;
			}
		}
	}
	cout<<DP[0][N+1]<<endl;
	return 0;
}