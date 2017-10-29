 
/*****************************************************************************************
 * @file  TestTupleGenerator.java 
 *
 * @author   Sadiq Charaniya, John Miller
 */

import static java.lang.System.out;

/*****************************************************************************************
 * This class tests the TupleGenerator on the Student Registration Database defined in the
 * Kifer, Bernstein and Lewis 2006 database textbook (see figure 3.6).  The primary keys
 * (see figure 3.6) and foreign keys (see example 3.2.2) are as given in the textbook.
 */
public class TestTupleGenerator
{
    /*************************************************************************************
     * The main method is the driver for TestGenerator.
     * @param args  the command-line arguments
     */
    public static void main (String [] args)
    {
        TupleGenerator test = new TupleGeneratorImpl ();

        test.addRelSchema ("Student",
                           "id name address status",
                           "Integer String String String",
                           "id",
                           null);
        
        test.addRelSchema ("Professor",
                           "id name deptId",
                           "Integer String String",
                           "id",
                           null);
        
        test.addRelSchema ("Course",
                           "crsCode deptId crsName descr",
                           "String String String String",
                           "crsCode",
                           null);
        
        test.addRelSchema ("Teaching",
                           "crsCode semester profId",
                           "String String Integer",
                           "crcCode semester",
                           new String [][] {{ "profId", "Professor", "id" },
                                            { "crsCode", "Course", "crsCode" }});
        
        test.addRelSchema ("Transcript",
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester",
                           new String [][] {{ "studId", "Student", "id"},
                                            { "crsCode", "Course", "crsCode" },
                                            { "crsCode semester", "Teaching", "crsCode semester" }});

        String [] tables = { "Student", "Professor", "Course", "Teaching", "Transcript" };
        
        /**
         * Generator 1
         * Comment this out if you're using generator 2.
         * 	NOTE: FINAL SUBMISSION MUST USE THIS (AKA Generator 1);
         */
        
        int tups [] = new int [] { 10000, 1000, 2000, 50000, 5000 };
    
        /**
         * Generator 2
         * use this ONLY for Initial test to see if Program works as intended
         * NOTE: FINAL SUBMISSION MUST USE Generator 1;
         */
        //int tups [] = new int [] { 100, 100, 200, 50, 50 };
        
        Comparable [][][] resultTest = test.generate (tups);


        Table Student = new Table("Student", "id name address status",
                "Integer String String String",
                "id");
        
        Table Professor = new Table ("Professor",
                "id name deptId",
                "Integer String String",
                "id" );
        Table Course = new Table("Course",
                           "crsCode deptId crsName descr",
                           "String String String String",
                           "crsCode");
        
        Table Teaching= new Table("Teaching",
                "crsCode semester profId",
                "String String Integer",
                "crsCode semester");
        Table Transcript = new Table("Transcript",
                           "studId crsCode semester grade",
                           "Integer String String String",
                           "studId crsCode semester");
        
        
        /*******************'
         * Generate Students
         */
        for (int i = 0; i < tups[0]; i++) {
        Student.insert(resultTest[0][i]);
        }

        
        /*******************'
         * Generate Professor
         */
        for (int i = 0; i < tups[1]; i++) {
        	Professor.insert(resultTest[1][i]);
        }


        /*******************'
         * Generate Course
         */
        for (int i = 0; i < tups[2]; i++) {
        	Course.insert(resultTest[2][i]);
        }

            
        /*******************'
         * Generate Teaching
         */
        for (int i = 0; i < tups[3]; i++) {
        Teaching.insert(resultTest[3][i]);
        }


        /*******************'
         * Generate Transcript
         */
        for (int i = 0; i < tups[4]; i++) {
        Transcript.insert(resultTest[4][i]);
        }
    
        /**
         * TESTING TO SEE IF THIS CLASS WORKS  (Comment out if tested valid);
         * 
         
        Table h_join =Student.h_join ("id", "studId", Transcript);
        Table t_join =Student.h_join ("id", "studId", Transcript);

        
        Table point_select = Student.select ( t -> t[Student.col("id")].equals ("809530") &&
                t[Student.col("status")].equals ("status290070") );
        Table range_select =  Student.select (t -> (Integer) t[Student.col("id")] < 200000);

        
        /*****************************************END TESTING*****************************************/
        
        /**
         * TESTING RUNTIME
         * 
         */
        System.out.println(Student.h_joinTime ("id", "studId", Transcript));
        
        System.out.println(Student.joinTime ("id", "studId", Transcript));
        
        System.out.println (Student.selectTime ( t -> t[Student.col("id")].equals ("809530") &&
                t[Student.col("status")].equals ("status290070")) );
        
        
        System.out.println(Student.selectTime (t -> (Integer) t[Student.col("id")] < 200000)) ;
        
       
   
   
        
        
        
        System.out.println ("-----------------END-------------------------");
     // main
    
    }
    
} // TestTupleGenerator
