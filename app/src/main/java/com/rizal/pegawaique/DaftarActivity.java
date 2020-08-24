package com.rizal.pegawaique;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DaftarActivity extends AppCompatActivity {

    public static final String TAG = "TAG";
    @BindView(R.id.txtDaftar)
    TextView txtregis;
    @BindView(R.id.et_nama)
    EditText mNama;
    @BindView(R.id.et_user)
    EditText mUser;
    @BindView(R.id.et_email)
    EditText mEmail;
    @BindView(R.id.et_pass)
    EditText mPass;
    @BindView(R.id.et_phone)
    EditText mPhone;
    @BindView(R.id.et_alamat)
    EditText mAlamat;
    @BindView(R.id.cvDaftar)
    CardView cvMasuk;

    String userID;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    FirebaseFirestore firebaseFirestore;

    @BindView(R.id.progressDaftar)
    ProgressBar mprogressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daftar);
        ButterKnife.bind(this);
        overridePendingTransition(R.anim.enter_right_to_left, R.anim.exit_right_to_left);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            Intent masuk = new Intent(DaftarActivity.this, MainActivity.class);
            startActivity(masuk);
            finish();
        }

        txtregis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(DaftarActivity.this, AuthActivity.class);
                startActivity(login);
                finish();
                overridePendingTransition(R.anim.enter_left_to_right, R.anim.exit_left_to_right);
            }
        });

        cvMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Nama = mNama.getText().toString().trim();
                final String User = mUser.getText().toString().trim();
                final String Email = mEmail.getText().toString();
                final String Pass = mPass.getText().toString();
                final String Phone = mPhone.getText().toString().trim();
                final String Alamat = mAlamat.getText().toString().trim();

                if (TextUtils.isEmpty(Nama)){
                    mEmail.setError("Nama Harus Diisi!");
                    return;
                }

                if (TextUtils.isEmpty(User)){
                    mEmail.setError("Email Harus Diisi!");
                    return;
                }

                if (TextUtils.isEmpty(Email)){
                    mEmail.setError("Email Harus Diisi!");
                    return;
                }

                if (TextUtils.isEmpty(Pass)){
                    mPass.setError("Password Harus Diisi!");
                    return;
                }

                if (Pass.length() < 6){
                    mPass.setError("Password Harus Lebih Dari 6 Karakter!");
                    return;
                }

                if (TextUtils.isEmpty(Phone)){
                    mEmail.setError("Handphone Harus Diisi!");
                    return;
                }

                if (TextUtils.isEmpty(Alamat)){
                    mEmail.setError("Alamat Harus Diisi!");
                    return;
                }

                mprogressBar.setVisibility(View.VISIBLE);

                firebaseAuth.createUserWithEmailAndPassword(Email, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(DaftarActivity.this, "Pegawai Berhasil Didaftarkan", Toast.LENGTH_SHORT).show();
                            userID = firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firebaseFirestore.collection("Pegawai").document(userID);
                            Map<String, Object> pegawai = new HashMap<>();
                            pegawai.put("namaPegawai", Nama);
                            pegawai.put("userPegawai", User);
                            pegawai.put("emailPegawai", Email);
                            pegawai.put("passPegawai", Pass);
                            pegawai.put("phonePegawai", Phone);
                            pegawai.put("alamatPegawai", Alamat);
                            documentReference.set(pegawai).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: Data Pegawai Berhasil Disimpan "+ userID);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d(TAG, "onFailure: "+ e.toString());
                                }
                            });

                            Intent akun = new Intent(DaftarActivity.this, MainActivity.class);
                            startActivity(akun);
                            finish();
                        }else {
                            Toast.makeText(DaftarActivity.this, "Pegawai Gagal Didaftarkan" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            mprogressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }
}
