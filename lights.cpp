#include <iostream>
#include <string.h>
using namespace std;

/*
Christmas Lights
Solution written by David Harmeyer

This problem is super easy to solve if you know what z-values are. If you don't know what
z-values are, well, good luck. Here is a tutorial on what z-values are if you are interested:
https://www.youtube.com/watch?v=CpZh4eF8QBw

Suppose the string has a period of 4. The string could look like
v   v   v   v
ABCDABCDABCDA...

ABCD ABCD ABCD A... = ABCD + ABCD ABCD A...
(so cutting off the first 4 characters is the same as cutting off the last 4)

Notice that if it can be divided into sections of length 4, then the characters from index 3 to the end of
the string are the same as the characters from index 0 to 4 less than the end. This is true for
any period length. We can therefore brute-force the period length and check if this property is true in constant
time with our precomputed z values.

This solution runs in O(n) time. (O(n) to computer z-values + O(n) to run the brute force)

*/

//calculates the zValues for a given string of length n
void calculateZValues(char* lights, int n, int* zValues) {
	zValues[0]=n;
	int l=0, r=0;
	for (int i=1; i<n; i++) {
		if (i>r) {
			l=r=i;
			while (r<n&&lights[r]==lights[r-i])
				r++;
			
			zValues[i]=r-l;
			r--;
		}
		else {
			int k=i-l;
			if (zValues[k]<r-i+1) {
				zValues[i]=zValues[k];
			}
			else {
				l=i;
				while (r<n&&lights[r-l]==lights[r])
					r++;
				zValues[i]=r-l;
				r--;
			}
		}
	}
}

int main() {
	int numberOfStrings;
	cin>>numberOfStrings;
	for (int stringNum=1; stringNum<=numberOfStrings; stringNum++) {
		char lights[1000001];
		int zValues[1000001];
		cin>>lights;
		int n=strlen(lights);
		calculateZValues(lights, n, zValues);
		bool works=false;
		for (int periodLength=1; periodLength<n; periodLength++)
			if (periodLength+zValues[periodLength]==n)
				works=true;
		cout<<(works?"OK":"MESSY")<<"\n";
	}
}



