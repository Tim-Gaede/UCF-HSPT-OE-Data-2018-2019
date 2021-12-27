import java.util.*;

public class lights
{
	// the one with Zvalues
	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		
		while(T-->0)
		{
			char[] str = in.next().toCharArray();
			int N = str.length;
			
			int[] z = new int[N];
			int l = 0, r = 0;
			
			for(int i = 0; i < N; i++)
			{
				if(r < i)
				{
					l = r = i;
					while(r < N && str[r-l] == str[r])
					{
						r++;
					}
					z[i] = r-l;
					r--;
				}
				else if(z[i-l] < r-i+1)
				{
					z[i] = z[i-l];
				}
				else
				{
					l = i;
					while(r < N && str[r-l] == str[r])
					{
						r++;
					}
					z[i] = r-l;
					r--;
				}
			}
			z[0] = N;
			
			boolean messy = true;
			for(int i = 1; i < N; i++)
			{
				if(z[i] == N-i)
				{
					messy = false;
				}
			}
			
			System.out.println( messy ? "MESSY" : "OK" );
		}
	}
	// the simpler solution that i'm 91% sure works
	public static void fakemain(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int T = in.nextInt();
		
		while(T-->0)
		{
			char[] str = in.next().toCharArray();
			
			
			int back = 0, front = 1;
			while(front < str.length)
			{
				if(str[back] == str[front])
				{
					// increment both, because they're the same character
					back++;
					front++;
				}
				
				else if(back != 0)
				{
					// reset our search
					// do not increment front, since it could be the start of a cycle
					back = 0;
				}
				else
				{
					// reset again but incrementing front
					front++;
				}
			}
			
			// now, we know that if the back pointer is 
			if(back > 0)
			{
				System.out.println("OK");
			}
			else
			{
				System.out.println("MESSY");
			}
		}
	}
}
