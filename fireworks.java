//Fireworks solution by Atharva Nagarkar

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class fireworks {
	
	class Firework implements Comparable<Firework> {
		int velocity;
		int index;
		public Firework(int v, int i) {
			velocity = v;
			index = i;
		}
		public int compareTo(Firework o) {
			return velocity - o.velocity;
		}
	}
	
	public void solve(Scanner in, PrintWriter out) {
		int t = in.nextInt();
		for(int testCase = 1; testCase <= t; ++testCase) {
			int n = in.nextInt();
			Firework[] fireworks = new Firework[n];
			for(int i = 0; i < n; ++i) {
				int v = in.nextInt();
				fireworks[i] = new Firework(v, i);
			}
			Arrays.sort(fireworks);
			out.printf("Case #%d:\n", testCase);
			out.printf("  Highest Firework: %d\n", 1 + fireworks[n - 1].index);
			out.printf("  Earliest Firework: %d\n", 1 + fireworks[0].index);
			out.println();
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		new fireworks().solve(in, out);
		in.close();
		out.close();
	}
}
