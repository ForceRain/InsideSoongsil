#include <iostream>
#include <cstring>

using namespace std;

int l_time[101];
char *space[101];

typedef struct _node
{
	char *name;
	int start;
	int end;
}interval;

void find_target_flower(int index,interval *arr,int size)
{
	int find_idx=-1;
	for (int i=0;i<5;i++)
	{
		if ((arr[i].start<=index) && (index<=arr[i].end))
		{
			for (int j=arr[i].start;j<=arr[i].end;j++)
				l_time[j]--;

			arr[i].start=-1;
			arr[i].end=-1;
			find_idx=i;
			break;
		}
	}
	space[index]=arr[find_idx].name;
}

int main(void)
{
	interval flower[5];
	int max_end=-1;
	int start,end;
	int target[5]={0};

	for (int i=0;i<5;i++)
	{
		cin>>start>>end;
		flower[i].start=start;
		flower[i].end=end;
		flower[i].name=new char[15];

		if (end>max_end)
			max_end=end;
	}

	for (int i=0;i<5;i++)
	{
		for (int j=flower[i].start;j<=flower[i].end;j++)
			l_time[j]++;
	}

	strcpy(flower[0].name,"Poppy");
	strcpy(flower[1].name,"Carnation");
	strcpy(flower[2].name,"Sunflower");
	strcpy(flower[3].name,"Daisy");
	strcpy(flower[4].name,"Pansy");

	for (int i=0;i<5;i++)
	{
		int day;
		cin>>day;
		target[i]=day;
	}

	int cnt=0;
	bool check=false;
	while (cnt!=5)
	{
		check=false;
		for (int i=0;i<5;i++)
		{
			if (l_time[target[i]]==1)
			{
				find_target_flower(target[i],flower,5);
				cnt++;
				check=true;
			}
		}

		if (!check)
		{
			cout<<"Ambiguous"<<endl;
			return 0;
		}
	}

	for (int i=0;i<5;i++)
	{
		cout<<space[target[i]]<<" ";
	}

	return 0;
}