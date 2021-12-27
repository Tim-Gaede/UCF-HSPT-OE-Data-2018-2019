//Colony solution by Atharva Nagarkar

import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.Scanner;

public class colony {
	
	class Query {
		int t;
		int a, b;
		Query(int tt, int aa, int bb) {
			t = tt;
			a = aa;
			b = bb;
		}
	}
	
	@SuppressWarnings("unchecked")
	public void solve(Scanner in, PrintWriter out) {
		int c = in.nextInt();
		for(int colony = 1; colony <= c; ++colony) {
			out.printf("Colony #%d:\n", colony);
			int n = in.nextInt();
			HashSet<Integer>[] adj = new HashSet[n];
			for(int i = 0; i < n; ++i) adj[i] = new HashSet<>();
			for(int i = 0; i < n - 1; ++i) {
				int a = in.nextInt() - 1;
				int b = in.nextInt() - 1;
				adj[a].add(b);
				adj[b].add(a);
			}
			int q = in.nextInt();
			Query[] queries = new Query[q];
			for(int i = q - 1; i >= 0; --i) {
				int t = in.nextInt();
				int a = in.nextInt() - 1;
				int b = in.nextInt() - 1;
				queries[i] = new Query(t, a, b);
				if(t == 1) {
					adj[a].remove(b);
					adj[b].remove(a);
				}
			}
			DSU dsu = new DSU(n);
			boolean[] visited = new boolean[n];
			for(int root = 0; root < n; ++root) {
				if(visited[root]) continue;
				ArrayDeque<Integer> Q = new ArrayDeque<>();
				Q.add(root);
				visited[root] = true;
				while(!Q.isEmpty()) {
					int u = Q.poll();
					for(int v : adj[u]) {
						if(visited[v]) continue;
						visited[v] = true;
						dsu.union(u, v);
						Q.add(v);
					}
				}
			}
			ArrayDeque<String> ans = new ArrayDeque<>();
			for(Query qq : queries) {
				if(qq.t == 1) {
					dsu.union(qq.a, qq.b);
					ans.addFirst(String.format("Tunnel from %d to %d collapsed!", qq.a+1, qq.b+1));
				} else {
					if(dsu.get(qq.a) == dsu.get(qq.b)) {
						ans.addFirst(String.format("Room %d can reach %d", qq.a+1, qq.b+1));
					} else {
						ans.addFirst(String.format("Room %d cannot reach %d", qq.a+1, qq.b+1));
					}
				}
			}
			for(String s : ans) {
				out.println(s);
			}
			out.println();
		}
	}
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		PrintWriter out = new PrintWriter(System.out);
		
		new colony().solve(in, out);
		
		in.close();
		out.close();
	}
	

	class DSU {
		int[] id;
		int size;
		DSU(int x) {
			size = x + 1;
			id = new int[size];
			for (int i = 0; i < size; ++i) {
				id[i] = i;
			}
		}
		int get(int a) {
			return id[a] == a ? a : (id[a] = get(id[a]));
		}
		void union(int a, int b) {
			if(get(a) == get(b)) return;
			id[get(a)] = id[get(b)];
			--size;
		}
	}

}
