#include <stdio.h>
int main(void)
{
	int N;
	printf("계산할 과목수를 입력 : ");
	scanf("%d",&N);
	double sub[N];
	double hakjum[N];
	double sub_sum=0.0;
	double hakjum_sum=0.0;
	double pyungpyung=0.0;
	int i;
	printf("받은 학점을 입력 : \n");
	for (i=0;i<N;i++)
	{
		scanf("%lf",&sub[i]);
	}
	printf("기본학점 입력(받은 학점의 과목의 순서와 일치하게) : \n");
	for (i=0;i<N;i++)
	{
		scanf("%lf",&hakjum[i]);
	}
	for (i=0;i<N;i++)
	{
		sub_sum+=sub[i]*hakjum[i];
		hakjum_sum+=hakjum[i];
	}
	printf("평점 평균은 : %lf\n",sub_sum/hakjum_sum);
	return 0;
}
