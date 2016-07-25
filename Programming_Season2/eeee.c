#include <stdio.h>
int main(void)
{
	    int max;
		    int max_index[5];
			    int i,j,sum=0,temp;
				    int score[8]={0};
					    for (i=0;i<8;i++)
							    {
									        scanf("%d",&score[i]);
											    }
						    for (i=0;i<5;i++)
								    {
										        max=score[0];
												        for (j=0;j<8;j++)
															        {
																		            if (max<score[j])
																						            {
																										                max_index[i]=j;
																														                max=score[j];
																																		            }
																					        }
														        sum+=max;score[max_index[i]]=0;
																     }
							    for (i=0;i<5;i++)
									    {
											        for (j=5;j>i;j--)
														        {
																	            if (max_index[j]<max_index[j-1])
																					            {
																									                temp=max_index[j];
																													                max_index[j]=max_index[j-1];
																																	                max_index[j-1]=temp;
																																					            }
																				        }
													    }
								    printf("%d\n",sum);
									    for (i=0;i<5;i++)
											    {
													        printf("%d ",max_index[i]+1);
															    }
										    return 0;
}
