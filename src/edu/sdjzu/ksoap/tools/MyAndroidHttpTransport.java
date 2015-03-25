package edu.sdjzu.ksoap.tools;

import java.io.IOException;

import org.ksoap2.transport.HttpTransportSE;
import org.ksoap2.transport.ServiceConnection;

/**
 * Http¡¨Ω”—” ±
 * @author Administrator
 *
 */
public class MyAndroidHttpTransport extends HttpTransportSE {

	private int timeout=30000;
	public MyAndroidHttpTransport(String url, int timeout) throws IOException {
		super(url);
		this.timeout = timeout;
	}
	public MyAndroidHttpTransport(String url) throws IOException {
		super(url);
	}
     protected ServiceConnection getServiceConnection() throws IOException {
    	 ServiceConnectionSEs serviceConnection=new ServiceConnectionSEs(this.url,timeout);
    	 return serviceConnection;
     }
}
