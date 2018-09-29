package webview.lbz.com.webviewdemo;

import android.util.Log;
import android.webkit.JavascriptInterface;

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
