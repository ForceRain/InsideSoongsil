#include <stdio.h>
union what{
	char no;
	short huh;
};
int main(void) {
	printf("%d", sizeof(union what));
	return 0;
}
