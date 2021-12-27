/*
C++ solution to fireworks.cpp by David Harmeyer

The key observation to solving this problem is that we don't need to know how long each firework is
in the air (or how high they go), but just which one goes the highest and which is in the air for
the least amount of time. Knowing this, it is clear that the firework with the largest initial velocity
will reach the highest point, and the firework with the lowest initial velocity will peak the earliest.
(That is, we don't need to dirty ourselves with messy physics equations and calculus if we don't want to.)

While we are on the subject of physics and gravity, it is a common misconception that if you throw a point mass 
in a vaccuum on Earth that the object will travel in a downward-opening parabola. This isn't actually true
at all; it travels in an elipse until it hits the ground. For it to travel in a parabola, the Earth would have
to be flat!

*/

#include <stdio.h>
#include <iostream>
#define MAX_N 2001
using namespace std;

int main() {
	int a[MAX_N], T, n;
	cin>>T;
	for (int t=1; t<=T; t++) {
		cin>>n;
		for (int i=0; i<n; i++)
			cin>>a[i];
		int minIndex=0, maxIndex=0;
		for (int i=0; i<n; i++) {
			if (a[i]<a[minIndex]) minIndex=i;
			if (a[i]>a[maxIndex]) maxIndex=i;
		}
		cout<<"Case #"<<t<<":\n  Highest Firework: "<<maxIndex+1<<"\n  Earliest Firework: "<<minIndex+1<<"\n\n";
	}
}
