int divide(int dividend, int divisor)
{
	extern int quotient,rem;
	if (is_zero(divisor))
		return -1;
	quotient=dividend/divisor;
	rem=dividend%divisor;
	return 0;
}
