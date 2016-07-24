#include <stdio.h>
#include <stdlib.h>
#include <termio.h>
#include <time.h>
#include <string.h>
#define N 100

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
	printf(">>영문 타자 연습 프로그램<<\n");																		//메뉴화면 출력
	printf("1. 자리 연습      2. 낱말 연습\n");																
	printf("3. 짧은 글 연습   4. 긴 글 연습\n");
	printf("5. 프로그램 종료 \n\n");
	printf("번호를 선택하세요 : ");
	scanf("%d",&p);
	getchar();
	printf("\n");
	return p;
}

int Jari_practice(void) {																							//자리연습
	int n, k, alphabet[52], z, Tal=1, in, count=0, process=0, wrong=0, right=0, escape;
	double correct=0.0;
	srand(time(NULL));
	system("clear");
	for(n=0, k=65; n<26; n++, k++)																					//알파벳의 아스키 코드를 배열에 입력
		alphabet[n]=k;
	for (n=0, k=97; n<26; n++, k++)
		alphabet[n+26]=k;
	for(count=0; count<20; Tal=1 ) {																				//20번 수행
		n=rand()%52;
		while(Tal) {																								//Tal이 0이 될때까지 계속 반복
			printf(">>영문 타자 연습 프로그램 : 자리 연습 <<\n");
			printf("진행도 : %d%c 오타수 : %d 정확도 : %.0f%c \n\n\n\n", process,37, wrong, correct,37);
			z=alphabet[n];																							//난수 생성기 이용, alphabet배열에서 골라서 z에 저장
			printf("%c\n", z);
			in=getch();																								//getch함수이용, in에 입력
				if (z==in)																							//만약 z와 in이 같다면 count++, right++을 수행후 Tal에 0을주어 while문 탈출.
					{
					count++;
					right++;
					Tal=0;
					}
				else																								//다를 경우 진행도는 그대로, count는 올라가고 Tal에 1을 주어 while문 반복.
					{
					count++;
					wrong++;
					Tal=1;
					}
				if(count==20)
					break;
				if(in==27)
					{printf("자리 연습을 종료합니다.\n");														//아스키 코드 27번이 esc이므로 in이 esc라면 바로 프로그램을 종료한다.
					sleep(1);
					system("clear");
					return 5;
					};
			correct = ((double)right/count)*100;																//정확도 계산.	
			system("clear");
					};
			process +=5;
	};
			printf("결과 :: 진행도 : %d%c 오타수 : %d 정확도 : %.0f%c \n\n\n\n", process,37, wrong, correct,37);	//결과 출력.
	sleep(3);
	while (1)
	{printf("::enter키를 입력하세요::");
		escape=getchar();
		if(escape=='\n')
		{system("clear");
		return 5;
		};
	};
	return 0;
}
int Word_practice(void) {
  	int tal,check=0, wrong=0, count=0,correct=0;
	float m=0.0;
	int i,j;
	char input_words[14];
	char words[100][14]={"add","salad","as","ad","dad","Dallas","fall","Alaska","LA","all","ask","from","close","make","sure","see","old","with","lesson","point","elementary","what","new","help","listen","grade","age","guess", "play", "here", "teacher", "many", "head", "happy", "country", "very", "warm", "like", "soccer", "nature", "name", "read", "meet", "year", "give", "ahead", "movie", "much", "address", "email", "get", "family", "song", "thank", "friend", "number", "class", "now", "pop", "member", "picture", "fat", "grandparent", "long","painting", "cartoon", "online", "dog", "same", "dark", "different", "fashion", "star", "wavy", "welcome", "think", "curly", "engineer", "gather", "communication", "cute", "best", "diligent", "short", "cusin", "party", "small", "parent", "big", "live", "tall", "black", "twin", "find", "right", "live", "bank", "birthday", "blond", "sport"};
		   
	for(j=0; j<20; j++)
		{
		char escape[14];
		system("clear");
		srand(time(NULL));
		int a=rand()%100,c=0,k=0;
		printf(">> 영문 타자 연습 프로그램 : 낱말 연습 <<\n");
		printf("진행도 : %d%%   오타수 : %d   정확도 : %.0f%%\n",count,wrong,m);
						   
		for(i=0; i<14; i++)
			{
			printf("%c",words[a][i]);
									   
			if(words[a][i]=='a'||words[a][i]=='b'||words[a][i]=='c'||words[a][i]=='d'||words    [a][i]=='e'||words[a][i]=='f'||words[a][i]=='g'||words[a][i]=='h'||words[a][i]=='i'||words[a    ][i]=='j'||words[a][i]=='k'||words[a][i]=='l'||words[a][i]=='m'||words[a][i]=='n'||words[a][    i]=='o'||words[a][i]=='p'||words[a][i]=='q'||words[a][i]=='r'||words[a][i]=='s'||words[a][i]    =='t'||words[a][i]=='u'||words[a][i]=='v'||words[a][i]=='w'||words[a][i]=='x'||words[a][i]==    'y'||words[a][i]=='z'||words[a][i]=='A'||words[a][i]=='D'||words[a][i]=='L')
											                 k++;
									    
				}
						    
		printf("\n");
							 
		for(check=0,i=0; i<14; i++)
			{
				c=getchar();
				escape[i]=c;						 
			if(c=='\n')
				break;
									
			if(c==words[a][i])
				check++;
			if(escape[i-2]=='#' && escape[i-1]=='#' && escape[i]=='#')
				{printf("\n\n낱말 연습을 종료합니다.\n\n");
				sleep(1);
				return 0;
				};
			}
							  
		if(check==k)
			correct++;
		else
			wrong++;
						
		count+=5;
								 
									  
		system("clear");
								  
		m=(float)correct/(correct+wrong)*100;
			}
		printf("\n\n낱말 연습을 종료합니다.\n\n");
		printf("결과 :: 진행도 : %d%%   오타수 : %d   정확도 : %.0f%%\n",count,wrong,m);
		sleep(1);
		while(1) {
			printf("\n\n::enter키를 입력하세요.::");
			tal=getchar();
			if(tal=='\n')
				return 0;
		};
		return 0;
}
 void print_short(int process, double current, double best, double correct) {							//짧은 글 출력.
	   	printf(">>영문 타자 연습 프로그램 : 짧은 글 연습 <<\n");
		printf("진행도 : %d%c  현재타수 : %.0lf  최고타수 : %.0lf 정확도 : %.0lf%c \n\n",process,37, current, best, correct,37);
	return;
	}

int Short_practice(void) {
	char sentence[30][N] = {"Courage is resistance to fear, mastery of fear - not absence of fear", 					//배열 초기화.
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
	int command[2]={27,127};																//esc, backspace의 아스키 코드값 입력.
	int escape,i=0, n=0, good=0, bad=0, process=0, count, choose, right=0, right_sentence=0;
	double current=0.0, best=0.0, correct=0.0, timer;
	time_t start, end;																		//time함수 이용.
	srand(time(NULL));
	for(count=0; count<5; good=0, bad=0, n=0) {												//5개 반복.
		choose = rand()%30;																	//무작위로 30개중 하나 선택.
		strcpy(print,sentence[choose]);														//sentence배열에서 하나 고른것을 print배열로 복사(2차원 배열에 있던것을 1차원 배열에 입력시켜서 비교)
		right=strlen(sentence[choose]);														//고른 문장의 문자갯수를 right에 입력시킴.
		char Check[N]="";
		start = time(NULL);
				for(i=0; i<N; i++) {														//최대 N개라고 가정하고 입력받는 for문을 실행.
						system("clear");
						print_short(process, current, best, correct);						//출력
						printf("%s\n", print);
						printf("%s", Check);												//getch함수 사용시 문자를 나타내기 위해 입력받은 Check배열을 출력시킨다.
						Check[i]=getch();
						if(Check[i]!=command[1]) {											
							if(Check[i]==print[i])
								good++;
							if(Check[i]!=print[i])
								bad++;
						}
						else 
							{																//만약 입력받은 것이 backspace면 앞에 있는 문자가 맞다면 good--를, 틀리다면 bad--를 실행
								if (Check[i-1]==print[i-1])
									good--;
								if (Check[i-1]!=print[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];									//출력을 위해 i-1번째, i번째에 널문자를 입력함.
							i=i-2;															//i-1번째로 가기위해서는 for문돌때 i++이 되므로 i-2를 i에 입력시킨다.
							};
		end = time(NULL);
						if (Check[i]==command[0]) {											//esc를 입력했을 경우 종료시킨다.
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;															//개행문자 입력시 break이용, for문 빠져나옴.
						timer =end - start;													//마침시간-시작시간을 이용
						current = (double)(good/timer)*60;									//입력받는 시간을 이용, 현재타수를 구함.
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;													//현재타수중에 가장 높은 타수를 best문자에 입력시킴.
						};
		if (right==good)																	//right과 good이 같으면 문장을 맞게 입력했으므로 right_sentence++.
			right_sentence++;
		count++;
		process +=20;
	};
	printf("\n\n결과 :: 진행도 : %d%c 맞은 문장수 : %d 틀린 문장수 : %d 최고타수 : %.0lf \n",process,37,right_sentence, 5-right_sentence, best);
	sleep(1);
	printf("\n짧은 글 연습을 종료합니다.\n");
	sleep(1);
	while(1) {
		printf("::enter키를 입력하세요::");
		escape=getchar();
		if(escape=='\n')
			{system("clear");
			return 3;
			};
	};
	return 3;
}
int Long_practice(void) {
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
	char inpu[5][100];
	char inpu1[5][100];
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
		inpu[k][j]=getch();
					
		if(inpu[k][j]==27)
			return 0;
						 
		system("clear");
   						
		if(inpu[k][j]=='\n')
			{
				k++;
				j=-1;
			}
		else
			{
								 
			if(inpu[k][j]==127&&j>0)
				{
				if(inpu[k][j-1]==writing[a][k][j-1])
					correct--;
				else
					wrong--;
							
					inpu[k][j]='\0';
					inpu[k][j-1]='\0';
								
					j-=2;
				}
							
			else
				{
								
				if(inpu[k][j]==writing[a][k][j])
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
			printf("%s",inpu[l]);
		}
	}				 
	for(j=0;j<100;j++)
		{
		inpu1[k][j]=getch();
						 
		if(inpu1[k][j]==27)
			return 0;
						 
		system("clear");
						 
		if(inpu1[k][j]=='\n')
			{
				k++;
				j=-1;
			}
						 
		else
			{
								 
			if(inpu1[k][j]==127&&j>0)
				{
				if(inpu1[k][j-1]==writing[a][k][j-1])
					correct--;
				else
					wrong--;
										 
				inpu1[k][j]='\0';
				inpu1[k][j-1]='\0';
										 
				j-=2;
				}
								 
			else
				{
										 
				if(inpu1[k][j]==writing[a][k][j])
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
			printf("%s",inpu1[l]);
								 
		printf("결과 : 정확도 : %.0f, 현재타수 : %.0f\n",m,now);
								
		printf("\nenter키를입력하면 메뉴로 복귀합니다");
								 
		e=getchar();
	
		if(e=='\n')
			return 0;
								 
		}
	else
		{
		for(i=0; i<5; i++)
			printf("%s\n", writing[a][i]);

		printf("\n");

		for(l=0; l<=k; l++)
			printf("%s", inpu1[l]);
		}
	}		
	return 0;
}
int main(void) {																			//메인 함수에는 함수 호출만 한다.
	int k, input, key=1;
	while(key) {																			//while문을 이용해서 계속 반복되도록 만듦.
		system("clear");
		input=Start_program();
		switch (input) {
		case 1 :
			Jari_practice();
			key=1;
			system("clear");
			break;
		case 2 :
			Word_practice();
			key=1;
			system("clear");
			break;
		case 3 :
			Short_practice();
			key=1;
			system("clear");
			break;
		case 4 :
			Long_practice();
			key=1;
			system("clear");
			break;
		case 5 :
			{printf("프로그램을 종료합니다.\n");
			sleep(1);
			system("clear");
			return 0;};
			break;
		default :
			printf("\n잘못된 입력입니다. 다시 입력 하세요.\n");
			sleep(1);
			system("clear");
			key = 1;
			break;
						};
				};
	return 0;
}



	
