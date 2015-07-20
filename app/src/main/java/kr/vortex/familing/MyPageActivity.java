package kr.vortex.familing;

import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MyPageActivity extends ActionBarActivity {

    TextView id, group, name, work;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page);
        setDefault();
    }

    public void setDefault() {
        sharedPreferences = getSharedPreferences("familing", 0);
        id = (TextView) findViewById(R.id.mypage_id);
        group = (TextView) findViewById(R.id.mypage_group);
        name  = (TextView)findViewById(R.id.mypage_name);
        id.setText("ID : "+sharedPreferences.getString("id", ""));
        group.setText("Group :"+sharedPreferences.getString("groupname", ""));
        name.setText("Name : "+sharedPreferences.getString("username", ""));

    }
}
