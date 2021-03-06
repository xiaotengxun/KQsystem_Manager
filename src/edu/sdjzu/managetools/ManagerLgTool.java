package edu.sdjzu.managetools;

import java.io.IOException;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class ManagerLgTool {
	private Context context;
	private WebTool web;

	public ManagerLgTool(Context ctx) {
		context = ctx;
		try {
			web = new WebTool(context);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void firstLogin(String uno) {
		web.getUserInfByUno(uno);
		web.getTeachTaskByUno(uno);
		web.getStuByUno(uno);
		// web.getLeaveByUno(uno);
		// web.getPhotoByUno(uno);
	}

	public void secondLogin(String uno) {
		web.getTeachTaskByUno(uno);
	}

	public void clearCache() {

	}
}
