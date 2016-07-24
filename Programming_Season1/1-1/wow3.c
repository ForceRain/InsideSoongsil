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
					return 5;
					};
			correct = ((double)right/(count-1))*100;
			system("clear");
					};
			process +=5;
	};
			printf("결과 :: 진행도 : %d%c 오타수 : %d 정확도 : %.0f%c \n\n\n\n", process,37, wrong, correct,37);
	sleep(3);
	system("clear");
	return 5;
}

 void print_short(int process, double current, double best, double correct) {
	   	printf(">>영문 타자 연습 프로그램 : 짧은 글 연습 <<\n");
		printf("진행도 : %d%c  현재타수 : %.0lf  최고타수 : %.0lf 정확도 : %.0lf%c \n\n",process,37, current, best, correct,37);
	return;
	}

int Short_practice(void) {
	char a[] = "Courage is resistance to fear, mastery of fear - not absence of fear";
	char b[] = "Poetry is the mother tongue of mankind";
	char c[] = "Misery loves company";
	char d[] = "Man is but a reed, the weaken in nature, but he is a thinking reed";
	char e[] = "Who reflects too much will accomplish little";
	char f[] = "Man should keep his friendship in constant repair";
	char g[] = "The right people in the right jobs";
	char h[] = "Time is the rider that breaks youth";
	char in[] = "A deep distress hath humanized my soul";
	char j[] = "Who reflects too much will accomplish little";
	char k[] = "Nature does not proceed by leaps";
	char l[] = "Man is a voluntary agent";
	char m[] = "Men have no right to what is not reasonable";
	char nn[] = "Wealth brings with it many anxieties";
	char o[] = "Being in a ship is being in a jail, with the chance of being drowned";
	char p[] = "Life is a progress from want to want, not from enjoyment to enjoyment";
	char q[] = "Life is half spent before we know what it is";
	char r[] = "The first virtue of a painting is to be a feast for the eyes";
	char s[] = "Love truth, but pardon error";
	char t[] = "It is a wise father that knows his own child";
	char u[] = "A man apt to promise is apt to forget";
	char v[] = "We are not hypocrites in our sleep";
	char w[] = "Genius does what it must, and talent does what it can";
	char x[] = "The right people in the right jobs";
	char y[] = "Experience is the only prophecy of wise men";
	char z[] = "You never miss the water till the well runs dry";
	char A[] = "A little learning is a dangerous thing";
	char B[] = "No one can be more wise than destiny";
	char C[] = "Old men are always young enough to learn, with profit";
	char D[] = "Music has charms to soothe a savage breast";
	char Check[N]="";
	char back[1]={0};
	int command[2]={27,127};
	int i=0, n=0, good=0, bad=0, process=0, count, choose;
	double current=0.0, best=0.0, correct=0.0, timer;
	time_t start, end;
	srand(time(NULL));
	for(count=0; count<5; count++, good=0, bad=0, n=0) {
		choose = rand()%30;
		char Check[N]="";
		start = time(NULL);
		switch(choose) {
			case 0 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", a);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==a[i])
								good++;
							if(Check[i]!=a[i])
								bad++;
						}
						else {
								if (Check[i-1]==a[i-1])
									good--;
								if (Check[i-1]!=a[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 1 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", b);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==b[i])
								good++;
							if(Check[i]!=b[i])
								bad++;
						}
						else {
								if (Check[i-1]==b[i-1])
									good--;
								if (Check[i-1]!=b[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 2 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", c);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==c[i])
								good++;
							if(Check[i]!=c[i])
								bad++;
						}
						else {
								if (Check[i-1]==c[i-1])
									good--;
								if (Check[i-1]!=c[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 3 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", d);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==d[i])
								good++;
							if(Check[i]!=d[i])
								bad++;
						}
						else {
								if (Check[i-1]==d[i-1])
									good--;
								if (Check[i-1]!=d[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 4 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", e);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==e[i])
								good++;
							if(Check[i]!=e[i])
								bad++;
						}
						else {
								if (Check[i-1]==e[i-1])
									good--;
								if (Check[i-1]!=e[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 5 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", f);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==f[i])
								good++;
							if(Check[i]!=f[i])
								bad++;
						}
						else {
								if (Check[i-1]==f[i-1])
									good--;
								if (Check[i-1]!=f[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 6 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", g);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==g[i])
								good++;
							if(Check[i]!=g[i])
								bad++;
						}
						else {
								if (Check[i-1]==g[i-1])
									good--;
								if (Check[i-1]!=g[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 7 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", h);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==h[i])
								good++;
							if(Check[i]!=h[i])
								bad++;
						}
						else {
								if (Check[i-1]==h[i-1])
									good--;
								if (Check[i-1]!=h[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 8 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", in);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==in[i])
								good++;
							if(Check[i]!=in[i])
								bad++;
						}
						else {
								if (Check[i-1]==in[i-1])
									good--;
								if (Check[i-1]!=in[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 9 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", j);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==j[i])
								good++;
							if(Check[i]!=j[i])
								bad++;
						}
						else {
								if (Check[i-1]==j[i-1])
									good--;
								if (Check[i-1]!=j[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 10 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", k);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==k[i])
								good++;
							if(Check[i]!=k[i])
								bad++;
						}
						else {
								if (Check[i-1]==k[i-1])
									good--;
								if (Check[i-1]!=k[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 11 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", l);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==l[i])
								good++;
							if(Check[i]!=l[i])
								bad++;
						}
						else {
								if (Check[i-1]==l[i-1])
									good--;
								if (Check[i-1]!=l[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 12 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", m);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==m[i])
								good++;
							if(Check[i]!=m[i])
								bad++;
						}
						else {
								if (Check[i-1]==m[i-1])
									good--;
								if (Check[i-1]!=m[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 13 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", nn);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==nn[i])
								good++;
							if(Check[i]!=nn[i])
								bad++;
						}
						else {
								if (Check[i-1]==nn[i-1])
									good--;
								if (Check[i-1]!=nn[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 14 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", o);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==o[i])
								good++;
							if(Check[i]!=o[i])
								bad++;
						}
						else {
								if (Check[i-1]==o[i-1])
									good--;
								if (Check[i-1]!=o[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 15 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", p);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==p[i])
								good++;
							if(Check[i]!=p[i])
								bad++;
						}
						else {
								if (Check[i-1]==p[i-1])
									good--;
								if (Check[i-1]!=p[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 16 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", q);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==q[i])
								good++;
							if(Check[i]!=q[i])
								bad++;
						}
						else {
								if (Check[i-1]==q[i-1])
									good--;
								if (Check[i-1]!=q[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 17 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", r);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==r[i])
								good++;
							if(Check[i]!=r[i])
								bad++;
						}
						else {
								if (Check[i-1]==r[i-1])
									good--;
								if (Check[i-1]!=r[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 18 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", s);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==s[i])
								good++;
							if(Check[i]!=s[i])
								bad++;
						}
						else {
								if (Check[i-1]==s[i-1])
									good--;
								if (Check[i-1]!=s[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 19 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", t);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==t[i])
								good++;
							if(Check[i]!=t[i])
								bad++;
						}
						else {
								if (Check[i-1]==t[i-1])
									good--;
								if (Check[i-1]!=t[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 20 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", u);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==u[i])
								good++;
							if(Check[i]!=u[i])
								bad++;
						}
						else {
								if (Check[i-1]==u[i-1])
									good--;
								if (Check[i-1]!=u[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 21 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", v);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==v[i])
								good++;
							if(Check[i]!=v[i])
								bad++;
						}
						else {
								if (Check[i-1]==v[i-1])
									good--;
								if (Check[i-1]!=v[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 22 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", w);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==w[i])
								good++;
							if(Check[i]!=w[i])
								bad++;
						}
						else {
								if (Check[i-1]==w[i-1])
									good--;
								if (Check[i-1]!=w[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 23 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", x);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==x[i])
								good++;
							if(Check[i]!=x[i])
								bad++;
						}
						else {
								if (Check[i-1]==x[i-1])
									good--;
								if (Check[i-1]!=x[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 24 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", y);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==y[i])
								good++;
							if(Check[i]!=y[i])
								bad++;
						}
						else {
								if (Check[i-1]==y[i-1])
									good--;
								if (Check[i-1]!=y[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 25 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", z);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==z[i])
								good++;
							if(Check[i]!=z[i])
								bad++;
						}
						else {
								if (Check[i-1]==z[i-1])
									good--;
								if (Check[i-1]!=z[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 26 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", A);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==A[i])
								good++;
							if(Check[i]!=A[i])
								bad++;
						}
						else {
								if (Check[i-1]==A[i-1])
									good--;
								if (Check[i-1]!=A[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 27 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", B);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==B[i])
								good++;
							if(Check[i]!=B[i])
								bad++;
						}
						else {
								if (Check[i-1]==B[i-1])
									good--;
								if (Check[i-1]!=B[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 28 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", C);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==C[i])
								good++;
							if(Check[i]!=C[i])
								bad++;
						}
						else {
								if (Check[i-1]==C[i-1])
									good--;
								if (Check[i-1]!=C[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			case 29 :
				{for(i=0; i<N; i++) {
						system("clear");
						print_short(process, current, best, correct);
						printf("%s\n", D);
						printf("%s", Check);
						Check[i]=getch();
						if(Check[i]!=command[1]) {
							if(Check[i]==D[i])
								good++;
							if(Check[i]!=D[i])
								bad++;
						}
						else {
								if (Check[i-1]==D[i-1])
									good--;
								if (Check[i-1]!=D[i-1])
									bad--;
							Check[i-1]=Check[i]=back[0];
							i=i-2;
							};
						end = time(NULL);
						if (Check[i]==command[0]) {
							printf("\n짧은 글 연습을 종료합니다.\n");
							sleep(1);
							return 0;
						};
						if (Check[i]=='\n')
							break;
						timer =end - start;
						current = (double)(good/timer)*60;
						correct = (double)good/(good+bad)*100;
						if(best<current)
							best=current;
						};
				}
				break;
			default :
				printf("잘못된 접근입니다.\n");
				break;
		}
		count++;
		process +=20;
	};
	printf("\n짧은 글 연습을 종료합니다.\n");
	sleep(1);
	system("clear");
	return 3;
}

int main(void) {
	int k, input, key=1;
	while(key) {
		system("clear");
		input=Start_program();
		switch (input) {
		case 1 :
			Jari_practice();
			key=1;
			system("clear");
			break;
		case 3 :
			Short_practice();
			key=1;
			system("clear");
			break;
		default :
			printf("프로그램을 종료합니다.\n");
			key = 0;
			break;
						};
				};
	return 0;
}



	
