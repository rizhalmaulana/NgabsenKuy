package com.rizal.pegawaique.ui.profile;

import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.rizal.pegawaique.R;

public class ProfileFragment extends Fragment {

    @BindView(R.id.textNamaPegawai)
    TextView txtNama;
    @BindView(R.id.textViewEmail)
    TextView txtEmail;
    @BindView(R.id.textViewAlamat)
    TextView txtAlamat;
    @BindView(R.id.textViewHp)
    TextView txtNohp;
    @BindView(R.id.checkNama)
    ImageView checkNama;
    @BindView(R.id.checkAlamat)
    ImageView checkAlamat;
    @BindView(R.id.checkEmail)
    ImageView checkEmail;
    @BindView(R.id.checkHp)
    ImageView checkHp;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel dashboardViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firebaseFirestore.collection("Pegawai").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                txtNama.setText(documentSnapshot.getString("namaPegawai"));
                checkNama.setImageResource(R.drawable.ic_check);
                txtEmail.setText(documentSnapshot.getString("emailPegawai"));
                checkEmail.setImageResource(R.drawable.ic_check);
                txtAlamat.setText(documentSnapshot.getString("alamatPegawai"));
                checkAlamat.setImageResource(R.drawable.ic_check);
                txtNohp.setText(documentSnapshot.getString("phonePegawai"));
                checkHp.setImageResource(R.drawable.ic_check);
            }
        });

    }
}
