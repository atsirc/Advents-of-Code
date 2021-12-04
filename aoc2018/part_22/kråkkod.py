def fs(n):
    a, b = 0, 1
    f=[0]
    for i in range(n):
        a, b = b, a + b
        f.append(a)
    return f
f=fs(1000)
print(f)