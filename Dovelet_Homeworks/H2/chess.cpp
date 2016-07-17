#include <iostream>
#include <vector>

using namespace std;

#define MAX 16

int main(void)
{
	char input[30];
	int white,black;

	vector<int> white_data;
	vector<int> black_data;
	int people=0;

	while (gets(input))
    {
        sscanf(input,"%d %d",&white,&black);
		white_data.push_back(white);
		black_data.push_back(black);			
		people++;
    }

	int ***P;

	P=new int**[people+1];

	for (int i=0;i<people+1;i++)
	{
		P[i]=new int*[MAX];
		for (int j=0;j<MAX;j++)
		{
			P[i][j]=new int[MAX];
		}
	}
	
	for (int i=0;i<people+1;i++)
	{
		for (int j=0;j<MAX;j++)
		{
			for (int k=0;k<MAX;k++)
			{
				if (j==0 || k==0)
					P[i][j][k]=0;
				else
					P[i][j][k]=-3000;
			}
		}
	}
	for (int l=1;l<MAX;l++)
	{
		P[0][0][l]=black_data[0];
		P[0][l][0]=white_data[0];
	}
	int i,j,k;
	//i==people, j==white, k==black
	for (i=1;i<people;i++)
	{
		for (j=0;j<MAX;j++)
		{
			for (k=0;k<MAX;k++)
			{
				if (j==0)
					P[i][1][k]=P[i-1][j][k]+white_data[i];
				else
				{
					if (P[i-1][j][k]<P[i-1][j-1][k]+white_data[i])
					{
						if(P[i][j][k]<P[i-1][j-1][k]+white_data[i])
							P[i][j][k]=P[i-1][j-1][k]+white_data[i];
					}
					else 
					{
						if (P[i-1][j][k]>P[i][j][k])
							P[i][j][k]=P[i-1][j][k];
					}
				}

				if (k==0)
					P[i][j][1]=P[i-1][j][k]+black_data[i];
				else
				{
					if (P[i-1][j][k]<P[i-1][j][k-1]+black_data[i])
					{
						if (P[i][j][k]<P[i-1][j][k-1]+black_data[i])
							P[i][j][k]=P[i-1][j][k-1]+black_data[i];
					}
					else
					{
						if (P[i-1][j][k]>P[i][j][k])
							P[i][j][k]=P[i-1][j][k];
					}
				}
			}//end of for k
		}//end of for j
	}//end of for i
	
	cout<<P[people-1][MAX-1][MAX-1]<<endl;

	return 0;
}