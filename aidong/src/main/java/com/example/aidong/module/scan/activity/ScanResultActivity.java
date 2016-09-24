package com.example.aidong.module.scan.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.aidong.ui.BaseActivity;
import com.example.aidong.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.scan_result_layout)
public class ScanResultActivity extends BaseActivity {

    @ViewInject(R.id.scan_result_tv)
    TextView scan_result_tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupView();
        initData();
    }

    protected void setupView() {
        // TODO Auto-generated method stub
        ViewUtils.inject(this);
        //		initTop(res.getString(R.string.scan_result), true, true, false, res.getString(R.string.scan_tit));
        //		top_tit_img.setImageResource(R.drawable.more_selector);
        //		top_tit_img.setVisibility(ImageView.VISIBLE);


    }

    protected void initData() {
        // TODO Auto-generated method stub

        final String result = getIntent().getStringExtra("result");
        //			new Thread(new Runnable() {
        //
        //				@Override
        //				public void run() {
        //					// TODO Auto-generated method stub
        //					try {
        //						HttpClient client = new DefaultHttpClient();
        //						HttpGet request = new HttpGet();
        //						request.setURI(new URI(result));
        //						HttpResponse response = client.execute(request);
        //						System.out.println(""+response.getFirstHeader("Content-Type"));
        //						System.out.println(""+response.getStatusLine().getStatusCode());
        //					} catch (ClientProtocolException e) {
        //						// TODO Auto-generated catch block
        //						e.printStackTrace();
        //					} catch (URISyntaxException e) {
        //						// TODO Auto-generated catch block
        //						e.printStackTrace();
        //					} catch (IOException e) {
        //						// TODO Auto-generated catch block
        //						e.printStackTrace();
        //					}
        //				}
        //			}).start();;

        scan_result_tv.setText(result);

        scan_result_tv.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                //						CustomDownloadDialog dialog=new CustomDownloadDialog(ScanResultActivity.this,R.style.DownloadDialog);
                //						dialog.show();
            }
        });
    }


}
