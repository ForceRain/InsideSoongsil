#include <stdio.h>
int main(void) {
	FILE *ifp;
	char temp;
	int a;
	ifp=fopen("a.txt", "r");
	fseek(ifp,0,SEEK_END);
	a=ftell(ifp);
	fseek(ifp,0,SEEK_SET);
	while (1) {
		fscanf(ifp,"%c",&temp);
		printf("%c", temp);
		if (ftell(ifp)==a)
			break;
	};
	fclose(ifp);
	return 0;
}
