package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.utils.Utils;

/**
 * 编辑自我介绍哦
 * Created by byw on 2016/12/14.
 */
public class EditIntroduceActivity extends BaseActivity {
    @Bind(R.id.et_introduce)
    EditText et_introduce;
    private String sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editintroduce, "个人介绍");
        getDoingView().setText("提交");
        sign = getIntent().getStringExtra("sign");
        initView();
    }

    @Override
    public void doing() {
        String str = et_introduce.getText().toString();
        if (Utils.isEmpty(str) || str.equals("-1")) {
            showToast("请填写个人介绍");
        } else {
            Intent data = new Intent();
            data.putExtra("sign", str);
            setResult(1003, data);
            finish();
        }
    }

    private void initView() {
        if (!Utils.isEmpty(sign)) {
            et_introduce.setText(sign);
        }
    }
}
