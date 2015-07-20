package kr.vortex.familing;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.rey.material.widget.Button;

import kr.vortex.familing.utils.FamilingConnector;
import kr.vortex.familing.utils.FamilingService;
import kr.vortex.familing.utils.User;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * Created by kotohana5706 on 15. 7. 12.
 */
public class RegisterDialogActivity extends Activity {

    String name, id, password;
    Button cancel, confirm;
    FamilingService service;
    View.OnClickListener dialogConfirm;
    String apikey;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setWindow();
        setContentView(R.layout.activity_register_dialog);
        setService();
        setDefault();
    }

    public void setService() {
        service = FamilingConnector.getInstance();
    }

    public void setDefault() {
        cancel = (Button) findViewById(R.id.cancel_button);
        confirm = (Button) findViewById(R.id.confirm_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText register_name = (EditText) findViewById(R.id.register_name);
                EditText register_id = (EditText) findViewById(R.id.register_id);
                EditText register_pw = (EditText) findViewById(R.id.register_pw);
                name = register_name.getText().toString();
                id = register_id.getText().toString();
                password = register_pw.getText().toString();
                if (name.trim().equals("")) register_name.setError("공백 없이 입력해주세요");
                else if (id.trim().equals("")) register_id.setError("공백 없이 입력해주세요");
                else if (password.trim().equals("")) register_pw.setError("공백 없이 입력해주세요");
                else {
                    MaterialDialog materialDialog = new MaterialDialog.Builder(RegisterDialogActivity.this)
                            .title("가입하시겠습니까?")
                            .positiveText("확인")
                            .negativeText("취소")
                            .callback(new MaterialDialog.ButtonCallback() {
                                @Override
                                public void onPositive(MaterialDialog dialog) {
                                    super.onPositive(dialog);
                                    service.register(id, password, name, new Callback<User>() {
                                        @Override
                                        public void success(User user, Response response) {
                                            Toast.makeText(getApplicationContext(), "정상적으로 가입되었습니다", Toast.LENGTH_SHORT).show();
                                            FamilingConnector.saveApiKey(getApplicationContext(), user.token);
                                            startActivity(new Intent(RegisterDialogActivity.this, MainActivity.class));
                                            finish();
                                        }

                                        @Override
                                        public void failure(RetrofitError error) {
                                            Log.e("Fail in RegisterDialog", error.getResponse().getStatus()+"");
                                    }
                                });
                                }

                            })
                            .show();
                }
            }
        });
    }

    public void setWindow() {
        WindowManager.LayoutParams lpWindow = new WindowManager.LayoutParams();
        lpWindow.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        lpWindow.dimAmount = 0.8f;
        getWindow().setAttributes(lpWindow);
    }
}