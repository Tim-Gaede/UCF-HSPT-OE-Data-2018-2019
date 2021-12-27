# Elli Howard
# 2018 HSPT Online Competion
# Hyper Even Numbers

# Pre-calculate the hyper even numbers
# Only powers of two are hyper even
curr = 2
evens = [2]
while curr <= 1000000000:
    curr *= 2
    evens.append(curr)

# Read in the number of cases to consider
cases = int(input())

# Deal with each case
for i in range(1, cases+1):
    level = int(input())
    dist = 0
    for j in evens:
        if j > level:
            dist = j-level
            break;

    print("Pokemon {}: {}".format(i, dist))
