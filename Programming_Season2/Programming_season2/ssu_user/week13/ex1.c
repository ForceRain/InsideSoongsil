#include <stdio.h>
#include <stdlib.h>
#define N 10
struct d_list{
	char data;
	struct d_list *prev;
	struct d_list *next;
}a;
typedef struct d_list ELEMENT;
typedef ELEMENT *LINK;
LINK string_to_list(char [],LINK);
void print_list(LINK,int);
int main(void)
{
	char input[N];
	LINK h=NULL;
	printf("문자열 입력 : ");
	scanf("%s",input);
	h=string_to_list(input,h);
	printf("변환 리스트 결과 : \n");
	print_list(h,0);
	return 0;
}
LINK string_to_list(char s[],LINK p)
{
	LINK head=NULL;
	if (p!=NULL)
		head=p;
	else
		head=malloc(sizeof(ELEMENT));
	if (s[0]=='\0')
		return NULL;
	else
	{
		head->data=s[0];
		head->next=malloc(sizeof(ELEMENT));
		head->next->prev=head->next;
		head->next=string_to_list(s+1,head->next);
		return head;
	}
}
void print_list(LINK head,int count)
{
	LINK p;
	p=head;
	int i=0,j;
	for (;head;head=head->next)
	{
		i++;
		printf("%c-->",head->data);
	}
	printf("-->NULL-->");
	for (j=0;j<i-1;j++,p=p->next);
	for (j=0;j<i-1;j++,p=p->prev)
	{
		printf("%c-->",p->data);
	}
	printf("-->NULL");
	return;
}
