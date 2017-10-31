/****************************************************************************************
 * @file  Table.java
 *Project-1
 * 
 * @author   John Miller
 * 
 * @Group Members:
 * Mishuk Dutta, Brodie Bohannon, Jinghuan Chen, Landon Smith
 * 
 * @Date: 10-02-2017
 */

import java.io.*;
import java.lang.reflect.Array;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;

import static java.lang.Boolean.*;
import static java.lang.System.out;


/****************************************************************************************
 * This class implements relational database tables (including attribute names, domains
 * and a list of tuples.  Five basic relational algebra operators are provided: project,
 * select, union, minus and join.  The insert data manipulation operator is also provided.
 * Missing are update and delete data manipulation operators.
 */
@SuppressWarnings("rawtypes")
public class Table
       implements Serializable
{
    /** Relative path for storage directory
     */
    private static final String DIR = "store" + File.separator;

    /** Filename extension for database files
     */
    private static final String EXT = ".dbf";

    /** Counter for naming temporary tables.
     */
    private static int count = 0;

    /** Table name.
     */
    private final String name;

    /** Array of attribute names.
     */
    private final String [] attribute;

    /** Array of attribute domains: a domain may be
     *  integer types: Long, Integer, Short, Byte
     *  real types: Double, Float
     *  string types: Character, String
     */
   
	private final Class [] domain;

    /** Collection of tuples (data storage).
     */
	private final List <Comparable []> tuples;

    /** Primary key. 
     */
    private final String [] key;

    /** Index into tuples (maps key to tuple number).
     */
    private final Map <KeyType, Comparable []> index;

    /** The supported map types.
     */
    private enum MapType { NO_MAP, TREE_MAP, LINHASH_MAP, BPTREE_MAP }

    /** The map type to be used for indices.  Change as needed.
     */
    private static final MapType mType = MapType.BPTREE_MAP;
    
    
    /************************************************************************************
     * Make a map (index) given the MapType.
     */
    private static Map <KeyType, Comparable []> makeMap ()
    {
        switch (mType) {
        case TREE_MAP:    return new TreeMap <> ();
        case LINHASH_MAP: return new LinHashMap <> (KeyType.class, Comparable [].class);
        case BPTREE_MAP:  return new BpTreeMap <> (KeyType.class, Comparable [].class);
        default:          return null;
        } // switch
    } // makeMap

    //-----------------------------------------------------------------------------------
    // Constructors
    //-----------------------------------------------------------------------------------

    /************************************************************************************
     * Construct an empty table from the meta-data specifications.
     *
     * @param _name       the name of the relation
     * @param _attribute  the string containing attributes names
     * @param _domain     the string containing attribute domains (data types)
     * @param _key        the primary key
     */  
    public Table (String _name, String [] _attribute, Class [] _domain, String [] _key)
    {
        name      = _name;
        attribute = _attribute;
        domain    = _domain;
        key       = _key;
        tuples    = new ArrayList <> ();
        index	  = makeMap();

    } // primary constructor

    /************************************************************************************
     * Construct a table from the meta-data specifications and data in _tuples list.
     *
     * @param _name       the name of the relation
     * @param _attribute  the string containing attributes names
     * @param _domain     the string containing attribute domains (data types)
     * @param _key        the primary key
     * @param _tuples     the list of tuples containing the data
     */  
    public Table (String _name, String [] _attribute, Class [] _domain, String [] _key,
                  List <Comparable []> _tuples)
    {
        name      = _name;
        attribute = _attribute;
        domain    = _domain;
        key       = _key;
        tuples    = _tuples;
        index     = makeMap();
    } // constructor

    /************************************************************************************
     * Construct an empty table from the raw string specifications.
     *
     * @param _name       the name of the relation
     * @param attributes  the string containing attributes names
     * @param domains     the string containing attribute domains (data types)
     * @param _key        the primary key
     */
    public Table (String _name, String attributes, String domains, String _key)
    {
        this (_name, attributes.split (" "), findClass (domains.split (" ")), _key.split(" "));

        out.println ("DDL> create table " + name + " (" + attributes + ")");
    } // constructor

    //----------------------------------------------------------------------------------
    // Public Methods
    //----------------------------------------------------------------------------------



	/************************************************************************************ 
        IMPLEMENT THIS (DONE)
    /************************************************************************************
     * Project the tuples onto a lower dimension by keeping only the given attributes.
     * Check whether the original key is included in the projection.
     *
     * #usage movie.project ("title year studioNo")
     *
     * @param attributes  the attributes to project onto
     * @return  a table of projected tuples
     */
    public Table project (String attributes)
    {
        out.println ("RA> " + name + ".project (" + attributes + ")");
        String [] attrs     = attributes.split (" ");
        Class []  colDomain = extractDom (match (attrs), domain);
        String [] newKey    = (Arrays.asList (attrs).containsAll (Arrays.asList (key))) ? key : attrs;

        List <Comparable []> rows = new ArrayList <> ();
     
       
      for (int i = 0; i < tuples.size(); i++)     
        {//  looking through tuples 
            rows.add(extract (tuples.get(i), attrs)); // extract Method [look below]
 
        }; 
         
        return new Table (name + count++, attrs, colDomain, newKey, rows);
    } // project

    /************************************************************************************
     * Select the tuples satisfying the given predicate (Boolean function).
     *
     * #usage movie.select (t -> t[movie.col("year")].equals (1977))
     *
     * @param predicate  the check condition for tuples
     * @return  a table with tuples satisfying the predicate
     */
    public Table select (Predicate <Comparable []> predicate)
    {
        out.println ("RA> " + name + ".select (" + predicate + ")");

        return new Table (name + count++, attribute, domain, key,
                   tuples.stream ().filter (t -> predicate.test (t))
                                   .collect (Collectors.toList ()));
    } // select
    
    
    /************************************************************************************
     * Select the tuples satisfying the given predicate (Boolean function).
     *
     * #usage movie.select (t -> t[movie.col("year")].equals (1977))
     * uses INDEX
     *
     * @param predicate  the check condition for tuples
     * @return  a table with tuples satisfying the predicate
     */
    
    
    public Table i_select (Predicate <Comparable []> predicate)
    {
        out.println ("RA> " + name + ".select (" + predicate + ")");
        List <Comparable []> rows = new ArrayList <> ();
        
        for (KeyType value : index.keySet()) {
        	Comparable [] t = index.get(value);
        	if (t!= null) {
        	if (predicate.test (t)) {
        		rows.add(t);
        	}
        }
        }
        
        return new Table (name + count++, attribute, domain, key,
        		rows);
    } // select
    
    
    

    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     * Select the tuples satisfying the given key predicate (key = value).  Use an index
     * (Map) to retrieve the tuple with the given key value.
     *
     * @param keyVal  the given key value
     * @return  a table with the tuple satisfying the key predicate
     */
    public Table select (KeyType keyVal)
    {
        out.println ("RA> " + name + ".select (" + keyVal + ")");
        
        List <Comparable []> rows = new ArrayList <> ();
        
        for (KeyType value : index.keySet()) { //Iterate through the Index
            if (value.equals(keyVal) ) { 
            	rows.add(index.get(keyVal)); // select the matching values
            }
        }
        
        return new Table (name + count++, attribute, domain, key, rows);
    } // select

    /************************************************************************************
     * IMPLEMENTED (DONE)
     * 
     * Union this table and table2.  Check that the two tables are compatible.
     *
     * #usage movie.union (show)
     *
     * @param table2  the rhs table in the union operation
     * @return  a table representing the union
     */
    public Table union (Table table2)
    {
      List <Comparable []> rows = new ArrayList <> ();
        out.println ("RA> " + name + ".union (" + table2.name + ")");
         if (!compatible (table2) || !typeCheck(table2.tuples.get(0)) ) {
              System.out.println("\nThe following table is invalid and will remain empty\n");
        	return new Table (name + count++, attribute, domain, key, rows);
        }

      
        
        rows.addAll(tuples); // Include all tuples in from the referencing Table into rows
        for (Map.Entry<KeyType, Comparable[]> table2key : table2.index.entrySet() ) //Iterate table2 keys
        	if (index.get(table2key.getKey()) == null) {//If referencing table (Table1) has no keys from table 2
        		rows.add(table2key.getValue()); // add the tuple into row
        	}

        return new Table (name + count++, attribute, domain, key, rows);
    } // union

    /************************************************************************************
     * IMPLEMENTED (DONE)
     * 
     * Take the difference of this table and table2.  Check that the two tables are
     * compatible.
     *
     * #usage movie.minus (show)
     *
     * @param table2  The rhs table in the minus operation
     * @return  a table representing the difference
     */
    public Table minus (Table table2)
    {
	List <Comparable []> rows = new ArrayList <> ();
        out.println ("RA> " + name + ".minus (" + table2.name + ")");
        if (!compatible (table2) || !typeCheck(table2.tuples.get(0)) ) {
              System.out.println("\nThe following table is invalid and will remain empty\n");
        	return new Table (name + count++, attribute, domain, key, rows);
        }

        rows.addAll(tuples); // Include all tuples in from the referencing Table into rows.
        for (Map.Entry<KeyType, Comparable[]> table2key : table2.index.entrySet()) //Iterate table2 keys 
        	if (index.get(table2key.getKey()) != null) //If referencing table (Table1) has keys from table 2
        		rows.remove(table2key.getValue()); // remove the tuple
                
        return new Table (name + count++, attribute, domain, key, rows);
    } // minus

    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     *Join this table and table2 by performing an "equi-join".  Tuples from both tables
     * are compared requiring attributes1 to equal attributes2.  Disambiguate attribute
     * names by append "2" to the end of any duplicate attribute name.  Implement using
     * a Nested Loop Join algorithm.
     *
     * #usage movie.join ("studioNo", "name", studio)
     *
     * @param attribute1  the attributes of this table to be compared (Foreign Key)
     * @param attribute2  the attributes of table2 to be compared (Primary Key)
     * @param table2      the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
 
	public Table join (String attributes1, String attributes2, Table table2)
    {
         out.println ("RA> " + name + ".join (" + attributes1 + ", " + attributes2 + ", "
                                               + table2.name + ")");

        String [] t_attrs = attributes1.split (" ");
        String [] u_attrs = attributes2.split (" ");

        List <Comparable []> rows = new ArrayList <> ();
 

        //For each tuple in table1, compare it to each tuple in table2
        this.tuples.stream().forEach(t1Attr -> table2.tuples.stream().filter(t2Attr -> {
    			for(int i = 0; i < t_attrs.length; i++){
    				//check if the values of in the attribute column matches
    				if(!t1Attr[this.col(t_attrs[i])].equals(t2Attr[table2.col(u_attrs[i])]))
    					return false;
    			};
    			return true; //for each matched Attribute value, concat the tuples
    			
    		}).forEach(t2Attr -> rows.add(ArrayUtil.concat(t1Attr,t2Attr))));
        
        List <String> attrs  = new ArrayList<>(Arrays.asList(this.attribute));
        ArrayList <String> dupAttrs = new ArrayList<>();
     
    
        
        //Add "2" to duplicate values. Extract non Duplicate Values in attrs
       for (int i =0; i < table2.attribute.length; i++) {
    	   if (attrs.contains(table2.attribute[i]))
    		   dupAttrs.add(table2.attribute[i]+"2");  //Add "2" to duplicate values.
    	   else          //Extract non Duplicate Values in attrs
    		   attrs.add(table2.attribute[i]);
       }

       String[] attrsa = attrs.toArray(new String[attrs.size()]); // converting to array
       String[] dupAttrsa = dupAttrs.toArray(new String[dupAttrs.size()]); //converting to array

        return new Table (name + count++, ArrayUtil.concat (attrsa, dupAttrsa),
                                          ArrayUtil.concat (domain, table2.domain), key, rows);
    } // join
	
	
	 /************************************************************************************
     * Join this table and table2 by performing an "equi-join".  Same as above, but implemented
     * using an Index Join algorithm.
     *
     * @param attribute1  the attributes of this table to be compared (Foreign Key)
     * @param attribute2  the attributes of table2 to be compared (Primary Key)
     * @param table2      the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
	
	
	
	 public Table i_join (String attributes1, String attributes2, Table table2)
	 	{

		 
		 String [] t_attrs = attributes1.split (" ");
	     String [] u_attrs = attributes2.split (" ");
	     ArrayList <Integer> T1_attLoc = new ArrayList<Integer>();
	     ArrayList <Integer> T2_attLoc = new ArrayList<Integer>();
	     List <Comparable []> rows = new ArrayList <> ();
	     
	     
	     //************************************* Setting up Attribute List*********************** 
         //Eliminating Duplicates
           List <String> attrs  = new ArrayList<>(Arrays.asList(this.attribute));
           ArrayList <String> dupAttrs = new ArrayList<>();
           
           //Add "2" to duplicate values. Extract non Duplicate Values in attrs
          for (int i =0; i < table2.attribute.length; i++) {
       	   if (attrs.contains(table2.attribute[i]))
       		   dupAttrs.add(table2.attribute[i]+"2");  //Add "2" to duplicate values.
       	   else          //Extract non Duplicate Values in attrs
       		   attrs.add(table2.attribute[i]);
          }

          String[] attrsa = attrs.toArray(new String[attrs.size()]); // converting to array
          String[] dupAttrsa = dupAttrs.toArray(new String[dupAttrs.size()]); //converting to array
           
          //************************************************
	     //Insert Attribute location
 
	     for (int i =0; i < t_attrs.length; i++) {
	    	 T1_attLoc.add(this.col(t_attrs[i]));
	    	 T2_attLoc.add(table2.col(u_attrs[i]));
	     }

	     //Compare Attributes from the Tuples by getting the Tuples through the INDEX
	     for (Map.Entry <KeyType, Comparable []> x: index.entrySet ()) {
	    	 for (Map.Entry <KeyType, Comparable []> z : table2.index.entrySet ()) { 
	    		 
	    			boolean b = false; 
	    		 for (int counter = 0; counter < T2_attLoc.size(); counter++) { 
	    			 //Compare indexed Tuple-attributes
	    		 if (x.getValue()[T1_attLoc.get(counter)].equals(z.getValue()[T2_attLoc.get(counter)])) b = true; //Compare each attribute for each tuple for both tables
	    		 else b = false;
	    		 }
	    		 if (b ==true) rows.add(ArrayUtil.concat(x.getValue(),z.getValue())); //add condition satifying tuples and concat them
	    	 }
	     }	 
	
	     return new Table (name + count++, ArrayUtil.concat (attrsa, dupAttrsa),
	               ArrayUtil.concat (domain, table2.domain), key, rows);
	    } // i_join

    
   /************************************************************************************
     * IMPLEMENTED (DONE)
     * Join this table and table2 by performing an "equi-join".  Same as above, but implemented
     * using a Hash Join algorithm.
     *
     * @param attribute1  the attributes of this table to be compared (Foreign Key)
     * @param attribute2  the attributes of table2 to be compared (Primary Key)
     * @param table2      the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */

	public Table h_join (String attributes1, String attributes2, Table table2)
    {
    	String [] u_attrs;
    	String [] t_attrs;
    	 Table smaller;
         Table bigger;
         //Making a check for Tuple sizes so that the smaller is converted into the hast table
         if (this.tuples.size() > table2.tuples.size()) {
        	 smaller = table2; bigger = this;
        	 t_attrs = attributes1.split (" ");
             u_attrs = attributes2.split (" ");	 
         }
         else {smaller = this; bigger = table2;
          u_attrs = attributes1.split (" ");
          t_attrs = attributes2.split (" ");
         }
    	
    	
    	out.println ("RA> " + name + ".join (" + attributes1 + ", " + attributes2 + ", "
                                               + table2.name + ")");

         //************************************* Setting up Attribute List*********************** 
         //Eliminating Duplicates
           List <String> attrs  = new ArrayList<>(Arrays.asList(this.attribute));
           ArrayList <String> dupAttrs = new ArrayList<>();
           
           //Add "2" to duplicate values. Extract non Duplicate Values in attrs
          for (int i =0; i < table2.attribute.length; i++) {
       	   if (attrs.contains(table2.attribute[i]))
       		   dupAttrs.add(table2.attribute[i]+"2");  //Add "2" to duplicate values.
       	   else          //Extract non Duplicate Values in attrs
       		   attrs.add(table2.attribute[i]);
          }

          String[] attrsa = attrs.toArray(new String[attrs.size()]); // converting to array
          String[] dupAttrsa = dupAttrs.toArray(new String[dupAttrs.size()]); //converting to array
           
          //************************************************
        
          List <Comparable []> rows = new ArrayList <> ();
          String t1_k="";
          String t2_k="";
          Hashtable <String, Comparable [] > ht = new Hashtable<String, Comparable [] >();
          
          
          //Setting up Keys and creating a hashTable         
          Set<String> tkeys = ht.keySet(); 
          for (int i = 0; i < smaller.tuples.size(); i++) {
        	  for (int j = 0; j <t_attrs.length ; j++) {
        		  t1_k += smaller.tuples.get(i)[smaller.col(u_attrs[j])];
        		  for(String keys: tkeys)	{ // Check for similar keys and re-asses the key
            		  if (keys.equals(t1_k))
            			  t1_k+="2";
        		  }
        	  }
         ht.put(t1_k, smaller.tuples.get(i));  //Putting key and Value into HashTable
         t1_k ="";							//Empty out t1_k
         }      
          
         for (int i = 0; i < bigger.tuples.size(); i++) {
       	  for (int j = 0; j <t_attrs.length ; j++) {
       		  t2_k += bigger.tuples.get(i)[bigger.col(t_attrs[j])];
       	  }
        	 //Check for equality through hashTable
        	 for(String keys: tkeys)	{
        		 	
        		 if (keys.equals(t2_k) || keys.equals(t2_k+"2"))
        		rows.add(ArrayUtil.concat(bigger.tuples.get(i),ht.get(keys))  );
        	 }
        	 t2_k ="";
         }  	     


       return new Table (name + count++, ArrayUtil.concat (attrsa, dupAttrsa),
               ArrayUtil.concat (domain, table2.domain), key, rows);
        //return null;
    } // h_join IMPLEMENTED(DONE)

    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     * Join this table and table2 by performing an "natural join".  Tuples from both tables
     * are compared requiring common attributes to be equal.  The duplicate column is also
     * eliminated.
     *
     * #usage movieStar.join (starsIn)
     *
     * @param table2  the rhs table in the join operation
     * @return  a table with tuples satisfying the equality predicate
     */
    public Table join (Table table2)
    {
out.println ("RA> " + name + ".join (" + table2.name + ")");

        List <Comparable []> rows = new ArrayList <> ();
        /*
         * Making a list of all the non duplicate Attributes
         */
        List <String> attrs  = new ArrayList<>(Arrays.asList(this.attribute));
        for (int i =0; i < table2.attribute.length; i++) {
        	if (!attrs.contains(table2.attribute[i]))
        		attrs.add(table2.attribute[i]);
        }
        //Converting ArrayList to an Array
        String [] attrsa = attrs.toArray(new String[attrs.size()]);
        
        //Checking tuple sizes to avoid null pointer error
        if (this.tuples.size() >= table2.tuples.size())
        {
        	//Only adding tuples that match
        	 for (KeyType key : table2.index.keySet()) {
        		 if (table2.index.get(key)!=null && index.get(key)!= null ) {
        		 if (table2.index.get(key).equals(index.get(key))) 
        			 rows.add(extract(index.get(key), attrsa));
        		 }
        	 }	
        }
      //Checking tuple sizes to avoid null pointer error
        if (this.tuples.size() < table2.tuples.size()) {
        	//Only adding tuples that match
        	for (KeyType key : this.index.keySet()) {
       		 if (this.index.get(key).equals(table2.index.get(key))) 
       			 rows.add(extract(index.get(key), attrsa));
       	 	}	
        }

        // FIX - eliminated duplicate columns (removed concat added a single array)
        return new Table (name + count++, attrsa,
                ArrayUtil.concat (domain, table2.domain), key, rows);
    } // join

    /************************************************************************************
     * Return the column position for the given attribute name.
     *
     * @param attr  the given attribute name
     * @return  a column position
     */
    public int col (String attr)
    {
        for (int i = 0; i < attribute.length; i++) {
           if (attr.equals (attribute [i])) return i;
        } // for

        return -1;  // not found
    } // col

    /************************************************************************************
     * Insert a tuple to the table.
     *
     * #usage movie.insert ("'Star_Wars'", 1977, 124, "T", "Fox", 12345)
     *
     * @param tup  the array of attribute values forming the tuple
     * @return  whether insertion was successful
     */
    public boolean insert (Comparable [] tup)
    {
        //out.println ("DML> insert into " + name + " values ( " + Arrays.toString (tup) + " )");

        if (typeCheck (tup)) {
            tuples.add (tup);
            Comparable [] keyVal = new Comparable [key.length];
            int []        cols   = match (key);
            for (int j = 0; j < keyVal.length; j++) keyVal [j] = tup [cols [j]];
            if (mType != MapType.NO_MAP) index.put (new KeyType (keyVal), tup);
            return true;
        } else {
            return false;
        } // if
    } // insert

    /************************************************************************************
     * Get the name of the table.
     *
     * @return  the table's name
     */
    public String getName ()
    {
        return name;
    } // getName

    /************************************************************************************
     * Print this table.
     */
    public void print ()
    {
        out.println ("\n Table " + name);
        out.print ("|-");
        for (int i = 0; i < attribute.length; i++) out.print ("---------------");
        out.println ("-|");
        out.print ("| ");
        for (String a : attribute) out.printf ("%15s", a);
        out.println (" |");
        out.print ("|-");
        for (int i = 0; i < attribute.length; i++) out.print ("---------------");
        out.println ("-|");
        for (Comparable [] tup : tuples) {
            out.print ("| ");
            for (Comparable attr : tup) out.printf ("%15s", attr);
            out.println (" |");
        } // for
        out.print ("|-");
        for (int i = 0; i < attribute.length; i++) out.print ("---------------");
        out.println ("-|");
    } // print

    /************************************************************************************
     * Print this table's index (Map).
     */
    public void printIndex()
    {
        out.println ("\n Index for " + name);
        out.println ("-------------------");
        if (mType != MapType.NO_MAP) {
            for (Map.Entry <KeyType, Comparable []> e : index.entrySet ()) {
                out.println (e.getKey () + " -> " + Arrays.toString (e.getValue ()));
            } // for
        } // if
        out.println ("-------------------");
    } // printIndex

    /************************************************************************************
     * Load the table with the given name into memory. 
     *
     * @param name  the name of the table to load
     */
    public static Table load (String name)
    {
        Table tab = null;
        try {
            ObjectInputStream ois = new ObjectInputStream (new FileInputStream (DIR + name + EXT));
            tab = (Table) ois.readObject ();
            ois.close ();
        } catch (IOException ex) {
            out.println ("load: IO Exception");
            ex.printStackTrace ();
        } catch (ClassNotFoundException ex) {
            out.println ("load: Class Not Found Exception");
            ex.printStackTrace ();
        } // try
        return tab;
    } // load

    /************************************************************************************
     * Save this table in a file.
     */
    public void save ()
    {
        try {
            ObjectOutputStream oos = new ObjectOutputStream (new FileOutputStream (DIR + name + EXT));
            oos.writeObject (this);
            oos.close ();
        } catch (IOException ex) {
            out.println ("save: IO Exception");
            ex.printStackTrace ();
        } // try
    } // save

    //----------------------------------------------------------------------------------
    // Private Methods
    //----------------------------------------------------------------------------------

    /************************************************************************************
     * Determine whether the two tables (this and table2) are compatible, i.e., have
     * the same number of attributes each with the same corresponding domain.
     *
     * @param table2  the rhs table
     * @return  whether the two tables are compatible
     */
    private boolean compatible (Table table2)
    {
        if (domain.length != table2.domain.length) {
            out.println ("compatible ERROR: table have different arity");
            return false;
        } // if
        for (int j = 0; j < domain.length; j++) {
            if (domain [j] != table2.domain [j]) {
                out.println ("compatible ERROR: tables disagree on domain " + j);
                return false;
            } // if
        } // for
        return true;
    } // compatible

    /************************************************************************************
     * Match the column and attribute names to determine the domains.
     *
     * @param column  the array of column names
     * @return  an array of column index positions
     */
    private int [] match (String [] column)
    {
        int [] colPos = new int [column.length];

        for (int j = 0; j < column.length; j++) {
            boolean matched = false;
            for (int k = 0; k < attribute.length; k++) {
                if (column [j].equals (attribute [k])) {
                    matched = true;
                    colPos [j] = k;
                } // for
            } // for
            if ( ! matched) {
                out.println ("match: domain not found for " + column [j]);
            } // if
        } // for

        return colPos;
    } // match

    /************************************************************************************
     * Extract the attributes specified by the column array from tuple t.
     *
     * @param t       the tuple to extract from
     * @param column  the array of column names
     * @return  a smaller tuple extracted from tuple t 
     */
    private Comparable [] extract (Comparable [] t, String [] column)
    {
        Comparable [] tup = new Comparable [column.length];
        int [] colPos = match (column);
        for (int j = 0; j < column.length; j++) tup [j] = t [colPos [j]];
        return tup;
    } // extract

   /************************************************************************************
     *IMPLEMENTED
     * 
     * Check the size of the tuple (number of elements in list) as well as the type of
     * each value to ensure it is from the right domain. 
     *
     * @param t  the tuple as a list of attribute values
     * @return  whether the tuple has the right size and values that comply
     *          with the given domains
     * 
     */
    private boolean typeCheck (Comparable [] t)
    { 
    	if (domain.length != t.length) {
            out.println ("tuple ERROR: incorrect size");
            return false;
        } // if
        for (int j = 0; j < domain.length; j++) {
            if (domain [j] != t[j].getClass()) {
                out.println ("tuple ERROR: tuble and table disagree on domain " + j);
                return false;
            } // if
        } // for
        return true;
    } // typeCheck
    
    /************************************************************************************
     *IMPLEMENTED
     * 
     * return the number of tuples of the table 

     * @return  return the number of tuples of the table 
     * 
     */
    @SuppressWarnings("unused")
	private int returnSize () {
    	return this.tuples.size();
    }
    
    /************************************************************************************
     * Find the classes in the "java.lang" package with given names.
     *
     * @param className  the array of class name (e.g., {"Integer", "String"})
     * @return  an array of Java classes
     */
    private static Class [] findClass (String [] className)
    {
        Class [] classArray = new Class [className.length];

        for (int i = 0; i < className.length; i++) {
            try {
                classArray [i] = Class.forName ("java.lang." + className [i]);
            } catch (ClassNotFoundException ex) {
                out.println ("findClass: " + ex);
            } // try
        } // for

        return classArray;
    } // findClass

    /************************************************************************************
     * Extract the corresponding domains.
     *
     * @param colPos the column positions to extract.
     * @param group  where to extract from
     * @return  the extracted domains
     */
    private Class [] extractDom (int [] colPos, Class [] group)
    {
        Class [] obj = new Class [colPos.length];

        for (int j = 0; j < colPos.length; j++) {
            obj [j] = group [colPos [j]];
        } // for

        return obj;
    } // extractDom
    
    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     *Returns runtime for the h_join method
     *
     * #usage movie.h_joinTime ("studioNo", "name", studio)
     *
     * @param attribute1  the attributes of this table to be compared (Foreign Key)
     * @param attribute2  the attributes of table2 to be compared (Primary Key)
     * @param table2      the rhs table in the join operation
     * @return Runtime for h_join
     */
 
    
    public long h_joinTime(String attributes1, String attributes2, Table table2) {
		long start = System.currentTimeMillis();
		
		h_join(attributes1, attributes2, table2);
	    long end = System.currentTimeMillis(); 
	    long duration = end -start;
	    System.out.println ("h_join Runtime in Milli-Seconds: ");
	    return duration;
	}	
    
    
    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     *Returns runtime for the h_join method
     *
     * #usage movie.i_joinTime ("studioNo", "name", studio)
     *
     * @param attribute1  the attributes of this table to be compared (Foreign Key)
     * @param attribute2  the attributes of table2 to be compared (Primary Key)
     * @param table2      the rhs table in the join operation
     * @return Runtime for i_join
     */
    
    public long i_joinTime(String attributes1, String attributes2, Table table2) {
		long start = System.currentTimeMillis();
		
		h_join(attributes1, attributes2, table2);
	    long end = System.currentTimeMillis(); 
	    long duration = end -start;
	    System.out.println ("i_join Runtime in Milli-Seconds: ");
	    return duration;
	}	
    
    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     *Returns runtime for the join method
     *
     * #usage movie.joinTime ("studioNo", "name", studio)
     *
     * @param attribute1  the attributes of this table to be compared (Foreign Key)
     * @param attribute2  the attributes of table2 to be compared (Primary Key)
     * @param table2      the rhs table in the join operation
     * @return  Runtime for join
     */
    
    /************************************************************************************
     *IMPLEMENTED (DONE)
     *
     * Returns runtime for the join method
     * 
     * #usage movieStar.join (starsIn)
     *
     * @param table2  the rhs table in the join operation
     * @return  Runtime for join
     */

    public long joinTime(Table table) {
		long start = System.currentTimeMillis();
		this.join(table);
	    long end = System.currentTimeMillis(); 
	    long duration = end - start;
	    System.out.println ("Join Runtime in Milli-Seconds: ");
	    return duration;
	}	
    
    public long joinTime(String attributes1, String attributes2, Table table2) {
		long start = System.currentTimeMillis();
		h_join(attributes1, attributes2, table2);
	    long end = System.currentTimeMillis(); 
	    long duration = end - start;
	    System.out.println ("Join Runtime in Milli-Seconds: ");
	    return duration;
	}	
    

    /************************************************************************************
     * Select the tuples satisfying the given predicate (Boolean function).
     *
     * #usage movie.selectTime (t -> t[movie.col("year")].equals (1977))
     *
     * @param predicate  the check condition for tuples
     * @return  Runtime for select
     */
    
    public long selectTime(Predicate <Comparable []> predicate) {
    	long start = System.currentTimeMillis();
    	select(predicate);
    	 long end = System.currentTimeMillis(); 
 	    long duration = end - start;
 	   System.out.println ("Select Runtime in Milli-Seconds: ");
 	    return duration;
    }
          
    /************************************************************************************
     * Select the tuples satisfying the given predicate (Boolean function).
     *
     * #usage movie.selectTime (t -> t[movie.col("year")].equals (1977)) for INDEXED select
     *
     * @param predicate  the check condition for tuples
     * @return  Runtime for i_select
     */
    
    public long i_selectTime(Predicate <Comparable []> predicate) {
    	long start = System.currentTimeMillis();
    	i_select(predicate);
    	long end = System.currentTimeMillis(); 
 	    long duration = end - start;
 	   System.out.println ("Indexed Select Runtime in Milli-Seconds: ");
 	    return duration;
    }
     


} // Table class
