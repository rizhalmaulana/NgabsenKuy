package com.rizal.pegawaique;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthActivity extends AppCompatActivity {
    @BindView(R.id.txtDaftar)
    TextView Daftar;
    @BindView(R.id.cvLogin)
    CardView cvLogin;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.et_pass)
    EditText etPass;
    @BindView(R.id.progressLogin)
    ProgressBar progressBar;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        ButterKnife.bind(this);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            Intent masuk = new Intent(AuthActivity.this, MainActivity.class);
            startActivity(masuk);
            finish();
        }

        Daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent daftar = new Intent(AuthActivity.this, DaftarActivity.class);
                startActivity(daftar);
                finish();
            }
        });

        cvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = etEmail.getText().toString();
                String Pass = etPass.getText().toString();

                if (TextUtils.isEmpty(Email)) {
                    etEmail.setError("Email Harus Diisi!");
                    return;
                }

                if (TextUtils.isEmpty(Pass)) {
                    etPass.setError("Password Harus Diisi!");
                    return;
                }

                if (TextUtils.isEmpty(Email) && TextUtils.isEmpty(Pass)) {
                    Toast.makeText(AuthActivity.this, "Silahkan Masukkan Akun Anda", Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.VISIBLE);
                //Login
                firebaseAuth.signInWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(AuthActivity.this, "Login Berhasil", Toast.LENGTH_SHORT).show();
                            Intent login = new Intent(AuthActivity.this, MainActivity.class);
                            startActivity(login);
                            finish();
                        } else {
                            Toast.makeText(AuthActivity.this, "Login Gagal " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

    }
}
