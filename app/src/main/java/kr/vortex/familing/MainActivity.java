package kr.vortex.familing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.w3c.dom.Text;

import java.util.ArrayList;

import kr.vortex.familing.utils.DrawerAdapter;
import kr.vortex.familing.utils.DrawerListData;
import retrofit.http.POST;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ArrayList<DrawerListData> drawerListData;
    ViewPager mPager;
    ListView drawerListview;
    LinearLayout linearLayout;
    int group_member;
    String name[], description[];
    FloatingActionButton post1, post2, post3, post4;
    FloatingActionsMenu floatingActionsMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefault();
        setDrawerListview();
        setFloating();
    }

    public void setDefault() {
        floatingActionsMenu = (FloatingActionsMenu)findViewById(R.id.float_menu);
        post1 = (FloatingActionButton) findViewById(R.id.post1);
        post2 = (FloatingActionButton) findViewById(R.id.post2);
        post3 = (FloatingActionButton) findViewById(R.id.post3);
        post4 = (FloatingActionButton) findViewById(R.id.post4);
        drawerListview = (ListView) findViewById(R.id.drawer_listview);
        mPager = (ViewPager) findViewById(R.id.ViewPager);
        name = new String[]{"아버지", "어머니", "동생", "나"};
        group_member = 4;
        mPager.setAdapter(new PagerAdapterClass(this));
        mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public void setDrawerListview() {
        drawerListData = new ArrayList<>();
        drawerListData.add(new DrawerListData(getApplicationContext(), R.drawable.abc_btn_check_to_on_mtrl_000, "가족"));
        drawerListData.add(new DrawerListData(getApplicationContext(), R.drawable.abc_btn_check_to_on_mtrl_000, "마이페이지"));
        drawerListData.add(new DrawerListData(getApplicationContext(), R.drawable.abc_btn_check_to_on_mtrl_000, "환경설정"));
        DrawerAdapter drawerAdapter = new DrawerAdapter(getApplicationContext(), drawerListData);
        drawerListview.setAdapter(drawerAdapter);
        drawerListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 1:
                        startActivity(new Intent(getApplicationContext(), MyPageActivity.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                }
            }
        });
    }

    public void setPage(View view, int position) {
        TextView nameText = (TextView) view.findViewById(R.id.name);
        TextView descriptionText = (TextView) view.findViewById(R.id.description);
        linearLayout = (LinearLayout) view.findViewById(R.id.linearlayout);
        nameText.setText(name[position]);
        descriptionText.setText(position + " 번째 페이지입니다");
        addView();
    }

    public void setFloating() {
        post1.setIcon(R.drawable.ic_float_how);
        post1.setTitle("asdf");
        post1.setStrokeVisible(true);
        post1.setSize(FloatingActionButton.SIZE_MINI);
        post1.setColorNormal(Color.parseColor("#ff8f00"));
        post1.setColorPressed(Color.parseColor("#ffda6c"));
        post2.setIcon(R.drawable.ic_float_normal);
        post2.setSize(FloatingActionButton.SIZE_MINI);
        post2.setColorNormal(Color.parseColor("#ff8f00"));
        post2.setColorPressed(Color.parseColor("#ffda6c"));
        post3.setIcon(R.drawable.ic_float_please);
        post3.setSize(FloatingActionButton.SIZE_MINI);
        post3.setColorNormal(Color.parseColor("#ff8f00"));
        post3.setColorPressed(Color.parseColor("#ffda6c"));
        post4.setIcon(R.drawable.ic_float_target);
        post4.setSize(FloatingActionButton.SIZE_MINI);
        post4.setColorNormal(Color.parseColor("#ff8f00"));
        post4.setColorPressed(Color.parseColor("#ffda6c"));
        post1.setOnClickListener(this);
        post2.setOnClickListener(this);
        post3.setOnClickListener(this);
        post4.setOnClickListener(this);

    }

    public void addView() {
        for (int i = 0; i < 10; i++) {
            View result = getLayoutInflater().inflate(R.layout.cardview_main, null, false);
            linearLayout.addView(result);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.post1 : startActivity(new Intent(getApplicationContext(), PostActivity.class).putExtra("type", 0));
                break;
            case R.id.post2 : startActivity(new Intent(getApplicationContext(), PostActivity.class).putExtra("type", 1));
                break;
            case R.id.post3 : startActivity(new Intent(getApplicationContext(), PostActivity.class).putExtra("type", 2));
                break;
            case R.id.post4 : startActivity(new Intent(getApplicationContext(), PostActivity.class).putExtra("type", 3));
                break;
        }
    }

    public class PagerAdapterClass extends PagerAdapter {
        private LayoutInflater mInflater;

        public PagerAdapterClass(Context c) {
            super();
            mInflater = LayoutInflater.from(c);
        }

        @Override
        public int getCount() {
            return group_member;
        }

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            // TODO: Set each page's view
            v = mInflater.inflate(R.layout.view_layout, null);
            setPage(v, position);

            ((ViewPager) pager).addView(v, 0);
            return v;
        }

        @Override
        public void destroyItem(View pager, int position, Object view) {
            ((ViewPager) pager).removeView((View) view);
        }

        @Override
        public boolean isViewFromObject(View pager, Object obj) {
            return pager == obj;
        }

        @Override
        public void restoreState(Parcelable arg0, ClassLoader arg1) {
        }

        @Override
        public Parcelable saveState() {
            return null;
        }

        @Override
        public void startUpdate(View arg0) {
        }

        @Override
        public void finishUpdate(View arg0) {
        }
    }

    public void makeToast(String s, int len) {
        Toast.makeText(getApplicationContext(), s, (len == 0) ? Toast.LENGTH_SHORT : Toast.LENGTH_LONG).show();
    }
}