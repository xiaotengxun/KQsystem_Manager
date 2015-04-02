package edu.sdjzu.managetools;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.MarshalBase64;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.xmlpull.v1.XmlPullParserException;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import edu.sdjzu.attr.Attr;
import edu.sdjzu.ksoap.tools.MyAndroidHttpTransport;
import edu.sdjzu.localtool.LocalSqlTool;
import edu.sdjzu.model.KQStuPerson;
import edu.sdjzu.model.KQresult;
import edu.sdjzu.model.KQStuClass;
import edu.sdjzu.model.Students;
import edu.sdjzu.model.TeachProgress;
import edu.sdjzu.model.TeachTask;
import edu.sdjzu.model.Teachers;
import edu.sdjzu.model.UserInf;

public class WebTool {
	private LocalSqlTool localSqlTool;
	@SuppressWarnings("unused")
	private Context context;
	private String NAMESPACE = "http://chenshuwan.org/";
	private String METHOD_NAME = "";
	private String SOAP_ACTION = "";
	private String URL = "http://jsjzy.sdjzu.edu.cn/sdjzu/service1.asmx";
	private static List<Students> listStu = new ArrayList<Students>();;
	private MyAndroidHttpTransport ht;

	public WebTool(Context context) throws IOException {
		super();
		this.context = context;
		localSqlTool = new LocalSqlTool(context);
		ht = new MyAndroidHttpTransport(URL);
	}

	/**
	 * �����û����ͽ��е�¼���ɹ��򷵻�true
	 * 
	 * @param userType
	 * @param username
	 * @param password
	 * @return
	 */
	public boolean LoginSuccess(String userType, String username, String password) {
		boolean istrue = false;
		METHOD_NAME = "LoginSuccess";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("userType", userType);
		rpc.addProperty("username", username);
		rpc.addProperty("password", password);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			return false;
		} catch (IOException e) {
			return false;
		}
		try {
			if (envelope.getResponse() != null) {
				istrue = Boolean.valueOf(envelope.getResponse().toString());
				System.out.println("�û�" + username + "�Ƿ��¼�ɹ���" + istrue);
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		return istrue;
	}

	/**
	 * ���ݸ���Ա��Ժ����Ա�ı�Ż���û���Ϣ
	 * 
	 * @param uno
	 * @return
	 */

	public UserInf getUserInfByUno(String uno) {
		UserInf user = null;
		METHOD_NAME = "getUserInfByUno";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("uno", uno);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			e1.printStackTrace();
			return user;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return user;
		}
		try {
			if (envelope.getResponse() != null) {
				Object result = (Object) envelope.getResponse();
				SoapObject data = (SoapObject) result;
				user = new UserInf();
				user.setUserNo(data.getProperty("Uno").toString());
				user.setUserPassword(data.getProperty("Upwd").toString());
				user.setUserName(data.getProperty("Uname").toString());
				user.setUserSex(data.getProperty("Usex").toString());
				user.setUserSdept(data.getProperty("Usdept").toString());
				user.setUserClass(data.getProperty("Usclass").toString());
				user.setUserTel(data.getProperty("Utel").toString());
				user.setUserType(data.getProperty("Utype").toString());
				Log.i("chen", user.getUserName());
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		if (user != null) {

		}
		localSqlTool.insertUserInfByUno(user, uno);// ///////////////////////////
		return user;

	}

	/**
	 * ���ݵ�Ա��Ż����Ӧ����ʦ�����
	 * 
	 * @param uno
	 * @return
	 */
	public List<TeachTask> getTeachTaskByUno(String uno) {
		// getRWTBbyUno
		List<TeachTask> tlist = new ArrayList<TeachTask>();

		METHOD_NAME = "getRWTBbyUno";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("uno", uno);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			e1.printStackTrace();
			return tlist;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return tlist;
		}
		try {
			if (envelope.getResponse() != null) {
				Object result = (Object) envelope.getResponse();
				SoapObject data = (SoapObject) result;
				int counts = data.getPropertyCount();
				for (int i = 0; i < counts; i++) {
					String str = "";
					SoapObject s = (SoapObject) data.getProperty(i);
					TeachTask task = new TeachTask();
					task.setCourseName(s.getProperty("Cname").toString());
					task.setCourseNo(s.getProperty("Cno").toString());
					task.setCourseType(s.getProperty("Ctype").toString());
					task.setTaskClass(s.getProperty("Rclass").toString());
					task.setTaskNo(Integer.valueOf(s.getProperty("Rno").toString()));
					task.setTaskTerms(s.getProperty("Rterms").toString());
					str = s.getProperty("Rweek").toString();
					task.setTaskWeek(str);
					task.setTeaName(s.getProperty("Tname").toString());
					Log.i("chen", task.getCourseName());
					tlist.add(task);
				}
			}
		} catch (SoapFault e) {
			e.printStackTrace();
			return tlist;
		}
		localSqlTool.insertTeachTaskByTaskNo(tlist);// //////////////////
		return tlist;
	}

	/**
	 * ���ݰ༶ �� �γ̻������Ӧѧ���Ŀ������
	 * 
	 * @param uno
	 * @return
	 */
	public List<KQStuClass> getStuPrivateKQByUno(String course, String cla) {
		// getStuKQTBbyUno
		List<KQStuClass> listPKQ = new ArrayList<KQStuClass>();
		METHOD_NAME = "getStuKQTBbyClaAndCourse";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("course", course);
		rpc.addProperty("cla", cla);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return listPKQ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listPKQ;
		}
		try {
			if (envelope.getResponse() != null) {
				Object result = (Object) envelope.getResponse();
				SoapObject data = (SoapObject) result;
				int counts = data.getPropertyCount();
				for (int i = 0; i < counts; i++) {
					SoapObject s = (SoapObject) data.getProperty(i);
					KQStuClass stu = new KQStuClass();
					stu.setChidao(Integer.valueOf(s.getProperty("Cdao").toString()));
					stu.setQingjia(Integer.valueOf(s.getProperty("Qjia").toString()));
					stu.setQueqing(Integer.valueOf(s.getProperty("Qqing").toString()));
					stu.setSno(s.getProperty("Sno").toString());
					stu.setSname(s.getProperty("Sname").toString());
					listPKQ.add(stu);
				}
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		Log.i("chen", "coursName=" + course + " className=" + cla + "size=" + listPKQ.size());
		return listPKQ;
	}

	/**
	 * ���ݵ�Ա��Ż������ص�����ѧ������Ϣ
	 * 
	 * @param uno
	 * @return
	 */
	public List<Students> getStuByUno(String uno) {
		// getStuByUno
		List<Students> listKQ = new ArrayList<Students>();
		METHOD_NAME = "getStuByUno";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("uno", uno);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			e1.printStackTrace();
			return listKQ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listKQ;
		}
		try {
			if (envelope.getResponse() != null) {
				Object result = (Object) envelope.getResponse();
				SoapObject data = (SoapObject) result;
				int counts = data.getPropertyCount();
				for (int i = 0; i < counts; i++) {
					SoapObject s = (SoapObject) data.getProperty(i);
					Students kq = new Students();
					kq.setStuClass(s.getProperty("Sclass").toString());
					kq.setStuNo(s.getProperty("Sno").toString());
					kq.setStuPic(s.getProperty("Spic").toString());
					kq.setStuSex(s.getProperty("Ssex").toString());
					kq.setStuName(s.getProperty("Sname").toString());
					kq.setStuState(s.getProperty("Sstate").toString());
					// kq.setCoursePercent(s.getProperty("CoursePercent")
					// .toString());//////////////////////////////////////////////////////////
					kq.setStuId(s.getProperty("Sid").toString());
					kq.setStuPassword(s.getProperty("Spwd").toString());
					kq.setParPassword(s.getProperty("Ppwd").toString());
					kq.setStuSdept(s.getProperty("Ssdept").toString());
					kq.setParTel(s.getProperty("Ptel").toString());
					kq.setStuTel(s.getProperty("Stel").toString());
					Log.i("chen", kq.getStuNo());
					listKQ.add(kq);
				}
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		localSqlTool.insertClassStuByRno(listKQ);// //
		return listKQ;
	}

	/**
	 * 
	 * ����ѧ����Ż��ѧ���Ŀ�����ϸ��Ϣ
	 * 
	 * @param sno
	 * @return
	 */
	public List<KQStuPerson> getKQStuPerson(String sno) {
		// getStuKQTBbyUno
		List<KQStuPerson> listPKQ = new ArrayList<KQStuPerson>();
		METHOD_NAME = "getKQStuPersonBySno";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("sno", sno);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return listPKQ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listPKQ;
		}
		try {
			if (envelope.getResponse() != null) {
				Object result = (Object) envelope.getResponse();
				SoapObject data = (SoapObject) result;
				int counts = data.getPropertyCount();
				for (int i = 0; i < counts; i++) {
					SoapObject s = (SoapObject) data.getProperty(i);
					KQStuPerson stu = new KQStuPerson();
					stu.setDate(s.getProperty("Date").toString());
					stu.setjClass(s.getProperty("JClass").toString());
					stu.setType(s.getProperty("Type").toString());
					stu.setWeek(s.getProperty("Week").toString());
					stu.setWeekDay(s.getProperty("WeekDay").toString());
					listPKQ.add(stu);
				}
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		return listPKQ;
	}

	/**
	 * ���ݵ�Ա��Ż�����µĿ���֪ͨ��Ϣ
	 * 
	 * @param uno
	 * @return
	 */
	public List<String> getLatestKqInfoByUno(String uno) {
		// getStuKQTBbyUno
		List<String> listPKQ = new ArrayList<String>();
		METHOD_NAME = "getLatestKqInfoByNo";
		SOAP_ACTION = NAMESPACE + METHOD_NAME;
		SoapObject rpc = new SoapObject(NAMESPACE, METHOD_NAME);
		rpc.addProperty("keyNo", uno);
		// AndroidHttpTransport ht = new AndroidHttpTransport(URL);
		ht.debug = true;
		SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
		envelope.bodyOut = rpc;
		envelope.dotNet = true;
		(new MarshalBase64()).register(envelope);
		envelope.setOutputSoapObject(rpc);
		try {
			ht.call(SOAP_ACTION, envelope);
		} catch (XmlPullParserException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return listPKQ;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return listPKQ;
		}
		try {
			if (envelope.getResponse() != null) {
				Object result = (Object) envelope.getResponse();
				SoapObject data = (SoapObject) result;
				int counts = data.getPropertyCount();
//				Log.i("chen", "ncounts=" + counts);
				for (int i = 0; i < counts; i++) {
					listPKQ.add(data.getProperty(i).toString());
				}
			}
		} catch (SoapFault e) {
			e.printStackTrace();
		}
		return listPKQ;
	}

}
