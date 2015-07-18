package com.akhilcacharya.melanorml;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.akhilcacharya.melanorml.Fragments.CameraFragment;
import com.akhilcacharya.melanorml.Fragments.CropFragment;
import com.akhilcacharya.melanorml.Fragments.ResultFragment;


public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.container, CameraFragment.newInstance()).commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        //Get current fragment
        Fragment fragment = this.getSupportFragmentManager().findFragmentById(R.id.container);

        if(fragment instanceof CropFragment || fragment instanceof ResultFragment){
            CameraFragment cameraFragment = CameraFragment.newInstance();
            getSupportFragmentManager().beginTransaction().replace(R.id.container, cameraFragment).commit();
        }else{
            finish();
        }
    }

}
