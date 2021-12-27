from math import pi, sin, cos, sqrt

T = int(input())
for t in range(1,T+1):
    n, r = map(int,input().split())
    s = input()
    numred=0
    numgreen=0
    numblue=0
    reds = []
    greens = []
    blues = []
    for i in range(0, n):
        c=s[i]
        if(c=='R'):
            numred=numred+1
            reds.append(i)
        if(c=='B'):
            numblue=numblue+1
            blues.append(i)
        if(c=='G'):
            numgreen=numgreen+1
            greens.append(i)
    #Using sin and cosine, we can determine the location of a given button around the laptop
    def calcdist(a,b):
        if(a==-1 or b==-1):
            return 0
        aangle = 2*pi*a/n
        bangle = 2*pi*b/n
        ax = r*cos(aangle)
        ay = r*sin(aangle)
        bx = r*cos(bangle)
        by = r*sin(bangle)
        deltax = ax-bx
        deltay = ay-by
        dist = sqrt(deltax*deltax + deltay*deltay)
        return dist
    memo = {}
    #This is a fairly classic problem: The Traveling Salesman problem
    #We maintain what is known as a "bitmask" or "mask", allowing us to maintain whether or not a location has been visited
    #You can think of it like using a number as a boolean array
    def findpath(cur, mask, need, color):
        if((cur,mask,need,color) in memo):
            return memo[(cur,mask,need,color)]
        oldmask = mask
        if(cur!=-1):
            mask = mask | (1<<cur)
        if(need==0):
            if(color=='R'):
                return findpath(cur,mask,numgreen,'G')
            if(color=='G'):
                return findpath(cur,mask,numblue,'B')
            return 0
        cheapest = 1000000000000
        for next in range(0,n):
            if(s[next]!=color):
                continue
            if((mask & (1<<next))!=0):
                continue
            cost = calcdist(cur,next)+findpath(next,mask,need-1,color)
            cheapest = min(cheapest,cost)
        memo[(cur,oldmask,need,color)]=cheapest
        return cheapest
    answer = "{0:.3f}".format(findpath(-1,0,numred,'R'))
    print("Scenario #",t,": ",answer,sep="")
    



