package com.example.filezip2;
import com.example.filezip2.ZipUtil.ZipListener;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import net.lingala.zip4j.util.Zip4jUtil;

import java.io.File;
import java.io.IOException;
import java.util.zip.ZipEntry;

public class MainActivity extends Activity {
    private ProgressBar progressBar1;
    private  ZipListener listener;
    public static Context mContext;
    private TextView textView;
    @SuppressLint("HandlerLeak")
    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case CompressStatus.START:
                    textView.setText("start!");
                    progressBar1.setVisibility(View.VISIBLE);
                    break;
                case CompressStatus.HANDLING:
                    Bundle b = msg.getData();
                    int percent = b.getInt(CompressStatus.PERCENT);
                    textView.setText(percent+ "%");
                    progressBar1.setProgress(percent);
//                    progressBar1.setProgress(Integer.parseInt(CompressStatus.PERCENT));
//                    progressBar1.setMax(100);
                    break;
                case CompressStatus.COMPLETED:
                    textView.setText("end!");
                    progressBar1.setVisibility(View.INVISIBLE);
                    break;
                case CompressStatus.ERROR:
                    Bundle bundle =msg.getData();
                    int error = bundle.getInt(String.valueOf(CompressStatus.ERROR));
                    textView.setText(error);
                    break;
            }
        };
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=findViewById(R.id.sb);
        progressBar1=findViewById(R.id.probar);
        Thread th2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
//                     Zip4Util.zip("/vr/1.jpeg","/vr/ASR/a.zip","ljj666");
                    Zip4Util.unZip("/vr/ASR/a.zip", "/vr/ASR", "ljj666", handler);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        th2.start();
    }



}