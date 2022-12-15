// D test program ack.d  CSE413 6/99, rev 5/00, 2/02, 11/06

// Ackerman's function grows tremendously fast as its arguments
// become gradually larger.  The definition is
//               A(1,j) = 2 ^ j for j >= 1
//               A(i,1) = A(i-1,2) for i >=2
//               A(i,j) = A(i-1,A(i,j-1)) i,j >= 2

// = 2^n
int powerOf2(int n) {
   int acc ;
   int k ;
   
   acc = 1;
   k = 1;

   while(!(k > n)) {
	  acc = acc * 2;
	  k = k + 1;
   }
   return acc;
}

// = ackerman function of x & y [A(x,y)]
int acker(int x, int y) {

   if (x==1)
      return (powerOf2(y));
   else if (y==1)
      return acker(x-1,2);
   else
      return acker(x-1,acker(x,y-1));
   return 0;
}


int main()
{
   int a;
   int b;
   int out;

   a = get();
   b = get();
   out = acker(a,b);
   out = put(out);
   return 1;
}

