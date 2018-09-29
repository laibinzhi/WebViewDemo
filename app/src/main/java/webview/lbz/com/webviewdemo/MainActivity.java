package webview.lbz.com.webviewdemo;

import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements JSBridge {

    WebView webView;
    Button send;
    EditText et;
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        mHandler = new Handler();
        webView = findViewById(R.id.webview);
        send = findViewById(R.id.send);
        et = findViewById(R.id.et);

        //允许webview加载js代码
        webView.getSettings().setJavaScriptEnabled(true);
        webView.addJavascriptInterface(new LbzJsInterface(this), "lbzLauncher");
        webView.loadUrl("file:///android_asset/index.html");


        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str =et.getText().toString();
                String jsCode="if(window.remote){window.remote('"+str+"')}";
                webView.loadUrl("javascript:"+jsCode);
            }
        });

        //打开允许调试的开关
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webView.setWebContentsDebuggingEnabled(true);
        }
    }


    @Override
    public void setTextViewValue(final String value) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                et.setText(value);
            }
        });
    }
}
