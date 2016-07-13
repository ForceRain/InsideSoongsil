#include <iostream>

using namespace std;

int music_stream[50000];
int current_ptr=0;

int binary_search(int elapsed_time)
{
	int head=0;
	int tail=current_ptr;
	int mid;
	int key;
	bool find_key=false;

	while ((head<=tail) && !find_key)
	{
		mid=(head+tail)/2;
		if (music_stream[mid]<elapsed_time)
			head=mid+1;
		else if (music_stream[mid]>elapsed_time)
			tail=mid-1;
		else
		{
			find_key=true;
			key=mid;
		}
	}

	if (!find_key)
	{
		key=head;
	}

	return key+1;
}

int main(void)
{
	int N,questions;
	cin>>N>>questions;

	current_ptr=N;
	int tmp;
	int total_sum=-1;
	for (int i=0;i<N;i++)
	{
		cin>>tmp;
		total_sum+=tmp;
		music_stream[i]=total_sum;
	}	

	for (int i=0;i<questions;i++)
	{
		cin>>tmp;
		cout<<binary_search(tmp)<<endl;
	}

	return 0;
}