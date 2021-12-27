/*
Conference Expansion Solution
2018 online UCF High School Programming Tournament

The idea behind this solution is to greedily take the
new employees with the lowest skills first. That way,
every new employee you add will vote in favor of any
future employees you try to add.
*/

#include <stdio.h>
#include <stdlib.h>


int main(int argc, char *argv[]) {
	int t,loop;
	scanf("%d",&t);
	for(loop=1;loop<=t;loop++){
		int o,n,i,j;
		scanf("%d%d",&o,&n);
		
		//originals stores the skills of the original employees
		int originals[o];
		for(i=0;i<o;i++){
			scanf("%d",&originals[i]);
		}
		
		//news stores the skills of the potential employees
		int news[n];
		for(i=0;i<n;i++){
			scanf("%d",&news[i]);
		}
		
		//I use a bubble sort to sort the new employees by their skills.
		for(i=0;i<n;i++){
			for(j=0;j<n-1;j++){
				if(news[j]>news[j+1]){
					int temp=news[j];
					news[j]=news[j+1];
					news[j+1]=temp;
				}
			}
		}
		
		//ans stores the number of new employees I've hired.
		int ans=0;
		for(i=0;i<n;i++){
			//Because we're going in ascending ordering by skills,
			//all new employees that have already been hired have
			//a lower skill rating and will vote yes.
			int votingYes=ans,votingNo=0;
			
			//This checks every original employee to see how they'll vote.
			for(j=0;j<o;j++){
				if(originals[j]<=news[i])
					votingYes++;
				else
					votingNo++;
			}
			
			//If the majority of your current employees want to hire this
			//person, then hire them.
			if(votingYes>votingNo)
				ans++;
		}
		printf("Expansion #%d: %d\n",loop,ans);
	}
	return 0;
}
