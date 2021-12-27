/*
HSPT Online 2017: Nails (c++)
Author: Alex Coleman
*/

#include <stdio.h>
#include <vector>

#define maxn 80

int abs(int v) {
	return v < 0 ? -v : v;
}
int main(void) {
	// We will solve for all the test cases at once, since our solution requires induction anyway
	std::vector<int> ans[maxn+1];
	// Base case: if n=1 we can place a single L1
	// We will use -i to represent L[i], and i to represent R[i]
	ans[1].push_back(-1);
	int frequency[maxn+1];
	for(int i=2;i<=maxn;i++) {
		// Find out how frequent each number was in the previous list
		for(int j = 1; j < i; j++)
			frequency[j] = 0;
		for(int k = 0; k < ans[i-1].size(); k++) {
			frequency[abs(ans[i-1][k])]++;
		}
		// Find out what the least frequent number was
		// Note this will be i - highestOneBit(i-1) because of how we construct, but we do not need this speedup
		int minFrequency = 1000000000, minId = -1;
		for(int j = 1; j < i; j++){
			if(frequency[j] < minFrequency) {
				minFrequency = frequency[j];
				minId = j;
			}
		}
		
		// Now we will go through the previous answer
		
		// Every time we see our selected number, we will add our new number wrapped around it next to it
		// i.e. if we have ..A L[i-1] ..B.. R[i-1] ..C where B contains no [i-1] elements, we want this to turn into
		//  ..A L[i-1] L[i] R[i-1] R[i] ..B.. L[i] L[i-1] R[i] R[i-1] C..
		//
		
		// We must show that now for any removal of [j], the whole string disappears (noting that any L[i]R[i] disappears)
		// Any choice of [j] < i will still work, and also if we remove [i] then after some cancelling
		// we are left with ..A L[i-1] ..B.. R[i-1] ..C which we know cancels out (because of previous iteration)
		// Thus this is a valid strategy. We choose the minimum frequency because we add a new amount proportional to old frequency
		// It can be shown empirically that this solution is optimal
		
		// Now we go through the last list and keep everything, but insert our new bundles into the appropriate spots
		for(int k = 0; k < ans[i-1].size(); k++) {
			int v = ans[i-1][k];
			if(v < 0)
				ans[i].push_back(v);
			if(abs(v) == minId) {
				ans[i].push_back(-i);
				ans[i].push_back(-v);
				ans[i].push_back(i);
			}
			if(v > 0)
				ans[i].push_back(v);
		}
	}
	
	// Now we can just print out the appropriate list for each case
	int T, n;
	scanf("%d", &T);
	for(int t=1;t<=T;t++) {
		scanf("%d", &n);
		printf("Picture #%d: %d", t, ans[n].size());
		for(int k = 0; k < ans[n].size(); k++) {
			int v = ans[n][k];
			printf(" %c%d", v < 0 ? 'L' : 'R', abs(v));
		}
		printf("\n");
	}
	return 0;
}
