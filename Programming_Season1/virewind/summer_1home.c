#include <stdio.h>
#include <time.h>
int change(int *q[], int l,int *zero) {
	int temp, y;
	l=l-1;
	if ((zero==q[l]-1 && l!=6 && l!=3) || (zero==q[l]+1 && l!=7 && l!=4) || (zero==q[l]+3) || (zero==q[l]-3))	 {
		temp=*zero;
		*zero=*q[l];
		*q[l]=temp;
	}
	else
		return -1;
	return 1;
}

int main(void) {
	srand(time(NULL));
	int con,out,i,j,n=0,temp,location;
	int num[3][3]={{1,2,3},{4,5,6},{7,8,0}};
	int check[9]={1,2,3,4,5,6,7,8,0};
	int q[3][3]={0};
	int *pos[9];
	int *zero;
	for (i=0; i<3; i++) {
		for (j=0; j<3; j++) {
			pos[n]=&q[i][j];
			n++;
		}
	}
	for (i=0; i<3; i++) {
		for (j=0; j<3; j++) {
			while (1) {
				temp=rand()%9;
				if (check[temp]!=-1) {
					q[i][j]=check[temp];
					check[temp]=-1;
					break;
				}
			}
		}
	}
	while (1) {
		for (i=0; i<3; i++) {
			for (j=0; j<3; j++) {
				if (q[i][j]==0)
					zero=&q[i][j];
			}
		}

		for (i=0; i<3; i++) {
			for (j=0; j<3; j++) {
				printf("%d ", q[i][j]);
				}
			printf("\n");
		}
		printf("\nenter position : ");
		out=scanf("%d", &location);
		if (out==EOF || location==-1)
			return 0;
		con=change(pos,location,zero);
		if (con==-1) {
			while (con!=1) {
				printf("\nre-enter postion : ");
				out=scanf("%d", &location);
				con=change(pos,location,zero);
				if (out==EOF || location==-1)
					return 0;
			}
		}
	}
	return 0;
}
