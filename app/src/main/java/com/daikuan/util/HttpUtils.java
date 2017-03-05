package com.daikuan.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.http.HttpStatus;

import com.daikuan.util.HttpApiManager.HttpApiResponseCallback;
import com.daikuan.util.HttpApiManager.ResponseHolder;

import android.os.AsyncTask;


public class HttpUtils {

	public static AsyncTaskHttpGetRequest requestUrl(String url, HttpApiResponseCallback callback) {
		AsyncTaskHttpGetRequest asyncRequest = new AsyncTaskHttpGetRequest(url,callback);
		asyncRequest.execute();
		return asyncRequest;
	}

	public static class AsyncTaskHttpGetRequest extends
			AsyncTask<Void, Void, ResponseHolder> {
		HttpURLConnection connect;
		String url;
		HttpApiManager.HttpApiResponseCallback callback;

		public void stopRequest() {
			if (connect != null) {
				try {
					connect.disconnect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			cancel(true);
		}

		public AsyncTaskHttpGetRequest(String url,HttpApiResponseCallback callback) {
			this.url = url;
			this.callback=callback;
		}

		@Override
		protected ResponseHolder doInBackground(Void... params) {
			ResponseHolder resultHolder = new ResponseHolder();
			try {
				URL url = new URL(this.url);
				connect = (HttpURLConnection) url.openConnection();
				HttpURLConnection.setFollowRedirects(false);
				connect.addRequestProperty("Accept-Encoding", "gzip");
				connect.addRequestProperty("Accept", "*/*");
				connect.addRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connect.setReadTimeout(30 * 1000);
				connect.setConnectTimeout(30 * 1000);
				connect.setUseCaches(false);
				connect.setDefaultUseCaches(false);
				connect.setDoOutput(false);
				connect.setDoInput(true);
				connect.setRequestMethod("GET");
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
						InputStream is = null;
						try {
							is = connect.getInputStream();
							ByteArrayOutputStream bos = new ByteArrayOutputStream();
							byte[] buffer = new byte[1024];
							int readLength = -1;
							while ((readLength = is.read(buffer)) != -1) {
								bos.write(buffer, 0, readLength);
							}
							resultHolder.result = new String(bos.toByteArray(),
									"UTF-8");
							resultHolder.code = ResponseHolder.SUCCESS_RESPONSE;
							try {
								bos.close();
								is.close();
							} catch (Exception e) {

							}
						} catch (IOException e) {
							e.printStackTrace();
							resultHolder.code = ResponseHolder.NETWORK_IO_ERROR;
						}
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
		
		protected void onPostExecute(ResponseHolder response) {
			try {
				if (response.code == ResponseHolder.NETWORK_IO_ERROR) {
					if (this.callback != null) {
						this.callback.onNetworkError();
					}
				} else if (response.code == ResponseHolder.SERVER_ERROR) {
					if (this.callback != null) {
						this.callback.onServerError();
					}
				} else if (response.code == ResponseHolder.SUCCESS_RESPONSE) {
					if (this.callback != null) {
						this.callback.onApiReponse(response.result);
					}
				}
			} catch (NullPointerException e) {
				android.util.Log.e("ERROR","HttpApiManager request callback operate on a destroyed Activity");
			}
		};
	}
}
