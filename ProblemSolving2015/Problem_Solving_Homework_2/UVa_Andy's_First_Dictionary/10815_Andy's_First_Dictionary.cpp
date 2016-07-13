#include <iostream>
#include <cstring>
#include <string>
#include <cstdio>
#include <map>

using namespace std;

char input[1000];
map<string, int> container;
int length;

bool is_char(int idx)
{
	if ((('A'<=input[idx]) && (input[idx]<='Z')) || (('a'<=input[idx]) && (input[idx]<='z')))
		return true;
	else
		return false;
}

char to_lower(int idx)
{
	if ((('a'<=input[idx]) && (input[idx]<='z')))
		return input[idx];
	else
		return input[idx]+'a'-'A';
}

int main(void)
{
	int start,end;
	string obj="";
	while (gets(input))
	{
		obj="";
		length=strlen(input);
		start=0;

		while (start<=length)
		{
			if (is_char(start))
			{
				obj+=to_lower(start);
			}
			else
			{
				if (obj!="")
					container.insert(pair<string,int>(obj,1));
				obj="";
			}
			start++;
		}
	}

	map<string,int>::iterator itera;
	for (itera=container.begin();itera!=container.end();itera++)
		cout<<itera->first<<endl;

	return 0;
}