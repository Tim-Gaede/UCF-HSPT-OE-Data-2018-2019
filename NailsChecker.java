import java.io.File;
import java.util.ArrayDeque;
import java.util.Scanner;
import java.util.StringTokenizer;

public class NailsChecker {
	static int[] expectedLength = {1, 4, 10, 16, 28, 40, 52, 64, 88, 112, 136, 160, 184, 208, 232, 256, 304, 352, 400, 448, 496, 544, 592, 640, 688, 736, 784, 832, 880, 928, 976, 1024, 1120, 1216, 1312, 1408, 1504, 1600, 1696, 1792, 1888, 1984, 2080, 2176, 2272, 2368, 2464, 2560, 2656, 2752, 2848, 2944, 3040, 3136, 3232, 3328, 3424, 3520, 3616, 3712, 3808, 3904, 4000, 4096, 4288, 4480, 4672, 4864, 5056, 5248, 5440, 5632, 5824, 6016, 6208, 6400, 6592, 6784, 6976, 7168};
	public static void main(String[] args) {
		if(args.length < 1){
			System.out.println("Syntax: java NailsChecker <submission_output>");
			return;
		}
		
		Scanner in;
		try{
			in = new Scanner(new File(args[0]));
		}
		catch(Exception e){
			System.out.println("Could not open the file \""+args[0]+"\"");
			return;
		}
		
		boolean formatingError = false;
		for(int i=1; i<=80; i++){
			StringTokenizer ln;
			
			try{
				ln = new StringTokenizer(in.nextLine());
			}
			
			catch(Exception e){
				System.out.println("FAILED: Only found "+(i-1)+" lines of Input when expecting 80");
				return;
			}
			
			String picture;
			try{
				picture = ln.nextToken();
			}
			catch(Exception e){
				System.out.println("FAILED: Input stopped unexpectedly");
				return;
			}
			
			if(!picture.equals("Picture"))
				formatingError = true;
			
			String id;
			try{
				id = ln.nextToken();
			}
			catch(Exception e){
				System.out.println("FAILED: Input stopped unexpectedly");
				return;
			}
			
			if(id.length() < 3 || id.charAt(0) != '#' || id.charAt(id.length()-1) != ':'){
				formatingError = true;
			}
			
			if(id.length() >= 3){
				int picNumber = -1;
				try{
					picNumber = Integer.parseInt(id.substring(1, id.length()-1));
				}
				catch(Exception e){}
				
				if(picNumber != i)
					formatingError = true;
			}
			
			if(!ln.hasMoreTokens()){
				System.out.println("FAILED: Input stopped unexpectedly");
				return;
			}
			
			int numCommands = 0;
			try{
				numCommands = Integer.parseInt(ln.nextToken());
			}
			catch(Exception e){
				System.out.println("FAILED: Input was uninterpretable");
				return;
			}
			
			// Just in case there is a better solution, this way we at least don't give WA
			if(numCommands > expectedLength[i-1]){
				System.out.println("FAILED: Incorrect output");
				return;
			}
			
			int[] comID = new int[numCommands];
			boolean[] comDIR = new boolean[numCommands];
			for(int j=0; j<numCommands; j++){
				String com;
				try{
					com = ln.nextToken();
				}
				catch(Exception e){
					System.out.println("FAILED: Input stopped unexpectedly");
					return;
				}
				
				if(com.length() < 2){
					System.out.println("FAILED: Input was uninterpretable");
					return;
				}
				
				if(com.charAt(0) != 'R' && com.charAt(0) != 'L'){
					System.out.println("FAILED: Input was uninterpretable");
					return;
				}
				
				int ID = -1;
				try{
					ID = Integer.parseInt(com.substring(1, com.length()));
				}
				catch(Exception e){
					System.out.println("FAILED: Input was uninterpretable");
					return;
				}
				
				comID[j] = ID;
				comDIR[j] = com.charAt(0) == 'R' ? true : false;
			}
			
			ArrayDeque<Integer> stack = new ArrayDeque<Integer>();
			for(int nail=0; nail<=i; nail++){
				for(int j=0; j<numCommands; j++){
					if(comID[j] == nail)
						continue;
					stack.push(j);
					
					while(stack.size() >= 2){
						int t = stack.pop();
						int peek2 = stack.peek();
						stack.push(t);
						if(comID[stack.peek()] != comID[peek2] || comDIR[stack.peek()] == comDIR[peek2])
							break;
						stack.pop();
						stack.pop();
					}
				}
				
				// Picture did not fall when removing this nail
				if(stack.size() != 0 && nail > 0){
					System.out.println("FAILED: Incorrect output");
					return;
				}
				
				// Picture did not stay when removing no nails.
				if(stack.size() == 0 && nail == 0){
					System.out.println("FAILED: Incorrect output");
					return;
				}
				stack.clear();
			}
		}
		
		if(formatingError){
			System.out.println("FAILED: Incorrect formatting");
			return;
		}
		else{
			System.out.println("Correct");
			return;
		}
	}
	
	
}
