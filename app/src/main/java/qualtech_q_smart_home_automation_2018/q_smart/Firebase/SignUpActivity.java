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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import qualtech_q_smart_home_automation_2018.q_smart.SimpleMenuActivity;
import qualtech_q_smart_home_automation_2018.q_smart.R;

public class SignUpActivity extends AppCompatActivity {

    private EditText mEmail, mFullname, mPassword, mRepeatPassword;
    private Button mRegister;
    private FirebaseAuth mAuth;
    private ProgressBar sProgressBar;
    private TextView alreadyHaveAnAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        mEmail = findViewById(R.id.edt_email);
        mFullname = findViewById(R.id.edt_full_name);
        mPassword = findViewById(R.id.edt_password);
        //mRepeatPassword = findViewById(R.id.edt_repeat_password);
        mRegister = findViewById(R.id.btn_register);
        sProgressBar = findViewById(R.id.sProgressbar);
        alreadyHaveAnAccount = findViewById(R.id.already_account);

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fullname = mFullname.getText().toString().trim();

                if (TextUtils.isEmpty(fullname)){
                    Toast.makeText(getApplicationContext(),"Please enter your name",Toast.LENGTH_LONG).show();
                    return;
                }
                String email = mEmail.getText().toString().trim();
                if (TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Please enter valid email id",Toast.LENGTH_LONG).show();
                    return;
                }
                String password = mPassword.getText().toString().trim();
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Please fill password field",Toast.LENGTH_LONG).show();
                    return;
                }
                /*String RepeatPassword = mRepeatPassword.getText().toString().trim();
                if (TextUtils.isEmpty(RepeatPassword)){
                    Toast.makeText(getApplicationContext(),"Please repeat password",Toast.LENGTH_LONG).show();
                }*/
                if (password.length()<6){
                    Toast.makeText(getApplicationContext(),"password is too short",Toast.LENGTH_LONG).show();
                }
                sProgressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email,password)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {

                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                sProgressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()){
                                    Toast.makeText(getApplicationContext(),"SignUp Successful",Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(SignUpActivity.this,SimpleMenuActivity.class));
                                    finish();
                                }else{
                                    Toast.makeText(getApplicationContext(),"Registration failed. try again",Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });

        alreadyHaveAnAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignUpActivity.this,LoginActivity.class));
                finish();
            }
        });

    }
}
