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
void apple(int input_char) {
	printf("\n\n\n\n\n\n\n  입력번호 %d번 : 사과\n", input_char - '0');
	return;
}
void pineapple(int input_char) {
	printf("\n\n\n\n\n\n\n  입력번호 %d번 : 파인애플\n", input_char - '0');
	return;
}
void orange(int input_char) {
	printf("\n\n\n\n\n\n\n  입력번호 %d번 : 오렌지\n", input_char - '0');
	return;
}
void banana(int input_char) {
	printf("\n\n\n\n\n\n\n  입력번호 %d번 : 바나나\n", input_char - '0');
	return;
}
int end(int input_char, int check) {
	printf("\n\n\n\n\n\n\n  입력번호 %d번 : 끝\n", input_char - '0');
	check =0;
	return check;
}	
void fault(int input_char) {
	printf("\n\n\n\n\n\n\n  입력번호 %d번 : 입력오류\n", input_char - '0');
	return;
}
int main(void) {
	int input_char;
	int check=1;
	system("clear");
	while(check) {
		printf("****************************************\n");
		printf("*         M E N U                       *\n");
		printf("* 1. Apple          2. Pineapple       *\n");
		printf("* 3. Orange         4. Banana          *\n");
		printf("* 5. End                               *\n");
		printf("*                                      *\n");
		printf("****************************************\n");
		printf("Select a menu : ");
		input_char = getch();
		putchar(input_char);
		system("clear");
		switch(input_char) {
			case '1' :
				apple(input_char);
				break;
			case '2' :
				pineapple(input_char);
				break;
			case '3' :
				orange(input_char);
				break;
			case '4' :
				banana(input_char);
				break;
			case '5' :
				check = end(input_char,check);
				break;
			default :
				fault(input_char);
				break;
		}
		printf("\n아무 키나 입력하세요 : ");
		getch();
		system("clear");
	}
	return 0;
}

					    

