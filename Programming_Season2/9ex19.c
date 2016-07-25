#include <stdio.h>
enum day {Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday};

int main(void) {
	enum day d=Sunday;
	printf("오늘은 %입니다.\n", d);
	return 0;
}
