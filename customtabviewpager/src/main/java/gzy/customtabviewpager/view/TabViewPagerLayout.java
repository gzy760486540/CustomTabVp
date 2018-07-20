package gzy.customtabviewpager.view;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import gzy.customtabviewpager.common.AnimationType;
import gzy.customtabviewpager.transformer.DepthPagerTransformer;
import gzy.customtabviewpager.adapter.TabViewPAdapter;
import gzy.customtabviewpager.transformer.ZoomOutPageTransformer;

/**
 * Created by GZY on 18/7/20.
 */

public class TabViewPagerLayout extends LinearLayout {
    private ViewPager viewPager;
    private TabLayout tabLayout;

    private List<String> titleList;
    private List<View> viewList;

    private ViewPager.PageTransformer transformer;

    private int initPosition = 0;//默认选中位置
    private int animationType = AnimationType.ZOOM_OUT;//动画类型 默认ZOOM_OUT
    private boolean isNeedAnimation = false;

    public TabViewPagerLayout(Context context) {
        super(context);
        init(context);
    }

    public TabViewPagerLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TabViewPagerLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //初始化
    private void init(Context context) {
        this.setOrientation(LinearLayout.VERTICAL);
        titleList = new ArrayList<>();
        viewList = new ArrayList<>();
        viewPager = new ViewPager(context);
        tabLayout = new TabLayout(context);
    }

    public TabViewPagerLayout setTitleList(List<String> titleList) {
        this.titleList = titleList;
        return this;
    }

    public TabViewPagerLayout setViewList(List<View> viewList) {
        this.viewList = viewList;
        return this;
    }

    public TabViewPagerLayout isNeedAnimation(boolean needAnimation, int animationType) {
        this.isNeedAnimation = needAnimation;
        this.animationType = animationType;
        return this;
    }

    /**
     * @return 创建TabViewPagerLayout
     */
    public void build() {
        initViewP();
        initTabLayout();
        addViews();
    }

    /**
     * 初始化TabLayout
     */
    private void initTabLayout() {
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.removeAllTabs();

        //设置高度
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, 200);
        tabLayout.setLayoutParams(params);

        //设置tab的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        //tab的字体选择器,默认黑色,选择时红色
        tabLayout.setTabTextColors(Color.BLACK, Color.RED);

        //tab的下划线颜色,默认是粉红色,如果要自定义选中效果,则可以将下划线设置为和背景色一样.
        tabLayout.setSelectedTabIndicatorColor(Color.RED);

        for (int i = 0; i < titleList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(titleList.get(i)));
        }

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //将默认位置选中为false
                isSelected(tabLayout.getTabAt(initPosition), false);
                //选中当前位置
                isSelected(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                //tab未选中
                isSelected(tab, false);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                //tab重新选中
                isSelected(tab, true);
            }
        });

        //进来默认选中位置第0个item
        isSelected(tabLayout.getTabAt(initPosition), true);
    }

    /**
     * 设置选中的tab是否带缩放效果
     *
     * @param tab
     * @param isSelected
     */
    private void isSelected(TabLayout.Tab tab, boolean isSelected) {
        View view = tab.getCustomView();
        if (null != view) {
            view.setScaleX(isSelected ? 1.3f : 1.0f);
            view.setScaleY(isSelected ? 1.3f : 1.0f);
        }
    }

    /**
     * 初始化ViewPager
     */
    private void initViewP() {
        TabViewPAdapter tabViewPAdapter = new TabViewPAdapter<>(viewList);
        viewPager.setAdapter(tabViewPAdapter);
        if (isNeedAnimation) {
            //添加动画
            switch (animationType) {
                case AnimationType.ZOOM_OUT:
                    transformer = new ZoomOutPageTransformer();
                    break;
                case AnimationType.DEPTH:
                    transformer = new DepthPagerTransformer();
                    break;
            }
            viewPager.setPageTransformer(false, transformer);
        }
    }

    /**
     * 将tablayout和viewpager添加到当前view中
     */
    private void addViews() {
        this.addView(tabLayout);
        this.addView(viewPager);
    }
}
