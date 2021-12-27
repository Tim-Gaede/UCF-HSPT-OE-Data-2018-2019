#include <stdio.h>
#include <iostream>
#include <vector>
#include <map>
using namespace std;

/*
Solution to Ant Colonies by David Harmeyer.

This solution uses a disjoint set and processes queries in reverse order. If we process queries in reverse, we start with
some seperated nodes, and then combine their trees when a tunnel "uncollapses". We can then see if two nodes can reach
each other by seeing if they have the same parent in the disjoint set.

A disjoint set is a data structure that can do the following opperations very quickly:
-start with n subtrees, each of size 1 (just a single node)
-join two subtrees together
-check the parent of some arbitrary node in the subtree. We can use this to check if two nodes are in the same subtree by
	checking if they have the same parent.
	
An implementation of disjoint sets can be found below.
*/

const int MAX_N=100001;
//if djSet[i] is negative, -djSet[i] is the height of that tree
//if djSet[i] is positive, djSet[i] = the parent of that tree (which might have a further parent)
int *djSet;


//disjoint set class (I forgot how to make classes in C++, so I am just doing a method instead)
int djSetFind(int a) {
	if (djSet[a]<0)
		return a;
	return djSet[a]=djSetFind(djSet[a]);
}

void djSetJoin(int a, int b) {
	a=djSetFind(a);
	b=djSetFind(b);
	if (a==b) return; //already in same tree
	if (-djSet[a]>-djSet[b]) {
		djSet[b]=a;
	}
	else if (-djSet[a]<-djSet[b]) {
		djSet[a]=b;
	}
	else {
		djSet[b]=a;
		djSet[a]--;
	}
}

int djSetSameTree(int a, int b) {
	return djSetFind(a)==djSetFind(b);
}
//end disjoint set class



int main() {
	int T, n;
	cin>>T;
	djSet=new int[MAX_N];
	
	for (int tt=1; tt<=T; tt++) {
		for (int i=0; i<MAX_N; i++)
			djSet[i]=-1;
		cin>>n;
		//build tree
		vector< vector<int> > adj(MAX_N);
		map< pair<int, int>, int > removedEdges;
		for (int i=0; i<n; i++) {
			vector<int> row;
			adj.push_back(row);
		}
		for (int i=0; i+1<n; i++) {
			int from, to;
			cin>>from>>to;
			from--;
			to--;
			adj[from].push_back(to);
			adj[to].push_back(from);
		}
		
		//read in all the queries so that we can process them in reverse order
		int nQueries;
		cin>>nQueries;
		int *queryType=new int[nQueries];
		int *from=new int[nQueries];
		int *to=new int[nQueries];
		int *queryAnswers=new int[nQueries];
		for (int i=0; i<nQueries; i++) {
			cin>>queryType[i]>>from[i]>>to[i];
			from[i]--;
			to[i]--;
			if (queryType[i]==1) {
				removedEdges[make_pair(from[i], to[i])]=1;
				removedEdges[make_pair(to[i], from[i])]=1;
			}
		}
		
		//combine all edges that aren't ever removed
		for (int i=0; i<n; i++) {
			for (int j=0; j<adj[i].size(); j++)  {
				if (!removedEdges.count(make_pair(i, adj[i][j]))) {
					djSetJoin(i, adj[i][j]);
				}
			}
		}
		
		//go through all the queries
		for (int i=nQueries-1; i>=0; i--) {
			if (queryType[i]==1) {
				//if it is query type 1, uncollapse the tunnel
				djSetJoin(from[i], to[i]);
			}
			else {
				//if it is query type 2, check if they are in the same subtree at this time
				queryAnswers[i]=djSetSameTree(from[i], to[i]);
			}
		}
		
		//print out the answer
		cout<<"Colony #"<<tt<<":\n";
		for (int i=0; i<nQueries; i++) {
			if (queryType[i]==1) {
				cout<<"Tunnel from "<<(from[i]+1)<<" to "<<(to[i]+1)<<" collapsed!\n";
			}
			else {
				if (queryAnswers[i]) {
					cout<<"Room "<<(from[i]+1)<<" can reach "<<(to[i]+1)<<"\n";
				}
				else {
					cout<<"Room "<<(from[i]+1)<<" cannot reach "<<(to[i]+1)<<"\n";
				}
			}
		}
		cout<<"\n";
	}
	return 0;
}
