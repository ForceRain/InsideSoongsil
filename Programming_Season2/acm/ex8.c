#include <stdio.h>
int main(void)
{
	int i;
	int ary[10];
	int ans[42]={0};
	int input,fin_count=0;
	for (i=0;i<10;i++)
	{
		scanf("%d",&input);
		ary[i]=input%42;
	}
	for (i=0;i<10;i++)
	{
		ans[ary[i]]++;
	}
	for (i=0;i<42;i++)
	{
		if (ans[i]!=0)
			fin_count++;
	}
	printf("%d",fin_count);
	return 0;
}
