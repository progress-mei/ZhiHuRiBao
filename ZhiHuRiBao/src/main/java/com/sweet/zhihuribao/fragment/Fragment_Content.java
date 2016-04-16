package com.sweet.zhihuribao.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.sweet.zhihuribao.R;
import com.sweet.zhihuribao.activity.ChooseDateActivity;
import com.sweet.zhihuribao.activity.MainActivity;
import com.sweet.zhihuribao.adapter.MyVpAdapter;
import com.sweet.zhihuribao.base.BaseFragment;
import com.sweet.zhihuribao.base.BasePager;
import com.sweet.zhihuribao.pager.Pager1;
import com.sweet.zhihuribao.pager.Pager2;
import com.sweet.zhihuribao.pager.Pager3;
import com.sweet.zhihuribao.pager.Pager4;
import com.sweet.zhihuribao.pager.Pager5;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Sweet on 2016/4/6/0006.
 */
public class Fragment_Content extends BaseFragment implements NavigationView.OnNavigationItemSelectedListener {

    private ViewPager mViewPager;
    private View view;
    private ArrayList<BasePager> pagers;
    private List<String> mTitleList;
    private String titleDate;
    private TabLayout mTabLayout;
    private MyVpAdapter myAdapter;

    @Override
    public View initView() {
        view = View.inflate(getActivity(), R.layout.fragment_content, null);
        mViewPager = (ViewPager) view.findViewById(R.id.vp_content);
        mTabLayout = (TabLayout) view.findViewById(R.id.tabLayout);


        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("知乎日报·");
        toolbar.setTitleTextColor(Color.WHITE);

        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChooseDateActivity.class);
                startActivity(intent);
;
            }
        });

        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                getActivity(), drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) view.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        return view;
    }

    @Override
    public void initData() {
        pagers = new ArrayList<>();
        pagers.add(new Pager1(getContext()));
        pagers.add(new Pager2(getContext()));
        pagers.add(new Pager3(getContext()));
        pagers.add(new Pager4(getContext()));
        pagers.add(new Pager5(getContext()));

        //页卡标题集合
        mTitleList = new ArrayList<>();
        mTitleList.add("今日热文");
        mTitleList.add(initDate(-1));
        mTitleList.add(initDate(-2));
        mTitleList.add(initDate(-3));
        mTitleList.add(initDate(-4));

        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);//设置tab模式，当前为滑动模式，标签完整显示
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(0)));//添加tab选项卡
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(1)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(2)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(3)));
        mTabLayout.addTab(mTabLayout.newTab().setText(mTitleList.get(4)));

        myAdapter = new MyVpAdapter(pagers, mTitleList);
        mViewPager.setAdapter(myAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabsFromPagerAdapter(myAdapter);//给Tabs设置适配器
        pagers.get(0).initData();
    }

    @Override
    public void initEvent() {
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                pagers.get(position).initData();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    /**
     * 获取当前时间
     */
    public String initDate(int i) {
        //获取当前时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, i);
        SimpleDateFormat title = new SimpleDateFormat("yyyy年MM月dd日");
        titleDate = title.format(calendar.getTime());
        return titleDate;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getActivity().getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}


