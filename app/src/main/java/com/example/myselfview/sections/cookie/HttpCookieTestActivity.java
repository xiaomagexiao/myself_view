package com.example.myselfview.sections.cookie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.params.HttpClientParams;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.myself_view.R;
import com.example.myselfview.sections.jdtoast.MCToast;

//参考网址：http://blog.csdn.net/jdsjlzx/article/details/44600259

public class HttpCookieTestActivity extends Activity implements OnClickListener {

	private static final String TAG = "HttpCookieTestActivity";
	private Button cookie_take;
	private Button cookie_save;
	public static HttpClient mHttpClient = null;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		this.setContentView(R.layout.cookie_activity);
		this.cookie_take = (Button) this.findViewById(R.id.cookie_take);
		this.cookie_save = (Button) this.findViewById(R.id.cookie_save);

		this.cookie_take.setOnClickListener(this);
		this.cookie_save.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.cookie_take:
			cookieTest();
			MCToast.show("成功！");
			break;
		case R.id.cookie_save:
			getCookieTest();
			MCToast.show("操作完成");
			break;
		}
	}

	private void getCookieTest() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {

				cookieTest222();
				return null;
			}

			protected void onPostExecute(String result) {
			};

		}.execute();
	}

	private String getAlias(String usr) {
		String tmp = usr.replace("@", "_");
		tmp = tmp.replace(".", "_");
		tmp = tmp.replace("-", "_");
		return tmp;
	}

	private String cookieTest222() {

		HttpClient client = getHttpClient();
		client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 15000); // 设置读数据超时时间(单位毫秒)
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
		HttpPost httpPost = new HttpPost("http://inside.kfkc.webtrn.cn/center/center/login_login.action");
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		params.add(new BasicNameValuePair("loginId", "111111@qq.com"));
		params.add(new BasicNameValuePair("passwd", "96e79218965eb72c92a549dd5a330112"));

		AddCookies(httpPost);
		String v5 = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse v2 = client.execute(((HttpUriRequest) httpPost));
			v5 = "";
			if (v2.getStatusLine().getStatusCode() == 200) {
				v5 = EntityUtils.toString(v2.getEntity(), "UTF-8");
			}

			System.out.println(v2.getStatusLine().getStatusCode() + v5);

			httpPost.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v5;
	}

	private String cookieTest111() {
		HttpClient client = getHttpClient();
		client.getParams().setIntParameter(HttpConnectionParams.SO_TIMEOUT, 15000); // 设置读数据超时时间(单位毫秒)
		client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 15000);
		HttpPost httpPost = new HttpPost(
				"http://inside.kfkc.webtrn.cn/login/kfkc/firstOpenCourseAction/openCourse/queryCourses.json");
		;
		List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();

		AddCookies(httpPost);
		String v5 = null;
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
			HttpResponse v2 = client.execute(((HttpUriRequest) httpPost));
			v5 = "";
			if (v2.getStatusLine().getStatusCode() == 200) {
				v5 = EntityUtils.toString(v2.getEntity(), "UTF-8");
			}
			System.out.println(v2.getStatusLine().getStatusCode() + v5);
			SaveCookies(v2);
			httpPost.abort();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return v5;
	}

	private void cookieTest() {
		new AsyncTask<Void, Void, String>() {

			@Override
			protected String doInBackground(Void... params) {

				cookieTest111();
				return null;
			}

			protected void onPostExecute(String result) {
			};

		}.execute();
	}

	private static HashMap<String, String> CookieContiner = new HashMap<String, String>();

	/**
	 * 保存Cookie
	 * 
	 * @param resp
	 */
	public void SaveCookies(HttpResponse httpResponse) {
		Header[] headers = httpResponse.getHeaders("Set-Cookie");
		String headerstr = headers.toString();
		if (headers == null)
			return;

		for (int i = 0; i < headers.length; i++) {
			String cookie = headers[i].getValue();
			String[] cookievalues = cookie.split(";");
			for (int j = 0; j < cookievalues.length; j++) {
				String[] keyPair = cookievalues[j].split("=");
				String key = keyPair[0].trim();
				String value = keyPair.length > 1 ? keyPair[1].trim() : "";
				CookieContiner.put(key, value);
			}
		}
	}

	/**
	 * 增加Cookie
	 * 
	 * @param request
	 */
	public void AddCookies(HttpPost request) {

		// CookieContiner.put("UC00OOIIll11",
		// "\"MTExMTExQHFxLmNvbTotMTplOTAxY2UzZDQxYTBiZmNkMTQ5MjMwNTlkNGY3NWNjNTpudWxsOmluc2lkZS5rZmtjLndlYnRybi5jbg==\"");
		// CookieContiner.put("JSESSIONID", "40AED853FF4FB881B99DC2B606DD9DD9");

		StringBuilder sb = new StringBuilder();
		Iterator iter = CookieContiner.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			String key = entry.getKey().toString();
			String val = entry.getValue().toString();
			sb.append(key);
			sb.append("=");
			sb.append(val);
			sb.append(";");
		}
		request.addHeader("cookie", sb.toString());
	}

	public void readCookie() {
		try {
			DefaultHttpClient httpclient = new DefaultHttpClient();
			HttpGet httpget = new HttpGet("http://www.hlovey.com/");
			HttpResponse response = httpclient.execute(httpget);
			HttpEntity entity = response.getEntity();
			List<Cookie> cookies = httpclient.getCookieStore().getCookies();
			if (entity != null) {
				entity.consumeContent();
			}

			if (cookies.isEmpty()) {
				Log.i(TAG, "NONE");
			} else {
				for (int i = 0; i < cookies.size(); i++) {
					Log.i(TAG, "- domain " + cookies.get(i).getDomain());
					Log.i(TAG, "- path " + cookies.get(i).getPath());
					Log.i(TAG, "- value " + cookies.get(i).getValue());
					Log.i(TAG, "- name " + cookies.get(i).getName());
					Log.i(TAG, "- port " + cookies.get(i).getPorts());
					Log.i(TAG, "- comment " + cookies.get(i).getComment());
					Log.i(TAG, "- commenturl" + cookies.get(i).getCommentURL());
					Log.i(TAG, "- all " + cookies.get(i).toString());
				}
			}
			httpclient.getConnectionManager().shutdown();

		} catch (Exception e) {
			// Todo
		} finally {
			// Todo
		}
	}

	private static HttpClient getHttpClient() {
		try {
			if (mHttpClient == null) {
				BasicHttpParams basicHttpParams = new BasicHttpParams();
				int v3_1 = 8000;
				ConnManagerParams.setTimeout(((HttpParams) basicHttpParams), ((long) v3_1));
				HttpConnectionParams.setConnectionTimeout(((HttpParams) basicHttpParams), v3_1);
				HttpConnectionParams.setSoTimeout(((HttpParams) basicHttpParams), v3_1);
				HttpProtocolParams.setUseExpectContinue(((HttpParams) basicHttpParams), true);
				HttpConnectionParams.setStaleCheckingEnabled(((HttpParams) basicHttpParams), false);
				HttpProtocolParams.setVersion(((HttpParams) basicHttpParams), HttpVersion.HTTP_1_1);
				HttpProtocolParams.setContentCharset(((HttpParams) basicHttpParams), "UTF-8");
				HttpClientParams.setRedirecting(((HttpParams) basicHttpParams), false);
				HttpConnectionParams.setTcpNoDelay(((HttpParams) basicHttpParams), true);
				HttpConnectionParams.setSocketBufferSize(((HttpParams) basicHttpParams), 8192);
				SchemeRegistry schemeRegistry = new SchemeRegistry();
				schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
				mHttpClient = new DefaultHttpClient(new ThreadSafeClientConnManager(((HttpParams) basicHttpParams),
						schemeRegistry), ((HttpParams) basicHttpParams));
			}
		} catch (Throwable v3) {
		}
		return mHttpClient;
	}

}
