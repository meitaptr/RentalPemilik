package com.example.meita.rentalpemilik.MenuManajemenKendaraan;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by meita on 13/06/2017.
 */

public class MenuManajemenKendaraanFragment extends Fragment {

    public static final String idKendaraan = "idKendaraan";
    private static final String TAG = MenuManajemenKendaraanFragment.class.getSimpleName();
    private RecyclerView recyclerView;
    private MenuManajemenKendaraanAdapter adapter;
    private DatabaseReference mDatabase;
    private FirebaseDatabase databaseRental;
    private ProgressDialog progressDialog;
    private List<KendaraanModel> dataKendaraan;
    private Activity activity;

    private List<KendaraanReference> kendaraanReference = new ArrayList<>();

    private FirebaseAuth auth;
    private String userID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getActivity().setTitle("Manajemen Kendaraan");
        View v = inflater.inflate(R.layout.fragment_menu_manajemen_kendaraan, container, false);
        FloatingActionButton fab_tambah_kendaraan = (FloatingActionButton) v.findViewById(R.id.fab);
        recyclerView = (RecyclerView) v.findViewById(R.id.listViewKendaraan);
        recyclerView.setHasFixedSize(true);

        final FragmentActivity c = getActivity();
        LinearLayoutManager layoutManager = new LinearLayoutManager(c);
        recyclerView.setLayoutManager(layoutManager);

        dataKendaraan = new ArrayList<>();

        mDatabase = FirebaseDatabase.getInstance().getReference();


        Firebase.setAndroidContext(getActivity());

        fab_tambah_kendaraan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // INI ERROR DISINI (SOLVED!!) utk intent dari fragment ke fragment
                /*FragmentTransaction transaction = getFragmentManager().beginTransaction();
                MenuKategoriTambahKendaraan tambah_kendaraan = new MenuKategoriTambahKendaraan();
                transaction.replace(R.id.content_frame, tambah_kendaraan).commit(); */

                //coba intent ke main activity, soalnya coding di fragment beda
                Intent a = new Intent(getActivity(), MenuTambahKendaraan.class);
                startActivity(a);
            }
        });

        getIndexKendaraan();

        return v;
    }

    public void getIndexKendaraan() {
        auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        userID = user.getUid();
        getPostKendaraan(user.getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                try {
                    if (dataSnapshot != null) {
                        KendaraanReference kendaraanRef = dataSnapshot.getValue(KendaraanReference.class);
                        if (!kendaraanReference.contains(kendaraanRef)) {
                            kendaraanReference.add(kendaraanRef);
                            adapter = new MenuManajemenKendaraanAdapter(activity, getActivity(), kendaraanReference);
                            recyclerView.setAdapter(adapter);
                        }
                    }
                } catch (Exception e) {
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public Query getPostKendaraan(String userID) {
        Query query = mDatabase.child("rentalKendaraan").child(userID).child("kendaraan");
        return query;
    }
}