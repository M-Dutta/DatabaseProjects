import static java.lang.System.out;

import java.util.concurrent.Callable;

public class PerformanceTesting {

	
	
	  public static void main (String [] args) throws Exception	{
		  PerformanceTesting pt = new PerformanceTesting();
		   out.println ();
	        Table movie = new Table ("movie", "title year length genre studioName producerNo",
	                                          "String Integer Integer String String Integer", "title year");

	        Table cinema = new Table ("cinema", "title year length genre studioName producerNo",
	                                            "String Integer Integer String String Integer", "title year");

	        Table movieStar = new Table ("movieStar", "name address gender birthdate",
	                                                  "String String Character String", "name");

	        Table starsIn = new Table ("starsIn", "movieTitle movieYear starName",
	                                              "String Integer String", "movieTitle movieYear starName");

	        Table movieExec = new Table ("movieExec", "certNo name address fee",
	                                                  "Integer String String Float", "certNo");

	        Table studio = new Table ("studio", "name address presNo",
	                                            "String String Integer", "name");
		  
	        int tupleSize = 50000;
	        int y = 1920;
	        int j = 130;
	        //Populate Movie Table
	        for (int i =0; i < tupleSize; i++) {
	        	String s1 =""; 
	        	String s2 =""; 	
	        	if (i%2 == 0) {s2 ="Fox"; s1 ="action" ;}
	        	else if (i%3 == 0) {s2 ="Universal"; s1 ="sciFi" ;}
	        	else if (i%5 == 0) {s2 ="Universal"; s1 ="action" ;}
	        	else if (i%7 == 0) {s2 ="Fox"; s1 ="sciFi" ;}
	        	else if (i%11 == 0) {s2 ="Warner Bros"; s1 ="action" ;}
	        	else if (i%13 == 0) {s2 ="Dreakworks"; s1 ="fantasy" ;}
	        	else {s2 ="Disney";  s1 = "Animantion";}
	        	
	        	if (j> 200) { j = 127;}
	        	if (y> 2018) { y = 1950;}
	        	
	     	   Comparable [] film6 =  { ("Random"+ Integer.toString(i)), y++, j++, s1, s2, 12123+i*2 };
	     	   movie.insert (film6); 
	        }
	        /** WARNING. YOUR IDE WILL RUN OUT OF CONSOLE SPACE TO PRINT THE TABLE IF THE TUPLE IS TOO BIG
	        movie.print ();    
	        */
	        
	        //Populate Studio Table:
	        for (int i =0; i < 5; i++) {	
	        	String s1 =""; 
	        	String s2 ="";
	        	if (i == 0) {s2 ="Fox"; s1 ="Los_Angeles" ;}
	        	else if (i== 1) {s2 ="Universal"; s1 ="Universal_City" ;}
	        	else if (i == 2) {s2 ="Disney"; s1 ="Burbank" ;}
	        	else if (i == 3) {s2 ="Warner Bros"; s1 ="London" ;}
	        	else {s2 ="DreakWorks";  s1 = "Universal_City";} 	
	
	        	int k = i;
	        	if (k ==0) k=9;

	        Comparable [] studio0 = { s2, s1, k*1111 };
	        studio.insert(studio0);
	        }
	        studio.print();
		  
	        /**
	         * Time Print method example:
	         */
	        //for join
	         System.out.println("NestedLoop runTime: "+movie.joinTime("studioName", "name", studio));
	       //for h_join
	         System.out.println("h_join runTime: " + movie.h_joinTime("studioName", "name", studio));
	         //for POINT Select
	         System.out.println("POINT Select runTime: " +
	        		 movie.selectTime (t -> t[movie.col("title")].equals ("Star_Wars") &&
                     t[movie.col("year")].equals (1977)));   
	         //for RANGE Select
	         System.out.println("RANGE Select runTime: " +movie.selectTime (t -> (Integer) t[movie.col("year")] < 1980));
	         Table t_select = movie.select(t -> (Integer) t[movie.col("year")] < 1980);
	         t_select.print();
	         
	         /** WARNING. YOUR IDE WILL RUN OUT OF CONSOLE SPACE TO PRINT THE TABLE IF THE TUPLE IS TOO BIG
		        t_join.print();   
		        */
		  
	         movie.select (t -> t[movie.col("title")].equals ("Random10") &&
                    t[movie.col("year")].equals (1930));
	      //  t_select.print ();

		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
		  
	  }
	    
}
