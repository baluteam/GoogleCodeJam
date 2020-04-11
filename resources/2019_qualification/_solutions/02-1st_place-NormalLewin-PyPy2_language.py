t = int(raw_input())

def solve():
    n = int(raw_input())
    s = raw_input()

    r = []
    for i in xrange(2*(n-1)):
        r.append('E' if s[i] == 'S' else 'S')
    return ''.join(r)

    
for __ in xrange(t):
    print "Case #%d: %s" % (__+1, solve())