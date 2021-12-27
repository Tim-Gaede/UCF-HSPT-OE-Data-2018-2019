import java.io.PrintWriter;
import java.util.Scanner;

public class flooding2 {

	static class Solver {

		int N, bad;
		long[][] points, segs;

		int cross(long x3, long y3, long[] p1, long[] p2) {
			long x1 = p1[0], x2 = p2[0], y1 = p1[1], y2 = p2[1];
			long X1 = x2 - x1, Y1 = y2 - y1, X2 = x3 - x1, Y2 = y3 - y1;
			long cross = X1 * Y2 - X2 * Y1;
			if (cross == 0)
				return 0;
			return cross < 0 ? -1 : 1;
		}

		boolean onseg(long x3, long y3, long[] p1, long[] p2) {
			long x1 = p1[0], x2 = p2[0], y1 = p1[1], y2 = p2[1];
			if (cross(x3, y3, p1, p2) != 0)
				return false;
			long lx = Math.min(x1, x2), rx = Math.max(x1, x2);
			long by = Math.min(y1, y2), ty = Math.max(y1, y2);
			return lx <= x3 && x3 <= rx && by <= y3 && y3 <= ty;
		}

		boolean in(long x, long y) {
			if (onseg(x, y, points[0], points[1]) || onseg(x, y, points[1], points[2])
					|| onseg(x, y, points[2], points[0]))
				return true;
			int[] r = { cross(x, y, points[0], points[1]), cross(x, y, points[1], points[2]),
					cross(x, y, points[2], points[0]) };
			return r[0] == r[1] && r[1] == r[2];
		}

		void solve(int testNumber, Scanner s, PrintWriter out) {
			points = new long[3][2];
			for (int i = 0; i < 3; i++)
				for (int j = 0; j < 2; j++)
					points[i][j] = s.nextLong();
			N = s.nextInt();
			bad = 0;
			for (int i = 0; i < N; i++)
				if (in(s.nextInt(), s.nextInt()) ^ in(s.nextInt(), s.nextInt()))
					bad++;
			out.printf("Scenario %d: %d%n", testNumber, bad);

		}

	}

	public static void main(String[] args) {

		Scanner s = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);

		Solver solver = new Solver();
		int T = s.nextInt();
		for (int t = 1; t <= T; t++)
			solver.solve(t, s, out);

		out.close();
		s.close();

	}

}
