#include <iostream>
#include <set>

using namespace std;

char input_stream[200];
int input_str_length;
int M[200][200];
int H[200][200];
int P[200][200];
set< pair<int,int> > container;

int my_min(int a,int b)
{
	return (a<b)?a:b;
}

int my_max(int a,int b)
{
	return (a>b)?a:b;
}

bool available(int front,int back)
{
	return (input_stream[front-1]!=input_stream[back-1]) && ((back-front)%2!=0);
}

void dynamic_programming()
{
	int caseM,caseH;

	for (int i=1;i<=input_str_length;i++)
	{
		M[i][i]=0;
		H[i][i]=0;
	}

	for (int i=1;i<input_str_length;i++)
	{
		if (available(i,i+1))
		{
			M[i][i+1]=3;
			H[i][i+1]=1;
		}
	}

	for (int diagonal=1; diagonal<=input_str_length-1;diagonal++)
	{
		for (int i=1;i<=input_str_length-diagonal;i++)
		{
			int j=i+diagonal;
			int MinM=10000,MaxH=0;
			caseM=10000,caseH=0;
			int give_k=-1;

			for (int k=i;k<=j-1;k++)
			{
				if ((available(i,k) || available(k+1,j)))
				{
					int tmpM=M[i][k]+M[k+1][j];

					if (MinM>tmpM)
					{
						MinM=tmpM;
						MaxH=my_max(H[i][k],H[k+1][j]);
						give_k=k;
					}
				}
			}
			
			if (available(i,j))
			{
				caseM=(2*(H[i+1][j-1]+1)+(j-i))+M[i+1][j-1];
				caseH=H[i+1][j-1]+1;
			}

			if (caseM>MinM)
			{
				M[i][j]=MinM;
				H[i][j]=MaxH;
				P[i][j]=give_k;
			}
			else
			{
				M[i][j]=caseM;
				H[i][j]=caseH;
				P[i][j]=-1;
			}
		}
	}
}

void search(int start, int end)
{
	if (start<end)
	{
		if (P[start][end]!=-1)
		{
			container.insert(pair<int,int>(start,P[start][end]));
			search(start+1,P[start][end]-1);
			search(P[start][end]+1,end);
		}
		else
		{
			container.insert(pair<int,int>(start,end));
			search(start+1,end-1);
		}
	}
}

int main(void)
{
	cin>>input_str_length;
	cin>>input_stream;

	dynamic_programming();

	cout<<M[1][input_str_length]<<endl;
	search(1,input_str_length);

	for (set< pair<int,int> >::iterator it=container.begin();it!=container.end();++it)
		cout<<it->first<<" "<<it->second<<endl;

	return 0;
}