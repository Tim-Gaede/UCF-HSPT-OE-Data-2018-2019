import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * Ant Colony
 * Solution by David Harmeyer
 * 
 * The intended aproach to this problem is to use Disjoint Sets. Disjoint sets allow us to do two things very quickly (O(log(n)) worst case):
 * 		1. Combine all of the elements in two groups into the same group
 * 		2. Find which group an element is in
 * 
 * We can also see if two elements are in the same group by testing to see if the group they are in is the same.
 * 
 * One observation to make about this problem is that if you process the queries backwards (start with all the rooms
 * seperated and then join two rooms when a tunnel 'uncollapses' then you can easily represent the map as a disjoint set.
 * This is the approach we use here.
 * 
 * 
 */
public class colony {

	public static void main(String[] args) {
		FastScanner fs=new FastScanner(System.in);
		int c=fs.nextInt();
		PrintWriter out=new PrintWriter(System.out);
		for (int colony=1; colony<=c; colony++) {
			//I use the first 32 bits of the long to store the lower index of the two nodes and the higher 32 bits to store the
			//higher index. This way I can quickly reference an edge when I need to update the time it is destroyed when going through
			//the queries
			HashMap<Long, Integer> timeEdgeIsBuilt=new HashMap<>();
			int n=fs.nextInt();
			int oo=1000000;
			for (int i=1; i<n; i++) {
				long[] edge={fs.nextInt()-1, fs.nextInt()-1};
				Arrays.sort(edge);
				timeEdgeIsBuilt.put((edge[0]<<32)+edge[1], oo);
			}
			int q=fs.nextInt();
			Query[] queries=new Query[q];
			for (int Q=0; Q<q; Q++) {
				queries[Q]=new Query(fs.nextInt(), fs.nextInt()-1, fs.nextInt()-1);
				
				//if this is a 'destroy an edge' query, I have to mark the time the edge is 'destroyed' or created
				//when going backwards
				if (queries[Q].type==1) {
					long from=Math.min(queries[Q].from, queries[Q].to);
					long to=Math.max(queries[Q].from, queries[Q].to);
					timeEdgeIsBuilt.put((from<<32)+to, Q);
				}
			}
			
			Edge[] edges=new Edge[n-1];
			int index=0;
			for (long l:timeEdgeIsBuilt.keySet()) {
				int first=(int) (l/(1l<<32));
				int second=(int) (l%(1l<<32));
				int queryNum=timeEdgeIsBuilt.get(l);
				
				//convert my hashmap of edges back into an array of edges so that they are easier to deal with
				edges[index++]=new Edge(first, second, queryNum);
			}
			
			//process the edges in the reverse order in which they were destroyed, so I can construct them instead of destroy them
			Arrays.sort(edges, (a, b)->{return b.queryNumber-a.queryNumber;});
			index=0;
			DisjointSet dj=new DisjointSet(n);

			//add in all of the edges that never got destroyed right away
			while (index<edges.length&&edges[index].queryNumber==oo) {
				dj.join(edges[index].from, edges[index].to);
				index++;
			}
			
			//go through the queries
			for (int queryNum=queries.length-1; queryNum>=0; queryNum--) {
				if (queries[queryNum].type==1) {
					continue;
				}
				
				//account for all the edges that were 'undestroyed' between the last query and now
				while (index<edges.length&&edges[index].queryNumber>queryNum) {
					dj.join(edges[index].from, edges[index].to);
					index++;
				}
				
				//if the two query nodes are in the same group in the disjoint set, then they are reachable. Otherwise, they aren't
				queries[queryNum].reachable=dj.find(queries[queryNum].from)==dj.find(queries[queryNum].to);
			}
			
			//print the answer
			out.println("Colony #"+colony+":");
			for (Query qq:queries) {
				out.println(qq);//Query.toString prints and formats the query
			}
			out.println();
		}
		out.close();

	}

	private static class Edge {
		int from, to;
		int queryNumber;
		public Edge(int from, int to, int q) {
			this.from=from;
			this.to=to;
			queryNumber=q;
		}
	}
	
	private static class Query {
		int type;
		int from, to;
		boolean reachable;
		
		public Query(int type, int from, int to) {
			this.type=type;
			this.from=from;
			this.to=to;
		}
		
		public String toString() {
			if (type==1) {
				return "Tunnel from "+(from+1)+" to "+(to+1)+" collapsed!";
			}
			else {
				if (reachable)
					return "Room "+(from+1)+" can reach "+(to+1);
				return "Room "+(from+1)+" cannot reach "+(to+1);
			}
		}
	}
	
	//used as part of the Disjoint set
	private static class Pair {
        int value;
        int height;
        public Pair(int value, int height) {
            this.value=value;
            this.height=height;
        }
    }
    
    private static class DisjointSet {
        int numberOfParts;
        Pair[] pairs;
        public DisjointSet(int n) {
            numberOfParts=n;
            pairs=new Pair[n];
            for (int i=0; i<n; i++)
                pairs[i]=new Pair(i, 0);
        }
        
        public int find(int index) {
            while (pairs[index].value!=index)
                index=pairs[index].value;
            return index;
        }
        
        public void join(int a, int b) {
            a=find(a);
            b=find(b);
            if (a==b)
                return;
            
            numberOfParts--;
            if (pairs[a].height<pairs[b].height) {
                pairs[a].value=b;
            }
            else {
                pairs[b].value=a;
                if (pairs[a].height==pairs[b].height) {
                    pairs[a].height++;//if they are equal, a just got one taller
                }
            }
        }
    }

    private static class FastScanner {
		BufferedReader br;
		StringTokenizer st;
		public FastScanner(InputStream in) {
			br = new BufferedReader(new InputStreamReader(in));
		}
		String next() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		int[] readArray(int n) {
			int[] a=new int[n];
			for (int i=0; i<n; i++)
				a[i]=nextInt();
			return a;
		}
	}
	
}
