#include<stdio.h>
#include<stdlib.h>
#include<time.h>

int word_test();
int check=0, correct=0, wrong=0, count=0;
int i,j;

char input_words[14];
char words[100][14]={"add","salad","as","ad","dad","Dallas","fall","Alaska","LA","all","ask","from","close","make","sure","see","old","with","lesson","point","elementary","what","new","help","listen","grade","age","guess", "play", "here", "teacher", "many", "head", "happy", "country", "very", "warm", "like", "soccer", "nature", "name", "read", "meet", "year", "give", "ahead", "movie", "much", "address", "email", "get", "family", "song", "thank", "friend", "number", "class", "now", "pop", "member", "picture", "fat", "grandparent", "long", "painting", "cartoon", "online", "dog", "same", "dark", "different", "fashion", "star", "wavy", "welcome", "think", "curly", "engineer", "gather", "communication", "cute", "best", "diligent", "short", "cusin", "party", "small", "parent", "big", "live", "tall", "black", "twin", "find", "right", "live", "bank", "birthday", "blond", "sport"};
	

int main(void)
{
		word_test();
	
	return 0;
}
	int word_test()
	{

		for(j=0; j<20; j++)
		{
			system("clear");
			
			srand(time(NULL));

			int a=rand()%100,c=0,k=0;

			printf(">> 영문 타자 연습 프로그램 : 낱말 연습 <<\n");
			printf("진행도 : %d%%   오타수 : %d   정확도 : %d%%\n",count,wrong,correct);	
		
		for(i=0; i<14; i++)
		{
			printf("%c",words[a][i]);
	
			if(words[a][i]=='a'||words[a][i]=='b'||words[a][i]=='c'||words[a][i]=='d'||words[a][i]=='e'||words[a][i]=='f'||words[a][i]=='g'||words[a][i]=='h'||words[a][i]=='i'||words[a][i]=='j'||words[a][i]=='k'||words[a][i]=='l'||words[a][i]=='m'||words[a][i]=='n'||words[a][i]=='o'||words[a][i]=='p'||words[a][i]=='q'||words[a][i]=='r'||words[a][i]=='s'||words[a][i]=='t'||words[a][i]=='u'||words[a][i]=='v'||words[a][i]=='w'||words[a][i]=='x'||words[a][i]=='y'||words[a][i]=='z'||words[a][i]=='A'||words[a][i]=='D'||words[a][i]=='L')
				k++;
			
		}
	
		printf("\n");

	for(check=0,i=0; i<14; i++)
	{
		c=getchar();
		
		if(c=='\n')
			break;

		if(c==words[a][i])
			check++;
	}

	if(check==k)
		correct+=5;
	else
		wrong++;

	count+=5;


		system("clear");

	}
			printf(">> 영문 타자 연습 프로그램 : 낱말 연습 <<\n");
			printf("진행도 : %d%%   오타수 : %d   정확도 : %d%%\n",count,wrong,correct);	

	return 0;
	}
