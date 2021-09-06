package android.nguyenphuocthienan.projecmain1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private TextView txtSignUp, txtForgotPwd;
    private EditText logUsername, logPassword;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logUsername = findViewById(R.id.logUsername);
        logPassword = findViewById(R.id.logPassword);

        txtSignUp = findViewById(R.id.txtSignUp);
        Button btnLogin = findViewById(R.id.btnLogin);

        // txtSignUp chuyển sang trang Đăng ký
        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user_name = logUsername.getText().toString();
                String password = logPassword.getText().toString();
                // kiểm tra trống
                if (TextUtils.isEmpty(user_name) || TextUtils.isEmpty(password))
                {
                    Toast.makeText(LoginActivity.this, "All fields required", Toast.LENGTH_SHORT).show();
                }
                else if (user_name.equals("admin@yahoo.com") && password.equals("123456"))
                {
                    Intent intent = new Intent(LoginActivity.this, ManagerActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    login(user_name, password);
                }
            }
        });


    }

    private void login(String user_name, String password) {
        firebaseAuth.signInWithEmailAndPassword(user_name, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    Toast.makeText(LoginActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}