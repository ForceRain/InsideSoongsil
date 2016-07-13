#include <iostream>
#include <cstdio>

using namespace std;

int space[21];
int question[21];
int num_q;
int N;
int move_num=0x7FFFFFFF;

int my_abs(int x)
{
	return (x>0)?(x):(-x);
}

void door(int level,int fp1,int fp2,int weight)
{
	if (level==num_q)
	{
		if (move_num>weight)
			move_num=weight;
	}
	else
	{
		door(level+1,fp1,fp2+(question[level]-fp2),weight+my_abs(fp2-question[level]));
		door(level+1,fp1+(question[level]-fp1),fp2,weight+my_abs(fp1-question[level]));
	}
}

int main(void)
{
	scanf("%d",&N);
	int fp1,fp2;
	scanf("%d%d",&fp1,&fp2);
	scanf("%d",&num_q);
	for (int i=0;i<num_q;i++)
		scanf("%d",&question[i]);

	door(0,fp1,fp2,0);
	printf("%d",move_num);

	return 0;
}