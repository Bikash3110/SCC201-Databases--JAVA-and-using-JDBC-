import java.sql.*;
import java.io.*;
import java.util.*;

public class CW {
	boolean	debug	= false;
	private static final String JDBC_DRIVER		= "org.sqlite.JDBC";

	private static final String DATABASE_LOCATION	= "jdbc:sqlite:";

	protected Connection	con	= null;

	public    String	dbName	= null;

	public void notify( String message, Exception e ) {
		System.out.println( message + " : " + e );
		e.printStackTrace ( );
		System.exit( 0 );
	}
	// -----get Connection -------------------
	private void getConnection( ) {
		try {
			con = DriverManager.getConnection(
					  DATABASE_LOCATION
					+ dbName);

			con.setAutoCommit(false);
		}
		catch ( SQLException sqle ) {
			notify( "Db.getConnection database location ["
					+ DATABASE_LOCATION
					+ "] db name["
					+ dbName
					+ "]", sqle);
			close( );
		}
	}
	//-----check files ----------------------
	private void open( ) {
		File dbf = new File( dbName );

		if ( dbf.exists( ) == false ) {
			System.out.println(
				 "SQLite database file ["
				+ dbName
				+ "] does not exist");
			System.exit( 0 );
		}
	
		try {
			Class.forName( JDBC_DRIVER );
			getConnection( );
		}
		catch ( ClassNotFoundException cnfe ) {
			notify( "Db.Open", cnfe );
		}
        
        System.out.println("Opened database successfully");

		if ( debug )
			System.out.println( "Db.Open : leaving" );
	}
	
	public final void close( ) {
		try {
			con.commit( ); // Commit any updates
			con.close ( );
		}
		catch ( Exception e ) {
			notify( "Db.close", e );
		};
	}

	public CW( String _dbName ) {
		dbName = _dbName;

		if ( debug )
			System.out.println(
				  "Db.constructor ["
				+ dbName
				+ "]");
        System.out.println("Opening _dbName");
		open( );
	}
//-------------------------------------------------------------------
    public void getTable()
    {
       try{	
    	 ResultSet result = null; 
    	 DatabaseMetaData database = con.getMetaData();                               // connects to metadata
    	 result = database.getTables(null,null,null,new String[]{"TABLE"});           // get tables      
    	 System.out.println("Printing TABLE_TYPE");
    	 System.out.println("-----------------------------");

         String[] Colname = new String[20];
         String[] CN = new String[20];
         String[] datatype = new String[20];
         String[] colSize = new String[20];
         //String[] DN = new String[5];
    	  
    	 while(result.next())                                              
    	 {
    		int i=0;
    		String table_name = result.getString("TABLE_NAME");                       // gets the name of the table in database
    		System.out.println(table_name);
    		System.out.print("\n");
    	    //--------Get Column--------------------------
    	    
    	    ResultSet col = database.getColumns(null,null,table_name,null);           // gets columns from the table   
    	    while(col.next())
    	    {
    	      	
               Colname[i] = col.getString("COLUMN_NAME");
               datatype[i] = col.getString("DATA_TYPE");
               //System.out.println(Colname[i]);
			   i++;
			}
//-------------------a-------------------------------------                               
            Statement q =  con.createStatement( );
			ResultSet rSet = q.executeQuery("SELECT * FROM "+ table_name +";");             // run query to extract values from tble and put it into INSERT INTO statement
			while(rSet.next()){
                for(int j=0;j<10;j++){
                 try{
                   //String c = Colname[j]; 	
			       CN[j] = rSet.getString(j+1);                                              // stores the value into arrays
			       }
			       catch(SQLException e)
			       {
                      break;
			       }
			   }
 
               System.out.print("INSERT INTO "+table_name+" VALUES(");                   // Print the desired INSERT INTO statement
			   for(int z =0;z<20 ;z++)
			   {
                  if(CN[z]!=null){
			   	    System.out.print(CN[z]);
			       }
			       else
			       {
			       	 break;
			       }

			       if(CN[z+1]!=null){
			   	    System.out.print(",");
			       }
			       else
			       {
			       	 break;
			       }
			   } 
			   System.out.print(");");
			    System.out.println("\n");
			}
			rSet.close();
			q.close();  
            
             
    	    System.out.println("\n");
//-----------------b-------------------------------------------
//-----------------b-------------------------------------------
   
            Statement q2 =  con.createStatement( );
			ResultSet rSet2 = q2.executeQuery("SELECT * FROM "+ table_name +";");
			while(rSet2.next()){
                for(int j=0;j<20;j++){
                 try{
                   String c = Colname[j]; 	
			       CN[j] = rSet2.getString(j+1);
			       }
			       catch(SQLException e)
			       {
                      break;
			       }
			   }

               System.out.print("INSERT INTO "+table_name+" VALUES(");
			   for(int z =0;z<20 ;z++)
			   {
                  if(CN[z]!=null){
                        
                      if(datatype[z].equals("12")){   
			   	           System.out.print("'"+CN[z]+"'");
			   	       }
			   	       else{
			   	      	   System.out.print(CN[z]);
			   	       }	
                  	}
			       else
			       {
			       	 break;
			       }

			       if(CN[z+1]!=null){
			   	    System.out.print(",");
			       }
			       else
			       {
			       	 break;
			       }
			   } 
			   System.out.print(");");
			    System.out.println("\n");
			}
			rSet2.close();
			q2.close();  
            
             
    	    System.out.println("\n");             
         }         
        }
       catch(Exception e)
       {
       	System.out.println(e);
       }
 
//----------------------c-------------------------------------------         
try{	
    	 ResultSet result2 = null; 
    	 DatabaseMetaData database2 = con.getMetaData();
    	 result2 = database2.getTables(null,null,null,new String[]{"TABLE"});
    	 System.out.println("Printing TABLE_TYPE");
    	 System.out.println("-----------------------------");

         String[] Colname2 = new String[20];
         String[] CN2 = new String[20];
         String[] datatype2 = new String[20];
         String[] colSize2 = new String[20];
         //String[] DN = new String[5]; 
    	 while(result2.next())
    	 {
    		int x=0;
    		String table_name2 = result2.getString("TABLE_NAME");
    		System.out.println(table_name2);
    		System.out.print("\n");
    	    //--------Get Column--------------------------
    	    
    	    System.out.print("\n");
            System.out.print("CREATE TABLE "+table_name2+"("); 
    	    
    	    ResultSet col2 = database2.getColumns(null,null,table_name2,null);
    	    while(col2.next())
    	    {
    	      	
               Colname2[x] = col2.getString("COLUMN_NAME");
               datatype2[x] = col2.getString("TYPE_NAME");
			
               System.out.print(" "+Colname2[x]+ " "+datatype2[x] + ",");
			   x++;
			}

            System.out.print(");");
            System.out.println("\n"); 

//---------------------------------ee--------------------------------------

    	    System.out.print("\n");
            System.out.print("CREATE TABLE "+table_name2+"("); 
    	    
    	    ResultSet col3 = database2.getColumns(null,null,table_name2,null);
    	    while(col3.next())
    	    {
    	      	
               Colname2[x] = col3.getString("COLUMN_NAME");
               datatype2[x] = col3.getString("TYPE_NAME");
			
               System.out.print(" "+Colname2[x]+ " "+datatype2[x] + ",");
			   x++;
			}

//GetPrimarykeys
            ResultSet PK = database2.getPrimaryKeys(null,null, table_name2);
            while(PK.next())
            {
              System.out.print( " PRIMARY KEY ("+PK.getString("COLUMN_NAME")+","+PK.getString("PK_NAME")+")");
            }


            System.out.print(");");
            System.out.println("\n");
            
            
    	    System.out.println("\n");
//-----------------------------------------------------------------------

             System.out.print("\n");
            System.out.print("CREATE TABLE "+table_name2+"("); 
    	    
    	    ResultSet col4 = database2.getColumns(null,null,table_name2,null);
    	    while(col4.next())
    	    {
    	      	
               Colname2[x] = col4.getString("COLUMN_NAME");
               datatype2[x] = col4.getString("TYPE_NAME");
			
               System.out.print(" "+Colname2[x]+ " "+datatype2[x] + ",");
			   x++;
			}

//GetPrimarykeys
            ResultSet PK2 = database2.getPrimaryKeys(null,null, table_name2);
            while(PK2.next())
            {
              System.out.print( " PRIMARY KEY ("+PK2.getString("COLUMN_NAME")+","+PK2.getString("PK_NAME")+")");
            }
    	    
//Get Foreign Keys
            ResultSet FK = database2.getImportedKeys(null, null, table_name2);
            while(FK.next())
            {
              System.out.println(" FOREIGN KEY "+ FK.getString("FKCOLUMN_NAME") + " REFERENCES " + FK.getString("PKTABLE_NAME") + "(" + FK.getString("PKCOLUMN_NAME")+")");
            }

            System.out.print(");");
            System.out.println("\n");
           
            }                      
    }
    catch(Exception e)
       {
       	System.out.println(e);
       }     
//---------------------d-----------------m v b ----------------------------
//----------------------d---------------------------------------------       
   }


    public static void main(String[] args)
    {
    	CW cw = new CW("LSH.db");
    	cw.getTable();

    	cw.close();
    }


}
