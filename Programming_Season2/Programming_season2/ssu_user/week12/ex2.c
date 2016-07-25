#include <stdio.h>
#include <stdlib.h>
#define N 10
struct linked_list{
	char d;
	struct linked_list *next;
};
typedef struct linked_list ELEMENT;
typedef ELEMENT *LINK;
LINK string_to_list(char s[])
{
	int i=0;
	LINK head=NULL,p=NULL;
	p=malloc(sizeof(ELEMENT));
	for (;p;i++,p=p->next)
	{
		/*if (s[i]=='\0')
		{
			p=NULL;
			return head;
		} */
		p->d=s[i];
		if (i==0)
			head=p;
		p->next=malloc(sizeof(ELEMENT));
		if (s[i+1]=='\0')
		{
			p->next=NULL;
			break;
		}
	}
	return head;
}
void print_list(LINK head)
{
	if (head==NULL)
		printf("NULL\n");
	else
	{
		printf("%c--> ",head->d);
		print_list(head->next);
	}
}

int main(void)
{
	char input[N];
	LINK h;
	printf("문자열 입력 : ");
	scanf("%s",input);
	h=string_to_list(input);
	printf("변환 리스트 결과 : \n");
	print_list(h);
	return 0;
}
