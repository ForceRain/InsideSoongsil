#include <stdio.h>
inline float tri_area_f(float w, float h) {return w*h/2;}
#define TRI_AREA(w,h) ((w)*(h)/2)
int main(void) {
	float a;
	printf("By inline function\n");
	a=tri_area_f(1.0,1.0);
	printf("The area is %f\n", a);
	printf("By macro\n");
	a=TRI_AREA(1.0,1);
	printf("The area is %f\n", a);
	return 0;
}
