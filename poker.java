import java.util.*;

public class poker {

	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		String[] arr = {"Straight flush", "Four of a kind", "Full house",
				"Flush", "Straight", "Three of a kind", "Two pair", "Pair",
				"High card"};
		ArrayList<String> hand = new ArrayList<String>();
		for(int i = 0; i < arr.length; i++) hand.add(arr[i]);
		int t = scan.nextInt();
		scan.nextLine();
		for(int q = 1; q <= t; q++){
			String a = scan.nextLine();
			String b = scan.nextLine();
			int c = hand.indexOf(a);
			int d = hand.indexOf(b);
			if(c < d) System.out.println("Game #"+q+": Ryan");
			else System.out.println("Game #"+q+": Tyler");
		}
	}
}

/*
3
Flush
Straight
High card
Three of a kind
Straight flush
Pair
*/