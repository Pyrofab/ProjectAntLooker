package fr.antproject.antlookerapp;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AntLookerApp extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;

    public ImageView imageView;

    static {
        System.loadLibrary("opencv_java3");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ant_looker_app);
        imageView = (ImageView) findViewById(R.id.imageView);
        Button takePicture = (Button) findViewById(R.id.takePicture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button selectPicture = (Button) findViewById(R.id.importPicture);
        selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhotoFromGallery();
            }
        });



    }


    private void selectPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if( data != null){
                    Log.d("Take picture",data.toString());
                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText(data.toString());

                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bmp);
                }

            }
        } else if(requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK) {
                if( data != null){
                    Log.d("Take picture",data.toString());
                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText(data.toString());

                    Bitmap bmp = (Bitmap) data.getExtras().get("data");
                    imageView.setImageBitmap(bmp);

                }

            }
        }
    }
}

