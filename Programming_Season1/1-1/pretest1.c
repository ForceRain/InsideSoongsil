#include <stdio.h>
int main(void) {
	int input_char;

	while(1) {
		printf("Type a letter : ");
		input_char = getchar();
		if (input_char=='y')
			printf("You inputed Yes\n");
		else if (input_char=='q') {
			printf("Bye\n");
			break;
		}
		else
			printf("You inputed %c\n",input_char);
	}
	return 0;
}

