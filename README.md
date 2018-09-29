# WebView调用调用Java方法
1. 允许WebView加载js

```
        webView.getSettings().setJavaScriptEnabled(true);

```
2. 编写js接口类

3. 给webview添加js接口

```
webView.addJavascriptInterface(obj,interfaceName);
```


# Android 调用js方法
使用loadUrl方法调用javascript

```
//jsString是要调用的js代码的字符串
webView.loadUrl(javascript:jsString);
```

# 实例
1. 新建一个工程*WebViewDemo*
2. *layout_main.xml*代码如下。分为上下两个部分，上部分为WebView，下部分为Native。两部分均有一个输入框和按钮，输入文字点击按钮，就会在对面的输入框显示。
   
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
            android:hint="请输入内容"
            android:layout_width="match_parent"
            android:layout_height="100px" />

        <Button
            android:id="@+id/send"
            android:text="发送到WebView"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content" />

    </LinearLayout>
</LinearLayout>

```
3. MainActivity实例化控件，并且允许WebView加载js
4. 编写js接口类*LbzJsInterface*

```
public class LbzJsInterface {

    public static final String TAG = "LbzJsInterface";

    private JSBridge jsBridge;

    public LbzJsInterface(JSBridge jsBridge){
        this.jsBridge =jsBridge;
    }

    //这个方法不在主线程中
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
5. 给webview添加js接口

```
        webView.addJavascriptInterface(new LbzJsInterface(this), "lbzLauncher");

```
6. MainActivity实现*JSBridge*

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


7. WebView要加载*index.html*

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

<div><input type="text" id="input" placeholder="请输入内容"></div>
<br/>
<div id="btn"><button >发送到Native</button ></div>
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

8. 加载html

```
 webView.loadUrl("file:///android_asset/index.html");
```

9. 添加Native的监听事件，在监听中Android 调用js方法

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

10. Demo的运行状态

![image](http://pd4brty72.bkt.clouddn.com/webview_gif.gif)


11. [最后附上项目地址](https://github.com/laibinzhi/WebViewDemo)

