#include <iostream>
#include <cstdio>
#include <cstring>

using namespace std;

char input_stream[101];
char f_text[101][101];
char s_text[101][101];
int c[101][101];
int b[101][101];
int out;

void dynamic_programming(int m,int n)
{
    for (int i=0;i<=m;i++)
        c[i][0]=0;

    for (int j=0;j<=n;j++)
        c[0][j]=0;

    for (int i=1;i<=m;i++)
    {
        for (int j=1;j<=n;j++)
        {
            if (strcmp(f_text[i-1],s_text[j-1])==0)
            {
                c[i][j]=c[i-1][j-1]+1;
                b[i][j]=1;
            }
            else if (c[i-1][j]>=c[i][j-1])
            {
                c[i][j]=c[i-1][j];
                b[i][j]=2;
            }
            else
            {
                c[i][j]=c[i][j-1];
                b[i][j]=3;
            }
        }
    }
}

void print_out(int i,int j)
{
    if (i==0 || j==0)
    {
        return;
    }

    if (b[i][j]==1)
    {
        print_out(i-1,j-1);
        if (out)
        {
            cout<<" "<<f_text[i-1];
        }
        else
        {
            out=1;
            cout<<f_text[i-1];
        }
    }
    else if (b[i][j]==2)
    {
        print_out(i-1,j);
    }
    else
    {
        print_out(i,j-1);
    }
}

void init()
{
    for (int i=0;i<=100;i++)
    {
        for (int j=0;j<=100;j++)
        {
            c[i][j]=0;
            b[i][j]=0;
        }
    }
}

int main(void)
{
    init();
    int x=0,y=0;

    while (cin>>input_stream)
    {
        x=0;y=0;
        if (strcmp(input_stream,"#")!=0)
        {
            strcpy(f_text[x],input_stream);
            x++;
        }

        while (cin>>input_stream)
        {
            if (strcmp(input_stream,"#")==0)
                break;
            strcpy(f_text[x],input_stream);
            x++;
        }
       
        while (cin>>input_stream)
        {
            if (strcmp(input_stream,"#")==0)
                break;

            strcpy(s_text[y],input_stream);
            y++;
        }

        dynamic_programming(x,y);
        out=0;
        print_out(x,y);
        printf("\n");
       
        init();     
    }
    

    return 0;
}