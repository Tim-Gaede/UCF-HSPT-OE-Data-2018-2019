//Even solution by Atharva Nagarkar

import java.io.PrintWriter;
import java.util.Scanner;

public class even {
	
	public void solve(Scanner in, PrintWriter out) {
		int t = in.nextInt();
		for(int testCase = 1; testCase <= t; ++testCase) {
			int x = in.nextInt();
			long nextNumber = Integer.highestOneBit(x) << 1L;
			long ans = nextNumber - x;
			out.printf("Pokemon %d: %d\n", testCase, ans);
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		new even().solve(in, out);
		in.close();
		out.close();
	}
}
