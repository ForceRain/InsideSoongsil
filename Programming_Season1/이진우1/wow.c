#include <stdio.h>
#include <stdlib.h>
#include <termio.h>
#include <time.h>
#define N 20

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
int Word_practice(void) {
	int n, k, z, i, count=1, process=0, wrong=0, right=0;
	double correct=0.0;
	  
	srand(time(NULL));
	system("clear");
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
		case 2 :
			Word_practice();
			key=1;
			break;
		default :
			printf("프로그램을 종료합니다.\n");
			key = 0;
			break;
						}
				};
	return 0;
}



	
