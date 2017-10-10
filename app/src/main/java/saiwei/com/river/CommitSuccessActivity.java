package saiwei.com.river;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Window;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * Created by saiwei on 9/23/17.
 */

public class CommitSuccessActivity extends Activity{

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            startActivity(new Intent(CommitSuccessActivity.this,MainActivity.class));
//            finish();
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_commit_success);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mHandler.sendEmptyMessageDelayed(1,2000);
    }

}
