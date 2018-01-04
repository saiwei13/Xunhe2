package saiwei.com.river;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import saiwei.com.river.entity.LoginBean;
import saiwei.com.river.logic.AccoutLogic;
import saiwei.com.river.model.User;
import saiwei.com.river.util.SharePreferenceUtil;


/**
 * 登录界面
 */
public class LoginActivity extends Activity implements View.OnClickListener{

    private final String TAG = "chenwei.LoginActivity";

    private ImageButton mTitleLeft;
    private TextView mTitleName;

    private EditText mEditAccount; // 帐号
    private EditText mEditPassword; // 密码
    private TextView mBtForgetPassword; // 忘记密码
    private Button mBtLogin; // 登录
    private TextView mBtAddAccount; // 注册新账户
    private CheckBox isShowPwd; // 显示密码

    String username = "";
    String password = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);// 不显示程序的标题栏
        setContentView(R.layout.activity_login);
        initView();
        initData();
    }

    private void initView() {

        mTitleLeft = (ImageButton) findViewById(R.id.title_btn_left);
        mTitleLeft.setOnClickListener(this);

        mTitleName = (TextView) findViewById(R.id.title_text_name);
        mTitleName.setText("龙岩河长制");

        findViewById(R.id.title_btn_right).setVisibility(View.GONE);

        mEditAccount = (EditText) this.findViewById(R.id.gdaccout_edit_account);
        mEditPassword = (EditText) this.findViewById(R.id.gdaccout_edit_password);
        mBtForgetPassword = (TextView) this.findViewById(R.id.gdaccout_bt_forget_password);
//        mBtForgetPassword.setTextColor(Color.BLUE);
        mBtForgetPassword.setOnClickListener(this);
        mBtLogin = (Button) this.findViewById(R.id.gdaccout_bt_login);
        mBtLogin.setOnClickListener(this);
        mBtAddAccount = (TextView) this.findViewById(R.id.gdaccout_bt_add_accout);
        mBtAddAccount.setOnClickListener(this);
        isShowPwd = (CheckBox) this.findViewById(R.id.gdaccount_checkbox_show_pwd);

        isShowPwd.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mEditPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                } else {
                    mEditPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
                mEditPassword.setSelection(mEditPassword.getText().length());
            }
        });
    }

    private void initData(){


        username = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_USERNAME
        );
        password = SharePreferenceUtil.getInstance().getStr(
                SharePreferenceUtil.SHARE_PREFERENCE_PASSWORD
        );

        if(!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)){
            mEditAccount.setText(username);
            mEditPassword.setText(password);
        }

    }

    @Override
    public void onClick(View view) {
        if(mTitleLeft == view){
            finish();
        }
//        else if(view.getId() == R.id.setting_about_us){
////            startActivity(new Intent(SettingActivity.this, AboutUsActivity.class));
//        }
        else if(mBtLogin == view){
            doLogin();
        }
//        else if(view.getId() == R.id.gdaccout_bt_forget_password){
//            startActivity(new Intent(mContext, ResetPwdActivity.class));
//        } else if(view.getId() == R.id.gdaccout_bt_add_accout){
//                startActivity(new Intent(this, RegisterActivity.class));
//        }
    }

    private SharedPreferences sp;


    /**
     * 操作：登录
     */
    private void doLogin(){

        username = mEditAccount.getText().toString();
        password = mEditPassword.getText().toString();

//        username = "test_lytest001";
//        password = "000000";

//        username = "13720823605";
//        password = "lf123456";

        if(TextUtils.isEmpty(username) || TextUtils.isEmpty(password)){
            Toast.makeText(this,"请输入用户名 和密码",Toast.LENGTH_SHORT).show();
            return;
        }

        showMyDialog(DIALOG_LOGIN);


//        username = "test_lytest001";
//        password = "000000";

        AccoutLogic.getInstance().login(username,password,new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {


//                Log.d(TAG,"onResponse()  "+response.body());
                LoginBean bean = response.body();

                if(bean.getRtnCode().equals("000000")){
                    User user = new User();
//        user.setUserId(1);
                    Log.d(TAG,"onResponse()  "+bean.getRtnMsg()+" , "+bean.getResponseData().getId()+" , "+bean.getResponseData().getLoginIp());

                    Toast.makeText(LoginActivity.this, "登陆成功", Toast.LENGTH_SHORT).show();

                    user.setUserid(bean.getResponseData().getId());
                    user.setName(bean.getResponseData().getName());
                    AccoutLogic.getInstance().insertDBUser(user);

                    AccoutLogic.getInstance().reqDefaultRiverInfo();

                    SharePreferenceUtil.getInstance().putStr(
                            SharePreferenceUtil.SHARE_PREFERENCE_USERNAME,
                            username
                    );
                    SharePreferenceUtil.getInstance().putStr(
                            SharePreferenceUtil.SHARE_PREFERENCE_PASSWORD,
                            password
                    );

                    LoginBean.ResponseDataBean.HzOrgCodeBean  hzOrgCode = bean.getResponseData().getHzOrgCode();
                    if(hzOrgCode!=null){
                        String town = hzOrgCode.getTown();
                        String towncode = hzOrgCode.getTowncode();
                        String countycode  = hzOrgCode.getCountycode();
                        String phone = bean.getResponseData().getMobile();


                        SharePreferenceUtil.getInstance().putStr(
                                SharePreferenceUtil.SHARE_PREFERENCE_TOWN,
                                town
                        );

                        SharePreferenceUtil.getInstance().putStr(
                                SharePreferenceUtil.SHARE_PREFERENCE_TOWNCODE,
                                towncode
                        );

                        SharePreferenceUtil.getInstance().putStr(
                                SharePreferenceUtil.SHARE_PREFERENCE_COUNTYCODE,
                                countycode
                        );

                        SharePreferenceUtil.getInstance().putStr(
                                SharePreferenceUtil.SHARE_PREFERENCE_MOBIEL,
                                phone
                        );
                    }

                    startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    finish();
                } else{

                    Toast.makeText(LoginActivity.this,"登录失败 "+bean.getRtnMsg(),Toast.LENGTH_SHORT).show();
                }
                dismissMyDialog();
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Log.d(TAG,"onFailure()  "+t.toString());

//                Toast.makeText(LoginActivity.this,"登录失败 "+t.toString(),Toast.LENGTH_SHORT).show();
                Toast.makeText(LoginActivity.this,"登录失败 ",Toast.LENGTH_SHORT).show();

                dismissMyDialog();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private final int DIALOG_LOGIN = 1;
    private ProgressDialog pd;

    /**
     * 显示dialog
     */
    private void showMyDialog(int id) {

        if(pd == null){
            pd = new ProgressDialog(this);
        }

        switch (id) {
            case DIALOG_LOGIN:
                pd.setMessage(getString(R.string.gdaccount_dialog_logining));
                break;
            default:
                break;
        }
        pd.show();
    }

    /**
     * 关闭dialog
     */
    private void dismissMyDialog() {
        if ((pd != null) && pd.isShowing()) {
            pd.dismiss();
        }
    }
}
