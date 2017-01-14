package sinia.com.entertainer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import butterknife.Bind;
import sinia.com.entertainer.R;
import sinia.com.entertainer.base.BaseActivity;
import sinia.com.entertainer.utils.Utils;

/**
 * 编辑名字
 * Created by byw on 2016/12/14.
 */
public class EditNameActivity extends BaseActivity {
    private String name;

    @Bind(R.id.et_name)
    EditText et_name;

    @Bind(R.id.img_xxx)
    ImageView img_xxx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editname, "艺名");
        getDoingView().setText("保存");
        initView();
    }

    @Override
    public void doing() {
        String newname = et_name.getText().toString();
        if (Utils.isEmpty(newname)) {
            showToast("请输入名字");
        } else {
            Intent data = new Intent();
            data.putExtra("nn", newname);
            setResult(1002, data);
            finish();
        }
    }

    private void initView() {
        name = getIntent().getStringExtra("name");
        if (!Utils.isEmpty(name)) {
            et_name.setText(name);
        }
        img_xxx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                et_name.setText("");
            }
        });
    }
}
