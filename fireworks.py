# Elli Howard
# 2018 HSPT Online Competion
# Fireworks

# Read in the number of cases to consider
cases = int(input())

# Deal with each case
for i in range(1, cases+1):
    fireworks = int(input())

    earliest = 0
    earliest_t = 100000000000000
    highest = 0
    highest_d = 0

    fired = input().split()

    for f in range(0, fireworks):
        velocity = int(fired[f])
        time = velocity / 9.81
        dist = .5*-9.81*time*time + velocity*time

        if time < earliest_t:
            earliest = f
            earliest_t = time

        if dist > highest_d:
            highest = f
            highest_d = dist

    print("Case #{}:".format(i))
    print("  Highest Firework: {}".format(highest+1))
    print("  Earliest Firework: {}".format(earliest+1))
    print()
