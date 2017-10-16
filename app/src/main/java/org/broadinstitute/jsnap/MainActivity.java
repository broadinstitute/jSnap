package org.broadinstitute.jsnap;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.File;
import java.lang.reflect.Array;
import java.util.List;

import static android.os.Environment.getExternalStorageDirectory;


public class MainActivity extends Activity {
    private static String logtag = "MainActivity";
    private static int TAKE_PICTURE = 1;

    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".fileprovider";
    private Uri mURI;
    private static final String PHOTOS="photos";
    private static final String FILENAME="jsnap_test.jpg";
    Uri imageURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button cameraButton = (Button)(findViewById(R.id.cam_button));
        cameraButton.setOnClickListener(cameraListener);
    }
    private View.OnClickListener cameraListener = new View.OnClickListener() {
        public void onClick(View v) {
            try {
                takePhoto();
            } catch (Exception e) {
                Log.e(logtag, e.toString());
                // Full stack trace.
                // Log.e(logtag, "exception", e);

            }
        }
    };
    private void takePhoto(){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        File imagePath = new File(getFilesDir(), "images");
        Log.e(logtag, imagePath.getAbsolutePath());
        File photo = new File(imagePath, FILENAME);
        Log.e(logtag, photo.getAbsolutePath());
        imageURI = FileProvider.getUriForFile(this, AUTHORITY, photo);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
        // TAKE_PICTURE is a request code saying we want to use the rear-facing camera.
        startActivityForResult(intent, TAKE_PICTURE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);

        if(resultCode == Activity.RESULT_OK) {
            Uri selectedImage = imageURI;
            getContentResolver().notifyChange(selectedImage, null);

            ImageView imageView = (ImageView)findViewById(R.id.image_camera);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;

            try {
                Log.e(logtag, selectedImage.getEncodedPath());
                bitmap = MediaStore.Images.Media.getBitmap(cr, selectedImage);
                imageView.setImageBitmap(bitmap);
                Toast.makeText(MainActivity.this, selectedImage.toString(), Toast.LENGTH_SHORT).show();
            } catch(Exception e) {
                Log.e(logtag, selectedImage.getPath());
                Log.e(logtag, e.toString());
                Log.e(logtag, "exception", e);
            }
        }
    }
}
