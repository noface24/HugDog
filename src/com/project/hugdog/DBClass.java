package com.project.hugdog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import android.R.string;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBClass extends SQLiteOpenHelper{
	
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_NAME = "Hugdog.db";
	private static final String TABLE_DOG = "dog";
	private static final String TABLE_VACCINE = "vaccine";
	private static final String TABLE_HISTORY = "history";
	private static final String TABLE_APPOINT = "appoint";
	public static final String KEY_ID="_id";	
	public static final String KEY_NAME ="name";
	public static final String KEY_COLOR = "color";
	public static final String KEY_BREED = "breed";
	public static final String KEY_WEIGHT = "weight";
	public static final String KEY_GENDER = "gender";
	public static final String KEY_HISID="historyID";
	public static final String KEY_DETAIL = "detail";
	public static final String KEY_DATE = "date";
	public static final String KEY_PLACE = "place";
	public static final String KEY_VACID= "vaccineID";
	public static final String KEY_ALARMID = "alarmID";
	public static final String KEY_VETNAME= "vetName";
	public static final String KEY_REPEATE= "isRepeating";
	public static final String KEY_COMPLETE = "completion";
	public static final String KEY_BIRTH = "birth";
	
	private SQLiteDatabase db;
	
	public DBClass(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		
		db.execSQL("CREATE TABLE "+TABLE_DOG+
					"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
					"name TEXT,"+
					KEY_BIRTH + " DATETIME,"+
					"color TEXT,"+
					"breed TEXT,"+
					"weight TEXT,"+
					"gender TEXT);");
		
		db.execSQL("CREATE TABLE "+TABLE_HISTORY+
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"detail TEXT,"+
				"date DATETIME,"+
				"place TEXT,"+
				"dog_id INTEGER," +
				"FOREIGN KEY(dog_ID) REFERENCES "+TABLE_DOG+"(_id));");
		
		db.execSQL("CREATE TABLE "+TABLE_VACCINE+
				"(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"dog_id INTEGER," +
				"name TEXT,"+
				"note TEXT,"+
				"alarmID" + " INTEGER,"+				
				"date DATETIME,"+
				"place TEXT,"+
				"vetName TEXT,"+	
				"isRepeating" + " INTEGER,"+
				"completion" + " INTEGER,"+				
				"FOREIGN KEY(dog_id) REFERENCES "+TABLE_DOG+"(_id));");
		
		db.execSQL("CREATE TABLE "+TABLE_APPOINT+
				"(appointID INTEGER PRIMARY KEY AUTOINCREMENT,"+				
				"alarmID" + " INTEGER,"+				
				"date DATETIME,"+
				"place TEXT,"+
				"detail TEXT);");
		Log.d("CREATE TABLE","Create Table Successfully.");
		
		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	// Write Data
	private void open() throws SQLException {
		db = this.getWritableDatabase();
	}
	
		
	
	// Insert Dog Data
		public long InsertDog(int dogID,String strName,String strColor,String strBreed,String strWeight, String strGender, String strBirth) {
			// TODO Auto-generated method stub
			
			 try {
				//SQLiteDatabase db;
				 open(); 
				
	     	   ContentValues Val = new ContentValues();
	     	   Val.put("_id", dogID); 
	     	   Val.put(KEY_NAME, strName);
	     	   Val.put(KEY_BIRTH, strBirth);
	     	   Val.put(KEY_COLOR, strColor);
	     	   Val.put(KEY_BREED, strBreed);
	     	   Val.put(KEY_WEIGHT, strWeight);
	     	   Val.put(KEY_GENDER, strGender);    
	     	   
				long rows = db.insert(TABLE_DOG, null, Val);

				db.close();
				return rows; // return rows inserted.
	           
			 } catch (Exception e) {
				 Log.d("Insert Fail",e.getMessage());
			    return -1;
			    
			 }

		}
		// END Insert Dog Data
		
	
	public Cursor getDog() {
	    
	    Cursor cursor;
	   
	    open();
	    String strSQL = "SELECT * FROM " +TABLE_DOG;
		cursor = db.rawQuery(strSQL, null);
	  
	    return cursor;

	}
	/*public Cursor getDogDetail(int id){
		 Cursor cursor;
		    open();
		    String strSQL = "SELECT * FROM " +TABLE_DOG+ " WHERE "+KEY_DOGID+" = "+ id;
		    
		    //db.query(TABLE_DOG, null, key, selectionArgs, groupBy, having, orderBy)
			cursor = db.rawQuery(strSQL, null);
			db.close();
		return cursor;
	}*/
	
	public sDog getDogData(int dogID){
		 	open();
		 	 String strSQL = "SELECT * FROM " +TABLE_DOG+ " WHERE "+KEY_ID+" = "+ dogID;
		 	 Cursor cursor = db.rawQuery(strSQL, null);		  
		    sDog cDog=null;

		    if (cursor != null) {
		        cursor.moveToFirst();
		    
		    if (cursor.moveToFirst())
            {
		    cDog = new sDog();		   
		    cDog.sDogID( cursor.getString(0));
		    cDog.sName(cursor.getString(1));
		    cDog.sBirth(cursor.getString(2));
		    cDog.sColor(cursor.getString(3));
		    cDog.sBreed(cursor.getString(4));
		    cDog.sWeight(cursor.getString(5));
		    cDog.sGender(cursor.getString(6));
            }
		     cursor.close();
		     db.close();
		    }
		    return cDog;
		    //fr
	}
	
	public long deleteDog(int id){
		try{
			open();
			long row = db.delete(TABLE_DOG, "_id = ?",new String[] {String.valueOf(id) });
			db.close();
			return row;
		} catch (Exception e){
			return -1;
		}		
	
	}
	public long updateDog(int id,String name,String color,String breed,String weight,String gender,String birth ){
		try{
			open();
			ContentValues value = new ContentValues();
			value.put(KEY_NAME, name);
			value.put(KEY_COLOR, color);
			value.put(KEY_BREED, breed);
			value.put(KEY_WEIGHT, weight);
			value.put(KEY_GENDER, gender);
			value.put(KEY_BIRTH, birth);
			long row = db.update(TABLE_DOG,value,"_id = ?",new String[] {String.valueOf(id) });
			//Cursor row = db.rawQuery(strSql, null);//(table, values, whereClause, whereArgs)
			db.close();
			return row;
		} catch (Exception e){
			return -1;
		}		
	
	}
	
	public Cursor getHis(int dog_id){
		try { 
	    Cursor cursor;	   
	    open();
	    String strSQL = "SELECT * FROM " +TABLE_HISTORY +" WHERE dog_id = "+dog_id;
		cursor = db.rawQuery(strSQL, null);
	  
	    return cursor;
		} catch (Exception e) {
			 Log.d("GetFail",e.getMessage());
		    return null;
		    
		 }
	}
	public Cursor getHisDate(int dogID,String date){
		Cursor cursor = null;	 
			try { 
	      
					open();
					String strSQL = "SELECT * FROM " +TABLE_HISTORY +" , "+ TABLE_DOG+ " WHERE date = '"+date+"' AND dog_id = "+dogID +" AND history.dog_id = "+TABLE_DOG+"._id";
					cursor = db.rawQuery(strSQL, null);
	  
					return cursor;
			} catch (Exception e) {
				Log.d("GetFail",e.getMessage());
				
		    
			}
		return cursor;
	}
	
	public long insertHis(String detail,String date,String place,int dogID) {
		// TODO Auto-generated method stub
		
		 try {
			
			 open();      				
     	   ContentValues Val = new ContentValues();
     	   //Val.put(KEY_DOGID, strMemberID); 
     	   Val.put(KEY_DETAIL, detail);
     	   Val.put(KEY_DATE, date);
     	   Val.put(KEY_PLACE, place);
     	   Val.put("dog_id", dogID);
     	      
     	   
			long rows = db.insert(TABLE_HISTORY, null, Val);

			db.close();
			return rows; // return rows inserted.
           
		 } catch (Exception e) {
			 Log.d("Insert Fail",e.getMessage());
		    return -1;
		    
		 }

	}
	public sHis getHisData(int dogID,String hisDate){
		open();
		//String strSQL = "SELECT * FROM " +TABLE_HISTORY +" WHERE dog_id = 1 AND date = 2015-02-24";
	 	String strSQL = "SELECT * FROM " +TABLE_HISTORY+ " WHERE date = '"+hisDate+"' AND dog_id = "+dogID;
	 	 Cursor cursor = db.rawQuery(strSQL, null);		  
	    sHis cHis=null;

	    if (cursor != null) {
	        cursor.moveToFirst();
	    
	        if (cursor.moveToFirst())
	        {
	        	cHis = new sHis();		   
	        	cHis.sHisID( cursor.getString(0));
	        	cHis.sDetail(cursor.getString(1));
	        	cHis.sDate(cursor.getString(2));
	        	cHis.sPlace(cursor.getString(3));
	        	cHis.sDogID(cursor.getString(4));
	        }
	     cursor.close();
	     db.close();
	    }
	    return cHis;
	    
	}
	public long deleteHis(String hisID) {
		// TODO Auto-generated method stub
		try{
			open();
			long row = db.delete(TABLE_HISTORY, "_id = ?",new String[] {String.valueOf(hisID) });
			db.close();
			return row;
		} catch (Exception e){
			return -1;
		}	
	}
	public long updateHis(int dogID,String id,String detail,String place,String date){
		try{
			open();
			ContentValues value = new ContentValues();
			value.put(KEY_DETAIL, detail);
			value.put(KEY_PLACE, place);
			value.put(KEY_DATE, date);
			value.put("dog_id", dogID);			
			long row = db.update(TABLE_HISTORY,value,"_id = ?",new String[] {String.valueOf(id) });
			//Cursor row = db.rawQuery(strSql, null);//(table, values, whereClause, whereArgs)
			db.close();
			return row;
		} catch (Exception e){
			return -1;
		}		
	
	}
	public int getNextID(String table) {

		String selectQuery = "SELECT MAX(_id) FROM " + table;
		open();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()){
			int i = cursor.getInt(0) + 1;
			cursor.close();
			close();
			return i;
		}
		else{
			cursor.close();
			close();
			return 1;
		}
	}
	public int getVaccineID(int dogID,String date) {
		// TODO Auto-generated method stub
		open();
		//String strSQL = "SELECT * FROM " +TABLE_HISTORY +" WHERE dog_id = 1 AND date = 2015-02-24";
	 	String strSQL = "SELECT * FROM " +TABLE_VACCINE+ " WHERE date = '"+date+"' AND dog_id = "+dogID;
	 	 Cursor cursor = db.rawQuery(strSQL, null);
	 	if (cursor.moveToFirst()){
			int id = cursor.getInt(cursor.getColumnIndex("_id"));
			cursor.close();
			close();
			return id;
		}
		else{
			cursor.close();
			close();
			return 0;
		}
	    
	}
	public long insertVaccine(int vacID,int dogID,String date,String name){
		 try {
				
			 open();      				
     	   ContentValues Val = new ContentValues();
     	   Val.put("_id", vacID); 
     	   Val.put(KEY_NAME, name);
     	   Val.put(KEY_DATE, date);
     	   Val.put(KEY_COMPLETE, 0);
     	  Val.put(KEY_VETNAME, "-");
     	 Val.put(KEY_PLACE, "-");
     	   //Val.put(KEY_PLACE, place);
     	   Val.put("dog_id", dogID);
     	      
     	   
			long rows = db.insert(TABLE_VACCINE, null, Val);

			db.close();
			return rows; // return rows inserted.
           
		 } catch (Exception e) {
			 Log.d("Insert Fail",e.getMessage());
		    return -1;
		    
		 }
	}
	public Cursor getAllVac(){
		Cursor cursor = null;	 
		try { 
      
				open();
				String strSQL = "SELECT * FROM " +TABLE_VACCINE ;
				cursor = db.rawQuery(strSQL, null);
  
				return cursor;
		} catch (Exception e) {
			Log.d("GetFail",e.getMessage());
			
	    
		}
	return cursor;
	}
	public Cursor getVacDate(String vacDate) {
		// TODO Auto-generated method stub
		Cursor cursor = null;	 
		try { 
      
				open();
				String strSQL = "SELECT date,vaccine.name as vacname,dog.name,vaccine._id,completion FROM " +TABLE_VACCINE +","+TABLE_DOG+" WHERE date LIKE '"+vacDate+"%' AND vaccine.dog_id = dog._id";
				//String strSQL = "SELECT * FROM " +TABLE_VACCINE +" , "+ TABLE_DOG+ " WHERE date = "+vacDate+" AND vaccine.dog_id = "+TABLE_DOG+"._id";
				cursor = db.rawQuery(strSQL, null);
  
				return cursor;
		} catch (Exception e) {
			Log.d("GetFail",e.getMessage());
			
	    
		}
		return null;
	}
	public sVac getVacData(String vacID){
		open();
		
	 	String strSQL = "SELECT * FROM " +TABLE_VACCINE+ " WHERE _id ="+vacID;
	 	 Cursor cursor = db.rawQuery(strSQL, null);		  
	    sVac cVac=null;

	    if (cursor != null) {
	        cursor.moveToFirst();
	    
	        if (cursor.moveToFirst())
	        {
	        	cVac = new sVac();		   
	        	cVac.sVacID(cursor.getString(0));
	        	cVac.sDogID(cursor.getString(1));
	        	cVac.sName(cursor.getString(2));
	        	cVac.sNote(cursor.getString(3));
	        	cVac.sDate(cursor.getString(5));
	        	cVac.sPlace(cursor.getString(6));
	        	cVac.sVetName(cursor.getString(7));
	        	cVac.sCompletion(cursor.getInt(9));
	        	
	        }
	     cursor.close();
	     db.close();
	    }
	    return cVac;
	}
	public long updateVac(int id,String note,String place,String date,String vetName,int complete){
		try{
			open();
			ContentValues value = new ContentValues();
			value.put("note", note);
			value.put(KEY_PLACE, place);
			value.put(KEY_DATE, date);
			//value.put("dog_id", dogID);	
			value.put(KEY_VETNAME, vetName);
			value.put(KEY_COMPLETE,complete);	
			long row = db.update(TABLE_VACCINE,value,"_id = ?",new String[] {String.valueOf(id) });
			//Cursor row = db.rawQuery(strSql, null);//(table, values, whereClause, whereArgs)
			db.close();
			return row;
		} catch (Exception e){
			Log.d("update vac", e.getMessage());
			return -1;
		}		
	
	}
	
	////////////////////////////////// 
	public boolean chkDB() {
		// TODO Auto-generated method stub
		open();
		Cursor mCursor = db.rawQuery("SELECT * FROM "+TABLE_DOG, null);
		Boolean rowExists;

		if (mCursor.moveToFirst())
		{
		   // DO SOMETHING WITH CURSOR
		  rowExists = true;

		} else
		{
		   // I AM EMPTY
		   rowExists = false;
		}
		return rowExists;	
		
	}
	
	/////////////dbm
	public ArrayList<Cursor> getData(String Query){
		//get writable database
		SQLiteDatabase sqlDB = this.getWritableDatabase();
		String[] columns = new String[] { "mesage" };
		//an array list of cursor to save two cursors one has results from the query 
		//other cursor stores error message if any errors are triggered
		ArrayList<Cursor> alc = new ArrayList<Cursor>(2);
		MatrixCursor Cursor2= new MatrixCursor(columns);
		alc.add(null);
		alc.add(null);
		
		
		try{
			String maxQuery = Query ;
			//execute the query results will be save in Cursor c
			Cursor c = sqlDB.rawQuery(maxQuery, null);
			

			//add value to cursor2
			Cursor2.addRow(new Object[] { "Success" });
			
			alc.set(1,Cursor2);
			if (null != c && c.getCount() > 0) {

				
				alc.set(0,c);
				c.moveToFirst();
				
				return alc ;
			}
			return alc;
		} catch(SQLException sqlEx){
			Log.d("printing exception", sqlEx.getMessage());
			//if any exceptions are triggered save the error message to cursor an return the arraylist
			Cursor2.addRow(new Object[] { ""+sqlEx.getMessage() });
			alc.set(1,Cursor2);
			return alc;
		} catch(Exception ex){

			Log.d("printing exception", ex.getMessage());

			//if any exceptions are triggered save the error message to cursor an return the arraylist
			Cursor2.addRow(new Object[] { ""+ex.getMessage() });
			alc.set(1,Cursor2);
			return alc;
		}

		
	}
	//Select dog
			public class sDog{
				String _DogID,_Name,_Birth,_Color,_Breed,_Weight,_Gender;
				
				//set value
				public void sDogID(String vDogID){
					this._DogID = vDogID;
				}
				public void sName(String vName){
					this._Name = vName;
				}
				public void sBirth(String vBirth){
					this._Birth = vBirth;
				}
				public void sColor(String vColor){
					this._Color = vColor;
				}
				public void sBreed(String vBreed){
					this._Breed = vBreed;
				}
				public void sWeight(String vWeight){
					this._Weight = vWeight;
				}
				public void sGender(String vGender){
					this._Gender = vGender;
				}
				//Get Value
				public String gDogID(){
					return _DogID;
				}
				public String gName(){
					return _Name;
				}
				public String gBirth(){
					return _Birth;
				}
				public String gColor(){
					return _Color;
				}
				public String gBreed(){
					return _Breed;
				}
				public String gWeight(){
					return _Weight;
				}
				public String gGender() {
					return _Gender;
				}
			}
			
			public class sHis{
				String _HisID,_Detail,_Date,_Place,_DogID;
				
				//set value
				public void sDogID(String vDogID){
					this._DogID = vDogID;
				}
				public void sDetail(String vDetail){
					this._Detail = vDetail;
				}
				public void sDate(String vDate){
					this._Date = vDate;
				}
				public void sPlace(String vPlace){
					this._Place = vPlace;
				}
				public void sHisID(String vHisID){
					this._HisID = vHisID;
				}
				
				//Get Value
				public String gDogID(){
					return _DogID;
				}
				public String gDetail(){
					return _Detail;
				}
				public String gDate(){
					return _Date;
				}
				public String gPlace(){
					return _Place;
				}
				public String gHisID(){
					return _HisID;
				}
				
			}
			public class sVac{
				String _VacID,_Name,_Note,_Place,_DogID,_VetName;
				int _Completion,_IsRepeat;
				String _Date;
				
				//set value
				public void sDogID(String vDogID){
					this._DogID = vDogID;
				}
				public void sVacID(String vVacID){
					this._VacID = vVacID;
				}
				public void sName(String vName){
					this._Name = vName;
				}
				public void sDate(String vDate){
					this._Date = vDate;
				}
				public void sPlace(String vPlace){
					this._Place = vPlace;
				}
				public void sVetName(String vVetName){
					this._VetName = vVetName;
				}
				public void sNote(String vNote){
					this._Note = vNote;
				}
				public void sCompletion(int vCompletion){
					this._Completion = vCompletion;
				}
				public void sIsRepeat(int vIsRepeat){
					this._IsRepeat = vIsRepeat;
				}
				
				
				//Get Value
				public String gDogID(){
					return _DogID;
				}
				public String gName(){
					return _Name;
				}
				public String gDate(){
					return _Date;
				}
				public String gPlace(){
					return _Place;
				}
				public String gVacID(){
					return _VacID;
				}
				public String gNote(){
					return _Note;
				}
				public String gVetName(){
					return _VetName;
				}
				public int gIsRepeating(){
					return _IsRepeat;
				}
				public int gCompletion(){
					return _Completion;
				}
			}
			
			
			///////////////////////

}
