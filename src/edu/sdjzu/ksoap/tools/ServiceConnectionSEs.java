package edu.sdjzu.ksoap.tools;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.ksoap2.transport.ServiceConnection;

public class ServiceConnectionSEs implements ServiceConnection {
	private HttpURLConnection connection;

	public ServiceConnectionSEs(String url,int timeout) throws IOException, IOException {
		super();
		// TODO Auto-generated constructor stub
		this.connection=((HttpURLConnection)new URL(url).openConnection());
		this.connection.setUseCaches(false);
		this.connection.setDoOutput(true);
		this.connection.setDoInput(true);
		this.connection.setConnectTimeout(timeout);;
		this.connection.setReadTimeout(timeout);
//		this.connection.
	}

	@Override
	public void connect() throws IOException {
		// TODO Auto-generated method stub
		this.connection.connect();

	}

	@Override
	public void disconnect() throws IOException {
		// TODO Auto-generated method stub
		this.connection.disconnect();

	}

	@Override
	public InputStream getErrorStream() {
		// TODO Auto-generated method stub
		return this.connection.getErrorStream();
	}

	public String getHost() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getPort() {
		// TODO Auto-generated method stub
		return 0;
	}

	@SuppressWarnings("rawtypes")
	public List getResponseProperties() throws IOException {
		// TODO Auto-generated method stub
		return (List) this.connection.getRequestProperties();
	}

	@Override
	public InputStream openInputStream() throws IOException {
		// TODO Auto-generated method stub
		return this.connection.getInputStream();
	}

	@Override
	public OutputStream openOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return this.connection.getOutputStream();
	}

	@Override
	public void setRequestMethod(String arg0) throws IOException {
		// TODO Auto-generated method stub
		this.connection.setRequestMethod(arg0);

	}

	@Override
	public void setRequestProperty(String arg0, String arg1) throws IOException {
		// TODO Auto-generated method stub
		this.connection.setRequestProperty(arg0, arg1);

	}
	
	public void setConnectionTimeOut(int timeout){
		
		this.connection.setConnectTimeout(timeout);

	}

}
