import java.util.Scanner;
/*
2
0 0 5 5 4 0
2
2 4 3 -2
4 3 3 2
0 0 5 5 4 0
1
2 1 1 2
 */
public class flooding {

	public static void main(String[] args) {
		Scanner fs=new Scanner(System.in);
		int T=fs.nextInt();
		for (int t=1; t<=T; t++) {
			Vec a=new Vec(fs.nextLong(), fs.nextLong()), b=new Vec(fs.nextLong(), fs.nextLong()),c=new Vec(fs.nextLong(), fs.nextLong());
			int n=fs.nextInt(), count=0;
			for (int i=0; i<n; i++) {
				Vec first=new Vec(fs.nextLong(), fs.nextLong()), second=new Vec(fs.nextLong(),fs.nextLong());
				if (inside(a, b, c, first)^inside(a, b, c, second))
					count++;
			}
			System.out.println("Scenario "+t+": "+count);
		}

	}
	
	static boolean inside(Vec a, Vec b, Vec c, Vec o) {
		long s1=Long.signum(b.sub(a).cross(o.sub(a)));
		long s2=Long.signum(c.sub(b).cross(o.sub(b)));
		long s3=Long.signum(a.sub(c).cross(o.sub(c)));
		if (s1==0&&s2==s3) {
			return true;
		}
		if (s2==0&&s1==s3) {
			return true;
		}
		if (s3==0&&s1==s2) {
			return true;
		}
		
		if (s1==0&&s1==s2) {
			return true;
		}
		if (s1==0&&s1==s3) {
			return true;
		}
		if (s2==0&&s3==s2) {
			return true;
		}
		return s1==s2&&s2==s3;
	}
	
	static class Vec {
		long x, y;
		public Vec(long x, long y) {
			this.x=x;
			this.y=y;
		}
		
		public Vec add(Vec o) {
			return new Vec(x+o.x, y+o.y);
		}
		
		public Vec scale(long s) {
			return new Vec(x*s, y*s);
		}
		
		public Vec sub(Vec o) {
			return add(o.scale(-1));
		}
		
		public long cross(Vec o) {
			return x*o.y-y*o.x;
		}
		
		public String toString() {
			return "("+x+", "+y+")";
		}
	}

}
