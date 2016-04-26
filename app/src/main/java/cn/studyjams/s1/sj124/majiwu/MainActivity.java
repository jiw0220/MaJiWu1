package cn.studyjams.s1.sj124.majiwu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zql.android.cardmenulib.CardMenu;
import com.zql.android.cardmenulib.CardMenuItem;

import java.util.ArrayList;

public class MainActivity extends Activity implements CardMenu.OnCardMenuSelecetedListener, View.OnClickListener {

    private LinearLayout mMainContent;
    private LayoutInflater inflater;
    int[] names = new int[]{R.string.title_a, R.string.title_b, R.string.title_c, R.string.title_d, R.string.title_e};
    int[] icons = new int[]{R.drawable.icon_a, R.drawable.icon_b, R.drawable.icon_c, R.drawable.icon_d, R.drawable.icon_e};
    int[] colors = new int[]{R.color.color_a, R.color.color_b, R.color.color_c, R.color.color_d, R.color.color_e};
    int[] progressIds = new int[]{R.id.progress_a, R.id.progress_b, R.id.progress_c, R.id.progress_d, R.id.progress_e};
    int[] viewIds = new int[]{R.layout.content_step_a, R.layout.content_step_b, R.layout.content_step_c, R.layout.content_step_d, R.layout.content_step_e};
    ArrayList<View> views = null;
    ArrayList<View> progressBtns = null;
    int state = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inflater = LayoutInflater.from(this);
        mMainContent = (LinearLayout) findViewById(R.id.main_content);
        CardMenu menu = (CardMenu) findViewById(R.id.cardMenu);
        menu.initMenus(names, icons, colors);
        menu.setOnCardMenuSelectedListener(this);
        initViews();
        initProgressBtns();
        mMainContent.addView(views.get(0));
        Snackbar snackbar = Snackbar.make(mMainContent, "Tips: 点击标题栏即可切换内容", Snackbar.LENGTH_INDEFINITE).setAction("我知道了", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        snackbar.show();
    }

    private void initViews() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int height = (int) (dm.widthPixels / 1.7);
        views = new ArrayList<>();
        ImageView img = null;
        ViewGroup.LayoutParams lp = null;
        for (int i = 0; i < viewIds.length; i++) {
            View view = inflater.inflate(viewIds[i], null);
            view.findViewById(R.id.btn_try).setOnClickListener(this);
            img = (ImageView) view.findViewById(R.id.image);
            lp = img.getLayoutParams();
            lp.height = height;
            views.add(view);
        }
    }

    private void initProgressBtns() {
        progressBtns = new ArrayList<>();
        for (int i = 0; i < progressIds.length; i++) {
            progressBtns.add(findViewById(progressIds[i]));
        }
    }

    private void runAnimate(View v) {
        v.setVisibility(View.VISIBLE);
        Animation fadeOut = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade_out);
        v.setAnimation(fadeOut);
        fadeOut.startNow();
//        AlphaAnimation alphaAnimation = new AlphaAnimation(1,0);
//        alphaAnimation.setDuration(200);
//        alphaAnimation.setFillAfter(true);
//        v.setAnimation(alphaAnimation);
//        alphaAnimation.startNow();
    }

    private void goEnd() {
        Intent intent = new Intent(MainActivity.this, FinishActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
        finish();
    }

    @Override
    public void cardMenuSelected(CardMenuItem item) {
        mMainContent.removeAllViews();
        mMainContent.addView(views.get(item.getIndex()));
    }

    @Override
    public void onClick(View v) {
        int index = Integer.parseInt((String) v.getTag());
        if (index - state != 1) {
            String name = getResources().getString(names[state + 1]);
            Toast.makeText(this, "请先完成 " + name, Toast.LENGTH_LONG).show();
        } else {
            state = index;
            runAnimate(progressBtns.get(index));
        }
        if (state == names.length - 1) {
            goEnd();
        }
    }
}

