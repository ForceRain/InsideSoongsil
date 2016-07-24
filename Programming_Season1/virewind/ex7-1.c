#include <stdio.h>
int main(void) {
	FILE *fp;
	char ch;
	int i=0;
	fp=fopen("my.txt", "r");
	if (fp==NULL) {
		printf("파일 개방 실패");
		return 0;
	}
	while (1) {
		ch=fgetc(fp);
		if (ch==EOF)
			break;
		printf("%c", ch);
		if (ch=='.') {
			printf("\n");
			i++;
		}
		if (i==3) {
			printf("\n");
			i=0;
		}
	}
	fclose(fp);
	return 0;
}
