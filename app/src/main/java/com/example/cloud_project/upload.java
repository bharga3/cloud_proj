//package com.javasampleapproach.s3amazon;
package com.example.cloud_project;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.util.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class upload extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "upload";
    final int PICKFILE_RESULT_CODE = 1;
    public ArrayList<String> All_filename = new ArrayList<String>();
    final String KEY = "";
    final String SECRET = "";
    private AmazonS3Client s3Client;
    private BasicAWSCredentials credentials;
    private TextView tvFileName;
    private ImageView imageView;
    private EditText edtFileName;


    private Uri fileUri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
        uploadFile();
        findViewById(R.id.choosefile).setOnClickListener(this);
        findViewById(R.id.btnUpload).setOnClickListener(this);
        credentials = new BasicAWSCredentials(KEY, SECRET);
        s3Client = new AmazonS3Client(credentials);
        s3Client.setRegion(Region.getRegion(Regions.US_WEST_2));


    }

    private void uploadFile()
    {
        if (fileUri != null) {
            final String fileName = edtFileName.getText().toString();

            if (!validateInputFileName(fileName)) {
                return;
            }
        final File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                "/" + fileName);

        createFile(getApplicationContext(), fileUri, file);

        TransferUtility transferUtility =
                TransferUtility.builder()
                        .context(getApplicationContext())
                        .awsConfiguration(AWSMobileClient.getInstance().getConfiguration())
                        .s3Client(s3Client)
                        .build();


        }
    }

  @Override
            protected void onActivityResult(int requestCode, int resultCode, Intent data) {
                super.onActivityResult(requestCode, resultCode, data);
                if (requestCode != PICKFILE_RESULT_CODE || resultCode != RESULT_OK) {
                    return;
                }
                //abcfile=data.getData();
               // changefilename(data.getData());

                // System.out.println("++++++++++++++" +data+ "=======================");
            }

// file extension
            private String getFileExtension(Uri uri) {
                ContentResolver contentResolver = getContentResolver();
                MimeTypeMap mime = MimeTypeMap.getSingleton();

                return mime.getExtensionFromMimeType(contentResolver.getType(uri));
            }

            public void onClick(View view) {
                int i = view.getId();

                if (i == R.id.choosefile) {
                    showChoosingFile();
                } else if (i == R.id.btnUpload) {
                    uploadFile();

                }
            }


       // input file validation
            private boolean validateInputFileName(String fileName) {

                if (TextUtils.isEmpty(fileName)) {
                    Toast.makeText(this, "Enter file name!", Toast.LENGTH_SHORT).show();
                    return false;
                }

                return true;
            }

//choosing file
           Button uploadbutton = findViewById(R.id.btnUpload);
            private void showChoosingFile() {


                uploadbutton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent chooseFile = new Intent(Intent.ACTION_GET_CONTENT);
                        chooseFile.setType("*/*");
                        chooseFile = Intent.createChooser(chooseFile, "Choose a file");
                        startActivityForResult(chooseFile, PICKFILE_RESULT_CODE);

                    }
                });
            }


        private void createFile(Context context, Uri srcUri, File dstFile) {
            try {
                InputStream inputStream = context.getContentResolver().openInputStream(srcUri);
                if (inputStream == null) return;
                OutputStream outputStream = new FileOutputStream(dstFile);
                IOUtils.copy(inputStream, outputStream);
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }




}

