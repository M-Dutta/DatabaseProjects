/************************************************************************************
 * @file LinHashMap.java
 *
 * @author  John Miller
 */

import java.io.*; 
import java.lang.reflect.Array;
import static java.lang.System.out;
import java.util.*;


/************************************************************************************
 * This class provides hash maps that use the Linear Hashing algorithm.
 * A hash table is created that is an array of buckets.
 */
public class LinHashMap <K, V>
       extends AbstractMap <K, V>
       implements Serializable, Cloneable, Map <K, V>
{
    /** The number of slots (for key-value pairs) per bucket.
     */
    private static final int SLOTS = 4;

    /** The class for type K.
     */
    private final Class <K> classK;

    /** The class for type V.
     */
    private final Class <V> classV;

    /********************************************************************************
     * This inner class defines buckets that are stored in the hash table.
     */
    private class Bucket
    {
        int    nKeys;
        K []   key;
        V []   value;
        Bucket next;
        @SuppressWarnings("unchecked")
        Bucket (Bucket n)
        {
            nKeys = 0;
            key   = (K []) Array.newInstance (classK, SLOTS);
            value = (V []) Array.newInstance (classV, SLOTS);
            next  = n;
        } // constructor
    } // Bucket inner class

    /** The list of buckets making up the hash table.
     */
    private final List <Bucket> hTable;

    /** The modulus for low resolution hashing
     */
    private int mod1;

    /** The modulus for high resolution hashing
     */
    private int mod2;

    /** Counter for the number buckets accessed (for performance testing).
     */
    private int count = 0;

    /** The index of the next bucket to split.
     */
    private int split = 0;

    /********************************************************************************
     * Construct a hash table that uses Linear Hashing.
     * @param classK    the class for keys (K)
     * @param classV    the class for keys (V)
     * @param initSize  the initial number of home buckets (a power of 2, e.g., 4)
     */
    public LinHashMap (Class <K> _classK, Class <V> _classV)//, int initSize)
    {
        classK = _classK;
        classV = _classV;
        hTable = new ArrayList <> ();
        mod1   = 4;
        mod2   = 2 * mod1;
        
        // adding empty buckets to hash table
        for(int i=0; i<mod1; i++){
        	hTable.add(new Bucket(null));
        }
        
    } // constructor

    /********************************************************************************
     * Return a set containing all the entries as pairs of keys and values.
     * @return  the set view of the map
     */
    public Set <Map.Entry <K, V>> entrySet ()
    {
    	Set <Map.Entry <K, V>> enSet = new HashSet <> ();

    	for( int i=0; i<hTable.size(); i++ ){ // loop through bucket
    		Bucket temp = hTable.get(i);
    		for( int j=0; j<temp.nKeys; j++ ){ // loop through values and keys
    			enSet.add(new AbstractMap.SimpleEntry<>( temp.key[j],temp.value[j]) );
    			//add keys and values
    		}
    	} // end for loop
    	return enSet;
    } // entrySet

    /********************************************************************************
     * Given the key, look up the value in the hash table.
     * @param key  the key used for look up
     * @return  the value associated with the key
     */
    public V get (Object key)
    {
    	int i = h (key);
    	if (i <0)	i=i*(-1);
    	
    	if(i<split){
    		i=h2(key);
    		if (i <0)	i=i*(-1);
    	}
    	Bucket temp = hTable.get(i);
    	if( temp.nKeys==0 ) return null; // check emptiness

    	else  	// get the value linked to the key
    	{ 
    	   while(temp!=null)
    	   {
    		   count++; // increment to bucket counter
    		   for( int j=0; j<temp.nKeys; j++ )
    		   {
    			   if( key.equals(temp.key[j]) )	return temp.value[j];

    		   } //for
    		   temp = temp.next; // if key not found, move to next
    	   } // while
    	} // else

    	return null; 
    } // get 	(DONE)

    /********************************************************************************
     * Put the key-value pair in the hash table.
     * @param key    the key to insert
     * @param value  the value to insert
     * @return  null (not the previous value)
     */
    public V put (K key, V value)
    {
       int i = h (key);
       if (i <0)	i=i*(-1);

       if(i<split){
              i=h2(key);
              if (i <0)	i=i*(-1);
       }
      
       Bucket temp = hTable.get(i);
       if( temp.nKeys < SLOTS ) // Noi split Necessary
       {  
              temp.key[temp.nKeys] = key;
              temp.value[temp.nKeys] = value;
              temp.nKeys++;
       } // end if
       
       else
       { // split required
    	   hTable.add(new Bucket(null));
    	   while(temp.next != null)
    		   temp = temp.next;
    	   
    	   if(temp.nKeys < SLOTS)
    	   {
    		   temp.key[temp.nKeys] = key;
    		   temp.value[temp.nKeys] = value;
    		   temp.nKeys++;
    	   }
    	   
    	   else
    	   { // add to new bucket
    		   temp.next = new Bucket(null);
    		   temp = temp.next;
    		   temp.key[temp.nKeys]=key;
    		   temp.value[temp.nKeys]=value;
    		   temp.nKeys++;
    	   }

    	   Bucket replacer = new Bucket(null); // bucket to replace split
    	   Bucket newTemp = new Bucket(null); // new bucket
    	   temp = hTable.get(split + 1); //the bucket to split
    	   for(int m = 0; m<temp.nKeys; m++){
    		   int i2 = h2(temp.key[m]);
    		   if(i2 == split){ // splitting time
    			   if(replacer.next ==null){
    				   replacer.next = new Bucket(null);
    				   replacer.next = replacer;
    			   }   
    			   replacer.key[replacer.nKeys] = temp.key[m];
    			   replacer.value[replacer.nKeys] = temp.value[m];
    			   temp.nKeys++;
    		   } 
    		   
    		   else
    		   { // new bucket time
    			   if(newTemp.next==null)
    			   {
    				   newTemp.next = new Bucket(null);
    				   newTemp = newTemp.next;
    			   }
    			   newTemp.key[newTemp.nKeys] = temp.key[m];
    			   newTemp.value[newTemp.nKeys] = temp.value[m];  	
    		   } 
    	   } 
    	   // update split
    	   if(split == mod1-1)
    	   { 
    		   split = 0;
    		   mod1 = mod1*2;
    		   mod2 = mod1*2;
    	   }
    	   
    	   else // +1 if split < mod1
    		   split++;
    	   
       } // end else     
             
        return null;
    } // put (DONE)

    /********************************************************************************
     * Return the size (SLOTS * number of home buckets) of the hash table. 
     * @return  the size of the hash table
     */
    public int size ()
    {
        return SLOTS * (mod1 + split);
    } // size

    /********************************************************************************
     * Print the hash table.
     */
    private void print ()
    {
    	out.println ("Hash Table (Linear Hashing) - Entries shown as Key:Value");
    	out.println ("-------------------------------------------");
    	
    	for(int i=0; i<hTable.size(); i++)
    	{
    		Bucket temp = hTable.get(i);
    		boolean chain = false;
    		out.println("Bucket:"+ i+"\n");
    		for( int j=0; j<SLOTS; j++ )
    			{
    				out.print(temp.key[j]+":"+temp.value[j]+ "\t");

    			}
    			out.println("\n");
    	}

    	out.println ("-------------------------------------------");
    
   } // print		(DONE)

    /********************************************************************************
     * Hash the key using the low resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h (Object key)
    {
        return key.hashCode () % mod1;
    } // h

    /********************************************************************************
     * Hash the key using the high resolution hash function.
     * @param key  the key to hash
     * @return  the location of the bucket chain containing the key-value pair
     */
    private int h2 (Object key)
    {
        return key.hashCode () % mod2;
    } // h2

  /********************************************************************************
     * The main method used for testing.
     * @param  the command-line arguments (args [0] gives number of keys to insert)
     */
    public static void main (String [] args)
    {

        int totalKeys    = 30;
        boolean RANDOMLY = false;

        LinHashMap <Integer, Integer> ht = new LinHashMap <> (Integer.class, Integer.class);
        if (args.length == 1) totalKeys = Integer.valueOf (args [0]);

        if (RANDOMLY) {
            Random rng = new Random ();
            for (int i = 1; i <= totalKeys; i += 2) ht.put (rng.nextInt (2 * totalKeys), i * i);
        } else {
            for (int i = 1; i <= totalKeys; i += 2) ht.put (i, i * i);
        } // if

        ht.print ();
        for (int i = 0; i <= totalKeys; i++) {
            out.println ("key = " + i + " value = " + ht.get (i));
        } // for
        out.println ("-------------------------------------------");
        out.println ("Average number of buckets accessed = " + ht.count / (double) totalKeys);
    } // main

} // LinHashMap class

