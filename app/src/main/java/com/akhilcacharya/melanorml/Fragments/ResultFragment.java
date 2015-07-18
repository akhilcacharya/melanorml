package com.akhilcacharya.melanorml.Fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.akhilcacharya.melanorml.Models.MelanormlQuery;
import com.akhilcacharya.melanorml.Models.MelanormlResult;
import com.akhilcacharya.melanorml.Models.MelanormlService;
import com.akhilcacharya.melanorml.Models.Prediction;
import com.akhilcacharya.melanorml.R;
import com.edmodo.cropper.CropImageView;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.rahatarmanahmed.cpv.CircularProgressView;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class ResultFragment extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private final String TAG = this.getClass().toString();


    private static final String ARG_PARAM1 = "base64_arg";
    private static final String ARG_FILENAME = "filename_arg";
    private static final String MELANOMA_CLASS = "MELANOMA";
    private static final String NORMAL_CLASS = "NORMAL";

    /* Data */
    private Bitmap sampleImage = null;

    /* Views */
    private ImageView sampleImageView;
    private TextView prognosis;
    private TextView recommendation;
    private TextView working;
    private HorizontalBarChart barChart;
    private CircularProgressView progressView;

    /* Typeface */
    Typeface robotoThin;

    public static ResultFragment newInstance(Bitmap image) {
        ResultFragment fragment = new ResultFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PARAM1, image);
        fragment.setArguments(args);
        return fragment;
    }

    public ResultFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            sampleImage = (Bitmap) getArguments().getParcelable(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_result, container, false);

        this.sampleImageView = (ImageView) layout.findViewById(R.id.fragment_result_image);
        this.sampleImageView.setImageBitmap(sampleImage);

        this.prognosis = (TextView) layout.findViewById(R.id.fragment_result_prognosis);
        this.recommendation = (TextView) layout.findViewById(R.id.fragment_result_recommendation);
        this.barChart = (HorizontalBarChart) layout.findViewById(R.id.fragment_result_graph);
        this.progressView = (CircularProgressView) layout.findViewById(R.id.progress_view);
        this.working = (TextView) layout.findViewById(R.id.fragment_result_working_indicator);

        ResultFragment.this.barChart.setVisibility(View.INVISIBLE);
        ResultFragment.this.prognosis.setVisibility(View.INVISIBLE);
        ResultFragment.this.recommendation.setVisibility(View.INVISIBLE);
        ResultFragment.this.working.setVisibility(View.VISIBLE);


        ResultFragment.this.progressView.setVisibility(View.VISIBLE);


        this.robotoThin = Typeface.createFromAsset(getActivity().getAssets(), "Roboto-Thin.ttf");

        new GetResultTask().execute();

        return layout;
    }


    public void setupChart(){
        XAxis xl = this.barChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setTypeface(this.robotoThin);

        YAxis yl = this.barChart.getAxisRight();
        yl.setTypeface(this.robotoThin);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void setChart(MelanormlResult result){
        /* Show on graph onPostExecute */
        ArrayList<Prediction> predictions = result.getPredictions();

        ArrayList<String> classes = new ArrayList<String>();
        ArrayList<BarEntry> confidences = new ArrayList<BarEntry>();

        int index = 0;
        for(Prediction prediction: predictions){
            classes.add(prediction.getClassName());
            confidences.add(new BarEntry(prediction.getProb() * 100, index++));
        }

        BarDataSet data = new BarDataSet(confidences, "Confidence");
        data.setValueTypeface(ResultFragment.this.robotoThin);

        BarData barData = new BarData(classes, data);
        ResultFragment.this.barChart.setData(barData);
        ResultFragment.this.barChart.animateY(1500);

        float topConfidence = confidences.get(0).getVal();
        String topClass = classes.get(0);

        String[] recommendations = getResources().getStringArray(R.array.prognosis_array);

        int RECOMMENDATION_NORMAL_HIGH = 0;
        int RECOMMENDATION_NORMAL_LOW = 1;
        int RECOMMENDATION_MELANOMA_HIGH = 2;
        int RECOMMENDATION_MELANOMA_LOW = 3;

        String recommendation = "";

        if(topClass.toUpperCase().equals(NORMAL_CLASS)){
            if(topConfidence >= 80){
                            /* High confidence normal */
                recommendation = recommendations[0];
            }else{
                            /* Low confidence normal */
                recommendation = recommendations[1];
            }
        }else if(topClass.toUpperCase().equals(MELANOMA_CLASS)){
            if(topConfidence >= 50){
                            /* High confidence melanoma */
                recommendation = recommendations[2];
            }else{
                            /* Low confidence melanoma */
                recommendation = recommendations[3];
            }
        }

        String prognosis = getResources().getString(R.string.prognosis_template, topClass, new DecimalFormat("#.00").format(topConfidence) + "%");

        ResultFragment.this.recommendation.setText(recommendation);
        ResultFragment.this.prognosis.setText(prognosis);

    }


    class GetResultTask extends AsyncTask<Void, Void, Void> {

        String base64;

        @Override
        protected Void doInBackground(Void... params) {
            //Convert to byte array
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            sampleImage.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] sampleImageBytes = stream.toByteArray();
            base64 = Base64.encodeToString(sampleImageBytes, 0, sampleImageBytes.length, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            MelanormlQuery query = new MelanormlQuery(base64);
            RestAdapter adapter = new RestAdapter.Builder().setEndpoint("https://www.metamind.io").build();
            MelanormlService service = adapter.create(MelanormlService.class);
            service.result(query, new Callback<MelanormlResult>() {
                @Override
                public void success(MelanormlResult melanormlResult, Response response) {

                    ResultFragment.this.barChart.setVisibility(View.VISIBLE);
                    ResultFragment.this.prognosis.setVisibility(View.VISIBLE);
                    ResultFragment.this.recommendation.setVisibility(View.VISIBLE);
                    ResultFragment.this.barChart.setDescription("");

                    ResultFragment.this.progressView.startAnimation();
                    ResultFragment.this.progressView.setVisibility(View.INVISIBLE);
                    ResultFragment.this.working.setVisibility(View.INVISIBLE);


                    setChart(melanormlResult);
                }

                @Override
                public void failure(RetrofitError error) {
                     Log.v(TAG, "FAILED" + error.toString());
                }
            });
        }
    }



}
