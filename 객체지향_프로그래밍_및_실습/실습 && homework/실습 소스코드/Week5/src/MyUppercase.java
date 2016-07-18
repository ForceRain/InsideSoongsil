import java.util.StringTokenizer;

class MyUppercase {
		private String input;
		
		public void getString(String input)
		{
				this.input=input;
		}
		
		public void capitalize()
		{
				String output=input;
				String []result=output.split("\\s");
				
				String print=null;
					for (int x=0;x<result.length;x++)
					{
							char up;
							up=result[x].charAt(0);
							
							String upp1,upp2;
							upp1=String.valueOf(up);
							upp1=upp1.toUpperCase();
							upp2=result[x].substring(1);
							
							if (print==null)
								print=upp1.concat(upp2);
							else
								print=print.concat(upp1.concat(upp2));
							
							print=print.concat(" ");
							
					}
					System.out.println(print);
				
		}
}
