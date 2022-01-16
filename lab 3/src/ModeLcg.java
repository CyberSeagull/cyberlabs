import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

class Lcgac{
	long a; long c;
	public Lcgac(long a, long c){
		this.a=a;
		this.c=c;
	}
}

public class ModeLcg {
	private static final long m  = (long) Math.pow(2, 32); 
	private static final int bet = 100; 
    List<Stplay> m_play  = new ArrayList<>();
	Gson g = new Gson();
	String pathuri;
	clientR cr;
	Craccount newacc;

	public ModeLcg(String pathuri){
		this.pathuri=pathuri;
		 cr= new clientR();
	}
	
    private int getnextnumber(long _last, Lcgac ac) 
	{
    	return (int) ((_last*ac.a+ac.c) % m);
	}
    
    static long ModInverse(long a, long m)
    {
    	long m0 = m;
    	long y = 0, x = 1;
        if (m == 1)  return 0;
        while (a > 1) {
        	long q = a / m;
        	long t = m;
            m = a % m; a = t;
            t = y;
            y = x - q * y;
            x = t;
        }
        if (x < 0)
            x += m0;
        return x;
    }    

    private Lcgac calculation_a_c() throws Throwable { 
    	newacc=cr.getplayerid(pathuri, g);
    	long a=0, c=0;
    	for (int i=0; i<3; i++) {
    		String sadd=pathuri+String.format("/playLcg?id=%d&bet=%d&number=%d", newacc.id, 1, clientR.rnd(555));
    	  	Stplay newplay = g.fromJson(cr.get(sadd), Stplay.class);
    	  	newplay.reallong=Integer.toUnsignedLong(newplay.realNumber);
    	  	m_play.add(newplay);
    	  	if (i>=2) {
    	  		a=m_play.get(i).reallong-m_play.get(i-1).reallong;
        	if (a<0)
//        		a=Integer.toUnsignedLong(  (int) a);
    		    a=  (int) (a)+m;
        	long modinv=m_play.get(i-1).reallong-m_play.get(i-2).reallong;
        	if (modinv<0)
        		modinv =(int) (modinv)+m;
        	
        	modinv = ModInverse(modinv,m);
        	
        	a=Integer.toUnsignedLong(  (int) ((a*modinv)% m) );
        	c=Integer.toUnsignedLong(  (int) (m_play.get(i-1).reallong-m_play.get(i-2).reallong*a) );

        	System.out.println("a= "+a+"   c="+c);
    	}
    	
    	}
    	
        Lcgac ac = new Lcgac(a, c);
		return ac;
    }
	
    public Stplay game() {
    	Stplay newplay = null;
		try { 
			Lcgac ac= calculation_a_c();
	    	newplay = m_play.get(m_play.size()-1);
	    	for (int i=0; i<17; i++) { 
				int realn = getnextnumber(newplay.reallong, ac);
	    	    String sadd=pathuri+String.format("/playLcg?id=%d&bet=%d&number=%d", newplay.account.id, bet, realn);
    //	        System.out.println( sadd );
	            String str;
				str = cr.get(sadd );
	            System.out.println( str );
	            if (  (str.indexOf("error")>0)  )
	            	break;
	            newplay = g.fromJson(str, Stplay.class);
	            newplay.reallong=Integer.toUnsignedLong(newplay.realNumber);
	            System.out.println( g.toJson(newplay) );
	            if (newplay.account.money>=1000000) break;
			} 
		} catch (Exception e) {e.printStackTrace();} catch (Throwable e) { e.printStackTrace(); 
		}
		System.out.println( g.toJson(newplay) );
		return newplay;
    }

}
