package com.example.ayurveda;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public class assignment4 extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment4);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_assign4);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        getDataFromApi();
    }

    private void getDataFromApi(){
        try{

            showProgressBar();
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .addInterceptor(new HttpLoggingInterceptor())
                    .callTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://raplherbal.com/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            ApiService service = retrofit.create(ApiService.class);
            Call<List<ApiResponse>> callApiUrl = service.getServerData();
            callApiUrl.enqueue(new Callback<List<ApiResponse>>() {
                @Override
                public void onResponse(@NotNull Call<List<ApiResponse>> call, @NotNull Response<List<ApiResponse>> response) {
                    dismissProgressBar();
                    if(response.code() == 200){
                        List<ApiResponse> apiResponse = response.body();
                        recyclerView.setLayoutManager(new GridLayoutManager(assignment4.this, 3));
                        recyclerViewAdapter = new RecyclerViewAdapter(apiResponse);
                        recyclerView.setAdapter(recyclerViewAdapter);
                        recyclerViewAdapter.notifyDataSetChanged();
                    }
                }

                @Override
                public void onFailure(@NotNull Call<List<ApiResponse>> call, @NotNull Throwable t) {
                    dismissProgressBar();
                    t.printStackTrace();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        private final List<ApiResponse> localDataSet;

        public class ViewHolder extends RecyclerView.ViewHolder {
            private final TextView textView;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);

                imageView = (ImageView) view.findViewById(R.id.imageView_assign4);
                textView = (TextView) view.findViewById(R.id.textView_assign4);
            }

            public ImageView getImageView() {
                return imageView;
            }

            public TextView getTextView() {
                return textView;
            }
        }

        public RecyclerViewAdapter(List<ApiResponse> dataSet) {
            localDataSet = dataSet;
        }

        @NotNull
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            // Create a new view, which defines the UI of the list item
            View view = LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.layout_assignment4, viewGroup, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {

            viewHolder.getTextView().setText(localDataSet.get(position).category);
            Glide
                .with(assignment4.this)
                .load(localDataSet.get(position).imageurl)
                .centerCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(viewHolder.getImageView());
        }

        @Override
        public int getItemCount() {
            return localDataSet.size();
        }
    }

    interface ApiService {

        @Headers("Accept: application/json")
        @GET("api/Category/")
        Call<List<ApiResponse>> getServerData();

    }

    public static class ApiResponse {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("category")
        @Expose
        private String category;
        @SerializedName("imageurl")
        @Expose
        private String imageurl;
        @SerializedName("bannerImageurl")
        @Expose
        private String bannerImageurl;
        @SerializedName("description")
        @Expose
        private String description;

        public ApiResponse(String id, String category, String imageurl, String bannerImageurl, String description) {
            super();
            this.id = id;
            this.category = category;
            this.imageurl = imageurl;
            this.bannerImageurl = bannerImageurl;
            this.description = description;
        }
    }

    private void showProgressBar() {
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void dismissProgressBar() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
    }
}