cases = int(input())

for case in range(1,cases+1):
    given = str(input())
    #We make use of what is known as Z-values or the Z Algorithm: http://codeforces.com/blog/entry/3107
    #This essentially tells how far of a substring starting at an index is a prefix of the whole string
    def zvalues(string):
        n = len(string)
        z = [0]*n
        z[0]=n
        L = 0
        R = 0
        left = [0]*n
        right = [0]*n
        for i in range(1,n):
            if(i > R):
                L = R = i
                while(R < n and string[R-L]==string[R]):
                    R=R+1
                z[i]=R-L
                R=R-1
            else :
                k = i-L
                if(z[k]<R-i+1):
                    z[i]=z[k]
                else:
                    L=i
                    while(R<n and string[R-L]==string[R]):
                        R=R+1
                    z[i]=R-L
                    R=R-1
            left[i]=L
            right[i]=R
        return z
    vals = zvalues(given)
    ok = False
    #Then in order to see if the pattern works, we just need to find an index that matches sufficiently for us to have had a pattern
    for idx in range(1,len(given)):
        ok = ok or vals[idx]+idx>=len(given)
    if(ok):
        print("OK")
    else:
        print("MESSY")

