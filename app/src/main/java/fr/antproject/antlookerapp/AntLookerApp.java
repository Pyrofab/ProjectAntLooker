package fr.antproject.antlookerapp;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.antproject.antlookerapp.configuration.SettingsActivity;

public class AntLookerApp extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGE = 2;
    public ImageView imageView;
    public String mCurrentPhotoPath;
    static {
        System.loadLibrary("opencv_java3");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ant_looker_app);
        imageView = findViewById(R.id.imageView);
        Button takePicture = findViewById(R.id.takePicture);
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });

        Button selectPicture = findViewById(R.id.importPicture);
        selectPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectPhotoFromGallery();
            }
        });

        FloatingActionButton settingButton = findViewById(R.id.floatingActionButton);
        settingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                launchSettingsActivity();
            }
        });




    }

    private void launchSettingsActivity(){
        Intent intent = new Intent(this,SettingsActivity.class);
        startActivity(intent);

    }


    private void selectPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "fr.antproject.antlookerapp.files.Pictures",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "PNG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".png",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        Log.d("File dir = ",Environment.DIRECTORY_PICTURES);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if( data != null){
                    Log.d("[LOG]Take picture",data.toString());
                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText(data.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Chargement de l'image importé", Toast.LENGTH_SHORT);
                    toast.show();
                    imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                }

            }
        } else if(requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK) {
                if( data != null){
                    Log.d("[LOG]Pick image from Gallery",data.toString());
                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText(data.toString());
                    Toast toast = Toast.makeText(getApplicationContext(), "Chargement de l'image importé", Toast.LENGTH_SHORT);
                    toast.show();
                    Uri selectedimg = data.getData();
                    try {
                        imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}

