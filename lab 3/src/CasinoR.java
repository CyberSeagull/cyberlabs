import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CasinoR {
	
	public static int rnd(int max) // return range [0;max]
	{	return (int) (Math.random() * ++max);
	}

	public static void main(String[] args) {
		List<Stplay> m_play = new ArrayList<>();
		// TODO Auto-generated method stub
		 long m=(long) Math.pow(2, 32);
         int id;
		 Gson g = new Gson();
 		 clientR cr= new clientR();		
		 String suri="http://95.217.177.249/casino/createacc?id="; 
		 String str=null;
		try {
			do 
			{   id=rnd(1000000);
				str=cr.get(suri+String.format("%d", id) );
				System.out.println( str );
				if ( !( (str.indexOf("error")>0) || (str==null)) ) {
					 Craccount newacc = g.fromJson(str, Craccount.class);
					 System.out.println( g.toJson(newacc) );
					 System.out.println(newacc.id);
					 break;
				}
			} while (true);

        	for(int i=1; i<4; i++) {
			suri="http://95.217.177.249/casino/play"; 
			String sadd=String.format("Lcg?id=%d&bet=%d&number=%d", id,i,7);
			sadd=suri+sadd;
			System.out.println( sadd );
			str=cr.get(sadd );
			System.out.println("getstr= "+ str );
			
			
			Stplay newplay = g.fromJson(str, Stplay.class);
			newplay.reallong= (newplay.realNumber>=0) ? newplay.realNumber:newplay.realNumber+m; 
			m_play.add(newplay);
			System.out.println( g.toJson(newplay) );
			 System.out.println("realNumber="+newplay.realNumber);
			 System.out.println("longNumber="+newplay.reallong);
        	}
			 System.out.println("  ========================================== ");
			
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
