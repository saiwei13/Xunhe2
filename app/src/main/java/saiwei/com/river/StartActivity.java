package saiwei.com.river;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import saiwei.com.river.logic.AccoutLogic;

/**
 * Created by saiwei on 9/23/17.
 */

public class StartActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(AccoutLogic.getInstance().isLogin()   ){
            startActivity(new Intent(StartActivity.this,MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(StartActivity.this,LoginActivity.class));
            finish();
        }
    }
}
