package edu.sdjzu.localtool;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;
import android.util.JsonReader;
import android.util.Log;
import edu.sdjzu.model.KQInfo;
import edu.sdjzu.model.Students;
import edu.sdjzu.model.TeachTask;
import edu.sdjzu.model.UserInf;

public class LocalSqlTool {
	private Context context;
	private DatabaseManager db;
	private static List<HashMap<String, String>> stuListHashMap = new ArrayList<HashMap<String, String>>();

	public LocalSqlTool(Context context) {
		super();
		this.context = context;
	}
	public boolean localLogin(String username, String password) {
		db = DatabaseManager.getInstance(context);
		String sql = "select * from UserInf where Uno=? and Upwd=?";
		Cursor cursor = db.Query(sql, new String[] { username, password });
		if (cursor.getCount() > 0) {
			cursor.close();
			return true;
		}
		cursor.close();
		return false;
	}
	// 将文件数据保存到SDCard
	public void saveToSDCard(String content, String fileName) throws Exception {
		File file = new File(Environment.getExternalStorageDirectory(), fileName);
		FileOutputStream fileOutputStream = new FileOutputStream(file);
		fileOutputStream.write(content.getBytes());
		fileOutputStream.close();
	}

	// 从SDCard读取文件数据
	@SuppressWarnings("resource")
	private String readFromSDCard(String fileName) throws Exception {
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		File SDFile = Environment.getExternalStorageDirectory();// 获取外存储设备的文件目录
		File file = new File(SDFile.getAbsolutePath() + File.separator + fileName);// 构建读取文件所需目录的文件对象
		FileInputStream inputStream = new FileInputStream(file);
		int len = 0;
		byte[] buffer = new byte[1024];
		while ((len = inputStream.read(buffer)) != -1) {
			byteArrayOutputStream.write(buffer, 0, len);// 写入内存中
		}
		byte[] data = byteArrayOutputStream.toByteArray();

		return new String(data);
	}

	@SuppressWarnings("resource")
	private List<String> parseJson(String Js, String sno) throws IOException {
		List<String> sjs = new ArrayList<String>();
		// String Js="[{\"name\":\"chen\",\"age\":21}]";
		JsonReader reader = new JsonReader(new StringReader(Js));
		reader.beginArray();
		reader.beginObject();
		while (reader.hasNext()) {
			String name = reader.nextName();
			if (name.equals(sno)) {
				sjs.add(reader.nextString());
				break;
			}
		}
		reader.endObject();
		reader.endArray();

		return sjs;
	}

	/**
	 * 根据学生学号获得学生图片
	 * 
	 * @param sno
	 * @return
	 */
	public Bitmap getPhotoBySno(String sno) {
		String fileName = sno + ".txt";
		Bitmap bitmap = null;
		String js = "";
		try {
			js = readFromSDCard(fileName);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (js.equals(""))
			return null;
		List<String> slist = new ArrayList<String>();
		try {
			slist = parseJson(js, sno);
		} catch (IOException e) {
		}
		if (slist.size() == 0)
			return null;
		byte[] bit = Base64.decode(slist.get(0), Base64.DEFAULT);
		bitmap = BitmapFactory.decodeByteArray(bit, 0, bit.length);
		return bitmap;
	}

	public String forMatTime(String time) {
		String[] s1 = time.split("/");
		String[] s2 = s1[2].split(" ");
		String[] s3 = s2[1].split(":");
		String result = "";
		result = String.valueOf(Integer.valueOf(s1[0])) + "/" + String.valueOf(Integer.valueOf(s1[1])) + "/"
				+ String.valueOf(Integer.valueOf(s2[0])) + " ";
		result = result + String.valueOf(Integer.valueOf(s3[0])) + ":" + s3[1] + ":" + s3[2];
		return result;
	}

	/**
	 * 向本地辅导员或院教务员的编号获得用户信息
	 * 
	 * @param u
	 */
	public void insertUserInfByUno(UserInf u, String uno) {
		System.out.println("user.getUserType=" + u.getUserType() + "  ");
		db = DatabaseManager.getInstance(context);
		if (uno != null) {
			String del = "delete from UserInf where Uno='" + uno + "'";
			db.execSQL(del);
		}
		String sql = "insert into UserInf(Uno, Upwd, Uname, Usex, Usdept, Uclass, Utel, Utype) values(?,?,?,?,?,?,?,?)";
		String[] arg = { u.getUserNo(), u.getUserPassword(), u.getUserName(), u.getUserSex(), u.getUserSdept(),
				u.getUserClass(), u.getUserTel(), u.getUserType() };
		db.execSQL(sql, arg);
	}

	/**
	 * 向本地插入教学任务
	 * 
	 * @param t
	 */
	public void insertTeachTaskByTaskNo(List<TeachTask> tList) {
		String sql = "";
		db = DatabaseManager.getInstance(context);
		sql="delete from TeachTask";
		db.execSQL(sql);
		sql = "	insert into TeachTask(Rno,Cno,Cname,Tname,Rclass,Ctype,Rweek,Rterms) values(?,?,?,?,?,?,?,?)";
		db.beginTransaction();
		try {
			for (TeachTask t : tList) {
				String[] arg = { String.valueOf(t.getTaskNo()), t.getCourseNo(), t.getCourseName(), t.getTeaName(),
						t.getTaskClass(), t.getCourseType(), t.getTaskWeek(), t.getTaskTerms() };
				db.execSQL(sql, arg);
			}
			db.setTransactionSuccessful();
			Log.i("chen", "insertTeachTaskByTaskNo success");
		} catch (Exception e) {
			Log.i("chen", "insertTeachTaskByTaskNo exeception" + e);
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * 向本地老师的任务编号查找一个教师上课的全部学生
	 * 
	 * @param stuList
	 */
	public void insertClassStuByRno(List<Students> stuList) {
		if (stuList.size() == 0)
			return;
		String sql = "insert into Students(Sid,Sno,Spwd,Ppwd,Sname,Ssex,Ssdept,Sclass,Sstate,Stel,Ptel,Spic) values(?,?,?,?,?,?,?,?,?,?,?,?)";
		db = DatabaseManager.getInstance(context);
		for (int i = 0; i < stuList.size(); i++) {
			Students stu = stuList.get(i);
			String[] arg = { stu.getStuId(), stu.getStuNo(), stu.getStuPassword(), stu.getParPassword(),
					stu.getStuName(), stu.getStuSex(), stu.getStuSdept(), stu.getStuClass(), stu.getStuState(),
					stu.getStuTel(), stu.getParTel(), stu.getStuPic() };
			db.execSQL(sql, arg);
		}
	}

	/**
	 * 根据班级获得所有课程名称
	 * 
	 * @return
	 */
	public List<String> getAllCourseByCla(String cla) {
		String sql = "select * from TeachTask  where Rclass like '%" + cla + "%'";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			list.add(cursor.getString(cursor.getColumnIndex("Cname")));
		}
		Log.i("chen", "course.length=" + list.size());
		return list;
	}

	/**
	 * 获得所管理的所有班级
	 * 
	 * @return
	 */
	public List<String> getAllClass() {
		String sql = "select * from UserInf";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		List<String> list = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String claStr = cursor.getString(cursor.getColumnIndex("Uclass"));
			String[] claArray = claStr.split("、");
			list = Arrays.asList(claArray);
		}
		Log.i("chen", "class.length=" + list.size());
		return list;
	}

	/**
	 * 根据学生姓名获得其相关信息
	 * 
	 * @param name
	 * @return
	 */
	public HashMap<String, String> getStuInfoByName(String name) {
		HashMap<String, String> hash = new HashMap<String, String>();
		String sql = "select * from Students where Sname='" + name + "'";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		while (cursor.moveToNext()) {
			hash.put("name", name);
			hash.put("sno", cursor.getString(cursor.getColumnIndex("Sno")));
			hash.put("class", cursor.getString(cursor.getColumnIndex("Sclass")));
		}
		return hash;
	}

	/**
	 * 向本地插入最新的考勤信息
	 * 
	 * @param list
	 */
	public void insertKqInfo(List<KQInfo> list) {
		db = DatabaseManager.getInstance(context);
		Log.i("chen","insetKqinfo.size="+list.size());
		String sql = "insert into KqInfo(Info,IsRead,ReceiveTime,InMan) values(?,?,?,?)";
		db.beginTransaction();
		int counts = list.size();
		try {
			for (int i = 0; i < counts; i++) {
				KQInfo kqInfo = list.get(i);
				db.execSQL(sql,
						new String[] { kqInfo.getMsg(), String.valueOf(kqInfo.getIsRead()), kqInfo.getDateTime(),
								kqInfo.getTname() });
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.i("chen",""+e);
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * 获得本地考勤信息
	 * 
	 * @return
	 */
	public List<KQInfo> getKqInfo() {
		db = DatabaseManager.getInstance(context);
		String sql = "select * from KqInfo ";
		Cursor cursor = db.Query(sql, null);
		Log.i("chen", "getKqInfo .size="+cursor.getCount());
		List<KQInfo> list = new ArrayList<KQInfo>();
		while (cursor.moveToNext()) {
			KQInfo kqInfo = new KQInfo();
			kqInfo.setDateTime(cursor.getString(cursor.getColumnIndex("ReceiveTime")));
			kqInfo.setIsRead(cursor.getInt(cursor.getColumnIndex("IsRead")));
			kqInfo.setMsg(cursor.getString(cursor.getColumnIndex("Info")));
			kqInfo.setTname(cursor.getString(cursor.getColumnIndex("InMan")));
			kqInfo.setId(cursor.getInt(cursor.getColumnIndex("Id")));
			list.add(kqInfo);
		}
		cursor.close();
		return list;
	}

	/**
	 * 更新考勤信息被阅读过
	 * 
	 * @param id
	 */
	public void updateKqInfo(List<Integer> listId) {
		db = DatabaseManager.getInstance(context);
		Log.i("chen","listId.size()="+listId.size());
		for (Integer id : listId) {
			String sql = "delete from KqInfo where Id='" + String.valueOf(id) + "'";
			db.execSQL(sql);
		}
	}

}
