import java.util.*;

public class expansion {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		int t = scan.nextInt();
		for(int q = 1; q <= t; q++){
			int n = scan.nextInt();
			int m = scan.nextInt();
			int[] A = new int[n];
			for(int i = 0; i < n; i++) A[i] = scan.nextInt();
			Arrays.sort(A);
			int[] B = new int[m];
			for(int i = 0; i < m; i++) B[i] = scan.nextInt();
			Arrays.sort(B);
			int total = n; //total teams
			int count = 0; //added teams
			//try to add each team in b
			//from lowest skill to highest
			for(int i = 0; i < m; i++){
				int votes = count; //every team added will vote for this
				for(int j = 0; j < n; j++){
					if(A[j] <= B[i]) votes++;
				}
				if(votes > total/2){
					count++;
					total++;
				}
			}
			System.out.println("Expansion #"+q+": "+count);
		}
	}
}

/*
2
2 2
1 10
5 11
5 5
5 4 3 2 1
1 2 3 4 5
*/