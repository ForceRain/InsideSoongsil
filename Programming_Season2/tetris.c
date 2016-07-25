#include <stdio.h>
#define a LEFT
#define s DOWN
#define d RIGHT
#define w UP
void print_menu(void) {
			printf("\n   *****************************************************************************************************************\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *                                                                                                               *\n");
			printf("   *****************************************************************************************************************\n");
}
int main(void) {
	int input=0, out1=0;
	while(1) {
		{
			if (out1==EOF || input=='5') 
				return 0;
			else 
				print_menu();
			printf("실행할 메뉴를 선택하세요 : ");
			out1=scanf("%c",&input);
		}
	}
	return 0;
}
