#include <stdio.h>
struct aa{
	unsigned gogo:1, no:1, gogogo:1, aa:1;
};
int main(void)
{
	struct aa tt={0};
	printf("%d %d %d %d",tt.gogo, tt.no, tt.gogogo, tt.aa);
	return 0;
}
