package com.rizal.pegawaique.ui.home;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.rizal.pegawaique.AuthActivity;
import com.rizal.pegawaique.CheckCovidActivity;
import com.rizal.pegawaique.R;
import com.rizal.pegawaique.SetMapsActivity;
import com.rizal.pegawaique.VerifikasiAbsenActivity;
import com.rizal.pegawaique.VerifikasiPulangActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HomeFragment extends Fragment {
    @BindView(R.id.namaPegawai)
    TextView txtNamaPegawai;
    @BindView(R.id.waktuDunia)
    TextView txtWaktu;
    @BindView(R.id.imagesetMaps)
    ImageView ivMaps;
    @BindView(R.id.cvViewVirus)
    CardView viewVirus;
    @BindView(R.id.lrMasuk)
    LinearLayout absenMasuk;
    @BindView(R.id.lrPulang)
    LinearLayout absenPulang;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, root);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout logout = view.findViewById(R.id.logout);
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        final DocumentReference documentReference = firebaseFirestore.collection("Pegawai").document(userID);
        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable DocumentSnapshot documentSnapshot, @javax.annotation.Nullable FirebaseFirestoreException e) {
                txtNamaPegawai.setText(documentSnapshot.getString("namaPegawai"));
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        viewVirus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), CheckCovidActivity.class));
            }
        });

        absenMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VerifikasiAbsenActivity.class));
            }
        });

        absenPulang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), VerifikasiPulangActivity.class));
            }
        });

        Date waktu = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEEE MMMM yyyy");
        txtWaktu.setText(simpleDateFormat.format(waktu));

        ivMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent maps = new Intent(getActivity(), SetMapsActivity.class);
                startActivity(maps);
                getActivity().finish();
            }
        });

    }

    private void showDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setTitle("Yakin Anda Ingin Keluar?");
        alertDialogBuilder
                .setIcon(R.drawable.logout)
                .setCancelable(false)
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseAuth.getInstance().signOut();
                        Intent logout = new Intent(getActivity(), AuthActivity.class);
                        startActivity(logout);
                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }



}
