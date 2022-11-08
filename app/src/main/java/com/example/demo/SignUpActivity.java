package com.example.demo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.demo.common.retrofit.RetrofitService;
import com.example.demo.hr.user.api.UserApi;
import com.example.demo.hr.user.model.User;
import com.example.logindemo.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTextView;

    TextInputEditText idText;
    TextInputEditText pwText;
    TextInputEditText nameText;
    RadioGroup radioGroup;
    AutoCompleteTextView genderDropDown;
    MaterialButton signUpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        setTitle("회원가입");

        initializeComponents();
    }
    private void initializeComponents(){
        autoCompleteTextView = findViewById(R.id.dropdown_gender);

        String[] genders = {"남성","여성"};
        ArrayAdapter<String> gendersAdapter = new ArrayAdapter<>(SignUpActivity.this,R.layout.item_list,genders);

        autoCompleteTextView.setAdapter(gendersAdapter);

        idText = findViewById(R.id.input_id);
        pwText = findViewById(R.id.input_password);
        nameText = findViewById(R.id.input_name);
        radioGroup = findViewById(R.id.radioGroup);
        genderDropDown = findViewById(R.id.dropdown_gender);
        signUpButton = findViewById(R.id.button_signup);

        RetrofitService retrofitService = new RetrofitService();

        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        signUpButton.setOnClickListener(view -> {
            User user = new User();
            user.setId(Long.parseLong(idText.getText().toString()));
            user.setPassword(pwText.getText().toString());
            user.setName(nameText.getText().toString());
            RadioButton radioButton = findViewById(radioGroup.getCheckedRadioButtonId());
            user.setAuthType(radioButton.getText().toString());
            user.setGender(genderDropDown.getText().toString());

            signUpValidate();

            userApi.createUser(user)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(SignUpActivity.this,"Failed",Toast.LENGTH_SHORT).show();
                            Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE,"실패");
                        }
                    });
        });
    }
    private void signUpValidate(){

    }
}
