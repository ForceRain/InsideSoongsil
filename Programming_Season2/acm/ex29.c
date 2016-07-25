#include <stdio.h>
int main(void)
{
	int N,i,j,count=0;
	char check;
	scanf("%d",&N);
	char arr[N][50];
	for (i=0;i<N;i++)
	{
		scanf("%s",arr[i]);
	}
	j=0;
	i=0;
	while (arr[0][j]!='\0')
	{
		count=0;
		check=arr[0][j];
		for (i=0;i<N;i++)
		{
			if (check==arr[i][j])
				count++;
		}
		if (count==N)
			printf("%c",arr[0][j]);
		else
			printf("?");
		j++;
	}
	return 0;
}
