package cn.studyjams.s1.sj124.majiwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class FinishActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
    }

    public void reStart(View v) {
        Intent intent = new Intent(FinishActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        finish();
    }
}
