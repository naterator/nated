// intdiv.d
// Integer software division for when you want to divide integers softly.
// I believe this is all legal D but I seem to have misplaced my compiler.
// Andrew Pardoe  CSE 413  5/00

int div (int a, int b) {
  int div;
  int count;
  count = 1;
  div = count * b;
  while (!(div > a)) {
    count = count + 1; 
    div = count * b;	
  }
  return (count-1);
}

int mod (int a, int b) {
  int div;
  int count;
  count = 1;
  div = count * b;
  while (!(div > a)) {
    count = count + 1; 
    div = count * b;	
  }
  return a - b * (count-1);
}

int main()         {
             int d; int m;
           int num; int den;
        num = get(); den = get();
  d = div(num, den); m = mod(num, den);
         d = put(d); m = put(m);
               return 0;
                   }

