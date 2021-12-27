/*
Slayer Solution
UCF 2018 Online High School Programming Tournament
Solution by Ethan Bainbridge

Solution Idea:	First, we need to know the lowest amount of energy required
				to survive a hit from the monster. To do this, I used a simple
				take-it-or-not memoization approach, keeping track of which
				block move I'm considering and the amount of health I still need
				to survive.
				Once we know how much energy we need to survive a hit from the
				monster, we don't have to worry about our health while deciding
				what attack and charge moves to use. We simply subtract the amount
				of energy we need to survive the hit from our energy total any time
				we stop considering charges and start considering attacks. This
				means that we can use memoization and keep track of only which
				move we're considering, the amount of energy we have left, and the
				amount of health the monster has left.
*/

import java.util.*;
public class slayer {
	public static move[] charges,attacks,blocks;
	public static int[][] memoForHealth;
	public static int a,cheapestHealth,oo=100000000;
	public static int[][][] memoForFighting;
	@SuppressWarnings("resource") //The day I close my scanners is the day I die.
	public static void main(String[] args) {
		Scanner in=new Scanner(System.in);
		int t=in.nextInt();
		for(int loop=1;loop<=t;loop++) {
			int q=in.nextInt(),n=in.nextInt(),m=in.nextInt(),p=in.nextInt();
			
			//I'm just reading in input here.
			a=in.nextInt();
			ArrayList<move> atks=new ArrayList<move>();
			ArrayList<move> chrgs=new ArrayList<move>();
			ArrayList<move> blcks=new ArrayList<move>();
			for(int i=0;i<n;i++) {
				move mo=new move(in.nextInt(),in.nextInt(),in.nextInt());
				if(mo.x==1)
					atks.add(mo);
				else if(mo.x==2)
					blcks.add(mo);
				else
					chrgs.add(mo);
			}
			
			//I turn everything into arrays here because they're cleaner.
			charges=new move[chrgs.size()];
			for(int i=0;i<charges.length;i++)
				charges[i]=chrgs.get(i);
			attacks=new move[atks.size()];
			for(int i=0;i<atks.size();i++)
				attacks[i]=atks.get(i);
			blocks=new move[blcks.size()];
			for(int i=0;i<blocks.length;i++)
				blocks[i]=blcks.get(i);
			
			//This is where I determine how much energy we need to survive a hit.
			if(a>=q) {
				//If we don't have any blocks to use, we can't survive the hit.
				if(blocks.length==0)
					cheapestHealth=oo;
				else {
					memoForHealth=new int[blocks.length][a-q+2];
					for(int i=0;i<blocks.length;i++)
						Arrays.fill(memoForHealth[i],-1);
					
					//This calls the method that finds how much energy we need.
					//a-q+1 is the amount of health we need from blocks to have 1 health left after the attack.
					cheapestHealth=healthDP(0,a-q+1);
				}
			}
			//If our health is higher than the monster's damage, we don't need any energy.
			else {
				cheapestHealth=0;
			}
			
			//If we can't hurt the monster, we can't win.
			if(attacks.length+charges.length==0) {
				System.out.println("Fight #"+loop+": Lose");
			}
			
			else {
				memoForFighting=new int[charges.length+attacks.length][m+1][p+1];
				for(int i=0;i<charges.length+attacks.length;i++)
					for(int j=0;j<m+1;j++)
						Arrays.fill(memoForFighting[i][j],-1);
				int ans=fightDP(0,m,p);
				//If ans<oo, we found a valid set of moves that defeats the monster.
				if(ans<oo)
					System.out.println("Fight #"+loop+": Win");
				else
					System.out.println("Fight #"+loop+": Lose");
			}
		}
	}
	public static int fightDP(int pos,int energy,int monsterHealth) {
		//This means we killed it.
		if(monsterHealth<=0)
			return 0;
		
		//If we're out of attacks and charges, there's no way for us to kill the monster.
		if(pos==charges.length+attacks.length)
			return oo;
		
		//If we've seen this state before, return the answer we got last time.
		if(memoForFighting[pos][energy][monsterHealth]!=-1)
			return memoForFighting[pos][energy][monsterHealth];
		
		//I make allowedEnergy because I have to keep energy unchanged to store my answer in my memo table.
		int allowedEnergy=energy;
		
		//This means that we're out of charges and the monster isn't dead, so it's going to hit us.
		//To survive the hit, we have to use what we calculated as the cheapest way to survive a hit earlier.
		if(pos==charges.length)
			allowedEnergy-=cheapestHealth;
		
		//If we're out of energy after blocking the hit, we couldn't afford the blocks we needed to survive, so we lose.
		if(allowedEnergy<=0)
			return memoForFighting[pos][energy][monsterHealth]=oo;
		
		//We consider charges first because if we kill it before using attack moves, we don't have to block.
		if(pos<charges.length) {
			int takeIt=oo;
			//We can only use this charge if we have enough energy.
			//If we use it, subtract its power from the monster's health and its cost from our energy.
			if(allowedEnergy>=charges[pos].z)
				takeIt=charges[pos].z+fightDP(pos+1,allowedEnergy-charges[pos].z,monsterHealth-charges[pos].y);
			
			//If we don't use it, just move on to the next move.
			int orNot=fightDP(pos+1,energy,monsterHealth);
			
			//Take the cheaper of the two paths.
			return memoForFighting[pos][energy][monsterHealth]=Math.min(takeIt,orNot);
		}
		
		//If we're out of charges, try using attacks.
		else {
			//Subtract the number of charges we have from our position in the attack array.
			int takeIt=oo,attackPos=pos-charges.length;
			
			//Like with the charges, we only use this attack if we have enough energy.
			if(allowedEnergy>=attacks[attackPos].z)
				takeIt=attacks[attackPos].z+fightDP(pos+1,allowedEnergy-attacks[attackPos].z,monsterHealth-attacks[attackPos].y);
			
			//If we don't use it, move on to the next attack.
			int orNot=fightDP(pos+1,energy,monsterHealth);
			
			//Take the cheaper of the two paths.
			return memoForFighting[pos][energy][monsterHealth]=Math.min(takeIt,orNot);
		}
	}
	public static int healthDP(int pos,int needed) {
		//This means we've already gained enough health to survive the hit.
		if(needed<=0)
			return 0;
		
		//This means we haven't gained enough health, but we're out of blocks.
		if(pos==blocks.length)
			return oo;
		
		//If we've seen this state before, return the answer we got last time.
		if(memoForHealth[pos][needed]!=-1)
			return memoForHealth[pos][needed];
		
		//If we use this block, add its cost to our needed energy and subtract its power from our needed health.
		int takeIt=blocks[pos].z+healthDP(pos+1,needed-blocks[pos].y);
		
		//If we don't use it, just move on to the next block.
		int orNot=healthDP(pos+1,needed);
		
		//Take the cheaper of the two paths.
		return memoForHealth[pos][needed]=Math.min(takeIt,orNot);
	}
	
	//I use this class to store each move's type, power, and cost.
	public static class move{
		int x,y,z;
		public move(int xx,int yy,int zz) {
			x=xx;
			y=yy;
			z=zz;
		}
	}
}