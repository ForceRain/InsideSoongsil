#include <stdio.h>
int main(void) {
	int aa, a, b, c, d, e, f, number, one_number=1;
	static int g=0;
	for (number=1; one_number<=1000000; one_number++) {
		number=one_number;
		if(number>=1000000) {
			aa=number/1000000;
			number=number%1000000;
		};
		if(number>=100000)
			{a=number/100000;
			number=number%100000;
			};
		if(number>=10000) {
			b=number/10000;
			number=number%10000;
		};
		if(number>=1000) {
			c=number/1000;
			number=number%1000;
		};
		if(number>=100) {
			d=number/100;
			number=number%100;
		};
		if(number>=10) {
			e=number/10;
			number=number%10;
		};
		f=number;
		if (aa==1)
			g++;
		if (a==1)
			g++;
		if (b==1)
			g++;
		if (c==1)
			g++;
		if (d==1)
			g++;
		if (e==1)
			g++;
		if (f==1)
			g++;
	};
	printf("1부터 1000000까지의 1의 갯수 : %d개\n",g);
	return 0;

}

