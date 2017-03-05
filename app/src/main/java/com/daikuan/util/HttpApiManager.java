package com.daikuan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;

import android.os.AsyncTask;

public class HttpApiManager {
	
//	final static String API_BASE_TEST = "http://115.28.11.101:8080/nodeapi_test/";
	final static String API_BASE_ONLINE = "http://115.28.11.101:8080/nodeapi/";
//	final static String API_BASE_TEST = "http://192.168.1.115:8080/nodeapi/";

	final static String API_BASE = API_BASE_ONLINE;
	
	final static String API_URL = API_BASE + "locationtrace";
	
	final static String USER_AGENT = "NodeLocationtrace";

	final static String API_BAIDU_POSITION_PARSER = "http://api.map.baidu.com/geocoder/v2/";
	
	final static String API_PHONE_LOC_API = API_BASE + "/phone?params=%1$s";
	
	final static String API_PHONE_SWITCH_API = API_BASE + "/ps?params=%1$s";
	
	//在线课程列表
	final static String API_COURSELIST_API = API_BASE
			+ "/courselist?params=%1$s";

	public static AsyncTaskHttpRequest addApiRequest(RequestHolder request) {
		AsyncTaskHttpRequest asyncRequest = new AsyncTaskHttpRequest(request,
				API_URL, "POST");
		asyncRequest.execute();
		return asyncRequest;
	}

	/**
	 * 百度经纬度解析api
	 */
	public static AsyncTaskHttpRequest addBaiduPositionParseRequest(
			RequestHolder request) {
		AsyncTaskHttpRequest asyncRequest = new AsyncTaskHttpRequest(request,
				API_BAIDU_POSITION_PARSER, "GET");
		asyncRequest.execute();
		return asyncRequest;
	}
	
	public static class AsyncTaskHttpRequest extends
			AsyncTask<Void, Void, ResponseHolder> {
		RequestHolder request;
		HttpURLConnection connect;
		String hostUrl;
		String requestMethod;

		public void stopRequest() {
			if (connect != null) {
				connect.disconnect();
			}
			cancel(true);
		}

		public AsyncTaskHttpRequest(RequestHolder request, String hostUrl,
				String requestMethod) {
			this.request = request;
			this.hostUrl = hostUrl;
			this.requestMethod = requestMethod;
		}

		@Override
		protected ResponseHolder doInBackground(Void... params) {
			ResponseHolder resultHolder = new ResponseHolder();
			try {
				URL url = new URL(hostUrl);
				connect = (HttpURLConnection) url.openConnection();
				HttpURLConnection.setFollowRedirects(false);
				connect.addRequestProperty("User-Agent", USER_AGENT);
				connect.addRequestProperty("Accept-Encoding", "gzip");
				connect.addRequestProperty("Accept", "*/*");
				connect.addRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connect.setReadTimeout(request.readTimeout);
				connect.setConnectTimeout(request.readTimeout);
				connect.setUseCaches(false);
				connect.setDefaultUseCaches(false);
				connect.setDoOutput(true);
				connect.setDoInput(true);
				connect.setRequestMethod(this.requestMethod);
				writeToOutputStream(connect, request.params);
				connect.connect();
				int responseCode = connect.getResponseCode();
				if (responseCode == HttpStatus.SC_OK) {
					if (connect.getContentEncoding() != null
							&& connect.getContentEncoding().toLowerCase()
									.trim().equals("gzip")) {
						InputStream is = null;
						InputStream gis = null;
						try {
							is = connect.getInputStream();
							gis = new GZIPInputStream(is);
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							byte[] buffer = new byte[1024];
							int readLength = -1;
							while ((readLength = gis.read(buffer)) != -1) {
								bos.write(buffer, 0, readLength);
							}
							resultHolder.result = new String(bos.toByteArray(),
									"UTF-8");
							resultHolder.code = ResponseHolder.SUCCESS_RESPONSE;
							try {
								bos.close();
								gis.close();
								is.close();
							} catch (Exception e) {

							}
						} catch (IOException e) {
							e.printStackTrace();
							resultHolder.code = ResponseHolder.NETWORK_IO_ERROR;
						}
					} else {
						resultHolder.code = ResponseHolder.SERVER_ERROR;
						android.util.Log.e("ERROR","HttpApiManager:" + "服务器未返回GZIP格式");
					}
				} else {
					if (responseCode >= 400) {
						InputStream is = connect.getErrorStream();
						byte[] buff = new byte[128];
						is.read(buff);
						try {
							is.close();
						} catch (Exception e) {
						}
						buff = null;
					}
					resultHolder.code = ResponseHolder.SERVER_ERROR;
					android.util.Log.e("zhenchuan", "code:"+resultHolder.code);
					android.util.Log.e("ERROR","HttpApiManager:" + "服务器遇到404、500等异常");
				}
			} catch (MalformedURLException e) {
				resultHolder.code = ResponseHolder.NETWORK_IO_ERROR;
				e.printStackTrace();
			} catch (IOException e) {
				resultHolder.code = ResponseHolder.NETWORK_IO_ERROR;
				e.printStackTrace();
			}
			if (connect != null) {
				connect.disconnect();
			}
			return resultHolder;
		}

		private void writeToOutputStream(HttpURLConnection connect,
				List<NameValuePair> param) throws IOException {
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < param.size(); i++) {
				NameValuePair pair = param.get(i);
				sb.append(pair.getName()).append("=")
						.append(URLEncoder.encode(pair.getValue(), "UTF-8"))
						.append("&");
			}
			OutputStream os = connect.getOutputStream();
			os.write(sb.toString().getBytes("UTF-8"));
			os.flush();
			os.close();
		}

		protected void onPostExecute(ResponseHolder response) {
			try {
				if (response.code == ResponseHolder.NETWORK_IO_ERROR) {
					if (request != null && request.callback != null) {
						request.callback.onNetworkError();
					}
				} else if (response.code == ResponseHolder.SERVER_ERROR) {
					if (request != null && request.callback != null) {
						request.callback.onServerError();
					}
				} else if (response.code == ResponseHolder.SUCCESS_RESPONSE) {
					if (request != null && request.callback != null) {
						request.callback.onApiReponse(response.result);
					}
				}
			} catch (NullPointerException e) {
				android.util.Log.e("ERROR","HttpApiManager request callback operate on a destroyed Activity");
			}
		};
	}

	public static interface HttpApiResponseCallback {

		/**
		 * 服务器异常，返回404，500等
		 */
		void onServerError();

		/**
		 * 网络异常，无网络或网络超时
		 */
		void onNetworkError();

		/**
		 * 返回json数据
		 * 
		 * @param json
		 */
		void onApiReponse(String json);

	}

	public static class RequestHolder {
		public List<NameValuePair> params;
		public HttpApiResponseCallback callback;
		public int readTimeout;

		public RequestHolder(List<NameValuePair> params,
				HttpApiResponseCallback callback, int readTimeout) {
			super();
			this.params = params;
			this.callback = callback;
			this.readTimeout = readTimeout;
		}

		public RequestHolder() {
		}
	}

	static class ResponseHolder {
		static int NETWORK_IO_ERROR = 1;
		static int SUCCESS_RESPONSE = 0;
		static int SERVER_ERROR = 2;// 404 ,500等
		int code;// 0:成功返回，1网络IO异常
		String result;// 返回的json串
	}
}
