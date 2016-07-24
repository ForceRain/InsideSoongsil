#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <termio.h>
#include <string.h>
#define N 1000
int getch(void) {
	int ch;
	struct termios buf;
	struct termios save;
	tcgetattr(0,&save);
	buf=save;
	buf.c_lflag&=~(ICANON|ECHO);
	buf.c_cc[VMIN]=1;
	buf.c_cc[VTIME]=0;
	tcsetattr(0,TCSAFLUSH,&buf);
	ch=getchar();
	tcsetattr(0,TCSAFLUSH,&save);
	return ch;
}
void print_long(double correct, double current) {
          printf(">>영문 타자 연습 프로그램 : 긴 글 연습 <<\n");
          printf("정확도 : %.0lf%c  현재타수 : %.0lf \n\n",correct,37,current);
      return;
}

int main(void) {
	char article[40][N] = {"The Selfish Giant",
	"Every afternoon, as they were coming from school, the children used",
	"to go and play in the Giant's garden.",
	"It was a large lovely garden, with soft green grass. Here and there",
	"over the grass stook beautiful flowers like stars, and there were",
	"twelve peachtrees that in the springtime broke out into delicate blos-",
	"soms of pink and pear I, and in the autumn bore rich fruit. The birds",
	"sat in the trees and sang so sweetly that the children used to stop",
	"their games in order to listen to them. \"How happy we are here!\" they",
	"cried to each other.",
	
	
	"Rapunzel",
	"There once lived a man and his wife, who had long wished for a child,",
	"but in vain. Now there was at the back of their house a little window",
	"which over looked a beautiful garden full of the finest vegetables and",
	"flowers; but there was a high wall all round it, and no one ventured",
	"into it, for it belonged to a witch of great might, and of whom all",
	"the world was afraid. One day, when the wife was standing at the win-",
	"dow, and looking into the garden, she saw a bed filled with the finest",
	"rampion; and it looked so fresh and green that she began to wish for",
	"some; and at length she longed for it greatly.",
	
	
	"Narcissus",
	"Long, long ago, there lived in Greece a young boy named Narcissus.",
	"All day long he tended his sheep in the hills, and drove them from",
	"place to place to find the very best pasture.",
	"One day he came to a little stream and wanted to drink form it. The",
	"water was very clear and reflected everything that leaned over it.",
	"While Narcissus was waiting for the sheep to drink, he chanced to see",
	"his own face in the water.",
	"He had never seen his likeness before, and he was so pleased with the",
	"pretty picture that he looked at it for a long time.",
	
	
	"Gulliver's Travels",
	"I am Lemuel Gulliver. I was born in England. When I grew up, I had a",
	"great wish to go round the world, and in the year 1699, I got on board",
	"a ship bound for the South Seas. For some time things went all right.",
	"But one day a great storm drove us far to the south and at last the",
	"whip ran on a rock and split her bow. We let down a boat and left the",
	"wreck. But a big wave sank the boat, and I lost sight of my poor",
	"friends.",
	"I swam on and on, and just when I felt I must give myself up, I found",
	"I could touch the sand. I was now safe."};
	char print_1st[5][N];
	char print_2nd[5][N];
	char check_1st[5][N];
	char check_2nd[5][N];
	int choose1, k=0, i=0, n=0,x=0,j=0, ni, good=0, bad=0, line_count,input,choose,key=1;
	double current=0.0, correct=0.0, timer;
	time_t start, end;
	srand(time(NULL));
	choose=((rand()%40)/10)*10;
	choose1=choose;
	for(k=0; k<2; k++) {//2개 나누기
		if(k==0) {
		start=time(NULL);
			for(i=0; i<5;i++){ 
				for(input=0; input<N; input++, choose=choose1) {
					system("clear");
					print_long(correct, current);
					for(j=0; j<5; j++,choose++) {
						strcpy(print_1st[j],article[choose]);
						printf("%s\n", print_1st[j]);
					};
					printf("\n");
					for(j=0; j<i; j++)
						printf("%s",check_1st[j]);
					printf("%s", check_1st[i]);									
						check_1st[i][input]=getch();
					if (check_1st[i][input]==print_1st[i][input])
						good++;
					if (check_1st[i][input]!=print_1st[i][input] && check_1st[i][input]!=127 && check_1st[i][input]!='\n')
						bad++;
					if (check_1st[i][input]=='\n') {
						check_1st[i][input]='\n';
						input=N;
						};
					if (check_1st[i][input]==127) {
							if (check_1st[i][input-1]==print_1st[i][input-1])
								good--;
							if (check_1st[i][input-1]!=print_1st[i][input-1])
								bad--;
							check_1st[i][input-1]=check_1st[i][input]='\0';
							input=input-2;
									};
		end = time(NULL);
					if (check_1st[i][input]==27) {
						printf("\n\n긴 글 연습을 종료합니다. \n\n");
						sleep(1);
						return 0;
								};
					correct=(double)good/(good+bad)*100;
					current=(double)good/(end-start)*60;
				};
			};
		};
		if(k==1) {
		start=time(NULL);
			for(i=0; i<5;i++){ 
				for(input=0; input<N; input++, choose=choose1) {
					system("clear");
					print_long(correct, current);
					for(j=0; j<5; j++,choose++) {
						strcpy(print_2nd[j],article[choose+5]);
						printf("%s\n", print_2nd[j]);
					};
					printf("\n");
					for(j=0; j<i; j++)
						printf("%s",check_2nd[j]);
					printf("%s", check_2nd[i]);									
						check_2nd[i][input]=getch();
					if (check_2nd[i][input]==print_2nd[i][input])
						good++;
					if (check_2nd[i][input]!=print_2nd[i][input] && check_2nd[i][input]!=127 && check_2nd[i][input]!='\n')
						bad++;
					if (check_2nd[i][input]=='\n') {
						check_2nd[i][input]='\n';
						input=N;
						};
					if (check_2nd[i][input]==127) {
							if (check_2nd[i][input-1]==print_2nd[i][input-1])
								good--;
							if (check_2nd[i][input-1]!=print_2nd[i][input-1])
								bad--;
							check_2nd[i][input-1]=check_2nd[i][input]='\0';
							input=input-2;
									};
		end = time(NULL);
					if (check_2nd[i][input]==27) {
						printf("\n\n긴 글 연습을 종료합니다. \n\n");
						sleep(1);
						return 0;
								};
					correct=(double)good/(good+bad)*100;
					current=(double)good/(end-start)*60;
				};
			};
		};
	};
	return 0;
}
