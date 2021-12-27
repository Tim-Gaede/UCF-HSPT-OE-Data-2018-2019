#We make use of a data structure known as disjoint sets in this problem: https://www.topcoder.com/community/data-science/data-science-tutorials/disjoint-set-data-structures/
class DisjointSet:
    def __init__(self,N):
        self.parent = []
        self.rank = [0]*N
        for i in range(0,N):
            self.parent.append(i)
    def find(self,i):
        if(self.parent[i]==i):
            return i
        self.parent[i] = self.find(self.parent[i])
        return self.parent[i]

    def union(self,a, b):
        x = self.find(a)
        y = self.find(b)
        if(x!=y):
            if(self.rank[x]>self.rank[y]):
                self.parent[y]=x
            else:
                self.parent[x]=y
                if(self.rank[x]==self.rank[y]):
                    self.rank[y]=self.rank[y]+1

cases = int(input())
for i in range(1,cases+1):
    nodes = int(input())
    graph = []
    #Build the graph
    for j in range(0,nodes):
        graph.append({})
    for j in range(0,nodes-1):
        a,b = map(int,input().split())
        a = a-1
        b = b-1
        graph[a][b]=True
        graph[b][a]=True
    events = int(input())
    eventlist = []
    #The key is to process the queries in reverse. Then instead of deleting hallways, we can add them instead
    for j in range(0,events):
        a,b,c = map(int,input().split())
        eventlist.append((a,b,c))
    for j in range(0,events):
        type = eventlist[j][0]
        a = eventlist[j][1]-1
        b = eventlist[j][2]-1
        #At the end, any hallways that will have collapsed no longer exist        
        if(type==1):
            graph[a][b]=False
            graph[b][a]=False
    ds = DisjointSet(nodes)
    for x in range(0,nodes):
        for y in graph[x]:
            if(graph[x][y]):
                ds.union(x,y)
    eventlist.reverse()
    outputlist = []
    #Now we can process the queries in reverse. The disjoint sets represent parts of the colony where all rooms can reach each other.
    for j in range(0,events):
        type = eventlist[j][0]
        a = eventlist[j][1]-1
        b = eventlist[j][2]-1
        if(type==1):
            ds.union(a,b)
            outputlist.append("Tunnel from "+str(a+1)+" to "+str(b+1)+" collapsed!")
        if(type==2):
            x = ds.find(a)
            y = ds.find(b)
            if(x==y):
                outputlist.append("Room "+str(a+1)+" can reach "+str(b+1))
            if(x!=y):
                outputlist.append("Room "+str(a+1)+" cannot reach "+str(b+1))
    outputlist.reverse()
    print("Colony #"+str(i)+":")
    for x in outputlist:
        print(x)
    print()
