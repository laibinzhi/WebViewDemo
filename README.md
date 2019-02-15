# WebView���õ���Java����
1. ����WebView����js

```
webView.getSettings().setJavaScriptEnabled(true);

```
2. ��дjs�ӿ���

3. ��webview���js�ӿ�

```
webView.addJavascriptInterface(obj,interfaceName);
```


# Android ����js����
ʹ��loadUrl��������javascript

```
//jsString��Ҫ���õ�js������ַ���
webView.loadUrl(javascript:jsString);
```

# ʵ��
1. �½�һ������*WebViewDemo*
2. *layout_main.xml*�������¡���Ϊ�����������֣��ϲ���ΪWebView���²���ΪNative�������־���һ�������Ͱ�ť���������ֵ����ť���ͻ��ڶ�����������ʾ��
   
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical">

    <WebView
        android:id="@+id/webview"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1" />

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:orientation="vertical"
        android:layout_weight="1">

        <TextView
            android:textColor="@android:color/black"
            android:layout_marginLeft="10dp"
            android:layout_marginTop="20dp"
            android:textStyle="bold"
            android:textSize="30dp"
            android:text="Native"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />


        <EditText
            android:id="@+id/et"
            android:layout_marginTop="20dp"
            android:hint="����������"
            android:layout_width="match_parent"
            android:layout_height="100px" />

        <Button
            android:id="@+id/send"
            android:text="���͵�WebView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </LinearLayout>
</LinearLayout>

```
3. MainActivityʵ�����ؼ�����������WebView����js
4. ��дjs�ӿ���*LbzJsInterface*

```
public class LbzJsInterface {

    public static final String TAG = "LbzJsInterface";

    private JSBridge jsBridge;

    public LbzJsInterface(JSBridge jsBridge){
        this.jsBridge =jsBridge;
    }

    //��������������߳���
    @JavascriptInterface
    public void setValue(String value) {
        jsBridge.setTextViewValue(value);
        Log.e(TAG, "value=" + value);
    }

}

```


```
public interface JSBridge {

    void setTextViewValue(String value);

}
```
5. ��webview���js�ӿ�

```
        webView.addJavascriptInterface(new LbzJsInterface(this), "lbzLauncher");

```
6. MainActivityʵ��*JSBridge*

```
    @Override
    public void setTextViewValue(final String value) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                et.setText(value);
            }
        });
    }
```


7. WebViewҪ����*index.html*

```
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>WebView</title>
    <style type="text/css">
        body{
        background: #7F7F7F;
        }

        .btn{
        line-height: 50px;
        margin: 20px;
        background: #cccccc;
        }


    </style>
</head>

<body>
<h2>WebView</h2>

<div><input type="text" id="input" placeholder="����������"></div>
<br/>
<div id="btn"><button >���͵�Native</button ></div>
<script type="text/javascript">
    var btnEle = document.getElementById("btn");
    var inputEle = document.getElementById("input");

    btnEle.addEventListener("click",function(){
      var str = inputEle.value;
      if (window.lbzLauncher) {
        lbzLauncher.setValue(str);
      }else {
        alert(str);
      }
    });

    var remote = function(str){
      inputEle.value = str
    }
</script>


</body>

</html>

```

8. ����html

```
 webView.loadUrl("file:///android_asset/index.html");
```

9. ���Native�ļ����¼����ڼ�����Android ����js����

```
 send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str =et.getText().toString();
                String jsCode="if(window.remote){window.remote('"+str+"')}";
                webView.loadUrl("javascript:"+jsCode);
            }
        });
```

10. Demo������״̬

![image](http://lbz-blog.test.upcdn.net/post/webview_gif.gif)


11. [�������Ŀ��ַ](https://github.com/laibinzhi/WebViewDemo)

