package fr.antproject.antlookerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import fr.antproject.antlookerapp.configuration.Configuration;
import fr.antproject.antlookerapp.configuration.SettingsActivity;
import fr.antproject.antlookercore.application.ImageProcessor;
import fr.antproject.antlookercore.model.diagram.IDiagram;

public class AntLookerApp extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public boolean isImageReady = false;
    public static final int PICK_IMAGE = 2;
    public ImageView imageView;
    public String mCurrentPhotoPath;


    //TO DO : L'image s'enleve quand il ya rotation de l'ecran

    static {
        System.loadLibrary("opencv_java3");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ant_looker_app);

        cleanCache();
        ImageProcessor.INSTANCE.setConfig(new Configuration(this));

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

        Button processButton = findViewById(R.id.buttonProcess);
        processButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView t = findViewById(R.id.textView);
                doProcess();
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


    private void doProcess(){
        if(isImageReady) {
            Toast.makeText(getApplicationContext(), "Processing", Toast.LENGTH_SHORT).show();
            final String filename = "imageToProcess.jpeg";
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String filename = "JPG_" + timeStamp + "_";
            File file;
            try {
                file = File.createTempFile(filename, ".tmp", getApplicationContext().getCacheDir());
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                FileOutputStream f = new FileOutputStream(file);
                f.write(imageInByte);
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
            TextView t = (TextView) findViewById(R.id.textView);


            File[] fs = getApplicationContext().getCacheDir().listFiles();
            IDiagram process = null;
            if (fs.length > 0) {
                //TO DO : Le traitement de l'image
                Toast.makeText(getApplicationContext(), fs[0].getName(), Toast.LENGTH_SHORT).show();
                String aff = "";
                for (File f : getApplication().getCacheDir().listFiles())
                    aff += " | " + f.getName();
                t.setText(aff);
                //process = ImageProcessor.INSTANCE.process(fs[0].getAbsolutePath());

            }
            if (process != null) {

                t.setText("Done : " + process.toString());
            }

        }else{
            Toast.makeText(getApplicationContext(),"Please Take or Import a picture",Toast.LENGTH_SHORT).show();
        }
    }

//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                ex.printStackTrace();
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "fr.antproject.antlookerapp.files.Pictures",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
//            }
//        }
//    }
private void dispatchTakePictureIntent() {
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }
}

private void cleanCache(){
        for (File f : getApplicationContext().getCacheDir().listFiles()){
            f.delete();
        }
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
                    Toast.makeText(getApplicationContext(), "Chargement de l'image importé", Toast.LENGTH_SHORT).show();

                    imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    isImageReady = true;
                }

            }
        } else if(requestCode == PICK_IMAGE){
            if (resultCode == RESULT_OK) {
                if( data != null){
                    Log.d("[LOG]Pick image from Gallery",data.toString());
                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText(data.toString());
                    Toast.makeText(getApplicationContext(), "Chargement de l'image importé", Toast.LENGTH_SHORT).show();
                    Uri selectedimg = data.getData();
                    try {
                        imageView.setImageBitmap(MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedimg));
                        isImageReady = true;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }
}
