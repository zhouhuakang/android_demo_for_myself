package com.demo.zhaojie.headset;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    public static final int INTENT_REQUEST_CODE_ALBUM = 0;
    public static final int INTENT_REQUEST_CODE_CAMERA = 1;

    private static final int PHOTO_PICKED_WITH_DATA = 0x10;
    private static final int PHOTO_CROP_DATA = 0x11;

    private ImageView mHead;
    private String headPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mHead = (ImageView) findViewById(R.id.head);
        ((Button) findViewById(R.id.button_select_photo)).setOnClickListener(this);
        ((Button) findViewById(R.id.button_take_picture)).setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_select_photo:
                selectPhoto(this);
                break;
            case R.id.button_take_picture:
                headPath = takePicture(this);
                break;
        }
    }


    public static void selectPhoto(Activity activity) {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/jpeg");
        intent.putExtra("return-data", true);
        activity.startActivityForResult(intent, PHOTO_PICKED_WITH_DATA);
    }

    public static String takePicture(Activity activity) {
        createDirFile(Environment.getExternalStorageDirectory().getPath() + "/zhaojie/images/");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String path = Environment.getExternalStorageDirectory().getPath() + "/zhaojie/images/" + timeStamp + ".png";
        File file = createNewFile(path);
        if (file != null) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
        }
        activity.startActivityForResult(intent, INTENT_REQUEST_CODE_CAMERA);
        return path;
    }

    public static File createNewFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                return null;
            }
        }
        return file;
    }

    public static void createDirFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    private void cropImageUri(Uri uri, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true); // no face detection
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK)
            return;
        Bitmap tempPhoto = null;
        switch (requestCode) {
            case PHOTO_PICKED_WITH_DATA: // ????????
                ContentResolver cr = this.getContentResolver();
                try {
                    Uri uri = data.getData();
                    cropImageUri(uri, PHOTO_CROP_DATA);
                    // tempPhoto =
                    // BitmapFactory.decodeStream(cr.openInputStream(uri));
                    // mStepPhoto.setUserPhoto(tempPhoto);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case INTENT_REQUEST_CODE_CAMERA:
                tempPhoto = BitmapFactory.decodeFile(headPath);
                cropImageUri(Uri.fromFile(new File(headPath)), PHOTO_CROP_DATA);
                break;
            case PHOTO_CROP_DATA:
                tempPhoto = data.getParcelableExtra("data");
                mHead.setImageBitmap(tempPhoto);
                break;
        }
    }
}
