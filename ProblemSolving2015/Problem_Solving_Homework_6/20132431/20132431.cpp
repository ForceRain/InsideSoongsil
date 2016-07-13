#include <iostream>
#include <algorithm>
#include <vector>
#include <stack>
#include <cstdio>
#include <cmath>

#define P_2(X) (X)*(X)

using namespace std;

vector<pair<double,double> > container;
vector<pair<double, pair<double,double> > > angle_set;
vector<pair<double,double> >backtrace;

double cross_product(pair<double,double> p1,pair<double,double> p2)
{
	return p1.first*p2.second-p1.second*p2.first;
}

double p_2_p(pair<double,double> p1,pair<double,double> p2)
{
	return sqrt(P_2(p1.first-p2.first)+P_2(p1.second-p2.second));
}

int main(void)
{
	double x1,y1;
	int N;
	cin>>N;

	for (int i=0;i<N;i++)
	{
		scanf("%lf%lf",&x1,&y1);
		container.push_back(pair<double,double>(x1,y1));
	}

	sort(container.begin(),container.end());
	pair<double,double> guijoon=container[container.size()-1];

	for (int i=0;i<container.size();i++)
	{
		pair<double,double> temp=container[i];
		temp.first=temp.first-guijoon.first;
		temp.second=temp.second-guijoon.second;
		container[i]=temp;
	}
	guijoon=container[container.size()-1];

	for (int i=0;i<N-1;i++)
	{
		double angular_val=asin((container[i].second)/sqrt(P_2(container[i].first)+P_2(container[i].second)));
		pair<double,pair<double,double> > angler;
		angler.first=angular_val;
		angler.second=container[i];
		angle_set.push_back(angler);
	}

	sort(angle_set.begin(),angle_set.end());

	int top=0;
	backtrace.push_back(guijoon);
	backtrace.push_back(angle_set[0].second);
	
	for (int i=1;i<N-1;)
	{
		top=backtrace.size()-1;

		pair<double,double> temp1(backtrace[top].first-backtrace[top-1].first,backtrace[top].second-backtrace[top-1].second);
		pair<double,double> temp2(angle_set[i].second.first-backtrace[top].first,angle_set[i].second.second-backtrace[top].second);
				
		if (cross_product(temp1,temp2)<0)
		{
			backtrace.push_back(angle_set[i].second);
			i++;
		}
		else
		{
			backtrace.pop_back();
		}
	}

	double output=0.0;
	for (int i=0;i<backtrace.size()-1;i++)
	{
		output+=p_2_p(backtrace[i],backtrace[i+1]);
	}
	output+=p_2_p(backtrace[backtrace.size()-1],guijoon);

	printf("%.2lf\n",output);

	return 0;
}