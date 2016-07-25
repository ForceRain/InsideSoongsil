#include <stdio.h>
int main(void)
{
	int sum,book_sum=0;
	scanf("%d",&sum);
	int book;
	int i;
	for (i=0;i<9;i++)
	{
		scanf("%d",&book);
		book_sum+=book;
	}
	printf("%d",sum-book_sum);
	return 0;
}
