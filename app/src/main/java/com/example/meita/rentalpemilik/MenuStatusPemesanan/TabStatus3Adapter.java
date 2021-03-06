package com.example.meita.rentalpemilik.MenuStatusPemesanan;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.MenuManajemenKendaraan.ImageLoader;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PenyewaanModel;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

/**
 * Created by meita on 10/28/2017.
 */

public class TabStatus3Adapter extends RecyclerView.Adapter<TabStatus3Adapter.ViewHolder> implements View.OnClickListener {
    private List<PenyewaanModel> penyewaanModel;
    DatabaseReference mDatabase;
    Context context;
    String idPenyewaan, tglSewa, tglKembali;

    public TabStatus3Adapter(Context context, List<PenyewaanModel> penyewaanModel) {
        this.penyewaanModel = penyewaanModel;
        this.context = context;
    }


    @Override
    public TabStatus3Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_tab_status3, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final TabStatus3Adapter.ViewHolder holder, int position) {
        final PenyewaanModel dataPemesanan = penyewaanModel.get(position);
        final String kategoriKendaraan = dataPemesanan.getKategoriKendaraan();
        final String idKendaraan = dataPemesanan.getIdKendaraan();
        final String idRental = dataPemesanan.getIdRental();
        final String idPelanggan = dataPemesanan.getIdPelanggan();
        final String statusPemesanan = dataPemesanan.getstatusPenyewaan();
        idPenyewaan = dataPemesanan.getidPenyewaan();
        getTanggal();
        holder.textViewStatusPemesanan.setText(dataPemesanan.getstatusPenyewaan());
        holder.textViewTglSewa.setText(dataPemesanan.getTglSewa());
        holder.textViewTglKembali.setText(dataPemesanan.getTglKembali());
        holder.textViewTotalPembayaran.setText("Rp. "+ BaseActivity.rupiah().format(dataPemesanan.getTotalBiayaPembayaran()));
        holder.tglBuatPenyewaan.setText(dataPemesanan.getTglPembuatanPenyewaan());
        holder.setClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, DetailPemesananStatus3.class);
                    bundle.putString("idPenyewaan", dataPemesanan.getidPenyewaan());
                    bundle.putString("idKendaraan", idKendaraan);
                    bundle.putString("idRental", idRental);
                    bundle.putString("idPelanggan", idPelanggan);
                    bundle.putString("kategoriKendaraan", kategoriKendaraan);
                    bundle.putString("statusPemesanan", statusPemesanan);
                    bundle.putString("tglSewa", tglSewa);
                    bundle.putString("tglKembali", tglKembali);
                    bundle.putString("statusPenyewaan", "berhasil");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                } else {
                    Bundle bundle = new Bundle();
                    Intent intent = new Intent(context, DetailPemesananStatus3.class);
                    bundle.putString("idPenyewaan", dataPemesanan.getidPenyewaan());
                    bundle.putString("idKendaraan", idKendaraan);
                    bundle.putString("idRental", idRental);
                    bundle.putString("idPelanggan", idPelanggan);
                    bundle.putString("kategoriKendaraan", kategoriKendaraan);
                    bundle.putString("statusPemesanan", statusPemesanan);
                    bundle.putString("tglSewa", tglSewa);
                    bundle.putString("tglKembali", tglKembali);
                    bundle.putString("statusPenyewaan", "berhasil");
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            }
        });
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("kendaraan").child(kategoriKendaraan).child(idKendaraan).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                holder.textViewTipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
                //Glide.with(context).load(dataKendaraan.getUriFotoKendaraan()).into(holder.fotoKendaraan);
                ImageLoader.getInstance().loadImageOther(context, dataKendaraan.getUriFotoKendaraan().get(0), holder.fotoKendaraan);
                if (dataKendaraan.isSupir() == true ) {
                    holder.textViewDenganSupir.setVisibility(View.VISIBLE);
                    holder.checkListDenganSupir.setVisibility(View.VISIBLE);
                    holder.textViewTanpaSupir.setVisibility(View.GONE);
                    holder.checkListTanpaSupir.setVisibility(View.GONE);
                } else {
                    holder.textViewDenganSupir.setVisibility(View.GONE);
                    holder.checkListDenganSupir.setVisibility(View.GONE);
                    holder.textViewTanpaSupir.setVisibility(View.VISIBLE);
                    holder.checkListTanpaSupir.setVisibility(View.VISIBLE);
                }

                if (dataKendaraan.isBahanBakar() == true ) {
                    holder.textViewDenganBBM.setVisibility(View.VISIBLE);
                    holder.checkListDenganBBM.setVisibility(View.VISIBLE);
                    holder.textViewTanpaBBM.setVisibility(View.GONE);
                    holder.checkListTanpaBBM.setVisibility(View.GONE);
                } else {
                    holder.textViewDenganBBM.setVisibility(View.GONE);
                    holder.checkListDenganBBM.setVisibility(View.GONE);
                    holder.textViewTanpaBBM.setVisibility(View.VISIBLE);
                    holder.checkListTanpaBBM.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        mDatabase.child("rentalKendaraan").child(idRental).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                holder.textViewNamaRental.setText(dataRental.getNama_rental());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void getTanggal() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        try {
            mDatabase.child("penyewaanKendaraan").child("berhasil").child(idPenyewaan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                        tglSewa = dataPemesanan.getTglSewa();
                        tglKembali = dataPemesanan.getTglKembali();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    @Override
    public int getItemCount() {
        return penyewaanModel.size();
    }

    @Override
    public void onClick(View v) {

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        private ItemClickListener clickListener;
        public ImageView fotoKendaraan, checkListDenganSupir, checkListTanpaSupir, checkListDenganBBM, checkListTanpaBBM;
        public TextView textViewStatusPemesanan, textViewTglSewa, textViewTglKembali, textViewTipeKendaraan,
                textViewNamaRental, textViewDenganSupir, textViewTanpaSupir, textViewDenganBBM,
                textViewTanpaBBM, textViewTotalPembayaran, tglBuatPenyewaan;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setTag(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            fotoKendaraan = (ImageView)itemView.findViewById(R.id.imageViewFotoKendaraan);
            checkListDenganSupir = (ImageView)itemView.findViewById(R.id.icCheckListDenganSupir);
            checkListTanpaSupir = (ImageView)itemView.findViewById(R.id.icCheckListTanpaSupir);
            checkListDenganBBM = (ImageView)itemView.findViewById(R.id.icCheckListDenganBBM);
            checkListTanpaBBM = (ImageView)itemView.findViewById(R.id.icCheckListTanpaBBM);
            textViewStatusPemesanan = (TextView)itemView.findViewById(R.id.textViewStatusPemesanan);
            textViewTglSewa = (TextView)itemView.findViewById(R.id.textViewTglSewa);
            textViewTglKembali = (TextView)itemView.findViewById(R.id.textViewTglKembali);
            textViewTipeKendaraan = (TextView)itemView.findViewById(R.id.textViewTipeKendaraan);
            textViewNamaRental = (TextView)itemView.findViewById(R.id.textViewRentalKendaraan);
            textViewDenganSupir = (TextView)itemView.findViewById(R.id.textViewDenganSupir);
            textViewTanpaSupir = (TextView)itemView.findViewById(R.id.textViewTanpaSupir);
            textViewDenganBBM = (TextView)itemView.findViewById(R.id.textViewDenganBBM);
            textViewTanpaBBM = (TextView)itemView.findViewById(R.id.textViewTanpaBBM);
            textViewTotalPembayaran = (TextView)itemView.findViewById(R.id.textViewTotalPembayaran);
            tglBuatPenyewaan = (TextView)itemView.findViewById(R.id.tglBuatPenyewaan);
        }

        public void setClickListener(ItemClickListener itemClickListener) {
            this.clickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(view, getPosition(), false);
        }

        @Override
        public boolean onLongClick(View view) {
            clickListener.onClick(view, getPosition(), true);
            return true;
        }
    }
}
