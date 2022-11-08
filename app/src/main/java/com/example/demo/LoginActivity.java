package com.example.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo.hr.user.model.User;
import com.example.demo.common.retrofit.RetrofitService;
import com.example.demo.hr.user.api.UserApi;
import com.example.logindemo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("로그인");
        initializeComponents();
        ClickSignUp();
    }

    private void initializeComponents(){
        TextInputEditText inputIdText = findViewById(R.id.input_id);
        TextInputEditText inputPwText = findViewById(R.id.input_password);
        MaterialButton loginBtn = findViewById(R.id.login_btn);

        RetrofitService retrofitService = new RetrofitService();

        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        loginBtn.setOnClickListener(view ->{
            Long id = Long.parseLong(inputIdText.getText().toString());
            String pw = inputPwText.getText().toString();

            User user = new User();
            user.setId(id);
            user.setPassword(pw);

            userApi.loginUser(user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Intent intent = new Intent(getApplicationContext(), QRActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(LoginActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
                            Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE,"실패");
                            inputIdText.setText("");
                            inputPwText.setText("");
                        }
                    });
        });
    }
    private void ClickSignUp(){
        TextView signUpTextView = findViewById(R.id.textview_signup);
        signUpTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });
    }
}