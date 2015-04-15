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
import edu.sdjzu.model.ChatInfo;
import edu.sdjzu.model.KQInfo;
import edu.sdjzu.model.Students;
import edu.sdjzu.model.TeachTask;
import edu.sdjzu.model.UserInf;

public class LocalSqlTool {
	private Context context;
	private DatabaseManager db;

	public LocalSqlTool(Context context) {
		super();
		this.context = context;
	}

	public String getManageName(String no) {
		String sql = "select * from UserInf where Uno="+no;
		String name = "";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		if (cursor.moveToNext()) {
			name = cursor.getString(cursor.getColumnIndex("Uname"));
		}
		cursor.close();
		return name;
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
		sql = "delete from TeachTask";
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
		db = DatabaseManager.getInstance(context);
		String sql = "delete from Students";
		db.execSQL(sql);
		sql = "insert into Students(Sid,Sno,Spwd,Ppwd,Sname,Ssex,Ssdept,Sclass,Sstate,Stel,Ptel,Spic) values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
			Log.i("chen", "insertKqInfo" + e);
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
		String sql = "select * from KqInfo order by ReceiveTime desc";
		Cursor cursor = db.Query(sql, null);
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
		for (Integer id : listId) {
			String sql = "delete from KqInfo where Id='" + String.valueOf(id) + "'";
			db.execSQL(sql);
		}
	}

	/**
	 * 向本地插入聊天消息
	 * 
	 * @param list
	 */
	public void insertNewChatInfo(List<ChatInfo> list) {
		// (SendNo,ReceiveNo ,Msg ,SendName,"
		// + "ReceiveName,Time,BothSend,IsRead,SendType,ReceiveType;
		String sql = "insert into ChatInfo(SendNo,ReceiveNo,Msg,SendName,ReceiveName,Time,"
				+ "BothSend,IsRead,SendType,ReceiveType) values(?,?,?,?,?,?,?,?,?,?)";
		db = DatabaseManager.getInstance(context);
		db.beginTransaction();
		try {
			int size = list.size();
			for (int i = 0; i < size; ++i) {
				ChatInfo ch = list.get(i);
				String[] arg = new String[] { ch.getSenderNo(), ch.getReceiverNo(), ch.getMsg(), ch.getSendName(),
						ch.getReceiveName(), ch.getTime(), String.valueOf(ch.getBothsend()),
						String.valueOf(ch.getIsRead()), ch.getSendType(), ch.getReceiveType() };
				db.execSQL(sql, arg);
			}
			db.setTransactionSuccessful();
		} catch (Exception e) {
			Log.i("chen", "insert chatinfo error =" + e);
		} finally {
			db.endTransaction();
		}
	}

	/**
	 * 获得聊天的种类条目
	 * 
	 * @param no
	 * @return
	 */
	public List<ChatInfo> getChatGroup(String no) {
		List<ChatInfo> listChatInfo = new ArrayList<ChatInfo>();
		String sql = "select * from ChatInfo where SendNo=" + no + " group by ReceiveNo"+" order by Time desc";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		while (cursor.moveToNext()) {
			ChatInfo chatInfo = new ChatInfo();
			chatInfo.setId(cursor.getString(cursor.getColumnIndex("Id")));
			chatInfo.setIsRead(Integer.valueOf(cursor.getString(cursor.getColumnIndex("IsRead"))));
			chatInfo.setMsg(cursor.getString(cursor.getColumnIndex("Msg")));
			chatInfo.setSenderNo(cursor.getString(cursor.getColumnIndex("SendNo")));
			chatInfo.setSendName(cursor.getString(cursor.getColumnIndex("SendName")));
			chatInfo.setReceiveName(cursor.getString(cursor.getColumnIndex("ReceiveName")));
			chatInfo.setReceiverNo(cursor.getString(cursor.getColumnIndex("ReceiveNo")));
			chatInfo.setTime(cursor.getString(cursor.getColumnIndex("Time")));
			listChatInfo.add(chatInfo);
		}
		return listChatInfo;
	}

	/**
	 * 删除聊天消息
	 * 
	 * @param list
	 */
	public void deleteChatInfo(List<ChatInfo> list) {
		String sql = "delete from ChatInfo where id=";
		int size = list.size();
		if (size > 0) {
			sql += list.get(0);
		}
		for (int i = 1; i < size; i++) {
			sql += " or id=" + list.get(i);
		}
		if (size > 0) {
			db = DatabaseManager.getInstance(context);
			db.execSQL(sql);
		}
	}

	/**
	 * 根据聊天消息的id更新消息的阅读状态
	 * 
	 * @param id
	 */
	public void updateChatInfoReadState(String id) {
		String sql = "update ChatInfo set IsRead=1 where id=" + id;
		db = DatabaseManager.getInstance(context);
		db.execSQL(sql);
	}

	/**
	 * 获得全部学生
	 * 
	 * @return
	 */
	public List<Students> getAllStu() {
		List<Students> listStu = new ArrayList<Students>();
		String sql = "select * from Students";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		while (cursor.moveToNext()) {
			Students stu = new Students();
			stu.setStuClass(cursor.getString(cursor.getColumnIndex("Sclass")));
			stu.setStuName(cursor.getString(cursor.getColumnIndex("Sname")));
			stu.setStuNo(cursor.getString(cursor.getColumnIndex("Sno")));
			listStu.add(stu);
		}
		cursor.close();
		return listStu;
	}

	/**
	 * 返回详细的聊天信息
	 * 
	 * @param pSno
	 * @return
	 */
	public List<ChatInfo> getChatDetail(String pSno) {
		List<ChatInfo> listChat = new ArrayList<ChatInfo>();
		String sql = "select * from ChatInfo where SendNo=" + pSno + " or ReceiveNo=" + pSno + " order by Time asc";
		db = DatabaseManager.getInstance(context);
		Cursor cursor = db.Query(sql, null);
		while (cursor.moveToNext()) {
			ChatInfo chatInfo = new ChatInfo();
			chatInfo.setMsg(cursor.getString(cursor.getColumnIndex("Msg")));
			chatInfo.setBothsend(Integer.valueOf(cursor.getString(cursor.getColumnIndex("BothSend"))));
			chatInfo.setTime(cursor.getString(cursor.getColumnIndex("Time")));
			listChat.add(chatInfo);
		}
		cursor.close();
		int size = listChat.size();
		// for (int i = 0; i < size; ++i) {
		// ChatInfo firstChat = listChat.get(i);
		// for (int j = i; j < size; ++j) {
		// ChatInfo sendChat = listChat.get(j);
		// int compare =
		// firstChat.getTime().compareToIgnoreCase(sendChat.getTime());
		// if (compare < 0) {// 大于
		// listChat.set(i, sendChat);
		// listChat.set(j, firstChat);
		// firstChat = listChat.get(i);
		// }
		// }
		// }
		return listChat;
	}

	public void deleteChatGroup(List<String> list, String tno) {
		String sql = "delete from ChatInfo where SendNo=" + tno + " and (";
		int size = list.size();
		if (size > 0) {
			for (int i = 0; i < size - 2; ++i) {
				sql += " receiveNo=" + list.get(i) + " or";
			}
			sql += " receiveNo=" + list.get(size - 1) + ")";
			db = DatabaseManager.getInstance(context);
			db.execSQL(sql);
		}
	}

	public void clearCache() {
		String sql = "delete from Students";
		db = DatabaseManager.getInstance(context);
		db.execSQL(sql);
	}

}
