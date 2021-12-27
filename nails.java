import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class nails {

	static class Solver {

		int N;
		Op root;
		ArrayList<Op>[] nailOps;

		@SuppressWarnings("unchecked")
		void solve(int testNumber, Scanner s, PrintWriter out) {
			N = s.nextInt();
			nailOps = new ArrayList[N];
			for (int i = 0; i < N; i++)
				nailOps[i] = new ArrayList<>();
			root = new Op(0, 1);
			nailOps[0].add(root);
			ArrayList<Op> rep;
			for (int i = 1; i < N; i++) {
				int nxt = 0;
				for (int j = 1; j < i; j++)
					if (nailOps[j].size() < nailOps[nxt].size())
						nxt = j;
				// replace all of nxt with operations for nxt and i
				rep = nailOps[nxt];
				nailOps[nxt] = new ArrayList<>();
				Op pnext, two, three, four;
				for (Op o : rep) {
					if (o.dir == 1) {
						// nxt -> (nxt) (i) (-nxt) (-i)
						pnext = o.next;
						nailOps[nxt].add(o);
						nailOps[i].add(two = new Op(i, 1));
						o.next = two;
						nailOps[nxt].add(three = new Op(nxt, -1));
						two.next = three;
						nailOps[i].add(four = new Op(i, -1));
						three.next = four;
						four.next = pnext;
					} else {
						// -nxt -> (i) (nxt) (-i) (-nxt)
						pnext = o.next;
						o.nail = i;
						o.dir = 1;
						nailOps[i].add(o);
						nailOps[nxt].add(two = new Op(nxt, 1));
						o.next = two;
						nailOps[i].add(three = new Op(i, -1));
						two.next = three;
						nailOps[nxt].add(four = new Op(nxt, -1));
						three.next = four;
						four.next = pnext;
					}
				}
			}
			int tot = 0;
			for (ArrayList<Op> oo : nailOps)
				tot += oo.size();
			Op cur = root;
			out.printf("Picture #%d: %d", testNumber, tot);
			while (cur != null) {
				out.printf(" %s%d", cur.dir == 1 ? "R" : "L", cur.nail + 1);
				cur = cur.next;
			}
			out.println();
		}

		class Op {

			int nail, dir;
			Op next = null;

			Op(int n, int d) {
				nail = n;
				dir = d;
			}

			void print(PrintWriter out) {

				if (next != null)
					next.print(out);
			}

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
