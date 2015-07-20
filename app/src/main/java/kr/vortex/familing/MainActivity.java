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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import kr.vortex.familing.utils.Article;
import kr.vortex.familing.utils.BaseUser;
import kr.vortex.familing.utils.DrawerAdapter;
import kr.vortex.familing.utils.DrawerListData;
import kr.vortex.familing.utils.FamilingConnector;
import kr.vortex.familing.utils.FamilingService;
import kr.vortex.familing.utils.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.POST;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {

    ArrayList<DrawerListData> drawerListData;
    ViewPager mPager;
    ListView drawerListview;
    FloatingActionButton post1, post2, post3, post4;
    FloatingActionsMenu floatingActionsMenu;
    FamilingService service;
    List<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setDefault();
        setDrawerListview();
        setFloating();
    }

    public void setDefault() {
        service = FamilingConnector.getInstance();
        floatingActionsMenu = (FloatingActionsMenu)findViewById(R.id.float_menu);
        post1 = (FloatingActionButton) findViewById(R.id.post1);
        post2 = (FloatingActionButton) findViewById(R.id.post2);
        post3 = (FloatingActionButton) findViewById(R.id.post3);
        post4 = (FloatingActionButton) findViewById(R.id.post4);
        drawerListview = (ListView) findViewById(R.id.drawer_listview);
        mPager = (ViewPager) findViewById(R.id.ViewPager);
        service.groupListByUsers(new Callback<List<BaseUser>>() {
            @Override
            public void success(List<BaseUser> users, Response response) {
                mPager.setAdapter(new PagerAdapterClass(MainActivity.this, users));
            }

            @Override
            public void failure(RetrofitError error) {
                // Crash
                Log.e("Network", error.getMessage());
            }
        });
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
        private List<BaseUser> userList;

        public PagerAdapterClass(Context c, List<BaseUser> userList) {
            super();
            mInflater = LayoutInflater.from(c);
            this.userList = userList;
        }

        @Override
        public int getCount() {
            return userList.size();
        }

        @Override
        public Object instantiateItem(View pager, int position) {
            View v = null;
            // TODO: Set each page's view
            v = mInflater.inflate(R.layout.view_layout, null);

            BaseUser user = userList.get(position);
            TextView nameText = (TextView) v.findViewById(R.id.name);
            TextView descriptionText = (TextView) v.findViewById(R.id.description);
            LinearLayout linearLayout = (LinearLayout) v.findViewById(R.id.linearlayout);
            nameText.setText(user.name);
            descriptionText.setText(position + " 번째 페이지입니다");
            for(Article article : user.articles) {
                View result = getLayoutInflater().inflate(R.layout.cardview_main, null, false);
                TextView articleNameText = (TextView) result.findViewById(R.id.entry_name);
                TextView articleDateText = (TextView) result.findViewById(R.id.entry_date);
                ImageView articleIcon = (ImageView) result.findViewById(R.id.icon);
                articleNameText.setText(article.name);
                // date
                Date createDate = article.getCreatedAt();
                SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd hh:mm");
                if(createDate != null) {
                    articleDateText.setText(dt1.format(createDate));
                } else {
                    articleDateText.setText("");
                }
                // icon
                int[] types = new int[] {R.drawable.ic_howto, R.drawable.ic_normal, R.drawable.ic_please, R.drawable.ic_target};
                articleIcon.setImageDrawable(getDrawable(types[article.type]));
                linearLayout.addView(result);
            }

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