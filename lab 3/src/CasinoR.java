import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class CasinoR {
	
	
	public static void main(String[] args) {
			
		 String pathuri="http://95.217.177.249/casino"; 
		 System.out.println( "=================== MT ===========================================" );
		 ModeMt mt = new ModeMt(pathuri);
	        mt.calculationMt();
			
	        System.out.println( "=================== Lcg ==========================================" );
			ModeLcg lcg = new ModeLcg(pathuri);
	        System.out.println( "<<<<<<<<<<<<<<<<<    Lcg  MONEY = "+lcg.game().account.money+"   >>>>>>>>>>>>>>>>>>>" );
	}

}
