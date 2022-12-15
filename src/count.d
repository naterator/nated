// D test program count.d  CSE413 5/00, 11/06

// Count from 0 to 20, tediously
// Test expressions and statements

int main() {
  int a; int b; int c;
  int i; int j; int k;
  int n; int // bad line breaks to confuse
                out // buggy compilers
;

  // expressions with constants
  out = put(0);                     // 0
  out = put(1);                     // 1
  out = put(1+1);                   // 2
  out = put(5-2);                   // 3
  out = put(6+3-5);                 // 4
  out = put(7-1-1);                 // 5
  out = put(2*3);                   // 6
  out = put(3+2*2);                 // 7

  // assignments and expressions with variables
  k = 8;
  n = put(k);                       // 8
  out = put(n+1);                   // 9
  a = 2*n-6;
  a = put(a)+2;                     // 10
  b = 2;
  c = 4;
  b = b*c+b;
  out = put(b+1);                   // 11
  a = put(a);                       // 12

  // control flow

  if (b > c)
    k = 13;
  k = put(k);                       // 13

  if (b==c) {
    j = put(0-1);
  } else
    j = put(2*7);                   // 14

  if (c+1 == c)
    out = put(0-2);

  if (!(c > a)) {
    j = 3;
    i = c*j + j;
    out = ((put(i)));               // 15
  }

  c = 2;
  while (!(c == 16))
    c = c+c;
  c = put(c);                       // 16

  j = 6; k = 10;
  a = 1; b = 0;
  if (j*k+a-b > a+b) {
    n = j+k+a+b;
    n = put(n);                     // 17
  } else {
    n = 42;
    n = put(n);
  }

  a = 20; c = 16; b = 1;
  while(!(c == a-2))
    if (!(a>b))
      c = 0;
    else
      c = c+1;
  out = put(c);                     // 18

  n = 16;
  if (n > 0) {
    while (19 > n) {
      n = n + 1;
    }
  } else
    n = 25;
  n = put(n);                       // 19

  a = 5;  b = 30;
  while (b > a) {
    a = a+a;
    b = b-a;
  }
  n = put(a);                       // 20


  return 0;
}
