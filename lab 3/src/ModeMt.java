import com.google.gson.Gson;


public class ModeMt {
	static final int bet = 100;
	Gson g = new Gson();
	String pathuri;
	clientR cr;
	Craccount newacc;
	public ModeMt(String pathuri) {
		this.pathuri = pathuri;
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

            RandomMT mt= new RandomMT();

            long realNumber = newplay.realNumber;
            mt.seed_mt((int) seed);
            long  mtNumber = Integer.toUnsignedLong(mt.getnumber());
            int i = 0;

            while ( mtNumber != realNumber ) {
            	mt.seed_mt((int)++seed);
            	mtNumber=Integer.toUnsignedLong(mt.getnumber());
                i++;
                System.out.println(mtNumber+"   "+realNumber+" seed: " + seed);
                if (i>100) {
                	seed =- 1;
                	break;
                }
            }

//  if seed<0 return seed;
            do {
        		mtNumber=Integer.toUnsignedLong(mt.getnumber());
            	sadd=pathuri+String.format("/playMt?id=%d&bet=%d&number=%d", newacc.id, bet, mtNumber);
    			System.out.println( sadd );
    			str = cr.get(sadd);

				if (str.indexOf("lost")>0)
					System.out.println("-----------------------------");
				System.out.println( str );
				newplay = g.fromJson(str, StplayMT.class);
				if (newplay.account.money>1000000)
					System.out.println( "<<<<<<<<<<<<<<<<<    MT  MONEY = "+newplay.account.money+"   >>>>>>>>>>>>>>>>>>>" );

			} while (newplay.account.money<1000000);


		} catch (Exception e) { e.printStackTrace(); }

    }
}
