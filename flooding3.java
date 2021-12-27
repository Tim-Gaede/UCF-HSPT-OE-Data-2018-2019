import java.util.*;

public class flooding3 {

	static double eps = 1e-7;
	
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		
		for (int c = 1; c <= t; c++)
		{
			p[] tri = new p[4];
			for (int i = 0; i < 3; i++)
				tri[i] = new p(in.nextInt(), in.nextInt());
			tri[3] = tri[0];
			
			int n = in.nextInt(), count = 0;
			double areaT = area(tri, tri[0]);
			for (int i = 0; i < n; i++)
			{
				p s = new p(in.nextInt(), in.nextInt());
				p e = new p(in.nextInt(), in.nextInt());
				
				boolean theta = Math.abs(area(tri, s)-areaT) < eps ? true : false;
				boolean alpha = Math.abs(area(tri, e)-areaT) < eps ? true : false;
				
				if (theta != alpha) count++;
			}
			System.out.println("Scenario "+c+": "+count);
		}
		
		in.close();
	}
	
	public static double area(p[] polygon, p a)
	{
		double sum = 0;
		for (int i = 0; i < polygon.length-1; i++)
			sum += crs(polygon[i], polygon[i+1], a);
		return sum;
	}
	
	public static double crs(p a, p b, p c)
	{
		return 0.5*Math.abs(((a.x-c.x)*(b.y-c.y)-(a.y-c.y)*(b.x-c.x)));
	}
	
	public static class p
	{
		int x, y;
		
		p(int xx, int yy)
		{
			x = xx; y = yy;
		}
	}
	
}
