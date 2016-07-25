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
	LINK head;
	printf("%s\n",__func__);
	if(s[0]=='\0')
		return NULL;
	else
	{
		head=malloc(sizeof(ELEMENT));
		head->d=s[0];
		head->next=string_to_list(s+1);
		return head;
	}
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
	printf("%s\n",__func__);
	LINK h;
	printf("문자열 입력 : ");
	scanf("%s",input);
	h=string_to_list(input);
	printf("변환 리스트 결과 : \n");
	print_list(h);
	return 0;
}
