#Poker Hands
#Solution by David Harmeyer

#To solve this problem, we need to see if the first player's hand comes before
#the second player's hand in a list of hands ordered by how good they are.
#Since the number of possible hands is extremely small (exactly 9), we don't need
#to worry about efficiency for this problem.

#The problem statement also guarantees that there will be no ties, so Ryan wins if his hand is
#better, otherwise Tyler wins.

hands=["Straight flush", "Four of a kind", "Full house", "Flush", "Straight", "Three of a kind", "Two pair", "Pair", "High card"]

g=int(input())
for game in range(1, g+1):
	ryansHand=input()
	tvhsHand=input()
	print("Game #"+str(game)+": "+("Ryan" if (hands.index(ryansHand)<hands.index(tvhsHand)) else "Tyler"))