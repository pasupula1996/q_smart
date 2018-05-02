package qualtech_q_smart_home_automation_2018.q_smart.Firebase;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import qualtech_q_smart_home_automation_2018.q_smart.Manifest;
import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenuActivity;
import qualtech_q_smart_home_automation_2018.q_smart.R;


public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnLogin;
    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    private TextView resetPassword;


    private String QUALTECH = "qsmarthelp@gmail.com";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            String qualtech = mAuth.getCurrentUser().getEmail();
                if (!qualtech.equals("qsmarthelp@gmail.com")){
                    startActivity(new Intent(LoginActivity.this,SimpleMenuActivity.class));
                    finish();
                }
        }

        setContentView(R.layout.activity_login);

        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.login_btn);
        resetPassword = findViewById(R.id.reset_password);
        mProgressBar = findViewById(R.id.login_progressbar);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please Enter Email id",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please Enter password",Toast.LENGTH_LONG).show();
                    return;
                }

                mProgressBar.setVisibility(View.VISIBLE);


                mAuth.signInWithEmailAndPassword(email,password)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgressBar.setVisibility(View.GONE);
                                if (!task.isSuccessful()){

                                    if (password.length()<6){
                                        inputPassword.setError("Password length should contain more 6 characters");
                                    }else{
                                        Toast.makeText(LoginActivity.this,"Logic Failed. Please Try again",Toast.LENGTH_LONG).show();
                                    }

                                }
                                else {
                                    if (email.equals(QUALTECH)){
                                        startActivity(new Intent(LoginActivity.this,SignUpActivity.class));
                                        finish();
                                    }else {
                                        Toast.makeText(getApplicationContext(),"Login Successful",Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(LoginActivity.this,SimpleMenuActivity.class));
                                        finish();
                                    }
                                }
                            }
                        });
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(LoginActivity.this,ResetActivity.class));
                finish();
            }
        });

    }

}
