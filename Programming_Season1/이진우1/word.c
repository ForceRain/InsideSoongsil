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

int main(void) {
	srand(time(NULL));
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
	int i,k,a, choose, process=0, wrong=0, right=0, escape_count=0;
	float correct=0.0;
	system("clear");
	for(i=0; i<20;i++, right=0, wrong=0, escape_count=0) {
		char check[N]="";
		system("clear");
		choose = rand()%100;
		a=strlen(word[choose]);
		strcpy(print,word[choose]);
			for(k=0; k<a+1; k++) {
				system("clear");
				printf(">>영문 타자 연습 프로그램<<\n");
				printf("진행도 : %d%c  오타수 : %d  정확도 : %.0f%c \n\n", process, 37, wrong, correct, 37);
				printf("%s\n", print);
				printf("%s", check);
				check[k]=getch();
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
				if (check[k]=='#')
					escape_count++;
				if (escape_count==3)
					return 0;
			};
			correct=(float)right/(right+wrong-1)*100;
			process+=5;
	};
	printf("\n\n결과 : %d%c  오타수 : %d  정확도 : %.0f%c \n\n", process, 37, wrong, correct, 37);
	return 0;
}
