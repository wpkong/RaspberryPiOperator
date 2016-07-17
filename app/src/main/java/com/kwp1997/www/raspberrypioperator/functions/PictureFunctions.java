package com.kwp1997.www.raspberrypioperator.functions;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kwp1997.www.raspberrypioperator.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

public class PictureFunctions extends AppCompatActivity {
    Button button;
    ImageView imageView;
    TextView text;
    EditText info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_functions);

        button = (Button)findViewById(R.id.picture_submit);
        imageView = (ImageView)findViewById(R.id.picture_view);
        info = (EditText)findViewById(R.id.picture_info);
        text = (TextView)findViewById(R.id.picture_name) ;

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = info.getText().toString();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final String url = getUrl(name);

                        final Bitmap bitmap = returnBitMap("xxxxxx"+url);

                        runOnUiThread(new Runnable() {
                            public void run() {
                                if(!url.equals("pictures/iss/查无此人")){
                                    String name_text = url.substring(13,url.indexOf('.'));
                                    text.setText(name_text);
                                    imageView.setImageBitmap(bitmap);
                                    Toast.makeText(getApplicationContext(),name_text,Toast.LENGTH_LONG).show();
                                }else {
                                    Toast.makeText(getApplicationContext(),"查无此人",Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                    }
                }).start();


            }


        });


    }

    private String getUrl(String info){
            String url = "xxxxxx" + info;
            String result = "";
            BufferedReader in = null;
            try {
                String urlNameString = url;
                URL realUrl = new URL(urlNameString);
                // 打开和URL之间的连接
                URLConnection connection = realUrl.openConnection();
                // 设置通用的请求属性
                connection.setRequestProperty("accept", "*/*");
                connection.setRequestProperty("connection", "Keep-Alive");
                connection.setRequestProperty("user-agent",
                        "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
                // 建立实际的连接
                connection.connect();
                // 获取所有响应头字段
                Map<String, List<String>> map = connection.getHeaderFields();
                // 遍历所有的响应头字段
                for (String key : map.keySet()) {
                    System.out.println(key + "--->" + map.get(key));
                }
                // 定义 BufferedReader输入流来读取URL的响应
                in = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    result += line;
                }
            } catch (Exception e) {
                result = "发送GET请求出现异常！";
                e.printStackTrace();
            }
            // 使用finally块来关闭输入流
            finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            }
            return result;
        }

    public Bitmap returnBitMap(String url){
        URL FileUrl = null;
        Bitmap bitmap = null;
        try {
            FileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) FileUrl.openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;
    }

}



