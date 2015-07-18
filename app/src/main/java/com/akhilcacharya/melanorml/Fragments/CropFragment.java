package com.akhilcacharya.melanorml.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.akhilcacharya.melanorml.R;
import com.edmodo.cropper.CropImageView;


public class CropFragment extends Fragment {

    private static final String ARG_PARAM1 = "image_arg";

    private Bitmap image = null;
    private CropImageView cropImageView = null;
    private Button crop;

    public static CropFragment newInstance(Bitmap image) {
        CropFragment fragment = new CropFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, image);
        fragment.setArguments(args);
        return fragment;
    }

    public CropFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            this.image = (Bitmap) getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout =  inflater.inflate(R.layout.fragment_crop, container, false);

        this.cropImageView = (CropImageView) layout.findViewById(R.id.fragment_crop_image);
        this.cropImageView.setAspectRatio(5, 5);
        this.cropImageView.setFixedAspectRatio(true);
        this.cropImageView.setGuidelines(1);

        this.crop = (Button) layout.findViewById(R.id.fragment_crop_btn);

        /* Set an image bitmap scaled to the activity width */
        int originalWidth = image.getWidth();
        int originalHeight = image.getHeight();

        double ratio = (double) originalHeight/originalWidth;

        int newWidth = getActivity().getWindow().getDecorView().getWidth();
        int newHeight = (int) (newWidth * ratio);

        this.cropImageView.setImageBitmap(Bitmap.createScaledBitmap(image, newWidth, newHeight, true));


        this.crop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap result = cropImageView.getCroppedImage();
                ResultFragment resultFragment = ResultFragment.newInstance(result);
                getFragmentManager().beginTransaction().replace(R.id.container, resultFragment).commit();
            }
        });


        return layout;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
