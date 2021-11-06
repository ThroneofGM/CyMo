package com.notice_center.cymo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.net.URLEncoder;

public class viewPdf extends AppCompatActivity {
    WebView pdfView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pdf);
        pdfView=findViewById(R.id.pdfView);
        pdfView.getSettings().setJavaScriptEnabled(true);


        String filename=getIntent().getStringExtra("filename");
        String fileurl=getIntent().getStringExtra("fileurl");

        final ProgressDialog pd=new ProgressDialog(this);
        pd.setTitle(filename);
        pd.setMessage("Opening...");


        pdfView.setWebViewClient(new WebViewClient()
        {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                pd.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                pd.dismiss();
            }
        });

        String url="";
        try {
            url= URLEncoder.encode(fileurl,"UTF-8");
        }catch (Exception ex)
        {}

        pdfView.loadUrl("https://docs.google.com/gview?embedded=true&url=" + url);

    }


}
