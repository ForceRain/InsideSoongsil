#include <stdio.h>
#include <stdlib.h>
#include <termio.h>
#include <time.h>
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
	int n, k, alphabet[52], z, Tal=1, i, count=1, process=0, wrong=0, right=0;
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
			i=getch();
				if (z==i)
					{printf("Good\n");
					count++;
					right++;
					Tal=0;
					}
				else
					{printf("Bad\n");
					count++;
					wrong++;
					Tal=1;
					}
				if(count==21)
					break;
				if(i==27)
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
int Short_practice(void) {
	char a[N] = "Courage is resistance to fear, mastery of fear - not absence of fear";
	char b[N] = "Poetry is the mother tongue of mankind";
	char c[N] = "Misery loves company";
	char d[N] = "Man is but a reed, the weaken in nature, but he is a thinking reed";
	char e[N] = "Who reflects too much will accomplish little";
	char f[N] = "Man should keep his friendship in constant repair";
	char g[N] = "The right people in the right jobs";
	char h[N] = "Time is the rider that breaks youth";
	char i[N] = "A deep distress hath humanized my soul";
	char j[N] = "Who reflects too much will accomplish little";
	char k[N] = "Nature does not proceed by leaps";
	char l[N] = "Man is a voluntary agent";
	char m[N] = "Men have no right to what is not reasonable";
	char n[N] = "Wealth brings with it many anxieties";
	char o[N] = "Being in a ship is being in a jail, with the chance of being drowned";
	char p[N] = "Life is a progress from want to want, not from enjoyment to enjoyment";
	char q[N] = "Life is half spent before we know what it is";
	char r[N] = "The first virtue of a painting is to be a feast for the eyes";
	char s[N] = "Love truth, but pardon error";
	char t[N] = "It is a wise father that knows his own child";
	char u[N] = "A man apt to promise is apt to forget";
	char v[N] = "We are not hypocrites in our sleep";
	char w[N] = "Genius does what it must, and talent does what it can";
	char x[N] = "The right people in the right jobs";
	char y[N] = "Experience is the only prophecy of wise men";
	char z[N] = "You never miss the water till the well runs dry";
	char A[N] = "A little learning is a dangerous thing";
	char B[N] = "No one can be more wise than destiny";
	char C[N] = "Old men are always young enough to learn, with profit";
	char D[N] = "Music has charms to soothe a savage breast";
	char Check[N];
	int num, koin, zhl, input, count=0, process=0, wrong=0, right=0, choose;
	double correct=0.0;
	srand(time(NULL));
	while(count!=5) {
		choose = rand()%30;
		printf(">>영문 타자 연습 프로그램 : 짧은 글 연습 <<\n");
		printf("진행도 : %d%c 현재타수 : %c 최고타수 : %c 정확도 : %c\n", process, 37,37,37,37);
		switch(choose) {
			case 0 :
				{printf("%s\n", a);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (a[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 1 :
				{printf("%s\n", b);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (b[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 2 :
				{printf("%s\n", c);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (c[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 3 :
				{printf("%s\n", d);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (d[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 4 :
				{printf("%s\n", e);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (e[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 5 :
				{printf("%s\n", f);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (f[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 6 :
				{printf("%s\n", g);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (g[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 7 :
				{printf("%s\n", h);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (h[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 8 :
				{printf("%s\n", i);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (i[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 9 :
				{printf("%s\n", j);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (j[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 10 :
				{printf("%s\n", k);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (k[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 11 :
				{printf("%s\n", l);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (l[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 12 :
				{printf("%s\n", m);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (m[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 13 :
				{printf("%s\n", n);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (n[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 14 :
				{printf("%s\n", o);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (o[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 15 :
				{printf("%s\n", p);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (p[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 16 :
				{printf("%s\n", q);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (q[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 17 :
				{printf("%s\n", r);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (r[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 18 :
				{printf("%s\n", s);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (s[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 19 :
				{printf("%s\n", t);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (t[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 20 :
				{printf("%s\n", u);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (u[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 21 :
				{printf("%s\n", v);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (v[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 22 :
				{printf("%s\n", w);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (w[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 23 :
				{printf("%s\n", x);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (x[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 24 :
				{printf("%s\n", y);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (y[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 25 :
				{printf("%s\n", z);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (z[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 26 :
				{printf("%s\n", A);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (A[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 27 :
				{printf("%s\n", B);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (B[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 28 :
				{printf("%s\n", C);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (C[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			case 29 :
				{printf("%s\n", D);
					getchar();
					for(koin=0; koin<N; koin++) {
						input = getch();
						if (D[koin]==input)
							right++;
						else 
							wrong++;
						if (input==27)
							return 3;
						if (input=='\n')
							break;
					};
				};
				break;
			default :
				printf("잘못된 접근입니다.\n");
				break;
		}
		count++;
		process +=20;
	};
	return 3;
}

int main(void) {
	int k, input, key=1;
	while(key) {
		input=Start_program();
		switch (input) {
		case 1 :
			Jari_practice();
			key=1;
			break;
		case 3 :
			Short_practice();
			key=1;
			break;
		default :
			printf("프로그램을 종료합니다.\n");
			key = 0;
			break;
						};
				};
	return 0;
}



	
