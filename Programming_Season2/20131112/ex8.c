#include <stdio.h>
struct list{
	int data;
	struct list *next;
};
int main(void) {
	struct list a = {1,NULL}, b={2,NULL}, c={3,NULL};
	a.next=&b;
	b.next=&c;
	c.next=&a;
	printf("a : %d, b: %d, c: %d\n",a.data, b.data, c.data);
	printf("a : %d, b(a.next->data) : %d, c(b.next->data) : %d\n",a.data,(a.next->data),(b.next->data));
	printf("a : %d, b(c.next->next->data) : %d, c(a.next->next->data) : %d\n",a.data,(c.next->next->data),(a.next->next->data));
	return 0;
}
