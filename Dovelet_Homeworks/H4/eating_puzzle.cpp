#include <iostream>
#include <algorithm>

using namespace std;

int calorie[21];
bool included[21];
int max_calorie=-1;
int limit_calorie;
int B;

void sum_of_calories(int index, int sum_calorie, int total_calorie)
{
	bool promising=((sum_calorie+total_calorie>=limit_calorie) && (sum_calorie==limit_calorie || sum_calorie+calorie[index+1]<=limit_calorie));
	int local_sum=0;
	for (int i=0;i<B;i++)
	{
		if (included[i])
			local_sum+=calorie[i];
	}

	if (max_calorie<local_sum)
		max_calorie=local_sum;

	if (promising)
	{
		included[index+1]=true;
		sum_of_calories(index+1,sum_calorie+calorie[index+1],total_calorie-calorie[index+1]);
		included[index+1]=false;
		sum_of_calories(index+1,sum_calorie,total_calorie-calorie[index+1]);
	}
}

int main(void)
{
	int C;
	cin>>C>>B;
	limit_calorie=C;
	int sum=0;
	int tmp;
	for (int i=0;i<B;i++)
	{
		cin>>tmp;
		sum+=tmp;
		calorie[i]=tmp;
	}
	sort(calorie,calorie+B);
	sum_of_calories(-1,0,sum);
	cout<<max_calorie<<endl;

	return 0;
}