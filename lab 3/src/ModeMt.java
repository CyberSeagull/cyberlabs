

import com.google.gson.Gson;

public class ModeMt {
	static final int bet = 100; 
	Gson g = new Gson();
	String pathuri;
	clientR cr;
	Craccount newacc;
	public ModeMt(String pathuri){
		this.pathuri=pathuri;
		 cr= new clientR();
	}
	
    public void calculationMt()  { 
		try {
    	newacc=cr.getplayerid(pathuri, g);
    		String sadd=pathuri+String.format("/playMt?id=%d&bet=%d&number=%d", newacc.id, 1, 5);
			System.out.println( sadd );
    		String str = cr.get(sadd);
    		System.out.println( str );
    		StplayMT newplay = g.fromJson(str, StplayMT.class);
            long seed = System.currentTimeMillis()/1000 - 7;

            RandomMt mt= new RandomMt();
            long realNumber = newplay.realNumber;

            mt.seed_mt((int) seed);
            long  mtNumber=Integer.toUnsignedLong(mt.getnumber());
            while ( mtNumber != realNumber ) {
            	mt.seed_mt((int)++seed);
            	 mtNumber=Integer.toUnsignedLong(mt.getnumber());
                System.out.println(mtNumber+"   "+realNumber+" seed: " + seed);
            }
            
	        System.out.println( g.toJson(newplay) );
	        
	        
		 
		}catch (Exception e) { e.printStackTrace(); }
    	
    }
}
