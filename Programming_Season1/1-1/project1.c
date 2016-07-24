#include<stdio.h>
#include<time.h>
#include<termio.h>
#include<stdlib.h>
#include<string.h>

int getch(void)
{
	int ch;
	
	struct termios buf;
	struct termios save;
	
	tcgetattr(0,&save);
	buf=save;
	
	buf.c_lflag&=~(ICANON|ECHO);
	buf.c_cc[VMIN]=1;
    buf.c_cc[VTIME]=0;

    tcsetattr(0, TCSAFLUSH, &buf);

    ch = getchar();
    tcsetattr(0, TCSAFLUSH, &save);

     return ch;
}

int writingtest(void)
{
	char writing[8][5][100]={
		{{"The Selfish Giant"},
		{"Every afternoon, as they were coming from school, the children used"},
		{"to go and play in the Giant's garden."},
		{"It was a large lovely garden, with soft green grass. Here and there"},
		{"over the grass stook beautiful flowers like stars, and there were"}},
		{{"twelve peachtrees that in the springtime broke out into delicate blos-"},
		 {"soms of pink and pearl, and in the autumn bore rich fruit. The birds"},
		 {"sat in the trees and sang so sweetly that the children used to stop"},
		 {"their games in order to listen to them. \"How happy we are here!\" they"},
		 {"cried to each other."}},
	   {{"Rapunzel"},
		{"There once lived a man and his wife, who had long wished for a child,"},
		{"but in vain. Now there was at the back of their house a little window"},
		{"which over looked a beautiful garden full of the finest vegetables and"},
		{"flowers; but there was a high wall all round it, and no one ventured"}},
	   {{"into it, for it belonged to a witch of great might, and of whom all"},
		{"the world was afraid. One day, when the wife was standing at the win-"},
		{"dow, and looking into the garden, she saw a bed filled with the finest"},
		{"rampion; and it looked so fresh and green that she began to wish for"},
		{"some; and at length she longed for it greatly."}},
	   {{"Narcissus"},
		{"Long, long ago, there lived in Greece a young boy named Narcissus."},
		{"All day long he tended his sheep in the hills, and drove them from"},
		{"place to place to find the very best pasture."},
		{"One day he came to a little stream and wanted to drink form it. The"}},
	   {{"water was very clear and reflected everything that leaned over it."},
		{"While Narcissus was waiting for the sheep to drink, he chanced to see"},
		{"his own face in the water."},
		{"He had never seen his likeness before, and he was so pleased with the"},
		{"pretty picture that he looked at it for a long time."}},
	   {{"Gulliver's Travels"},
		{"I am Lemuel Gulliver. I was born in England. When I grew up, I had a"},
		{"great wish to go round the world, and in the year 1699, I got on board"},
		{"a ship bound for the South Seas. For some time things went all right."},
		{"But one day a great storm drove us far to the south and at last the"}},
	   {{"whip ran on a rock and split her bow. We let down a boat and left the"},
		{"wreck. But a big wave sank the boat, and I lost sight of my poor"},
		{"friends."},
		{"I swam on and on, and just when I felt I must give myself up, I found"},
		{"I could touch the sand. I was now safe."}}
	};
	
	char input[5][100];
	char input1[5][100];
	char e;
	int a=0,i=0,j=0,k=0,l=0;
	int correct=0, wrong=0, sum_time; 
	float m=0.0, now=0;
	clock_t start_time, end_time, dif_time;

	srand(time(NULL));

	a=rand()%8;
	
	if(a%2==1)
		a-=1;

	system("clear");

	printf(">>영문 타자 연습 프로그램 : 긴 글 연습 <<\n");
	printf("정확도 : %.0f%%   현재타수 : %0.f\n\n",m, now);
	
	for(i=0;i<5;i++)
		printf("%s\n",writing[a][i]);

	printf("\n");

		start_time=time(NULL);

	for(j=0;j<100;j++)
	{
		input[k][j]=getch();

		if(input[k][j]==27)
			return 0;

		system("clear");
		
		if(input[k][j]=='\n')
		{
			k++;
			j=-1;
		}
			
		else
		{
	
				if(input[k][j]==127&&j>0)
					{
						if(input[k][j-1]==writing[a][k][j-1])
							correct--;
						else
							wrong--;
	
						input[k][j]='\0';
						input[k][j-1]='\0';
					
						j-=2;
					}
			
				else
				{
			
					if(input[k][j]==writing[a][k][j])
						correct++;
					else
						wrong++;
				}
		}
		
		end_time=time(NULL);

		dif_time=end_time - start_time;

		now=(float)correct*60/dif_time;
		m=(float)correct/(correct+wrong)*100;

		printf(">>영문 타자 연습 프로그램 : 긴 글 연습 <<\n");
		printf("정확도 : %.0f%%   현재타수 : %0.f\n\n",m, now);

		if(k==5)
		{	
			a++;
			k=0;
			j++;

			for(i=0; i<5; i++)
				printf("%s\n",writing[a][i]);

			printf("\n");

			break;
		
		}
		else
		{
			for(i=0;i<5;i++)
				printf("%s\n",writing[a][i]);
			
			printf("\n");
		
			for(l=0;l<=k;l++)
				printf("%s",input[l]);
		}
	}

	for(j=0;j<100;j++)
	{
		input1[k][j]=getch();

		if(input1[k][j]==27)
			return 0;

		system("clear");
		
		if(input1[k][j]=='\n')
		{
			k++;
			j=-1;
		}
			
		else
		{
	
				if(input1[k][j]==127&&j>0)
					{
						if(input1[k][j-1]==writing[a][k][j-1])
							correct--;
						else
							wrong--;
	
						input1[k][j]='\0';
						input1[k][j-1]='\0';
					
						j-=2;
					}
			
				else
				{
			
					if(input1[k][j]==writing[a][k][j])
						correct++;
					else
						wrong++;
				}
		}
		
		end_time=time(NULL);

		dif_time=end_time - start_time;

		now=(float)correct*60/dif_time;
		m=(float)correct/(correct+wrong)*100;

		printf(">>영문 타자 연습 프로그램 : 긴 글 연습 <<\n");
		printf("정확도 : %.0f%%   현재타수 : %0.f\n\n",m, now);

		if(k==5)
		{
			for(i=0; i<5; i++)
				printf("%s\n",writing[a][i]);

			printf("\n");

			for(l=0; l<k; l++)
				printf("%s",input1[l]);

			printf("결과 : 정확도 : %.0f, 현재타수 : %.0f\n",m,now);

			printf("\nenter키를입력하면 메뉴로 복귀합니다");
			
			e=getchar();
			
			if(e=='\n')
				return 0;	
		
		}
		else
		{
			for(i=0;i<5;i++)
				printf("%s\n",writing[a][i]);
			
			printf("\n");
		
			for(l=0;l<=k;l++)
				printf("%s",input1[l]);
		}
	}

	return 0;
}

int main(void)
{
	writingtest();

	return 0;
}
