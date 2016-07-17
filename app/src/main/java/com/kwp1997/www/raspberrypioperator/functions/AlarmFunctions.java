package com.kwp1997.www.raspberrypioperator.functions;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kwp1997.www.raspberrypioperator.R;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

public class AlarmFunctions extends AppCompatActivity {

    TextView alarm_time;
    Button alarm_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_functions);

        alarm_time = (TextView) findViewById(R.id.alarm_time);
        alarm_button = (Button) findViewById(R.id.alarm_button);


        alarm_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                final String time_long = alarm_time.getText().toString();
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        final String result = alarm(time_long);
                        runOnUiThread(new Runnable() {
                            public void run() {
                                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                }).start();
            }

            private String alarm(String time) {
                String url = "xxxxxxxxxxxxxxx" + time;
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


        });
    }
}
