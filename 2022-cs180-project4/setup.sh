#!/bin/sh

rm -f -- *.txt

javac *.java

# accounts

echo "2
a
a
customer
8" | java Market

echo "2
b
b
seller
10" | java Market

echo "2
c
d
customer
8" | java Market

echo "2
e
f
seller
10" | java Market

# items

## bb items

echo "1
b
b
3
2
cat
pet
fish
11
2222.22
.80
44
10" | java Market

echo "1
b
b
3
2
dog
pet
bone
99
88.88
.23
66
10" | java Market

echo "1
b
b
3
2
wolf
pet2
bone but BAD
999
888.88
.23
666
10" | java Market

## ef items

echo "1
e
f
3
2
fish
water
gold
20
30.30
.60
5
10" | java Market

# purchases

echo "1
a
a
3
1
1
4
10
1
3
4
100
4
2
8" | java Market

echo "1
c
d
3
1
2
4
50
4
2
8" | java Market

echo "1
a
a
3
1
3
4
10
1
4
4
10
8" | java Market

echo "1
c
d
3
1
4
4
10
8" | java Market
