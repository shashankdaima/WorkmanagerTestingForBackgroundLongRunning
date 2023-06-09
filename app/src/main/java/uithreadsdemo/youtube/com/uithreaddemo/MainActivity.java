package uithreadsdemo.youtube.com.uithreaddemo;

import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Button buttonStart, buttonStop;
    private TextView textViewthreadCount;
    int count = 0;

    private boolean isServiceBound;
    private ServiceConnection serviceConnection;

    WorkManager workManager;

    /*Handler handler;*/


    private Intent serviceIntent;

    private boolean mStopLoop;
    private PeriodicWorkRequest workRequest;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.i(getString(R.string.service_demo_tag), "MainActivity thread id: " + Thread.currentThread().getId());

        buttonStart = (Button) findViewById(R.id.buttonThreadStarter);
        buttonStop = (Button) findViewById(R.id.buttonStopthread);

        textViewthreadCount = (TextView) findViewById(R.id.textViewthreadCount);

        buttonStart.setOnClickListener(this);
        buttonStop.setOnClickListener(this);

        workManager = WorkManager.getInstance(getApplicationContext());


        workRequest = new PeriodicWorkRequest.Builder(RandomNumberGeneratorWorker.class, 15, TimeUnit.MINUTES).addTag("SHASHANKWORK").build();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonThreadStarter:
                mStopLoop = true;
                workManager.enqueueUniquePeriodicWork("SHASHANK", ExistingPeriodicWorkPolicy.KEEP, workRequest);
                break;
            case R.id.buttonStopthread:
                workManager.cancelAllWorkByTag("SHASHANKWORK");
                break;
        }
    }
}
