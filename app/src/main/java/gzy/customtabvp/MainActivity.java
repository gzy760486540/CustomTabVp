package gzy.customtabvp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gzy.customtabviewpager.common.AnimationType;

public class MainActivity extends AppCompatActivity {
    private gzy.customtabviewpager.view.TabViewPagerLayout tabViewPagerLayout;
    private List<String> stringList;
    private List<View> viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabViewPagerLayout = findViewById(R.id.tabViewPagerLayout);

        initData();
        initTab();
    }

    private void initData() {
        stringList = new ArrayList<>();
        viewList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            stringList.add(String.valueOf(i));
            TextView textView = new TextView(this);
            textView.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            textView.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            textView.setText(String.valueOf(i));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(Color.parseColor("#FFFFFF"));
            viewList.add(textView);
        }
    }

    private void initTab() {
        tabViewPagerLayout.setTitleList(stringList)
                .setViewList(viewList)
                .isNeedAnimation(true, AnimationType.DEPTH)
                .build();
    }
}
