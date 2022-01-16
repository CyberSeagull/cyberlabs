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
	Craccount myacc;

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
    	long  a=m_play.get(2).reallong-m_play.get(1).reallong;
    	if (a<0) a=(int) (a)+m;
    	long modinv=m_play.get(1).reallong-m_play.get(0).reallong;
    	if (modinv<0) modinv =(int) (modinv)+m;
    	a=Integer.toUnsignedLong(  (int) ((a*ModInverse(modinv,m))% m) );
    	long c=Integer.toUnsignedLong(  (int) (m_play.get(1).reallong-m_play.get(0).reallong*a) );
    	System.out.println("a= "+a+"   c="+c);
        Lcgac ac = new Lcgac(a, c);
		return ac;
    }
	
    public Stplay game() {
    	Stplay newplay = null;
    	try { 
    		if (m_play.size()>0)
            	for(int i=m_play.size()-1; i>=0; i--)
            		m_play.remove(m_play.get(i));
    		myacc=cr.getplayerid(pathuri, g);
        	for (int i=0; i<3; i++) {
        		String sadd=pathuri+String.format("/playLcg?id=%d&bet=%d&number=%d", myacc.id, 1, clientR.rnd(555));
        	    newplay = g.fromJson(cr.get(sadd), Stplay.class);
        		newplay.reallong=Integer.toUnsignedLong(newplay.realNumber);
        		m_play.add(newplay);
        	}
			Lcgac ac= calculation_a_c();
	    	newplay = m_play.get(m_play.size()-1);
	    	for (int i=0; i<17; i++) { 
				int realn = getnextnumber(newplay.reallong, ac);
	    	    String sadd=pathuri+String.format("/playLcg?id=%d&bet=%d&number=%d", newplay.account.id, bet, realn);
	            String str = cr.get(sadd );
	            //System.out.println( str );
	            if ( str.indexOf("error")>0 )
	            	break;
	            newplay = g.fromJson(str, Stplay.class);
	            newplay.reallong=Integer.toUnsignedLong(newplay.realNumber);
	           // System.out.println( g.toJson(newplay) );//
	            if (newplay.account.money>=1000000) break;
			} 
		} catch (Exception e) {e.printStackTrace();} catch (Throwable e) { e.printStackTrace(); 
		}
		//System.out.println( g.toJson(newplay) );
		return newplay;
    }
	
}
