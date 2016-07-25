#include <stdio.h>
unsigned packdata(unsigned id_num, unsigned job){
	unsigned person=0;
	person |= job;
	person |= id_num << 7;
	return person;
}
void print(unsigned person) {
	unsigned id_num, job;
	job = person & 0x7F;
	id_num = (person >> 7) & 0x2FFF;
	printf("ID : %u\n", id_num);
	printf("작업 형태 : %u\n", job);
}
int main(void) {
	unsigned in_ID, in_job, output;
	printf("ID값을 입력하세요 : ");
	scanf("%u", &in_ID);
	printf("작업형태를 입력하세요 : ");
	scanf("%u", &in_job);
	output=packdata(in_ID, in_job);
	print(output);
	return 0;
}
