/*
Solution to Slayer by David Harmeyer in C++

Problem summary (because it took me a while to understand):
You (the slayer) have some number of hit points, manna, and moves you can use.
The Monster has some number of hit points and does some damage.
There are 3 types of attacks:
	-Charge (first priority), does damage
	-Block (second priority), increases your health
	-Attack (third priority), does damage

You can use any combinations of attacks so long as they do not add up to more manna than you have.
Note that the monster only attacks one time, since the whole battle is only one turn.

Observations:
	-You have two choices:
		Strategy 1: Kill the monster before it can attack with charges
		Strategy 2: Heal yourself enough to survive the monster's attack and then kill the monster using attacks and/or charges.
	-We can calculate the minimum manna cost to kill the monster with only charges (and whether it is possible) using
	0/1 knapsack.***
	-We can calculate the minimum manna cost to use enough blocks to survive the Monster's attack using 0/1 knapsack.***
	-We can calculate the minimum manna cost to kill the Monster using a combination of charges and attacks ith 0/1 knapsack.***
	-After we can heal to 101 health, we don't need to keep track of exactly how many hp points we have, because we can't be killed anyway, since
	the monster can do at most 100 points of damage to us.
	-If we have enough manna to kill the monster with only charges, or enough manna to survive, and then kill the monster with any set of attacks
	then we can win, otherwise, we can't.

Therefore, we can solve this problem in O(#testcases * (100*3 + reading input)).
	
***A fantastic knapsack tutorial is made by Matt Fontaine (A former UCF Programming Team member) and is published on Algorithms Live here:
	https://www.youtube.com/watch?v=U4O3SwDamA4
	
*/

#include <stdio.h>
#include <iostream>

#define MAX(x, y) (x)>=(y)?(x):(y)
#define MIN(x, y) (x)<=(y)?(x):(y)

using namespace std;

int main() {
	int T, mannaLeftAtEnemyHealth[101], mannaLeftToGetHealth[102], mannaLeftAtEnemyHealthChargeOnly[101];
	int moveType[101], movePower[101], moveCost[101];
	int slayerHealth, numMoves, startingManna, monsterHealth, monsterAttack;
	cin>>T;
	for (int t=1; t<=T; t++) {
		//read in input
		cin>>slayerHealth>>numMoves>>startingManna>>monsterHealth>>monsterAttack;
		for (int i=0; i<numMoves; i++)
			cin>>moveType[i]>>movePower[i]>>moveCost[i];
		
		//clear out arrays used for knapsack
		for (int i=0; i<=100; i++)
			mannaLeftAtEnemyHealth[i]=mannaLeftToGetHealth[i]=mannaLeftAtEnemyHealthChargeOnly[i]=-1;
		mannaLeftToGetHealth[101]=-1;
		mannaLeftAtEnemyHealthChargeOnly[monsterHealth]=mannaLeftAtEnemyHealth[monsterHealth]=mannaLeftToGetHealth[slayerHealth]=startingManna;
		
		//process each possible
		for (int move=0; move<numMoves; move++) {
			if (moveType[move]==1) {
				//attack
				for (int i=0; i<=100; i++) {
					if (mannaLeftAtEnemyHealth[i]==-1) continue;
					if (mannaLeftAtEnemyHealth[i]<moveCost[move]) continue;
					int healthLeft=MAX(0, i-movePower[move]);
					mannaLeftAtEnemyHealth[healthLeft]=MAX(mannaLeftAtEnemyHealth[i]-moveCost[move], mannaLeftAtEnemyHealth[healthLeft]);
				}
			}
			else if (moveType[move]==2) {
				//block
				for (int i=101; i>=0; i--) {
					if (mannaLeftToGetHealth[i]==-1) continue;
					if (mannaLeftToGetHealth[i]<moveCost[move])continue;
					int newHealth=MIN(101, movePower[move]+i);
					mannaLeftToGetHealth[newHealth]=MAX(mannaLeftToGetHealth[newHealth], mannaLeftToGetHealth[i]-moveCost[move]);
				}
				
			}
			else {
				//charge
				for (int i=0; i<=100; i++) {
					if (mannaLeftAtEnemyHealth[i]==-1) continue;
					if (mannaLeftAtEnemyHealth[i]<moveCost[move]) continue;
					int healthLeft=MAX(0, i-movePower[move]);
					mannaLeftAtEnemyHealth[healthLeft]=MAX(mannaLeftAtEnemyHealth[i]-moveCost[move], mannaLeftAtEnemyHealth[healthLeft]);
				}
				for (int i=0; i<=100; i++) {
					if (mannaLeftAtEnemyHealthChargeOnly[i]==-1) continue;
					if (mannaLeftAtEnemyHealthChargeOnly[i]<moveCost[move]) continue;
					int healthLeft=MAX(0, i-movePower[move]);
					
					mannaLeftAtEnemyHealthChargeOnly[healthLeft]=MAX(mannaLeftAtEnemyHealthChargeOnly[i]-moveCost[move], mannaLeftAtEnemyHealthChargeOnly[healthLeft]);
				}
			}
		}
		int maxLeftToSurvive=-1;
		for (int i=monsterAttack+1; i<=101; i++) maxLeftToSurvive=MAX(maxLeftToSurvive, mannaLeftToGetHealth[i]);
		if (mannaLeftAtEnemyHealthChargeOnly[0]>=0||(mannaLeftAtEnemyHealth[0]>=0&&maxLeftToSurvive+mannaLeftAtEnemyHealth[0]>=startingManna)) {
			cout<<"Fight #"<<t<<": "<<"Win\n";
		}
		else {
			cout<<"Fight #"<<t<<": "<<"Lose\n";
		}
	}
}

