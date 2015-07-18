package com.akhilcacharya.melanorml.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akhilcacharya.melanorml.R;

import java.io.File;
import java.io.IOException;

public class CameraFragment extends Fragment {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    public final String TAG = this.getClass().toString();

    public Uri imageUri = null;

    public static CameraFragment newInstance() {
        CameraFragment fragment = new CameraFragment();
        return fragment;
    }

    public CameraFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_camera, container, false);

        final Button takePicture = (Button) layout.findViewById(R.id.fragment_camera_snap);
        takePicture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
            File outputFile = null;

            try {
                outputFile = createImageFile(".jpeg");
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(outputFile != null) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                imageUri = Uri.fromFile(outputFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
            }
        });


        return layout;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

    }

    private File createImageFile(String fileExtensionToUse) throws IOException {
        File storageDir = new File(
                Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_PICTURES
                ),
                "Melanorml"
        );

        if(!storageDir.exists()) {
            if (!storageDir.mkdir()){
                Log.d(TAG, "was not able to create it");
            }
        }
        if (!storageDir.isDirectory()){
            Log.d(TAG, "Don't think there is a dir there.");
        }

        File image = File.createTempFile(
            "lastImage",
            fileExtensionToUse,
            storageDir
        );

        return image;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == getActivity().RESULT_OK) {
            Bitmap imageBitmap = BitmapFactory.decodeFile(imageUri.getPath());
            CropFragment cropFragment = CropFragment.newInstance(imageBitmap);
            getFragmentManager().beginTransaction().replace(R.id.container, cropFragment).commit();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
