#include <stdio.h>
enum day {SUN, MON, TUE, WED, THU, FRI, SAT};
enum name {kim, lee, park, jung, hong=266};
int main(void) {
	enum day d;
	d=kim;
	return 0;
}
