#include <iostream>
#include <vector>
#include <cstdio>

using namespace std;

typedef struct _node
{
	int begin;
	int end;
}Node;

class Interval
{
private :
	int *data;
	int data_num;
	int **arr;

public :
	Interval(int in_num)
	{
		data_num=in_num;
		data=new int[data_num];
		
		for (int i=0;i<data_num;i++)
		{
			cin>>data[i];
		}

		arr=new int*[data_num];

		for (int i=0;i<data_num;i++)
			arr[i]=new int[data_num];

		for (int i=0;i<data_num;i++)
		{
			for (int j=0;j<data_num;j++)
				arr[i][j]=0;
		}
	}
	int find_gap(int start,int end)
	{
		int max,min;

		if (end>data_num)
			end=data_num;

		max=data[start-1];		//
		min=data[start-1];		//

		for (int i=start-1;i<=end-1;i++)
		{
			if (max<data[i])
				max=data[i];

			if (min>data[i])
				min=data[i];
		}

		return max-min;
	}
	void fill_in()
	{
		for (int i=1;i<data_num;i++)
		{
			arr[i-1][i]=find_gap(i,i+1);
		}

		int length=0;
		
		Node *check;
		check=new Node[data_num];

		for (int path=1;path<=data_num;path++,length++)
		{		
			int val1_smallest_begin=data_num;
			int begin=0,end=0;
			for (int i=1;i<=data_num;i++)
			{
				int border=i+length;				

				if (border>data_num)
					border=data_num;
				
				int val1_begin=i;
				int val1_end=border;

				int val1=find_gap(i,border);
				int val2=0;
				int val2_end=0;
				int val2_begin=0;

				for (int k=i+1;k<=border-1;k++)				//배열의 인덱스는 0부터시작, 입력되는 값은 1부터 시작이므로 값을 하나 내림.
				{
					if (val2<arr[i-1][k-1]+arr[k][border-1])
					{
						val2=arr[i-1][k-1]+arr[k][border-1];

						val2_end=k-1;
						val2_begin=k;
					}
				}
				if (val1>val2)
				{
					if (val1_smallest_begin>val1_begin)
						val1_smallest_begin=val1_begin;

					arr[i-1][border-1]=val1;
					end=val1_end;
					begin=val1_smallest_begin;
				}
				else
				{
					arr[i-1][border-1]=val2;
					end=data_num;
					begin=val2_begin;
				}

				if (path==data_num)
				{
					check[i-1].begin=begin;
					check[i-1].end=end;
				}
			}
		}

		cout<<arr[0][data_num-1]<<endl;	//값 출력

		int *cnt;
		cnt=new int[data_num];

		for (int i=0;i<data_num;i++)
		{
			cnt[i]=0;
		}
		
		int bef_idx=0;
		int bef_div_idx=0;
		bool divided=true;
		for (int i=0;i<data_num;i++)
		{
			for (int j=check[i].begin;j<=check[i].end;j++)
			{
				bef_idx=check[i].begin;
				if (j-bef_idx>1)
				{
					cnt[j]++;
					divided=true;
				}
				else if (bef_idx-bef_div_idx>1)
				{
					cnt[j]++;
					divided=true;
				}
				else
				{
					divided=false;
					break;
				}
			}
			if (divided)
				bef_div_idx=bef_idx;			
		}

		vector <int> reposit;
		int div_num=0;
		int div_total=1;
		int bef=0;
		for (int i=0;i<data_num;i++)
		{
			if (bef!=cnt[i])
			{
				div_total++;
				reposit.push_back(div_num);
				div_num=1;
			}
			else
				div_num++;

			bef=cnt[i];
		}
		reposit.push_back(div_num);

		cout<<div_total<<endl;
		for (int i=0;i<reposit.size();i++)
			cout<<reposit[i]<<" ";
		cout<<endl;
	}
};

int main(void)
{
	int N;
	cin>>N;
	
	Interval object(N);

	object.fill_in();

	return 0;
}