#include <stdio.h>
#include <string.h>
typedef struct name_grade{
	char name[10];
	int id;
	int grade;
}name_grade;

int grade_sort(const void *p, const void *q) {
	if ((*(name_grade *)p).grade > (*(name_grade *)q).grade)
		return 1;
	else if ((*(name_grade *)p).grade < (*(name_grade *)q).grade)
		return -1;
	return 0;
}
int name_sort(const void *p, const void *q) {
	return strcmp((*(name_grade *)p).name, (*(name_grade *)q).name);
}

int main() {
	int i;
	name_grade stu[6]={{"kim", 1, 30}, {"choi", 3, 29}, {"lee", 9, 50}, {"park", 6, 80}, {"cho", 4, 69}, {"han", 10, 60}};

	for (i=0; i<6; i++) 
		printf("%d %s %d\n", stu[i].id, stu[i].name, stu[i].grade);

	printf("\n");															//초기화 출력
	qsort(stu,6,sizeof(name_grade),grade_sort);
	for (i=0; i<6; i++) 
		printf("%d %s %d\n", stu[i].id, stu[i].name, stu[i].grade);
																			//점수 정렬 후 출력
	printf("\n");
	qsort(stu,6,sizeof(name_grade),name_sort);
	for (i=0; i<6; i++) 
		printf("%d %s %d\n", stu[i].id, stu[i].name, stu[i].grade);
																			//이름 정렬 후 출력
	return 0;
}
