#include <stdio.h>
int main(void)
{
	    int a,b,baek,sip,il;
		    scanf("%d",&a);
			    scanf("%d",&b);
				    baek=b/100;
					    sip=b/10;
						    il=b%10;
							    printf("%d\n",a*il);
									printf("%d\n",a*sip);
									    printf("%d\n",a*baek);
										    printf("%d", a*il+a*sip*10+a*baek*100);
											    return 0;
}
