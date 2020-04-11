import sys
t = int(raw_input())

def solve():
    n,b,f = map(int, raw_input().split())
    nb = [[0 for __ in xrange(n)] for ___ in xrange(f)]
    for i in xrange(n):
        r = i & 31
        for j in xrange(5):
            nb[j][i] = (r>>j)&1
    for j in xrange(5): print "".join(map(str,nb[j]))
    sys.stdout.flush()

    bs = [map(int, raw_input()) for __ in xrange(5)]
    want = 0
    bads = []
    idx = 0
    for i in xrange(n):
        r = 0
        for j in xrange(5):
            if idx < len(bs[j]):
                r |= (bs[j][idx]) << j
        if r != want or idx == len(bs[j]):
            bads.append(i)
        else:
            idx += 1
        want = (want + 1) & 31

    print ' '.join(map(str, bads))
    sys.stdout.flush()
    raw_input()

for __ in xrange(t):
    solve()