#include <stdio.h>
#include <termio.h>
int getch(void) {
	int ch;

	struct termios buf;
	struct termios save;
	tcgetattr(0,&save);
	buf=save;
	buf.c_lflag&=~(ICANON|ECHO);
	buf.c_cc[VMIN] = 1;
	buf.c_cc[VTIME] = 0;
	tcsetattr(0,TCSAFLUSH,&buf);
	ch = getchar();
	tcsetattr(0,TCSAFLUSH,&save);
	return ch;
}

int main(void) {
	int input_char,num=0, count=0;
	float freq;
	while (num!=10){
		printf("Type a letter : ");
		input_char = getch();
		if(input_char=='g')
			count++;
		if(input_char=='y')
			printf("You inputed Yes\n");
		else if(input_char=='q') {
			printf("Bye\n");
			break;
		}
		else
			printf("You inputed %c\n", input_char);
		num++;
	}
	printf("'g'입력 횟수 : %d번\n", count);
	freq=(float)count/10;
	printf("빈도 : %.1f\n", freq);
	return 0;
}



