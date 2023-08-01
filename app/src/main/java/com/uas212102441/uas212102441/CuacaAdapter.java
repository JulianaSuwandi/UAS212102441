package com.uas212102441.uas212102441;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CuacaAdapter extends RecyclerView.Adapter<CuacaViewHolder> {

    private List<ListModel> listModelList;
    private RootModel rm;

    public CuacaAdapter(RootModel rm){
        this.rm = rm;

        listModelList = rm.getListModelList();
    }


    @NonNull
    @Override
    public CuacaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_cuaca, parent, false);
        return new CuacaViewHolder(view);}
    private double toCelcius(double kelvin) {return kelvin - 272.15;}
    public String formatNumber(double number, String format){
        DecimalFormat decimalFormat = new DecimalFormat(format);
        return decimalFormat.format(number);}
    @Override
    public void onBindViewHolder(@NonNull CuacaViewHolder holder, int position) {
        ListModel lm = listModelList.get(position);
        WeatherModel wm = lm.getWeatherModelList().get(0);
        MainModel mm = lm.getMainModel();

        String suhu = formatNumber(toCelcius(mm.getTemp_min()), "###.##") + "°C - " +
                formatNumber(toCelcius(mm.getTemp_max()),"###.##") + "°C";

        String tanggalWaktuWib = formatWib(lm.getDt_txt());

        holder.namaTextView.setText(wm.getMain());
        holder.deskripsiTextView.setText(wm.getDescription());
        holder.tglWaktuTextView.setText(tanggalWaktuWib);
        holder.suhuTextView.setText(suhu);
    }

    @Override
    public int getItemCount() {
        return (listModelList != null) ? listModelList.size() : 0;
    }
    private String formatWib(String tanggalWaktuGet_string) {
        Log.d("*tw*", "Waktu GMT :" + tanggalWaktuGet_string);

        Date tanggalWaktuGmt = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            tanggalWaktuGmt = sdf.parse(tanggalWaktuGet_string);
        }
        catch (ParseException e){
            Log.e("*tw*", e.getMessage());
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(tanggalWaktuGmt);
        calendar.add(Calendar.HOUR_OF_DAY, 7);

        Date tanggalWaktuWib = calendar.getTime();

        String tanggalWaktuWib_string = sdf.format(tanggalWaktuWib);
        tanggalWaktuWib_string = tanggalWaktuGet_string.replace("00:00", "00 WIB");

        Log.d("*tw*", "Tanggal Waktu Indonesia Barat :" + tanggalWaktuWib_string);

        return tanggalWaktuWib_string;
    }

}
