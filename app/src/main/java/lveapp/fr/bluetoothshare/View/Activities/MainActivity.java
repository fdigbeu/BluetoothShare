package lveapp.fr.bluetoothshare.View.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.IOException;

import lveapp.fr.bluetoothshare.Presenter.Main.MainPresenter;
import lveapp.fr.bluetoothshare.Presenter.Main.MainView;
import lveapp.fr.bluetoothshare.R;

public class MainActivity extends AppCompatActivity implements MainView.IMainActivity, View.OnClickListener {
    // Ref widgets
    private ImageView imageSelected;
    private Button buttonSelectFile;
    private Button buttonShare;
    private Button buttonExport;
    private ProgressBar progressBar;
    // Ref presenter
    private MainPresenter presenter;
    // Request code
    private final int PICK_IMAGE_REQUEST = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //--
        presenter = new MainPresenter(this);
        presenter.loadMainData(MainActivity.this);
    }

    @Override
    public void init() {
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        imageSelected = (ImageView) findViewById(R.id.imageSelected);
        buttonExport = (Button)findViewById(R.id.buttonExport);
        buttonExport.setOnClickListener(this);
        buttonSelectFile = (Button)findViewById(R.id.buttonSelectFile);
        buttonSelectFile.setOnClickListener(this);
        buttonShare = (Button)findViewById(R.id.buttonShare);
        buttonShare.setOnClickListener(this);
    }

    @Override
    public void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.btn_select_file)), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case PICK_IMAGE_REQUEST:
                switch (resultCode){
                    case RESULT_OK:
                        presenter.retrieveUserAction(data);
                        break;
                }
                break;
        }
    }

    @Override
    public void showImageView(String srcImage) {
        Picasso.with(MainActivity.this).load(srcImage).into(imageSelected);
        buttonExport.setVisibility(View.VISIBLE);
        imageSelected.setVisibility(View.VISIBLE);
    }

    @Override
    public void showBitmapImageView(Uri filePath) {
        try {
            //Getting the Bitmap from Gallery
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
            //Setting the Bitmap to ImageView
            imageSelected.setImageBitmap(bitmap);
            buttonExport.setVisibility(View.VISIBLE);
            imageSelected.setVisibility(View.VISIBLE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showButtonShare() {
        buttonShare.setVisibility(View.VISIBLE);
    }

    @Override
    public void showToastMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void activateButton() {
        buttonShare.setEnabled(true);
        buttonSelectFile.setEnabled(true);
    }

    @Override
    public void desactivateButton() {
        buttonShare.setEnabled(false);
        buttonSelectFile.setEnabled(false);
    }

    @Override
    public void onClick(View view) {
        presenter.retrieveUserAction(view);
    }
}
