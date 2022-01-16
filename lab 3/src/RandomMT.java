
public class RandomMT {
	private final static int  w = 32;
	private final static int  n = 624;
	private final static int  m = 397;
	private final static int  r = 31;
	private final static int  a = 0x9908B0DF;
	private final static int  u = 11;
	private final static int  d = 0xFFFFFFFF;
	private final static int  s = 7;
	private final static int  b = 0x9D2C5680;
	private final static int  t = 15;
	private final static int  c = 0xEFC60000;
	private final static int  l = 18;
	private final static long f = 1812433253l;
	private final static int  upper_mask      = 0x80000000;	
	private final static int  lower_mask      = 0x7fffffff;
	private int[] mt =new int [n]; 
	private int index =n; 

	// Initialize the generator from a seed
	public void seed_mt(int seed) {
        index=n;
		mt[0]=seed;
		for (int i=1; i<n; i++) {
			 mt[i] = (int) ( (f * (mt[i-1] ^ (mt[i-1] >>> w-2)) + i) & 0xFFFFFFFFl);
		}
	}
	public int getnumber() {
	     if (index >= n) {
	         if (index > n) 
	           System.out.println( "ERROR MT not initial");
	         twist();	     
	         }
	     int y= mt[index];
	     y^=  ((y >>> u) & d);
	     y^=  ((y << s) & b);
	     y^=  ((y << t) & c);
 	     y^=  (y >>> l);
	     index++;
		return y;
	}
	public void twist() {
		for (int i=0; i<n; i++ ) {
			int x = (mt[i] & upper_mask)+mt[(i+1) %n] & lower_mask; 
			int xA = x>>>1;
      	    if (x % 2 !=0)
      	    	xA = xA^a;
      	    mt[i]=mt[(i+m) % n] ^xA;
			
		}
	     index = 0;		
	}

}
