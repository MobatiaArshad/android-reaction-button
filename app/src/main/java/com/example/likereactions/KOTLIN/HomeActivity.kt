package com.example.likereactions.KOTLIN

import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.example.likereactions.KOTLIN.adapter.HomeAdapter
import com.example.likereactions.KOTLIN.dataclass.DataClass
import com.example.likereactions.R
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity() {

    lateinit var mContext: Context
    lateinit var mActivity: Activity
    lateinit var arrayList: ArrayList<DataClass>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        mContext = this
        mActivity = this

        iniUI()
    }

    private fun iniUI() {
        recycler.layoutManager = LinearLayoutManager(mContext,LinearLayoutManager.VERTICAL,false)
        setRecyclerData()
        val adapter = HomeAdapter(mContext,mActivity,arrayList,recycler)
        recycler.adapter = adapter
    }

    private fun setRecyclerData() {
        val itemAnimator: RecyclerView.ItemAnimator = recycler.itemAnimator!!
        if (itemAnimator is SimpleItemAnimator) {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        arrayList = ArrayList()

        arrayList.add(DataClass("Chris Evans",
                "https://cdn.britannica.com/28/215028-050-94E9EA1E/American-actor-Chris-Evans-2019.jpg",
                mContext.getResources().getString(R.string.description),
                256,
                "https://thumbor.forbes.com/thumbor/fit-in/1200x0/filters%3Aformat%28jpg%29/https%3A%2F%2Fblogs-images.forbes.com%2Fscottmendelson%2Ffiles%2F2018%2F04%2Fimage001.jpg",
                "0",
                ""))

        arrayList.add(DataClass("Tom cruise",
                "https://gumlet.assettype.com/freepressjournal%2Fimport%2F2017%2F10%2FTom-Cruise.jpg?auto=format%2Ccompress&w=1200",
                mContext.getResources().getString(R.string.description),
                1,
                "https://www.assignmentx.com/wp-content/uploads/2018/07/MISSION-IMPOSSIBLE-FALLOUT-movie-poster.jpg",
                "1",
                "2"))

        arrayList.add(DataClass("Keanu Reeves",
                "https://static.wikia.nocookie.net/matrix/images/5/57/Keanu-reeves-.jpeg/revision/latest/top-crop/width/360/height/450?cb=20200709213927",
                mContext.getResources().getString(R.string.description),
                99,
                "https://images-na.ssl-images-amazon.com/images/G/01/digital/video/hero/Movies/2014/JohnWick_152500700_1810080-219613._V331809634_SX1080_.jpg",
                "0",
                ""))

        arrayList.add(DataClass("Johnny Depp",
                "https://m.media-amazon.com/images/M/MV5BMTM0ODU5Nzk2OV5BMl5BanBnXkFtZTcwMzI2ODgyNQ@@._V1_.jpg",
                mContext.getResources().getString(R.string.description),
                5,
                "https://wallpaperaccess.com/full/1103973.jpg",
                "0",
                ""))

        arrayList.add(DataClass("Tom Hardy",
                "https://static.independent.co.uk/s3fs-public/thumbnails/image/2016/02/28/14/hardy-getty2.jpg",
                mContext.getResources().getString(R.string.description),
                1,
                "https://c4.wallpaperflare.com/wallpaper/1004/908/1017/movie-venom-eddie-brock-hd-wallpaper-preview.jpg",
                "1",
                "1"))
    }
}