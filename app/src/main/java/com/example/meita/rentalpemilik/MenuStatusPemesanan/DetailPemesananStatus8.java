package com.example.meita.rentalpemilik.MenuStatusPemesanan;

import android.content.Intent;
import android.os.StrictMode;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meita.rentalpemilik.Base.BaseActivity;
import com.example.meita.rentalpemilik.MainActivity;
import com.example.meita.rentalpemilik.ProfilPelanggan.LihatProfilPelanggan;
import com.example.meita.rentalpemilik.R;
import com.example.meita.rentalpemilik.model.KendaraanModel;
import com.example.meita.rentalpemilik.model.PelangganModel;
import com.example.meita.rentalpemilik.model.PembayaranModel;
import com.example.meita.rentalpemilik.model.PenyewaanModel;
import com.example.meita.rentalpemilik.model.RentalModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;

public class DetailPemesananStatus8 extends AppCompatActivity {
    DatabaseReference mDatabase;
    TextView textViewTipeKendaraan, textViewNamaRental, textViewDenganSupir, textViewTanpaSupir,
            textViewDenganBBM, textViewTanpaBBM, textViewStatusPemesanan, textViewAreaPemakaian, textViewTotalPembayaran, textViewWaktuPenjemputan, textViewWaktuPengambilan,
            textViewWaktuPenjemputanValue, textViewWaktuPengambilanValue, textViewLokasiPenjemputan, textViewLokasiPenjemputanValue,
            textViewNamaPemesan, textViewAlamatPemesan, textViewTelponPemesan, textViewEmailPemesan;
    TextView textViewNamaRekeningPelanggan, textViewNomorRekeningPelanggan, textViewNamaBankPelanggan,
            textViewJumlahTransfer, textViewWaktuTransfer;
    TextView textViewNamaRekeningRental, textViewNomorRekeningRental, textViewNamaBankRental;
    public ImageView checkListDenganSupir, checkListTanpaSupir, checkListDenganBBM, checkListTanpaBBM, icLokasiPenjemputan;
    Button buttonLihatBuktiPembayaran, buttonKonfirmasiSisaPembayaran, btnLihatBuktiSisaPembayaran, btnLihatLokasiPenjemputan,btnLihatProfilPelanggan ;
    Date tglSewaCekSisa, tglKembaliCekSisa, tglSewaDipesan, tglKembaliDipesan;
    TextView textViewTglSewa, textViewTglKembali, textViewJumlahSewaKendaraan, textViewMobil, textViewMotor, textViewJmlHariPenyewaan;
    TextView textViewNamaRekeningPelangganSisaPembayaran,textViewNomorRekeningPelangganSisaPembayaran,
            textViewNamaBankPelangganSisaPembayaran, textViewJumlahTransferSisaPembayaran,textViewWaktuTransferSisaPembayaran,
            textViewNamaRekeningRentalSisaPembayaran, textViewNomorRekeningRentalSisaPembayaran,
            textViewNamaBankRentalSisaPembayaran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pemesanan_status8);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        textViewStatusPemesanan = (TextView)findViewById(R.id.textViewStatusPemesanan);
        textViewTipeKendaraan = (TextView)findViewById(R.id.textViewTipeKendaraan);
        textViewNamaRental = (TextView)findViewById(R.id.textViewNamaRentalKendaraan);
        textViewAreaPemakaian = (TextView)findViewById(R.id.textViewAreaPemakaian);
        textViewTotalPembayaran = (TextView)findViewById(R.id.textViewTotalPembayaran);
        textViewDenganSupir = (TextView)findViewById(R.id.textViewDenganSupir);
        textViewTanpaSupir = (TextView)findViewById(R.id.textViewTanpaSupir);
        textViewDenganBBM = (TextView)findViewById(R.id.textViewDenganBBM);
        textViewTanpaBBM = (TextView)findViewById(R.id.textViewTanpaBBM);
        textViewWaktuPenjemputan = (TextView)findViewById(R.id.textViewWaktuPenjemputan);
        textViewWaktuPenjemputanValue = (TextView)findViewById(R.id.textViewWaktuPenjemputanValue);
        textViewWaktuPengambilan = (TextView)findViewById(R.id.textViewWaktuPengambilan);
        textViewWaktuPengambilanValue = (TextView)findViewById(R.id.textViewWaktuPengambilanValue);
        textViewLokasiPenjemputan = (TextView)findViewById(R.id.textViewLokasiPenjemputan);
        textViewLokasiPenjemputanValue = (TextView)findViewById(R.id.textViewLokasiPenjemputanValue);
        textViewNamaRekeningPelanggan = (TextView)findViewById(R.id.textViewNamaRekeningPelanggan);
        textViewNomorRekeningPelanggan = (TextView)findViewById(R.id.textViewNomorRekeningPelanggan);
        textViewNamaBankPelanggan = (TextView)findViewById(R.id.textViewNamaBankPelanggan);
        textViewJumlahTransfer = (TextView)findViewById(R.id.textViewJumlahTransfer);
        textViewWaktuTransfer = (TextView)findViewById(R.id.textViewWaktuTransfer);
        textViewNamaRekeningRental = (TextView)findViewById(R.id.textViewNamaRekeningRental);
        textViewNomorRekeningRental = (TextView)findViewById(R.id.textViewNomorRekeningRental);
        textViewNamaBankRental = (TextView)findViewById(R.id.textViewNamaBankRental);
        textViewNamaPemesan = (TextView)findViewById(R.id.textViewNamaPemesan);
        textViewAlamatPemesan = (TextView)findViewById(R.id.textViewAlamatPemesan);
        textViewTelponPemesan = (TextView)findViewById(R.id.textViewTelponPemesan);
        textViewEmailPemesan = (TextView)findViewById(R.id.textViewEmailPemesan);
        btnLihatProfilPelanggan = (Button)findViewById(R.id.btnLihatProfilPelanggan);

        //text view dari pembayaran sisa
        textViewNamaRekeningPelangganSisaPembayaran = (TextView)findViewById(R.id.textViewNamaRekeningPelangganSisaPembayaran);
        textViewNomorRekeningPelangganSisaPembayaran = (TextView)findViewById(R.id.textViewNomorRekeningPelangganSisaPembayaran);
        textViewNamaBankPelangganSisaPembayaran = (TextView)findViewById(R.id.textViewNamaBankPelangganSisaPembayaran);
        textViewJumlahTransferSisaPembayaran = (TextView)findViewById(R.id.textViewJumlahTransferSisaPembayaran);
        textViewWaktuTransferSisaPembayaran = (TextView)findViewById(R.id.textViewWaktuTransferSisaPembayaran);
        textViewNamaRekeningRentalSisaPembayaran = (TextView)findViewById(R.id.textViewNamaRekeningRentalSisaPembayaran);
        textViewNomorRekeningRentalSisaPembayaran = (TextView)findViewById(R.id.textViewNomorRekeningRentalSisaPembayaran);
        textViewNamaBankRentalSisaPembayaran = (TextView)findViewById(R.id.textViewNamaBankRentalSisaPembayaran);

        buttonKonfirmasiSisaPembayaran = (Button)findViewById(R.id.buttonKonfirmasiSisaPembayaran);
        btnLihatBuktiSisaPembayaran = (Button)findViewById(R.id.btnLihatBuktiSisaPembayaran);

        textViewTglSewa = (TextView)findViewById(R.id.textViewTglSewa);
        textViewTglKembali = (TextView)findViewById(R.id.textViewTglKembali);
        textViewJumlahSewaKendaraan = (TextView)findViewById(R.id.textViewJumlahSewaKendaraan);
        textViewMobil = (TextView)findViewById(R.id.textViewMobil);
        textViewMotor = (TextView)findViewById(R.id.textViewMotor);
        textViewJmlHariPenyewaan = (TextView)findViewById(R.id.textViewJmlHariPenyewaan);

        checkListDenganSupir = (ImageView)findViewById(R.id.icCheckListDenganSupir);
        checkListTanpaSupir = (ImageView)findViewById(R.id.icCheckListTanpaSupir);
        checkListDenganBBM = (ImageView)findViewById(R.id.icCheckListDenganBBM);
        checkListTanpaBBM = (ImageView)findViewById(R.id.icCheckListTanpaBBM);
        icLokasiPenjemputan = (ImageView)findViewById(R.id.icLokasiPenjemputan);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        buttonLihatBuktiPembayaran = (Button) findViewById(R.id.btnLihatBuktiPembayaran);
        buttonLihatBuktiPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
                final String statusPenyewaan = getIntent().getStringExtra("statusPenyewaan");
                Bundle bundle = new Bundle();
                Intent intent = new Intent(DetailPemesananStatus8.this, GambarBuktiPembayaran.class);
                bundle.putString("idPenyewaan", idPenyewaan);
                bundle.putString("statusPenyewaan", statusPenyewaan);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        buttonKonfirmasiSisaPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                konfirmasiSisaPembayaran();
            }
        });

        btnLihatLokasiPenjemputan = (Button)findViewById(R.id.btnLihatLokasiPenjemputan);
        btnLihatLokasiPenjemputan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
                Intent intent = new Intent(DetailPemesananStatus8.this, PetaLokasiPenjemputan.class);
                intent.putExtra("idPenyewaan", idPenyewaan);
                intent.putExtra("statusPenyewaan", "menungguKonfirmasiSisaPembayaran");
                startActivity(intent);
            }
        });

        btnLihatBuktiSisaPembayaran.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lihatBuktiSisaPembayaran();
            }
        });

        btnLihatProfilPelanggan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String idPelanggan = getIntent().getStringExtra("idPelanggan");
                Intent intent = new Intent(DetailPemesananStatus8.this, LihatProfilPelanggan.class);
                intent.putExtra("idPelanggan", idPelanggan);
                startActivity(intent);
            }
        });

        infoKendaraan();
        infoRentalKendaraan();
        infoPelanggan();
        infoPenyewaan();
        infoPembayaran();
        infoSisaPembayaran();
    }

    public void konfirmasiSisaPembayaran() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
        final String status = "Berhasil";

        mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiSisaPembayaran").child(idPenyewaan).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                mDatabase.child("penyewaanKendaraan").child("berhasil").child(idPenyewaan).setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                        mDatabase.child("penyewaanKendaraan").child("berhasil").child(idPenyewaan).child("statusPenyewaan").setValue(status);
                        mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiSisaPembayaran").child(idPenyewaan).removeValue();
                        Toast.makeText(getApplicationContext(), "Konfirmasi Sisa Pembayaran Berhasil", Toast.LENGTH_LONG).show();
                        buatPemberitahuan();
                        Intent intent = new Intent(DetailPemesananStatus8.this, MainActivity.class);
                        intent.putExtra("halamanStatusKonfirmasiPesanan", 2);
                        startActivity(intent);
                        finish();
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void buatPemberitahuan() {
        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
        final String tglSewa = getIntent().getStringExtra("tglSewa");
        final String tglKembali = getIntent().getStringExtra("tglKembali");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        try {
            // JSON here
            String jsonResponse;

            URL url = new URL("https://onesignal.com/api/v1/notifications");
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setUseCaches(false);
            con.setDoOutput(true);
            con.setDoInput(true);

            con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            con.setRequestProperty("Authorization", "Basic MWRlZjUzNzUtMjMwMS00NDQxLTgyMDEtYThhNmU0MDlmNTg5");
            con.setRequestMethod("POST");

            String strJsonBody = "{"
                    +   "\"app_id\": \"8d59b6c9-1cd7-4c76-8390-38a065ac6924\","
                    +   "\"filters\": [{\"field\": \"tag\", \"key\": \"UID\", \"relation\": \"=\", \"value\": \"" + idPelanggan +"\"}],"
                    +   "\"data\": {\"statusPenyewaan\": \"berhasil\"},"
                    +   "\"headings\": {\"en\": \"Penyewaan berhasil! Sisa pembayaran telah di konfirmasi\"},"
                    +   "\"contents\": {\"en\": \"Untuk Tanggal "+tglSewa+" - "+tglKembali+"\"}"
                    + "}";

            System.out.println("strJsonBody:\n" + strJsonBody);

            byte[] sendBytes = strJsonBody.getBytes("UTF-8");
            con.setFixedLengthStreamingMode(sendBytes.length);

            OutputStream outputStream = con.getOutputStream();
            outputStream.write(sendBytes);

            int httpResponse = con.getResponseCode();
            System.out.println("httpResponse: " + httpResponse);

            if (  httpResponse >= HttpURLConnection.HTTP_OK
                    && httpResponse < HttpURLConnection.HTTP_BAD_REQUEST) {
                Scanner scanner = new Scanner(con.getInputStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            else {
                Scanner scanner = new Scanner(con.getErrorStream(), "UTF-8");
                jsonResponse = scanner.useDelimiter("\\A").hasNext() ? scanner.next() : "";
                scanner.close();
            }
            System.out.println("jsonResponse:\n" + jsonResponse);
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
//        String idPemberitahuan = mDatabase.push().getKey();
//        final String idRental = getIntent().getStringExtra("idRental");
//        final String idKendaraan = getIntent().getStringExtra("idKendaraan");
//        final String tglSewa = getIntent().getStringExtra("tglSewa");
//        final String tglKembali = getIntent().getStringExtra("tglKembali");
//        final String idPelanggan = getIntent().getStringExtra("idPelanggan");
//        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
//        //int valueHalaman1 = 0;
//        String valueHalaman1 = "berhasil";
//        String statusPemesanan1 = "Berhasil";
//        HashMap<String, Object> dataNotif = new HashMap<>();
//        dataNotif.put("idPemberitahuan", idPemberitahuan);
//        dataNotif.put("idRental", idRental);
//        dataNotif.put("idKendaraan", idKendaraan);
//        dataNotif.put("tglSewa", tglSewa);
//        dataNotif.put("tglKembalian", tglKembali);
//        dataNotif.put("nilaiHalaman", valueHalaman1);
//        dataNotif.put("statusPenyewaan", statusPemesanan1);
//        dataNotif.put("idPelanggan", idPelanggan);
//        dataNotif.put("idPenyewaan", idPenyewaan);
//        mDatabase.child("pemberitahuan").child("pelanggan").child("berhasil").child(idPelanggan).child(idPemberitahuan).setValue(dataNotif);
//        //mDatabase.child("pemberitahuan").child("rental").child("belumBayar").child(idRental).child(idPemberitahuan).child("nilaiHalaman").setValue(valueHalaman);
    }

    public void infoKendaraan() {
        try {
            final String idKendaraan = getIntent().getStringExtra("idKendaraan");
            final String kategoriKendaraan = getIntent().getStringExtra("kategoriKendaraan");
            mDatabase.child("kendaraan").child(kategoriKendaraan).child(idKendaraan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    KendaraanModel dataKendaraan = dataSnapshot.getValue(KendaraanModel.class);
                    textViewTipeKendaraan.setText(dataKendaraan.getTipeKendaraan());
                    textViewAreaPemakaian.setText(dataKendaraan.getAreaPemakaian());
                    if (dataKendaraan.isSupir() == true ) {
                        textViewDenganSupir.setVisibility(View.VISIBLE);
                        checkListDenganSupir.setVisibility(View.VISIBLE);
                        textViewTanpaSupir.setVisibility(View.GONE);
                        checkListTanpaSupir.setVisibility(View.GONE);
                    } else {
                        textViewDenganSupir.setVisibility(View.GONE);
                        checkListDenganSupir.setVisibility(View.GONE);
                        textViewTanpaSupir.setVisibility(View.VISIBLE);
                        checkListTanpaSupir.setVisibility(View.VISIBLE);
                    }

                    if (dataKendaraan.isBahanBakar() == true ) {
                        textViewDenganBBM.setVisibility(View.VISIBLE);
                        checkListDenganBBM.setVisibility(View.VISIBLE);
                        textViewTanpaBBM.setVisibility(View.GONE);
                        checkListTanpaBBM.setVisibility(View.GONE);
                    } else {
                        textViewDenganBBM.setVisibility(View.GONE);
                        checkListDenganBBM.setVisibility(View.GONE);
                        textViewTanpaBBM.setVisibility(View.VISIBLE);
                        checkListTanpaBBM.setVisibility(View.VISIBLE);
                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void infoRentalKendaraan() {
        try {
            final String idRental = getIntent().getStringExtra("idRental");
            mDatabase.child("rentalKendaraan").child(idRental).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                    textViewNamaRental.setText(dataRental.getNama_rental());

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void infoPelanggan() {
        try {
            final String idPelanggan = getIntent().getStringExtra("idPelanggan");
            mDatabase.child("pelanggan").child(idPelanggan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    PelangganModel dataPelanggan = dataSnapshot.getValue(PelangganModel.class);
                    textViewNamaPemesan.setText(dataPelanggan.getNamaLengkap());
                    textViewAlamatPemesan.setText(dataPelanggan.getAlamat());
                    textViewTelponPemesan.setText(dataPelanggan.getNoTelp());
                    textViewEmailPemesan.setText(dataPelanggan.getEmail());
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void infoPenyewaan() {
        try {
            final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiSisaPembayaran").child(idPenyewaan).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PenyewaanModel dataPemesanan = dataSnapshot.getValue(PenyewaanModel.class);
                        textViewStatusPemesanan.setText(dataPemesanan.getstatusPenyewaan());
                        textViewTotalPembayaran.setText("Rp. "+ BaseActivity.rupiah().format(dataPemesanan.getTotalBiayaPembayaran()));
                        textViewTglSewa.setText(dataPemesanan.getTglSewa());
                        textViewTglKembali.setText(dataPemesanan.getTglKembali());
                        textViewJumlahSewaKendaraan.setText(String.valueOf(dataPemesanan.getJumlahKendaraan()));
                        textViewJmlHariPenyewaan.setText(String.valueOf(dataPemesanan.getJumlahHariPenyewaan()));
                        if (dataPemesanan.getKategoriKendaraan().equals("Mobil")) {
                            textViewMobil.setVisibility(View.VISIBLE);
                            textViewMotor.setVisibility(View.GONE);
                        } else {
                            textViewMotor.setVisibility(View.VISIBLE);
                            textViewMobil.setVisibility(View.GONE);
                        }
                        if (dataPemesanan.getJamPenjemputan() == null) {
                            textViewWaktuPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPenjemputanValue.setVisibility(View.GONE);
                            textViewLokasiPenjemputan.setVisibility(View.GONE);
                            textViewLokasiPenjemputanValue.setVisibility(View.GONE);
                            icLokasiPenjemputan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setText(dataPemesanan.getJamPengambilan());
                            btnLihatLokasiPenjemputan.setVisibility(View.GONE);


                        } else {
                            textViewWaktuPengambilan.setVisibility(View.GONE);
                            textViewWaktuPengambilanValue.setVisibility(View.GONE);
                            textViewWaktuPenjemputanValue.setText(dataPemesanan.getJamPenjemputan());
                            textViewLokasiPenjemputanValue.setText(dataPemesanan.getAlamatPenjemputan());
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        } catch (Exception e) {

        }
    }

    public void infoPembayaran() {
        try {
            final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
            final String idRental = getIntent().getStringExtra("idRental");
            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiSisaPembayaran").child(idPenyewaan).child("pembayaran").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PembayaranModel dataPembayaran = dataSnapshot.getValue(PembayaranModel.class);
                        final String idRekening = dataPembayaran.getIdRekeningRental();

                        textViewNamaRekeningPelanggan.setText(dataPembayaran.getNamaPemilikRekeningPelanggan());
                        textViewNomorRekeningPelanggan.setText(dataPembayaran.getNomorRekeningPelanggan());
                        textViewNamaBankPelanggan.setText(dataPembayaran.getBankPelanggan());
                        textViewJumlahTransfer.setText(dataPembayaran.getJumlahTransfer());
                        textViewWaktuTransfer.setText(dataPembayaran.getWaktuPembayaran());
                        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                                textViewNamaRekeningRental.setText(dataRental.getNamaPemilikBank());
                                textViewNomorRekeningRental.setText(dataRental.getNomorRekeningBank());
                                textViewNamaBankRental.setText(dataRental.getNamaBank());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void infoSisaPembayaran() {
        try {
            final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
            final String idRental = getIntent().getStringExtra("idRental");
            mDatabase.child("penyewaanKendaraan").child("menungguKonfirmasiSisaPembayaran").child(idPenyewaan).child("pembayaran").child("sisaPembayaran").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        PembayaranModel dataPembayaran = dataSnapshot.getValue(PembayaranModel.class);
                        final String idRekening = dataPembayaran.getIdRekeningRental();

                        textViewNamaRekeningPelangganSisaPembayaran.setText(dataPembayaran.getNamaPemilikRekeningPelanggan());
                        textViewNomorRekeningPelangganSisaPembayaran.setText(dataPembayaran.getNomorRekeningPelanggan());
                        textViewNamaBankPelangganSisaPembayaran.setText(dataPembayaran.getBankPelanggan());
                        textViewJumlahTransferSisaPembayaran.setText(dataPembayaran.getJumlahTransfer());
                        textViewWaktuTransferSisaPembayaran.setText(dataPembayaran.getWaktuPembayaran());
                        mDatabase.child("rentalKendaraan").child(idRental).child("rekeningPembayaran").child(idRekening).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                RentalModel dataRental = dataSnapshot.getValue(RentalModel.class);
                                textViewNamaRekeningRentalSisaPembayaran.setText(dataRental.getNamaPemilikBank());
                                textViewNomorRekeningRentalSisaPembayaran.setText(dataRental.getNomorRekeningBank());
                                textViewNamaBankRentalSisaPembayaran.setText(dataRental.getNamaBank());
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } catch (Exception e) {

        }
    }

    public void lihatBuktiSisaPembayaran() {
        final String idPenyewaan = getIntent().getStringExtra("idPenyewaan");
        Bundle bundle = new Bundle();
        Intent intent = new Intent(DetailPemesananStatus8.this, GambarBuktiSisaPembayaran.class);
        bundle.putString("idPenyewaan", idPenyewaan);
        bundle.putString("statusPenyewaan", "menungguKonfirmasiSisaPembayaran");
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
