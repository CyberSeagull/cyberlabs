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
	ñlientR cr;
	Craccount newacc;

	public ModeLcg(String pathuri){
		this.pathuri=pathuri;
		 cr= new clientR();
	}
	
    private int getnextnumber(long _last, Lcgac ac) 
	{
    	return (int) ((_last*ac.a+ac.c) % m);
	}

    private Lcgac calculation_a_c() throws Throwable { 
    	newacc=cr.getplayerid(pathuri, g);
    	String sadd=pathuri+String.format("/playLcg?id=%d&bet=%d&number=%d", newacc.id, 1, clientR.rnd(555));
		String str = cr.get(sadd);
  		Stplay newplay = g.fromJson(str, Stplay.class);
       	newplay.reallong=Integer.toUnsignedLong(newplay.realNumber);
      	m_play.add(newplay);
        Lcgac ac = new Lcgac(111,222);
		return ac;
    }
	
    public Stplay game() {
    	Stplay newplay = null;
		try { 
			Lcgac ac= calculation_a_c();
	    	newplay = m_play.get(m_play.size()-1);
	    	for (int i=0; i<2; i++) { 
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
