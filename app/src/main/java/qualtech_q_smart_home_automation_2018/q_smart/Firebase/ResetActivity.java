package qualtech_q_smart_home_automation_2018.q_smart.Firebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import qualtech_q_smart_home_automation_2018.q_smart.R;

public class ResetActivity extends AppCompatActivity {

    private Button restButton;
    private FirebaseAuth mAuth;
    private EditText mEmailField;
    private TextView btnBack;
    private ProgressBar rProgressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset);

        mAuth = FirebaseAuth.getInstance();

        restButton = findViewById(R.id.btn_reset);
        mEmailField = findViewById(R.id.btn_editTxt_reset);
        btnBack = findViewById(R.id.btn_back);
        rProgressbar = findViewById(R.id.rProgressBar);


        restButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEmailField.getText().toString();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter your Register email id", Toast.LENGTH_LONG).show();
                    return;
                }
                rProgressbar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if (task.isSuccessful()){
                                    Toast.makeText(ResetActivity.this,"We have sent you instructions to reset your password!",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(ResetActivity.this,"Failed to send email ",Toast.LENGTH_LONG).show();
                                }
                                rProgressbar.setVisibility(View.GONE);
                            }
                        });

            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ResetActivity.this,LoginActivity.class));
                finish();
            }
        });

    }
}
