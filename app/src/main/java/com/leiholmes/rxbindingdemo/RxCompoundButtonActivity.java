package com.leiholmes.rxbindingdemo;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxCompoundButton;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.functions.Consumer;

/**
 * Description:   RxBinding2中RxCompoundButton演示Activity
 * author         xulei
 * Date           2017/10/25 17:46
 */
public class RxCompoundButtonActivity extends BaseActivity {
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.cb_contract)
    CheckBox cbContract;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_rx_compound_button;
    }

    @Override
    protected void onViewCreated(Bundle savedInstanceState) {
        checkedChanges();
    }

    /**
     * CheckBox选中状态改变事件
     * 实际项目中会遇到需要用户确认协议后才可登录的需求
     */
    private void checkedChanges() {
        //默认不可点击
        btnLogin.setEnabled(false);
        btnLogin.setClickable(false);
        RxCompoundButton.checkedChanges(cbContract)
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {
                        btnLogin.setEnabled(aBoolean);
                        btnLogin.setClickable(aBoolean);
                        btnLogin.setBackgroundResource(aBoolean ? R.color.colorPrimary : R.color.colorGray);
                        btnLogin.setTextColor(aBoolean ? Color.parseColor("#ffffff") : Color.parseColor("#000000"));
                    }
                });
        RxView.clicks(btnLogin)
                .throttleFirst(2, TimeUnit.SECONDS)
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
                        Toast.makeText(RxCompoundButtonActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
