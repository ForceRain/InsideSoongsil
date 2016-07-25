int is_zero(int);
int divide(int divi,int diviso)
{
	extern int quo,rem;
	if (is_zero(diviso))
		return -1;
	quo=divi/diviso;
	rem=divi%diviso;
	return 0;
}
int is_zero(int num)
{
	if (num)
		return 0;
	return 1;
}
