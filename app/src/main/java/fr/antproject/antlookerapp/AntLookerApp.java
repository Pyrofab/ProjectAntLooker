package fr.antproject.antlookerapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
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

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import fr.antproject.antlookerapp.configuration.Configuration;
import fr.antproject.antlookerapp.configuration.SettingsActivity;
import fr.antproject.antlookercore.application.ImageProcessor;
import fr.antproject.antlookercore.model.diagram.IDiagram;

public class AntLookerApp extends AppCompatActivity {
    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final String SERVER_URL = "http://90.49.195.135:5000/antlooker";
    public boolean isImageReady = false;
    public static final int PICK_IMAGE = 2;
    public static final int SHARE_IMAGE = 3;
    public ImageView imageView;
    public String mCurrentPhotoPath;


    //TODO : L'image s'enleve quand il ya rotation de l'ecran

    static {
        System.loadLibrary("opencv_java3");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ant_looker_app);
        setTheme(R.style.AppTheme);

        cleanCache();
        //ImageProcessor.INSTANCE.setConfig(new Configuration(this));

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
                if(isImageReady){
                    TextView t = findViewById(R.id.textView);
                    new Thread(new Runnable() {
                        public void run() {
                            ImageView i = findViewById(R.id.imageView);
                            sendImage(i);
                        }
                    }).start();
                }else{
                    Toast.makeText(AntLookerApp.this, "Please choose an image to send", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void launchSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);

    }


    private void selectPhotoFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }

    private void sendImage(ImageView i){
        Log.d("DEBUG >>>>>","CREATE IMAGE TO SEND");
        Bitmap bitmap = ((BitmapDrawable)i.getDrawable()).getBitmap();

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost(SERVER_URL);

        MultipartEntityBuilder builder = MultipartEntityBuilder.create();


        builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);


        File f = new File(getApplicationContext().getCacheDir(), "image");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileBody fileBody = new FileBody(f); //image should be a String
        builder.addPart("my_image", fileBody);

        HttpEntity entity = builder.build();
        httppost.setEntity(entity);

        Log.d("DEBUG >>>>>","SENDING IMAGE");
        try {
            HttpResponse response = httpclient.execute(httppost);
        } catch (IOException e) {
            e.printStackTrace();

        }
        Log.d("DEBUG >>>>>","SENDING DONE");

    }

    private void doProcess() {
        if (isImageReady) {
            Toast.makeText(getApplicationContext(), "Processing", Toast.LENGTH_SHORT).show();
            final String filename = "imageToProcess.jpeg";
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String filename = "JPG_" + timeStamp + "_";
            File file;
            try {
                file = File.createTempFile(filename, ".tmp", getApplicationContext().getCacheDir());
                Drawable drawable = imageView.getDrawable();
                if (!(drawable instanceof BitmapDrawable)) {
                    Log.e("AntLookerApp", "The imageview drawable is not a BitmapDrawable !");
                    return;
                }
                Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageInByte = baos.toByteArray();

                FileOutputStream f = new FileOutputStream(file);
                f.write(imageInByte);
                f.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            TextView t = findViewById(R.id.textView);


            File[] fs = getApplicationContext().getCacheDir().listFiles();
            IDiagram result = null;
            if (fs.length > 0) {
                //TO DO : Le traitement de l'image
                Toast.makeText(getApplicationContext(), fs[0].getName(), Toast.LENGTH_SHORT).show();
                String aff = "";
                for (File f : getApplication().getCacheDir().listFiles())
                    aff += " | " + f.getName();
                t.setText(aff);
                Toast.makeText(getApplicationContext(),fs[0].getAbsolutePath(),Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(),fs[0].getTotalSpace()+"",Toast.LENGTH_LONG).show();
                result = ImageProcessor.INSTANCE.process(fs[0].getAbsolutePath());

            }
            if (result != null) {
                t.setText(String.format("Done : %s", result));
                Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
            }

            //Quand l'image a été scanné on l'affiche dans une autre activité

            Intent intent = new Intent(getBaseContext(), ShareActivity.class);
            //Put value in intent
            startActivityForResult(intent, SHARE_IMAGE);


        } else {
            Toast.makeText(getApplicationContext(), "Please Take or Import a picture", Toast.LENGTH_SHORT).show();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private void cleanCache() {
        for (File f : getApplicationContext().getCacheDir().listFiles()) {
            f.delete();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        Log.d("File dir = ", Environment.DIRECTORY_PICTURES);
        if (requestCode == REQUEST_IMAGE_CAPTURE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Log.d("[LOG]Take picture", data.toString());
                    TextView t = (TextView) findViewById(R.id.textView);
                    t.setText(data.toString());
                    Toast.makeText(getApplicationContext(), "Chargement de l'image importé", Toast.LENGTH_SHORT).show();

                    imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
                    isImageReady = true;
                }

            }
        } else if (requestCode == PICK_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    Log.d("[LOG]Pick image from Gallery", data.toString());
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
