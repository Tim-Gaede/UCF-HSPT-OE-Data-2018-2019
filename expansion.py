#Conference Expansion
#Solution by David Harmeyer

#The first key observation to make is that a greedy solution is always optimal.
#That is, it is never bad to add the lowest-skill team that it is possible to add
#right away. If, instead, you add a higher-skilled team now, that team will vote against
#your lower-skilled team later when you try to add it. Also, if you add teams in
#increasing order, you can always keep adding more (if the last one made it, a better one
#certainly will because it will have no more votes against it, and at least one more vote
#it by the team you just added).

#By doing this, we can easily make an O(o*(o+n)) naive solution that will run in about 2048^2 time
#in the worst case. This is probably fast enough for most reasonable languages, but Python
#is notoriously slow, so this solution can be improved. If we know the lowest-skill team that
#we can add in the beginning, we can simply count the number of teams with a skill of that or
#greater, since we can add them in increasing order. Therefore, we just have to count the number
#of elements in the array of new teams greater than or equal to the (o)/2th element in the
#array of original teams once it is sorted. This can be done in a single sweep through the
#new teams after sorting the original teams, so the time complexity of this solution is
#  O(n + o*log(o)), which is plenty fast enough when n and o are both <= 2048

t=int(input())
for scenario in range(1, t+1):
	o,n=map(int, input().split())#read input
	original=list(map(int, input().split()))
	newTeams=list(map(int, input().split()))
	original.sort()#sort the original teams by value to find the cut-off value
	count=0;
	for i in newTeams:
		if i>=original[int(o/2)]:#Count the number of teams greater or equal to the cut-off value
			count+=1
	print("Expansion #"+str(scenario)+": "+str(count))
	
	