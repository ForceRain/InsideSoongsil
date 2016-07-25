#include <stdio.h>
#include <string.h>
struct gojo{
	char name[20];
	int num;
	int times;
};
int main(void)
{
	int i,j;
	int process[3]={0};
	char input[20]={0};
	struct gojo ohm[10]={{"black",0,1},{"brown",1,10},{"red",2,100},{"orange",3,1000},{"yellow",4,10000},\
		{"green",5,100000},{"blue",6,1000000},{"violet",7,10000000},{"grey",8,100000000},{"white",9,1000000000}};
	for(i=0;i<3;i++)
	{
		scanf("%s",input);
		for (j=0;j<10;j++)
		{
			if (i==2)
			{
				if (strcmp(input,ohm[j].name)==0)
				{
					process[i]=ohm[j].times;
				}
			}
			else
			{
				if (strcmp(input,ohm[j].name)==0)
				{
					process[i]=ohm[j].num;
				}
			}
		}
	}
	printf("%d",(process[0]*10+process[1])*process[2]);
	return 0;    
}
