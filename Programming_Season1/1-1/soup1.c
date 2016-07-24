#include <stdio.h>
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
	tcsetattr(0,TCSAFLUSH, &buf);
	ch=getchar();
	tcsetattr(0,TCSAFLUSH, &save);
	return ch;
}
void print_short(int process, double current, double best, double correct) {
			printf(">>영문 타자 연습 프로그램 : 짧은 글 연습 <<\n");
			printf("진행도 : %d%c  현재타수 : %.0lf  최고타수 : %.0lf 정확도 : %.0lf%c \n\n",process,37, current, best, correct,37);
	return;
}

int main(void) {
	char a[]="Save best for last.";
	char Check[N]="";
	char back[1]={0};
	int command[2]={27,127};
	int i=0,count,n=0;
	int good=0, bad=0, process=0;
	double current=0.0, best=0.0, correct=0.0;
	double timer;
	time_t start, end;
	for(count=0; count<5; count++, good=0, bad=0, n=0){
		char Check[N]="";
			start = time(NULL);
		for(i=0; i<N; i++) {
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
			if (Check[i]==command[0])
				return 0;
			if (Check[i]=='\n')
				break;
			timer =end - start;
			current = (double)(good/timer)*60;
			correct = (double)good/(good+bad)*100;
			if(best<current) 
				best=current;
		};
		printf("\n%d, %d\n",good, bad-1);
		process+=20;
	};
	return 0;
}






