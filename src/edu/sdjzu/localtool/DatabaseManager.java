package edu.sdjzu.localtool;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * DatabaseManager数据库管理函数
 * 
 * @author Administrator
 * 
 */
public class DatabaseManager {

	private static DBHelper helper;
	private static SQLiteDatabase db = null;
	private Context context = null;
	private static DatabaseManager dm = null;

	/**
	 * DatabaseManager构造函数
	 * 
	 * @param context
	 *            上下文类型
	 */
	public DatabaseManager(Context context) {
		this.context = context;
		helper = new DBHelper(context);
		db = helper.getWritableDatabase();
	}

	public static DatabaseManager getInstance(Context ctx) {
		if (dm == null) {
			dm = new DatabaseManager(ctx);
		}
		return dm;
	}

	/**
	 * 插入函数
	 * 
	 * @param tableName
	 *            数据表名
	 * @param nullValue
	 *            空列填充值
	 * @param values
	 *            新记录值组成的Values
	 * @return 返回long类型的新建记录行号
	 */
	public long Insert(String tableName, String nullValue, ContentValues values) {
		return db.insert(tableName, nullValue, values);
	}

	/**
	 * 更新函数
	 * 
	 * @param tableName
	 *            表名
	 * @param values
	 *            修改的列组成的Values
	 * @param whereClause
	 *            更新的条件语句
	 * @param whereArgs
	 *            更新条件语句的参数数组
	 * @return 返回int类型的影响行数
	 */
	public int Upadte(String tableName, ContentValues values, String whereClause, String[] whereArgs) {
		return db.update(tableName, values, whereClause, whereArgs);
	}

	/**
	 * 删除函数
	 * 
	 * @param tableName
	 *            表名
	 * @param whereClause
	 *            删除的条件语句
	 * @param whereArgs
	 *            删除条件语句的参数数组
	 * @return 删除影响的行数
	 */
	public int Delete(String tableName, String whereClause, String[] whereArgs) {
		return db.delete(tableName, whereClause, whereArgs);
	}

	/**
	 * Exec执行insert、delete、update和CREATE TABLE之类有更改行为的SQL语句
	 * 
	 * @param sql
	 *            SQL语句
	 */
	public void execSQL(String sql) {
		db.execSQL(sql);
	}

	/**
	 * Exec执行insert、delete、update和CREATE TABLE之类有更改行为的SQL语句
	 * 
	 * @param sql
	 *            带值SQL语句
	 * @param bindArgs
	 */
	public void execSQL(String sql, Object[] bindArgs) {
		db.execSQL(sql, bindArgs);
	}

	/**
	 * 执行select语句
	 * 
	 * @param sql
	 *            SQL查询语句
	 * @param selectionArgs
	 * @return 返回SQL光标
	 */
	public Cursor Query(String sql, String[] selectionArgs) {
		return db.rawQuery(sql, selectionArgs);
	}

	/**
	 * 关闭database对象
	 */
	public void closeDB() {
		db.close();
		helper.close();
	}

	public void beginTransaction() {
		db.beginTransaction();
	}

	public void setTransactionSuccessful() {
		db.setTransactionSuccessful();
	}

	public void endTransaction() {
		db.endTransaction();
	}

}
