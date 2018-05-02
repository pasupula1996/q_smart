package qualtech_q_smart_home_automation_2018.q_smart;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import qualtech_q_smart_home_automation_2018.q_smart.Firebase.LoginActivity;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.BLUETOOTH;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_TIME_OUT = 1000;

    public String[] permissionsRequired = new String[]{CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.BLUETOOTH,
            android.Manifest.permission.READ_EXTERNAL_STORAGE,
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
    public static final int PERMISSION_CALL_BACK_REQUEST = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        /*if (checkPermissions()){
            Toast.makeText(this,"All Permissions Granted",Toast.LENGTH_SHORT).show();
        }else {
            requestPermissions();
        }*/
        requestPermissions();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(i);
                finish();
            }
        },SPLASH_TIME_OUT);
    }
    public void requestPermissions(){
        ActivityCompat.requestPermissions(SplashActivity.this,new String[]{
                Manifest.permission.CAMERA,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.BLUETOOTH,Manifest.permission.READ_EXTERNAL_STORAGE
                ,Manifest.permission.WRITE_EXTERNAL_STORAGE
        },PERMISSION_CALL_BACK_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CALL_BACK_REQUEST:
                if (grantResults.length>0){
                    boolean cameraPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean locationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean bluetoothPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                    boolean storageRead = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                    boolean storageWrite = grantResults[4]==PackageManager.PERMISSION_GRANTED;

                    if (cameraPermission && locationPermission && bluetoothPermission && storageRead && storageWrite){
                        Toast.makeText(this,"Permissions Granted",Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(this,"Permission denied",Toast.LENGTH_SHORT).show();
                        requestPermissions();
                    }
                }

                break;
        }
    }
    public boolean checkPermissions(){
        int cameraP = ContextCompat.checkSelfPermission(getApplicationContext(),CAMERA);
        int locationP = ContextCompat.checkSelfPermission(getApplicationContext(),ACCESS_FINE_LOCATION);
        int bluetoothP = ContextCompat.checkSelfPermission(getApplicationContext(),BLUETOOTH);
        int storage = ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        int writeStorageP = ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);

        return cameraP == PackageManager.PERMISSION_GRANTED &&
                locationP == PackageManager.PERMISSION_GRANTED &&
                bluetoothP == PackageManager.PERMISSION_GRANTED &&
                storage == PackageManager.PERMISSION_GRANTED &&
                writeStorageP == PackageManager.PERMISSION_GRANTED;
    }
}
