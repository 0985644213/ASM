package com.example.asm.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.asm.R;
import com.example.asm.adapter.HourAdapter;
import com.example.asm.model.Weather;
import com.example.asm.network.ApiManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private RecyclerView rvHour;
    private TextView tvTem;
    private TextView tvStatus;
    List<Weather> listWeather;
    HourAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvTem = (TextView) findViewById(R.id.tvTem);
        tvStatus = (TextView) findViewById(R.id.tvStatus);

        getHours();
        listWeather = new ArrayList<>();

        adapter = new HourAdapter(MainActivity.this, listWeather);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        rvHour = findViewById(R.id.rvHour);
        rvHour.setLayoutManager(layoutManager);
        rvHour.setAdapter(adapter);
    }

    private void getHours(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiManager.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiManager service =retrofit.create(ApiManager.class);

        service.gethour().enqueue(new Callback<List<Weather>>(){
            @Override
            public void onResponse(Call<List<Weather>> call, Response<List<Weather>> response){
                if (response.body()==null) return;

                listWeather = response.body();

                adapter.reloadData(listWeather);

                Weather weather = listWeather.get(0);
                tvTem.setText(weather.getTemperature().getValue().intValue()+"°");
                tvStatus.setText(weather.getIconPhrase());

            }

            @Override
            public void onFailure(Call<List<Weather>> call, Throwable t) {
                Log.d("ERORR", "E", t);
            }

        });

    }

}