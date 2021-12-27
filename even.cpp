/*
	even.cpp
	for the UCF High School Programming Tournament,
	Spring 2018

	by Charles Bailey
	03 04 2018

	g++ -o even.out --std=c++0x even.cpp
*/
#include <bits/stdc++.h>

typedef long long ll;

static void one_case(const ll& num) {
	ll level;
	std::scanf("%lld",&level);

	/*
		Calculate the _next_ power of two and then subtract from that the current level.
		We add 1 to level in the log2 call to avoid getting zero as an answer if level
		is already a power of two.
	*/
	ll ans = (1LL << static_cast<ll>(std::ceil(std::log2(level+1))) ) - level;

	std::printf("Pokemon %lld: %lld\n", num, ans);
}

int main(void) {
	/* enter each test case. */
	ll t; std::scanf("%lld",&t);
	for (ll i = 1; i <= t; ++i) one_case(i);
	return 0;
}
