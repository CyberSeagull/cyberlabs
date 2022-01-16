public class CasinoR {
	public static void main(String[] args) {
		String pathuri="http://95.217.177.249/casino"; 

        System.out.println( "=================== MT ===========================================" );
        ModeMt mt = new ModeMt(pathuri);
		StplayMT lastMT= mt.calculationMt();
        System.out.println( "<<<<<<<<<<<<<<<<<    MT  MONEY = "+lastMT.account.money+"   >>>>>>>>>>>>>>>>>>>" );
		
        System.out.println( "=================== Lcg ==========================================" );
		ModeLcg lcg = new ModeLcg(pathuri);
		Stplay lastlcg=lcg.game();
        System.out.println( "<<<<<<<<<<<<<<<<<    Lcg  MONEY = "+lastlcg.account.money+"   >>>>>>>>>>>>>>>>>>>" );
        
	   
	}

}
