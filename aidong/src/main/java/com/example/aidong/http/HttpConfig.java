package com.example.aidong.http;

import android.graphics.Bitmap;

import com.example.aidong.ui.BaseApp;
import com.example.aidong.R;
import com.example.aidong.utils.common.MXLog;
import com.example.aidong.entity.model.result.MsgResult;
import com.example.aidong.utils.ImageFactory;
import com.example.aidong.utils.LogUtils;
import com.example.aidong.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.leyuan.commonlibrary.http.IHttpCallback;
import com.leyuan.commonlibrary.http.IHttpTask;
import com.leyuan.commonlibrary.http.IHttpToastCallBack;
import com.leyuan.commonlibrary.http.threads.ThreadPool;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.xml.sax.SAXException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

public class HttpConfig {
    private static HttpConfig instance;
    public static final String JSON = "json";
    public static final String XML = "xml";

    private static int TIMEOUT = 30 * 1000;

    public static final int GET = 0;
    public static final int POST = 1;
    public static final int PUT = 2;
    public static final int DELETE = 3;

    private HttpConfig() {
    }

    public static HttpConfig getInstance() {
        if (null == instance) {
            synchronized (HttpConfig.class) {
                if (null == instance) {
                    instance = new HttpConfig();
                }
            }
        }
        return instance;
    }

    public void sendRequest(IHttpCallback callback, int method,
                            String jsonOrXml, IHttpTask task, int requestCode, IHttpToastCallBack base) {
        sendRequest(callback, null, method, jsonOrXml, task, requestCode, base);
    }

    public void sendRequest(IHttpCallback callback, String path, int method,
                            String jsonOrxml, IHttpTask task, int requestCode, IHttpToastCallBack base) {
        handleRequest(callback, task, method, jsonOrxml, path, requestCode,
                base);
    }

    private void handleRequest(IHttpCallback callback, IHttpTask task,
                               int method, String jsonOrxml, String filepath, int requestCode,
                               IHttpToastCallBack base) {
        Worker wk = new Worker(callback, task, filepath, method, jsonOrxml,
                requestCode, base);
        ThreadPool.getExecutorServiceInstance().execute(wk);
    }

    private static class Worker implements Runnable {
        private IHttpCallback callback;
        private IHttpTask task;
        private int method;
        public String filepath;
        private String jsonOrxml;
        private int requestCode;

        private IHttpToastCallBack base;

        public Worker(IHttpCallback calback, IHttpTask task, String filepath,
                      int method, String jsonOrxml, int requestCode, IHttpToastCallBack base) {
            this.callback = calback;
            this.task = task;
            this.method = method;
            this.filepath = filepath;
            this.jsonOrxml = jsonOrxml;
            this.requestCode = requestCode;
            this.base = base;
        }

        public void run() {
            HttpClient client = null;
            HttpURLConnection conn = null;

            try {
                if (task.getJson() != null) {
                    client = new DefaultHttpClient();
                    HttpParams par = client.getParams();
                    HttpConnectionParams.setConnectionTimeout(par, TIMEOUT);
                    HttpConnectionParams.setSoTimeout(par, 60000);
                    ConnManagerParams.setTimeout(par, TIMEOUT);
                    client.getParams().setIntParameter(
                            HttpConnectionParams.SO_TIMEOUT, 60000); // 超时设置
                    client.getParams().setIntParameter(
                            HttpConnectionParams.CONNECTION_TIMEOUT, 30000);// 连接超时
                    HttpPostData(client, task.getSubUrl(), task.getJson());
                    return;
                }
                HttpUtils http = new HttpUtils(TIMEOUT, "Android");
                http.configSoTimeout(60000);
                RequestParams params = new RequestParams();
                if (BaseApp.mInstance.isLogin() && BaseApp.mInstance.getUser() != null) {
                    MXLog.out("t:" + BaseApp.mInstance.getUser().getToken());
                    params.addHeader("token", BaseApp.mInstance.getUser().getToken());
                }
                if (task.getHeaderMap() != null) {
                    for (String key : task.getHeaderMap().keySet()) {
                        params.addHeader(key, URLEncoder.encode(task.getHeaderMap().get(key), "UTF-8"));
                    }
                }
                HttpMethod putOrPostMethod = HttpMethod.POST;
                HttpMethod getOrdeteteMethod = HttpMethod.GET;
                List<BasicNameValuePair> parls = task.getparams();
                if (method != DELETE) {
                    if (parls == null) {
                        parls = new ArrayList<>();
                    }
                    parls.add(0, new BasicNameValuePair("device", Utils.getIMEI(BaseApp.mInstance)));
                    //					parls.add(new BasicNameValuePair("channel", ""));
                    parls.add(0, new BasicNameValuePair("version", Utils.getVersion(BaseApp.mInstance)));
                    parls.add(0, new BasicNameValuePair("lng", "" + BaseApp.mInstance.lon));
                    parls.add(0, new BasicNameValuePair("lat", "" + BaseApp.mInstance.lat));
                    if (BaseApp.mInstance.getToken() != null) {
                        parls.add(0, new BasicNameValuePair("token", BaseApp.mInstance.getToken()));
                    }
                }

                switch (method) {
                    case DELETE:
                        getOrdeteteMethod = HttpMethod.DELETE;
                        StringBuilder desb = new StringBuilder(task.getSubUrl());
                        if (task.getparams() != null && !task.getparams().isEmpty()) {

                            for (BasicNameValuePair pair : task.getparams()) {
                                desb.append('/');
                                desb.append(pair.getValue());
                            }
                        }
                        http.configCurrentHttpCacheExpiry(200);

                        http.send(getOrdeteteMethod, desb.toString(), params,
                                requestCallBack);

                        break;
                    case GET:
                        StringBuilder sb = new StringBuilder(task.getSubUrl());

                        if (parls != null && !parls.isEmpty()) {
                            sb.append('?');
                            for (BasicNameValuePair pair : parls) {

                                sb.append(pair.getName()).append('=')
                                        .append(URLEncoder.encode(pair.getValue(), "UTF-8")).append('&');
                            }
                            sb.deleteCharAt(sb.length() - 1);
                        }
                        http.configCurrentHttpCacheExpiry(200);
                        MXLog.out("url:" + sb.toString());
                        LogUtils.e("url",sb.toString());
                        http.send(getOrdeteteMethod, sb.toString(), params,
                                requestCallBack);

                        break;
                    case PUT:
                        putOrPostMethod = HttpMethod.PUT;
                    case POST:
                        if (parls != null && !parls.isEmpty()) {
                            for (BasicNameValuePair pair : parls) {
                                params.addBodyParameter(pair.getName(), pair.getValue());
                            }
                        }
                        if (task.mapFiles != null && task.mapFiles.size() > 0) {
                            params.addHeader("connection", "keep-alive");// "connection",
                            // "keep-alive"

                            Set<Entry<String, Object[]>> fileset = task.mapFiles
                                    .entrySet();
                            for (Entry<String, Object[]> param : fileset) {
                                String key = param.getKey();
                                int i = 0;
                                if (key.equals("image") || key.equals("photo")) {
                                    for (Object path : param.getValue()) {
                                        if (path instanceof String) {
                                            // f = new File((String) path);
                                            Bitmap b = ImageFactory.ratio((String) path, 480, 800);
                                            //										InputStream is = ImageFactory.Bitmap2InputStream(b);
                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            baos = ImageFactory.compressAndGenImageOs(b, 200);
                                            //								        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                            InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                            if (key.equals("photo")) {
                                                String k;
                                                if (i == 0) {
                                                    k = key;
                                                } else {
                                                    k = key + i;
                                                }
                                                i++;
                                                params.addBodyParameter(k, is, baos.size(), "image.jpg");
                                            } else {
                                                params.addBodyParameter("" + i++, is, baos.size(), "image.jpg");
                                            }
                                            ;

                                        } else if (path instanceof File) {
                                            // f = (File) path;
                                            Bitmap b = ImageFactory.ratio(((File) path).getAbsolutePath(), 480, 800);
                                            //										InputStream is = ImageFactory.Bitmap2InputStream(b);
                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            baos = ImageFactory.compressAndGenImageOs(b, 200);
                                            //								        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                            InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                            if (key.equals("photo")) {
                                                String k;
                                                if (i == 0) {
                                                    k = key;
                                                } else {
                                                    k = key + i;
                                                }
                                                i++;
                                                params.addBodyParameter(k, is, baos.size(), "image.jpg");
                                            } else {
                                                params.addBodyParameter("" + i++, is, baos.size(), "image.jpg");
                                            }
                                        } else if (path instanceof Bitmap) {
                                            Bitmap b = ImageFactory.ratio((Bitmap) path, 480, 800);
                                            //										InputStream is = ImageFactory.Bitmap2InputStream(b);
                                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                            baos = ImageFactory.compressAndGenImageOs(b, 200);
                                            //								        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                            InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                            if (key.equals("photo")) {
                                                String k;
                                                if (i == 0) {
                                                    k = key;
                                                } else {
                                                    k = key + i;
                                                }
                                                i++;
                                                params.addBodyParameter(k, is, baos.size(), "image.jpg");
                                            } else {
                                                params.addBodyParameter("" + i++, is, baos.size(), "image.jpg");
                                            }
                                        }
                                    }

                                } else {
                                    if (param.getValue().length == 1) {
                                        Object path = param.getValue()[0];
                                        if (key.equals("avatar")) {
                                            if (path instanceof String) {
                                                // f = new File((String) path);
                                                Bitmap b = ImageFactory.ratio((String) path, 480, 800);
                                                //											InputStream is = ImageFactory.Bitmap2InputStream(b);
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                baos = ImageFactory.compressAndGenImageOs(b, 200);
                                                //									        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                                params.addBodyParameter(key, is, baos.size(), "image.jpg");
                                                ;

                                            } else if (path instanceof File) {
                                                // f = (File) path;
                                                Bitmap b = ImageFactory.ratio(((File) path).getAbsolutePath(), 480, 800);
                                                //											InputStream is = ImageFactory.Bitmap2InputStream(b);
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                baos = ImageFactory.compressAndGenImageOs(b, 200);
                                                //									        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                                params.addBodyParameter(key, is, baos.size(), "image.jpg");
                                            } else if (path instanceof Bitmap) {
                                                Bitmap b = ImageFactory.ratio((Bitmap) path, 480, 800);
                                                //											InputStream is = ImageFactory.Bitmap2InputStream(b);
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                baos = ImageFactory.compressAndGenImageOs(b, 200);
                                                //									        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                                params.addBodyParameter(key, is, baos.size(), "image.jpg");
                                            }
                                        } else {
                                            if (path instanceof String) {
                                                // f = new File((String) path);

                                                params.addBodyParameter(key,
                                                        new File((String) path));

                                            } else if (path instanceof File) {
                                                // f = (File) path;
                                                params.addBodyParameter(key, (File) path);
                                            } else if (path instanceof Bitmap) {
                                                Bitmap b = ImageFactory.ratio((Bitmap) path, 480, 800);
                                                //										InputStream is = ImageFactory.Bitmap2InputStream(b);
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                baos = ImageFactory.compressAndGenImageOs(b, 200);
                                                //									        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                                params.addBodyParameter(key, is, baos.size(), "image.jpg");

                                            } else {
                                                return;
                                            }
                                        }
                                    } else {
                                        for (Object path : param.getValue()) {

                                            if (path instanceof String) {
                                                // f = new File((String) path);

                                                params.addBodyParameter("" + i++,
                                                        new File((String) path));

                                            } else if (path instanceof File) {
                                                // f = (File) path;
                                                params.addBodyParameter(""
                                                        + i++, (File) path);
                                            } else if (path instanceof Bitmap) {
                                                Bitmap b = ImageFactory.ratio((Bitmap) path, 480, 800);
                                                //										InputStream is = ImageFactory.Bitmap2InputStream(b);
                                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                                baos = ImageFactory.compressAndGenImageOs(b, 200);
                                                //										        b.compress(Bitmap.CompressFormat.JPEG, 30, baos);
                                                InputStream is = new ByteArrayInputStream(baos.toByteArray());
                                                params.addBodyParameter("" + i++, is, baos.size(), "image.jpg");

                                            } else {
                                                return;
                                            }
                                        }
                                    }

                                }
                            }

                        }
                        http.send(putOrPostMethod, task.getSubUrl(), params,
                                requestCallBack);
                        break;

                }

            } catch (SocketTimeoutException e1) {
                e1.printStackTrace();

                if (null != callback) {
                    // if(!getLastResponse()){

                    callback.onError("请求超时", requestCode);
                    if (base != null) {
                        base.showToastOnUiThread("请求超时");
                    }
                    // }

                }
            } catch (ConnectTimeoutException e) {
                e.printStackTrace();

                if (null != callback) {
                    // if(!getLastResponse()){
                    callback.onError("请求超时", requestCode);
                    if (base != null) {
                        base.showToastOnUiThread("请求超时");
                    }
                    // }

                }
            } catch (Exception e) {
                e.printStackTrace();
                if (null != callback) {
                    // if(!getLastResponse()){
                    callback.onError("网络错误", requestCode);
                    if (base != null) {
                        //						base.showToastOnUiThread(base.getResources().getString(
                        //								R.string.net_error));
                        base.showToastOnUiThread("当前网络不可用，请检查网络设置");
                    }
                    // }
                }
            } finally {
                if (client != null) {
                    client.getConnectionManager().shutdown();
                }
                if (conn != null) {
                    conn.disconnect();
                }
            }
        }

        RequestCallBack<String> requestCallBack = new RequestCallBack<String>() {

            @Override
            public void onFailure(HttpException arg0, String arg1) {
                // TODO Auto-generated method stub
                if (null != callback) {
                    // if(!getLastResponse()){
                    callback.onError("网络错误", requestCode);
                    MXLog.out(arg1 + "===" + requestCode);
                    MXLog.out("url===" + task.getSubUrl());
                    MXLog.e("error", arg1 + "===" + requestCode);
                    if (base != null) {
                        //						base.showToastOnUiThread(base.getResources().getString(
                        //								R.string.net_error)
                        //								+ arg0.getExceptionCode());
                        if (arg0.getExceptionCode() == 0) {
                            if (arg1.startsWith("org.apache.http.conn.ConnectTimeoutException")) {
                                base.showToastOnUiThread("请求超时");
                            } else {
                                base.showToastOnUiThread("当前网络不可用，请检查网络设置");
                            }
                        } else if (arg0.getExceptionCode() == 409) {
                            base.showToastOnUiThread("提交失败，请重新提交");
                        } else {
                            //							base.showToastOnUiThread(base.getResources().getString(
                            //									R.string.net_error));
                            base.showToastOnUiThread("网络异常");
                        }
                    }
                    // }
                }
            }

            @Override
            public void onSuccess(ResponseInfo<String> arg0) {
                // TODO Auto-generated method stub
                try {
                    parseData(arg0.result);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    MXLog.out("url===" + task.getSubUrl());
                    if (null != callback) {
                        // if(!getLastResponse()){
                        callback.onError("网络错误", requestCode);
                        if (base != null) {
                            base.showToastOnUiThread(base.getResources()
                                    .getString(R.string.net_error));
                        }
                        // }
                    }
                } catch (ParserConfigurationException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (null != callback) {
                        // if(!getLastResponse()){
                        callback.onError("网络错误", requestCode);
                        if (base != null) {
                            base.showToastOnUiThread(base.getResources()
                                    .getString(R.string.net_error));
                        }
                        // }
                    }
                } catch (SAXException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                    if (null != callback) {
                        // if(!getLastResponse()){
                        callback.onError("网络错误", requestCode);
                        if (base != null) {
                            base.showToastOnUiThread(base.getResources()
                                    .getString(R.string.net_error));
                        }
                        // }
                    }
                }
            }
        };


        /**
         * 通过传jsonObj来上传数�?
         *
         * @throws IOException
         * @throws ClientProtocolException
         * @throws SAXException
         * @throws ParserConfigurationException
         */
        public String HttpPostData(HttpClient httpclient, String url,
                                   JSONObject obj) throws ClientProtocolException, IOException,
                ParserConfigurationException, SAXException {
            String rev = null;
            // try {
            HttpPost httppost = new HttpPost(url);
            // 添加http头信�?
            // httppost.addHeader("Authorization", token); //认证token
            httppost.addHeader("Content-Type", "application/json");
            httppost.addHeader("User-Agent", "Android");

            httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));
            HttpResponse response;
            response = httpclient.execute(httppost);
            // �?验状态码，如果成功接收数�?
            int code = response.getStatusLine().getStatusCode();
            if (code == 200) {
                rev = EntityUtils.toString(response.getEntity(), "UTF-8");// 返回json格式�?


            } else {
                if (callback != null) {
                    // if(!getLastResponse()){
                    callback.onError("network error", requestCode);
                    if (base != null) {
                        base.showToastOnUiThread(base.getResources().getString(
                                R.string.net_error)
                                + code);
                    }
                }
                return null;
            }

            parseData(rev);
            return rev;
        }

        private void parseData(String response) throws IOException,
                ParserConfigurationException, SAXException, NullPointerException, JsonSyntaxException, NumberFormatException {
            MXLog.out(response);
            Object obj = new Object();

            if (jsonOrxml.equals(JSON)) {

                Gson gson = new Gson();
                MXLog.out("==============" + requestCode);
                MXLog.out("url===" + task.getSubUrl());
                obj = gson.fromJson(response, task.getmClass());
                MsgResult res = (MsgResult) obj;
                if (res.getCode() != 1) {
                    if (base != null) {
                        base.showToastOnUiThread(res.getMessage());
                    }
                }

            } else if (jsonOrxml.equals(XML)) {

            }

            if (null != callback) {
                callback.onGetData(obj, requestCode, response);
                return;
            }
        }


    }
}
