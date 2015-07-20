package kr.vortex.familing;

import android.app.ActionBar;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.io.File;

import kr.vortex.familing.utils.FamilingConnector;
import kr.vortex.familing.utils.FamilingService;
import kr.vortex.familing.utils.Group;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;


public class PostActivity extends ActionBarActivity {

    int posttype;
    FloatingActionButton floatingActionButton;
    ImageView toGallery;//이미지뷰 선언
    FamilingService service;
    private static int RESULT_LOAD_IMAGE = 1;
    EditText title, description;
    private static final int CAMERA_REQUEST = 1888;
    String picturePath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        Intent i = getIntent();
        posttype = i.getIntExtra("type", -1);
        Log.e("posttype", posttype+"");
        setService();
        setDefault();
        setImage();
        setClick();
    }

    public void setService() {
        FamilingConnector.loadApiKey(this);
        service = FamilingConnector.getInstance();
    }

    public void setClick() {
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = (EditText) findViewById(R.id.item_name);
                description = (EditText) findViewById(R.id.item_comment);
                final String titleString = title.getText().toString();
                final String descString = description.getText().toString();
                Log.e("asdf", titleString + descString+"");
                service.postArticle(posttype, titleString, descString, null, null, true, (!picturePath.equals("")) ? new TypedFile("image/jpeg", new File(picturePath)) : null, new Callback<Group>() {
                    @Override
                    public void success(Group group, Response response) {
                        Toast.makeText(getApplicationContext(), "성공적으로 업로드되었습니다", Toast.LENGTH_SHORT).show();
                        finish();

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.e("asdf", error.getResponse().getStatus() + "");
                    }
                });
            }
        });
    }

    public void setDefault() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#E38242")));
        actionBar.setTitle(Html.fromHtml("<font color='#f2f2f2'><b>글 작성하기!</b> </font>"));
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setElevation(0);
        floatingActionButton = (FloatingActionButton) findViewById(R.id.postbutton);
        floatingActionButton.setColorNormal(Color.parseColor("#ff8f00"));
        floatingActionButton.setColorPressed(Color.parseColor("#ffda6c"));
        floatingActionButton.setIcon(R.drawable.ic_float_normal);

    }

    public void setImage() {
        toGallery = (ImageView) findViewById(R.id.post_image); //이미지뷰 초기화
        RelativeLayout r = (RelativeLayout) findViewById(R.id.click);
        r.setOnClickListener(new View.OnClickListener() {  //이미지뷰가 클릭되었을 때의 리스너
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //안드로이드 시스템에 있는 이미지들에서 선택(PICK)을 위한 인텐트 생성
                startActivityForResult(i, RESULT_LOAD_IMAGE);//위에서 선언한 1이라는 결과 코드로 액티비티를 선언
//                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            picturePath = cursor.getString(columnIndex);
            Log.e("파일 경로", picturePath);
//Uri에서 이미지의 외장 메모리상의 주소를 받아온 뒤
            cursor.close();
            toGallery.setImageBitmap(BitmapFactory.decodeFile(picturePath));//이미지뷰에 뿌려줍니다.
        } else if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            saveBitmaptoJpeg();
//            File imgFile = new File(finalPath);
//            if(imgFile.exists()){
//                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                toGallery.setImageBitmap(myBitmap);
//            }
//            Log.e("Path", finalPath);
//            toGallery.setImageBitmap(photo);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
