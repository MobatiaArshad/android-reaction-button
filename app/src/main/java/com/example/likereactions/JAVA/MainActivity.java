package com.example.likereactions.JAVA;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SimpleItemAnimator;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.likereactions.JAVA.adapter.HomeAdapter;
import com.example.likereactions.JAVA.models.DataModel;
import com.example.likereactions.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Context mContext;
    Activity mActivity;
    RecyclerView mRecycler;
    ArrayList<DataModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        mActivity = this;

        IniUI();
    }

    private void IniUI() {
        mRecycler = findViewById(R.id.recycler);
        mRecycler.setLayoutManager(new LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false));
        setRecyclerData();
        HomeAdapter adapter = new HomeAdapter(mContext,mActivity,arrayList,mRecycler);
        mRecycler.setAdapter(adapter);
    }

    private void setRecyclerData() {
        RecyclerView.ItemAnimator animator = mRecycler.getItemAnimator();
        if (animator instanceof SimpleItemAnimator) {
            ((SimpleItemAnimator) animator).setSupportsChangeAnimations(false);
        }

        arrayList = new ArrayList<>();

        arrayList.add(new DataModel("Chris Evans",
                "https://cdn.britannica.com/28/215028-050-94E9EA1E/American-actor-Chris-Evans-2019.jpg",
                mContext.getResources().getString(R.string.description),
                256,
                "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fblogs-images.forbes.com%2Fscottmendelson%2Ffiles%2F2018%2F04%2Fimage001.jpg",
                "0",
                ""));

        arrayList.add(new DataModel("Tom cruise",
                "https://gumlet.assettype.com/freepressjournal%2Fimport%2F2017%2F10%2FTom-Cruise.jpg?auto=format%2Ccompress&w=1200",
                mContext.getResources().getString(R.string.description),
                1,
                "https://www.assignmentx.com/wp-content/uploads/2018/07/MISSION-IMPOSSIBLE-FALLOUT-movie-poster.jpg",
                "1",
                "2"));

        arrayList.add(new DataModel("Keanu Reeves",
                "https://static.wikia.nocookie.net/matrix/images/5/57/Keanu-reeves-.jpeg/revision/latest/top-crop/width/360/height/450?cb=20200709213927",
                mContext.getResources().getString(R.string.description),
                99,
                "https://images-na.ssl-images-amazon.com/images/G/01/digital/video/hero/Movies/2014/JohnWick_152500700_1810080-219613._V331809634_SX1080_.jpg",
                "0",
                ""));

        arrayList.add(new DataModel("Johnny Depp",
                "https://m.media-amazon.com/images/M/MV5BMTM0ODU5Nzk2OV5BMl5BanBnXkFtZTcwMzI2ODgyNQ@@._V1_.jpg",
                mContext.getResources().getString(R.string.description),
                5,
                "https://wallpaperaccess.com/full/1103973.jpg",
                "0",
                ""));

        arrayList.add(new DataModel("Tom Hardy",
                "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/02/28/14/hardy-getty2.jpg",
                mContext.getResources().getString(R.string.description),
                1,
                "https://c4.wallpaperflare.com/wallpaper/1004/908/1017/movie-venom-eddie-brock-hd-wallpaper-preview.jpg",
                "1",
                "1"));
    }
}