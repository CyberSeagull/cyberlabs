

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
	
    public void calculatioMt()  { 
		try {
    	newacc=cr.getplayerid(pathuri, g);
    	for(int i=0; i<2; i++) {
    		String sadd=pathuri+String.format("/playMt?id=%d&bet=%d&number=%d", newacc.id, 1, i);
		//	System.out.println( sadd );
    		String str = cr.get(sadd);
//    		System.out.println( str );
				
    		StplayMT newplay = g.fromJson(str, StplayMT.class);
	        System.out.println( g.toJson(newplay) );
	        
	        
	        
		} 
		}catch (Exception e) { e.printStackTrace(); }
    	
    }
}
