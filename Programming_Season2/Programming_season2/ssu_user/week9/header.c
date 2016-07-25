#include "headerfile.h"
#ifdef PI
	#undef PI
#endif
#define PI 3.141592
int main(void) {
	printf("%f", PI);
	return 0;
}
