package com.ruoyi.system.tool;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class HttpClientUtil
{
	private String list_url;
	private String data_select_col;
	private String data_select_code;
	private String method;

	private int data_page_no;
	private int data_page_size = 1000;

	private final String format = "JSON";
	private final String locale = "ZH_CN";
	private final String version = "1.0";

	private final String charset = "UTF-8";
	public final String appId = "0002";
	public final String key = "n3H8hLAs";
	private final BASE64Encoder encB64 = new BASE64Encoder();

	
    public String doPost(String url,Map<String,String> map,String charset)
	{
	    org.apache.http.client.HttpClient httpClient = null;
		HttpPost httpPost = null;
		String result = null;
		try
		{
			HttpClientBuilder clientBuilder=HttpClients.custom();
			httpClient = clientBuilder.build();
			httpPost = new HttpPost(url);
			//设置参数
			List<NameValuePair> list = new ArrayList<NameValuePair>();
			Iterator iterator = map.entrySet().iterator();
			while(iterator.hasNext())
			{
				Entry<String,String> elem = (Entry<String, String>) iterator.next();
				list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
			}
			if(list.size() > 0)
			{
				UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list,charset);
				httpPost.setEntity(entity);
			}
			HttpResponse response = httpClient.execute(httpPost);
			if(response != null)
			{
				HttpEntity resEntity = response.getEntity();
				if(resEntity != null)
				{
					result = EntityUtils.toString(resEntity,charset);
				}
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		return result;
	}

	public String get(String url, String charset)
	{
		CloseableHttpClient httpclient = HttpClients.createDefault();
		String result = null;
		try
		{
			HttpGet httpget = new HttpGet(url);
//			System.out.println("executing request " + httpget.getURI());
			CloseableHttpResponse response = httpclient.execute(httpget);
			try
			{
				if (response != null)
				{
					HttpEntity resEntity = response.getEntity();
					if (resEntity != null)
					{
						result = EntityUtils.toString(resEntity, charset);
					}
				}
			}
			finally
			{
				response.close();
			}
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				httpclient.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return result;
	}
}
