#include <stdio.h>
#include <string.h>
int main(void)
{
	int N;
	scanf("%d",&N);
	char input[N][1000];
	char result[N][100];
	int i,temp;
	for (i=0;i<N;i++)
	{
		scanf("%s",input[i]);
	}
	for (i=0;i<N;i++)
	{
		temp=strlen(input[i]);
		if (input[i][temp/2]==input[i][temp/2+1])
			strcpy(result[i],"Do-it");
		else
			strcpy(result[i],"Do-it-Not");
	}
	for (i=0;i<N;i++)
	{
		printf("%s\n",result[i]);
	}
	return 0;
}
