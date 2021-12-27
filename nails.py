def Neg(l):
    return [-x for x in l]

def Rev(l):
    l.reverse()
    return Neg(l)

def Printable(x):
    if x < 0:
        return "R"+(str(abs(x)))
    return "L"+(str(abs(x)))

def PrintableList(l):
    return [Printable(x) for x in l]

t = int(input())
for testcase in range(0,t):
    n = int(input())
    lastassigned = 1
    def solve(n):
        if n == 1:
            global lastassigned
            l = [lastassigned]
            lastassigned = lastassigned+1
            return l
        leftsize = n//2
        rightsize = n - leftsize
        left = solve(leftsize)
        right = solve(rightsize)
        leftrev = left.copy()
        rightrev = right.copy()
        leftrev = Rev(leftrev)
        rightrev = Rev(rightrev)
        ans = []
        ans.extend(left)
        ans.extend(right)
        ans.extend(leftrev)
        ans.extend(rightrev)
        return ans
    ans = PrintableList(solve(n))
    line = []
    line.append("Picture #"+str(testcase+1)+": "+str(len(ans)))
    line.extend(ans)
    print(' '.join(line))
