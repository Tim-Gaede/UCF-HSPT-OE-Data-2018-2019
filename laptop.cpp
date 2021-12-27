/*
	laptop.cpp
	for the UCF High School Programming Tournament,
	(Online) December 2018

	by Charles Bailey
	03 04 2018

	g++ -o laptop.out --std=c++0x laptop.cpp
*/

#include <bits/stdc++.h>

typedef long long ll;

enum COLOR {
	RED,
	GREEN,
	BLUE
};

struct point {
	double x,y;
	double distance(const point& other) const {
		return std::sqrt( (x - other.x)*(x - other.x) + (y - other.y)*(y - other.y));
	}
};

static double one_case(void) {
	ll n,r;
	std::scanf("%lld %lld", &n, &r);

	std::vector<COLOR> colors(n);
	std::vector<ll> counts(3); /* count occurence of each color */
	char tmp;
	for (auto& c : colors) {
		std::scanf(" %c", &tmp);
		if (tmp == 'R')
			c = COLOR::RED;
		else if (tmp == 'G')
			c = COLOR::GREEN;
		else
			c = COLOR::BLUE;
		counts[c]++;
	}

	std::vector<point> points(n);
	double tot = 2 * std::acos(-1) / n;
	for (ll i = 0; i < n; ++i) {
		points[i].x = r * std::cos(tot * i);
		points[i].y = r * std::sin(tot * i);
	}

	double total_interior_angle = (n - 2) * std::acos(-1) /* PI */;
	double one_interior_angle = total_interior_angle / n;

	std::vector<std::vector<double>> dp(n, std::vector<double>(1 << n, std::numeric_limits<double>::max()));
	dp[0][0] = 0;
	for (ll i = 0; i < n; ++i)
		// if (colors[i] == RED)
			dp[i][1 << i] = 0;

	std::vector<ll> tmp_color;
	for (ll mask = 1; mask < (1<<n); ++mask) {
		tmp_color.assign(3,0);
		for (ll i = 0; i < n; ++i) {
			if (mask & (1 << i)) {
				tmp_color[colors[i]]++;
			}
		}

		bool have_all_reds = tmp_color[RED] == counts[RED];
		bool have_all_green = tmp_color[GREEN] == counts[GREEN];

		bool cant_have_green = !have_all_reds;
		bool cant_have_blue = cant_have_green || !have_all_green;

		bool invalid = (cant_have_green && tmp_color[GREEN] != 0) || (cant_have_blue && tmp_color[BLUE] != 0);
		if (invalid) {
			// std::printf("%s is invalid.\n", std::bitset<16>(mask).to_string().data());
			continue;
		}

		for (ll i = 0; i < n; ++i) {
			if ( !(mask & (1 << i)) )
				continue;
			for (ll j = 0; j < n; ++j) {
				if ( (mask & (1<<j)) || (cant_have_blue && colors[j] == BLUE) || (cant_have_green && colors[j] == GREEN) )
					continue;
				dp[j][mask | (1<<j)] = std::min(dp[j][mask | (1<<j)], dp[i][mask] + points[i].distance(points[j]) );
			}
		}
	}

	double min = std::numeric_limits<double>::max();
	for (ll i = 0; i < n; ++i)
		min = std::min(min, dp[i].back());

	return min;
}

int main(void) {
	ll t;
	std::scanf("%lld",&t);
	for (ll i = 1; i <= t; ++i)
		std::printf("Scenario #%lld: %.3f\n", i, one_case());
	return 0;
}
