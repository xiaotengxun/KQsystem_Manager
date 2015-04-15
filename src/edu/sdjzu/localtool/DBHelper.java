package edu.sdjzu.localtool;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "sdjzu.db";
	private static final int DATABASE_VERSION = 1;

	public DBHelper(Context context) {
		// CursorFactory设置为null,使用默认值
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE  UserInf  ( Uno  varchar(10) primary key, Upwd  varchar(20), Uname  varchar(20), "
				+ "Usex  varchar(1), Usdept  varchar(20), Uclass  varchar(100), Utel  varchar(15), Utype  varchar(5))");
		db.execSQL("CREATE TABLE  Students  (Sid  varchar(18) DEFAULT '无',Sno  varchar(15),Spwd  varchar(20),Ppwd  varchar(20),"
				+ "Sname  varchar(10),Ssex  varchar(1),Ssdept  varchar(10),Sclass  varchar(10),Sstate  varchar(2) DEFAULT '在校',"
				+ "Stel  varchar(11) DEFAULT '未填写',Ptel  varchar(11) DEFAULT '未填写',Spic  varchar(3) DEFAULT '未上传')");
		db.execSQL("CREATE TABLE  Teachers  (Tno  varchar(10) primary key,Tpwd  varchar(20),Tname  varchar(20),Tsex  varchar(1),Tsdept  varchar(10),"
				+ "Temail  varchar(50) DEFAULT '未填写',Tel  varchar(11) DEFAULT '未填写')");
		db.execSQL("CREATE TABLE  TeachTask  (id int primary key,Rno  int ,Cno  varchar(10),Cname  varchar(30),Tname  varchar(30),Rclass  varchar(30),Ctype  varchar(4),"
				+ "Rweek  varchar(10) DEFAULT '',Rterms  varchar(11) DEFAULT '')");
		db.execSQL("CREATE TABLE  TeachProgress  (Jno  int primary key ,Rno  int,Cname  varchar(50),Tname  varchar(30),Jclass  varchar(30),Jweek  varchar(2),"
				+ "Jtime  varchar(10),Jaddress  varchar(15),StartTime  datetime,EndTime  datetime,IsKQ  varchar(3) DEFAULT '未提交',InMan  varchar(20),InTime  datetime)");
		db.execSQL("CREATE TABLE  TeachLocalProgress  (Jno  int primary key ,Rno  int,Cname  varchar(50),Jweek  varchar(2),"
				+ "Jtime  varchar(10),IsKQ  varchar(3) DEFAULT '未提交',IsSaved varchar(3) default '否')");
		db.execSQL("CREATE TABLE  KQresult  (Kno integer primary key AUTOINCREMENT,Rno  int,Jno  int ,Sno  varchar(15) ,Kstate  varchar(3),"
				+ "Kmarks  varchar(20) DEFAULT '无',IsSubmin  varchar(3) DEFAULT '未提交',InMan  varchar(20),InTime  datetime)");
		db.execSQL("CREATE TABLE KqInfo (Id integer primary key AUTOINCREMENT,Info varchar(200),IsRead int DEFAULT '0',ReceiveTime datetime,InMan varchar(10))");
		db.execSQL("CREATE TABLE ChatInfo (Id integer primary key AUTOINCREMENT,SendNo varchar(15),ReceiveNo varchar(15),Msg varchar(200),SendName varchar(10),"
				+ "ReceiveName varchar(10),Time datetime,BothSend int,IsRead int,SendType varchar(2),ReceiveType varchar(2))");

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}

}
