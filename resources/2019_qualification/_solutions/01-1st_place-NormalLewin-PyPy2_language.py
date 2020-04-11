t = int(raw_input())

def solve():
    s = raw_input()
    a = ""
    for i in xrange(len(s)):
        if s[i] == '4':
            a += '1'
        else:
            a += '0'
    return "%s %s" % (int(a), int(s)-int(a))

    
for __ in xrange(t):
    print "Case #%d: %s" % (__+1, solve())