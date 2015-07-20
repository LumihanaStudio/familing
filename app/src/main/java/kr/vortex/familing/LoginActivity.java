package kr.vortex.familing;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;

import org.w3c.dom.Text;

import kr.vortex.familing.utils.FamilingConnector;
import kr.vortex.familing.utils.FamilingService;
import kr.vortex.familing.utils.User;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener {

    EditText id, pw;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    FrameLayout login;
    TextView register;
    MaterialDialog  materialDialog;
    FamilingService service;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setService();
        setDefault();
        setLogin();
    }
    public void setService(){
        FamilingConnector.loadApiKey(this);
        service = FamilingConnector.getInstance();
    }
    public void setDefault(){
        materialDialog = new MaterialDialog.Builder(LoginActivity.this)
                .title("잠시만 기다려주세요")
                .content("로그인을 시도하는 중입니다.")
                .progress(true, 0)
                .show();
        id = (EditText)findViewById(R.id.login_id);
        pw = (EditText)findViewById(R.id.login_pw);
        login = (FrameLayout)findViewById(R.id.btn_login);
        register = (TextView)findViewById(R.id.register);
        login.setOnClickListener(this);
        register.setOnClickListener(this);
        sharedPreferences = getSharedPreferences("familing", 0);
        editor = sharedPreferences.edit();
    }
    public void setLogin(){
        service.userSelfInfo(new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                materialDialog.dismiss();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                finish();
            }

            @Override
            public void failure(RetrofitError error) {
                materialDialog.dismiss();
                Log.e("familing", "error"+error.getMessage());
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_login:
                service.login(id.getText().toString(), pw.getText().toString(), new Callback<User>() {
                    @Override
                    public void success(User user, Response response) {
                        FamilingConnector.saveApiKey(getApplicationContext(), user.token);
                        editor.putString("username", user.name);
                        editor.putString("photo", user.photo);
                        editor.putString("groupname", user.group.name);
                        editor.putString("id", id.getText().toString());
                        editor.commit();
                        Toast.makeText(getApplicationContext(), "정상적으로 로그인되었습니다", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("Fail at Login", error.getResponse().getStatus()+"");
                    }
                });
                break;
            case R.id.register:
                startActivity(new Intent(getApplicationContext(), RegisterDialogActivity.class));
                break;
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_login, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
