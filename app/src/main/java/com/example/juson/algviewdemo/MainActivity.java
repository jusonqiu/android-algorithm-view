package com.example.juson.algviewdemo;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.com.juson.algorithms.Algorithm_entry;
import com.com.juson.algorithms.GenArray;
import com.com.juson.algorithms.Insertion;
import com.com.juson.algorithms.MergeSort;
import com.com.juson.algorithms.QuickSort;
import com.com.juson.algorithms.ShellSort;
import com.com.juson.algorithms.TestUpdate;
import com.juson.view.algview.AlgView;

public class MainActivity extends Activity {

    private static final String TAG = "MainActivity";
    private AlgView mAlgView;
    private Spinner mSpinner;
    private String  mSelectString;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            mAlgView.setArr((int[] ) msg.obj, msg.arg1, msg.arg2);
        }
    };

    private Algorithm_entry mEntry = new Algorithm_entry() {
        @Override
        public void sort(int[] a, TestUpdate testor) {

            if (mSelectString == null) return;

            switch (mSelectString){
                case "ShellSort":
                    ShellSort.sort(a, testor);
                    break;
                case "MergeSort":
                    MergeSort.sort(a, testor);
                    break;
                case "InsertionSort":
                    Insertion.sort(a, testor);
                    break;
                case "QuickSort":
                    QuickSort.sort(a, testor);
                    break;
                default:
                    break;
            }
        }
    };

    private int[] testArray ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        mAlgView = (AlgView) findViewById(R.id.alg_view);
        mSpinner = (Spinner) findViewById(R.id.spinner);

        testArray = new int[100];
        GenArray.genIntArray(testArray, 100);

        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mSelectString = mSpinner.getSelectedItem().toString();
                new SortTestor(testArray.clone(), mEntry).start();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private class SortTestor extends Thread implements TestUpdate{
        private int[] aa;
        private Algorithm_entry entry;

        public SortTestor(int[] a,  Algorithm_entry entry) {
            aa = a;
            this.entry = entry;
        }

        private void setEnable(final boolean enable){
            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mSpinner.setEnabled(enable);
                }
            });
        }

        @Override
        public void run() {
            setEnable(false);
            entry.sort(aa, this);
            setEnable(true);
        }

        @Override
        public void onUpdate(int[] a, int i, int j) {
            mHandler.obtainMessage(0, i, j, a).sendToTarget();
            try {
                Thread.sleep(50);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
