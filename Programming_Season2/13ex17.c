#include <stdio.h>
#include <stdlib.h>
#include <string.h>
struct linked_list{
	int num;
	char name[7];
	struct linked_list *next;
};
typedef struct linked_list ELEMENT;
typedef ELEMENT *LINK;
LINK string_to_list(char s[])
{
	LINK head=NULL;
	if (s[0]=='\0')
		return NULL;
	else
	{
		head=malloc(sizeof(ELEMENT));
		head->name=s[0];
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
		printf("%c ", head->d);
		print_list(head->next);
	}
}
void concatenate(LINK h1,LINK h2)
{
	if (h1->next==NULL)
	{
		h1->next=h2;
		return;
	}
	else
	{
		concatenate(h1->next,h2);
	}
}
void disconnect(LINK h1, LINK h2,int n) {
	int i=0;
	if (h1->next!=NULL && n==1)
		h1->next=NULL;
	else
	{
		disconnect(h1->next,h2,n-1);
	}
	return;
}
int main(void) {
	char input1[]="first", input2[]="second", input3[]="third";
	LINK h1,h2,h3;
	h1=string_to_list(input1);
	h2=string_to_list(input2);
	h3=string_to_list(input3);
	concatenate(h1,h2);
	printf("연결 리스트 결과 : \n");
	print_list(h1);
	printf("\n");
	disconnect(h1,h2,strlen(input1));
	printf("연결 리스트 결과 : \n");
	concatenate(h1,h3);
	concatenate(h3,h2);
	print_list(h1);
	printf("\n");
		
	return 0;
}
