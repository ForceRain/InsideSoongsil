#include <stdio.h>
#include <stdlib.h>
#include <termio.h>
#include <time.h>
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
int Start_program(void) {
	int p;
	printf(">>영문 타자 연습 프로그램<<\n");
	printf("1. 자리 연습      2. 낱말 연습\n");
	printf("3. 짧은 글 연습   4. 긴 글 연습\n");
	printf("5. 프로그램 종료 \n\n");
	printf("번호를 선택하세요 : ");
	scanf("%d",&p);
	getchar();
	printf("\n");
	return p;
}
void end_ceremony(void){
	int input;
	while(1) {
		sleep(1);
		printf("::엔터키를 입력하세요::");
		input=getchar();
		if(input=='\n') {
			system("clear");
			return ;
		};
	};
	return;

}
int Jari_practice(void) {
	int n, k, alphabet[52], z, Tal=1, in, count=1, process=0, wrong=0, right=0;
	double correct=0.0;
	srand(time(NULL));
	system("clear");
	for(n=0, k=65; n<26; n++, k++)
		alphabet[n]=k;
	for (n=0, k=97; n<26; n++, k++)
		alphabet[n+26]=k;
	for(count=1; count<21; Tal=1 ) {
		n=rand()%52;
		while(Tal) {
			printf(">>영문 타자 연습 프로그램 : 자리 연습 <<\n");
			printf("진행도 : %d%c 오타수 : %d 정확도 : %.0f%c \n\n\n\n", process,37, wrong, correct,37);
			z=alphabet[n];
			printf("%c\n", z);
			in=getch();
				if (z==in)
					{
					count++;
					right++;
					Tal=0;
					}
				else
					{
					count++;
					wrong++;
					Tal=1;
					}
				if(count==21)
					break;
				if(in==27)
					{printf("자리 연습을 종료합니다.\n");
					sleep(1);
					system("clear");
					return 1;
					};
			correct = ((double)right/(count-1))*100;
			system("clear");
					};
			process +=5;
	};
			printf("결과 :: 진행도 : %d%c 오타수 : %d 정확도 : %.0f%c \n\n\n\n", process,37, wrong, correct,37);
			end_ceremony();
	return 1;
}
int Word_practice(void) { 
 	char word[100][N]={"add", "salad", "as", "ad", "dad", "Dallas", "fall", "Alaska", "LA", "all"
						"ask", "from", "close", "make", "sure", "see", "old", "with", "lesson", "point",
						"elementary", "what", "new", "help", "listen", "grade", "age", "guess", "play", "here",
						"teacher", "many", "head", "happy", "country", "very", "warm", "like", "soccer", "nature",
						"name", "read", "meet", "year", "give", "ahead", "movie", "much", "address", "email",
						"get", "family", "song", "thank", "friend", "number", "class", "now", "pop", "member",
						"picture", "fat", "grandparent", "long", "painting", "cartoon", "online", "dog", "same", "dark",
						"different", "fashion", "star", "wavy", "welcome", "think", "curly", "engineer", "gather", "communication",
						"cute", "best", "diligent", "short", "cousin", "party", "small", "parent", "big", "live",
						"tall", "black", "twin", "find", "right", "live", "bank", "birthday", "blond", "sport"};
	char print[N]="";
	char check[N]="";
	int i,k,a, choose,input, process=0, wrong=0, right=0, escape_count=0;
	float correct=0.0;
	srand(time(NULL));
	system("clear");
	for(i=0; i<20;i++, right=0, wrong=0, escape_count=0) {
		char check[N]="";
		system("clear");
		choose = rand()%100;
		a=strlen(word[choose]);
		strcpy(print,word[choose]);
			for(k=0; k<N; k++) {
				system("clear");
				printf(">>영문 타자 연습 프로그램<<\n");
				printf("진행도 : %d%c  오타수 : %d  정확도 : %.0f%c \n\n", process, 37, wrong, correct, 37);
				printf("%s\n", print);
				check[k]=getchar();
					if(check[k]==print[k])
						right++;
					if(check[k]!=print[k] && check[k]!=127)
						wrong++;
					if (check[k]=='\n'){
						check[k]='0';
						break;};
					if (check[k]==127){
							if (check[k-1]==print[k-1])
									right--;
							if (check[k-1]!=print[k-1])
									wrong--;
							check[k-1]=check[k]='\0';
							k=k-2;
								};
					if (check[k]=='#' && check[k-1]=='#' && check[k-2]=='#')
						escape_count++;
					if (escape_count==1) {
						printf("\n\n 낱말 연습을 종료합니다. \n\n");
						while(1) {
							sleep(1);
							printf("::엔터키를 입력하세요::");
							input=getchar();
							if(input=='\n') {
							system("clear");
							return ;
							};
						};
						sleep(1);
						
						return 2;
					}
					};
				correct=(float)right/(right+wrong-1)*100;
				process+=5;
				};
		printf("\n\n결과 : %d%c  오타수 : %d  정확도 : %.0f%c \n\n", process, 37, wrong, correct, 37);
		end_ceremony();
	return 2;
}
			                                               
 void print_short(int process, double current, double best, double correct) {
	   	printf(">>영문 타자 연습 프로그램 : 짧은 글 연습 <<\n");
		printf("진행도 : %d%c  현재타수 : %.0lf  최고타수 : %.0lf 정확도 : %.0lf%c \n\n",process,37, current, best, correct,37);
	return;
	}

int Short_practice(void) {
	char sentence[30][N] = {"Courage is resistance to fear, mastery of fear - not absence of fear",
						"Poetry is the mother tongue of mankind",
						"Misery loves company",
						"Man is but a reed, the weaken in nature, but he is a thinking reed",
						"Who reflects too much will accomplish little",
						"Man should keep his friendship in constant repair",
						"The right people in the right jobs",
						"Time is the rider that breaks youth",
						"A deep distress hath humanized my soul",
						"Who reflects too much will accomplish little",
						"Nature does not proceed by leaps",
						"Man is a voluntary agent",
						"Men have no right to what is not reasonable",
						"Wealth brings with it many anxieties",
						"Being in a ship is being in a jail, with the chance of being drowned",
						"Life is a progress from want to want, not from enjoyment to enjoyment",
						"Life is half spent before we know what it is",
						"The first virtue of a painting is to be a feast for the eyes",
						"Love truth, but pardon error",
						"It is a wise father that knows his own child",
						"A man apt to promise is apt to forget",
						"We are not hypocrites in our sleep",
						"Genius does what it must, and talent does what it can",
						"The right people in the right jobs",
						"Experience is the only prophecy of wise men",
						"You never miss the water till the well runs dry",
						"A little learning is a dangerous thing",
						"No one can be more wise than destiny",
						"Old men are always young enough to learn, with profit",
						"Music has charms to soothe a savage breast"};
	char Check[N]="";
	char print[N]="";
	char back[1]={0};
	int command[2]={27,127};
	int i=0, n=0, good=0, bad=0, process=0, count, choose;
	double current=0.0, best=0.0, correct=0.0, timer;
	time_t start, end;
	srand(time(NULL));
	for(count=0; count<5; good=0, bad=0, n=0) {
		choose = rand()%30;
		strcpy(print,sentence[choose]);
		char Check[N]="";
		start = time(NULL);
				for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", print);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==print[i])
								good++;
							if(Check[i]!=print[i])
								bad++;
						}
						else {
								if (Check[i-1]==print[i-1])
									good--;
								if (Check[i-1]!=print[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 3;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
		
		count++;
		process +=20;
	};
	print_short(process, current, best, correct);
	end_ceremony();
	return 3;
}
void print_long(double correct, double current) {
	printf(">>영문 타자 연습 프로그램 : 긴 글 연습 <<\n");
 	printf("정확도 : %.0lf%c  현재타수 : %.0lf \n\n",correct,37,current);
	return ;
}

int Long_practice(void) {
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
		for(k=0; k<2; k++) {
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
								return 4;
								};
								correct=(double)good/(good+bad)*100;
								current=(double)good/(end-start)*60;
								                   					 };
							   	};
				  	}
			else if(k==1) {
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
									return 4;
									};
								correct=(double)good/(good+bad)*100;
								current=(double)good/(end-start)*60;
																	};
					              };
				   		};
			       };
				print_long(correct, current);
				end_ceremony();
		     return 4;
		 }

int main(void) {
	int k, input, key=1;
	while(1) {
		system("clear");
		input=Start_program();
		switch (input) {
		case 1 :
			Jari_practice();
			system("clear");
			break;
		case 2 :
			Word_practice();
			system("clear");
			break;
		case 3 :
			Short_practice();
			system("clear");
			break;
		case 4 :
			Long_practice();
			system("clear");
			break;
		case 5 : {
			printf("프로그램을 종료합니다.\n");
			system("clear");
			return 20132431;
				 };
			break;
		default :
			printf("잘못된 입력입니다.\n");
			break;
						};
				};
	return 0;
}



	
