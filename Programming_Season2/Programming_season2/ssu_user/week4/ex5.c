#include <stdio.h>
int main(void) {
	struct name_grade{
		char name[2];
		int grade;
	};
	struct name_grade a;
	printf("%d %d %d", sizeof(a.name), sizeof(a.grade), sizeof(struct name_grade));
	printf("%p %p", a.name, &a.grade);
	return 0;
}
