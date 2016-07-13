#include <iostream>
#include <cstdio>
#include <cstring>

using namespace std;

char input_stream[160];

bool is_character(char c)
{
	if ((('A'<=c) && (c<='Z') || (c=='*') || (c=='!') || (c=='b')))
		return true;
	else
		return false;
}

int main(void)
{
	int length;
	int total_out;
	while (gets(input_stream)!=NULL)
	{
		total_out=0;
		length=strlen(input_stream);

		for (int i=0;i<length;i++)
		{
			if (!is_character(input_stream[i]))
				total_out+=input_stream[i]-'0';
			else
			{
				if (input_stream[i]=='!')
				{
					for (int j=0;j<=total_out;j++)
						printf("\n");
				}
				else if (input_stream[i]=='b')
				{
					for (int j=0;j<total_out;j++)
						printf(" ");
				}
				else
				{
					for (int j=0;j<total_out;j++)
						printf("%c",input_stream[i]);
				}
				total_out=0;
			}
		}
		printf("\n");
	}

	return 0;
}