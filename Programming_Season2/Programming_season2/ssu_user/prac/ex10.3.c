#include <stdio.h>

typedef struct {
	unsigned b0 : 8, b1:8, b2:8, b3:8;
} word_bytes;

typedef struct {
	unsigned
		b0 : 1, b1 : 1, b2 : 1, b3: 1, b4 : 1, b5 : 1, b6 : 1, b7 : 1, b8 : 1, b9 : 1, b10 : 1, b11 : 1, b12 : 1, b13 : 1, b14 : 1, b15 : 1, b16 : 1, b17 : 1, b18 : 1, b19 : 1, b20 : 1, b21 : 1, b22 : 1, b23 : 1, b24 : 1, b25 : 1, b26 : 1, b27 : 1, b28 : 1, b29 : 1, b30 : 1, b31 : 1;
} word_bits;

typedef union {
	int i;
	word_bytes byte;
	word_bits bit;
}word;
void bit_print (int);
void bit_print(int a) {
	int i;
	int n=sizeof(int)*8;
	int mask=1<<(n-1);
	for(i=1; i<=n; i++) {
		putchar(((a&mask)==0)? '0' : '1');
		a<<=1;
		if(i%8==0 && i<n)
			putchar(' ');
	}
}

int main(void) {
	word w={0};
	w.bit.b8=1;
	w.byte.b0='a';
	printf("w.i = %d\n", w.i);
	bit_print(w.i);
	return 0;
}
