package com.example.likereactions.KOTLIN.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.likereactions.KOTLIN.dataclass.DataClass
import com.example.likereactions.R

/**
 * Created by Arshad on 24,February,2021
 */
class HomeAdapter(private val mContext: Context, val mActivity: Activity, private val arrayList: ArrayList<DataClass>, val mRecyclerView: RecyclerView): RecyclerView.Adapter<HomeAdapter.MyViewHolder>() {

    var FingerMoved = false
    var MovedToReaction = 0

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var ProPic = itemView.findViewById<ImageView>(R.id.profile_image)
        var Pic = itemView.findViewById<ImageView>(R.id.recycler_Img)
        var LikeBtn = itemView.findViewById<ImageButton>(R.id.LikeBtn)
        var CommentBtn = itemView.findViewById<ImageButton>(R.id.CommentBtn)
        var ShareBtn = itemView.findViewById<ImageButton>(R.id.ShareBtn)
        var Name = itemView.findViewById<TextView>(R.id.profile_name)
        var Description = itemView.findViewById<TextView>(R.id.recycler_Dscr)
        var LikeCount = itemView.findViewById<TextView>(R.id.LikeCount)

        // Reaction Layout Elements
        var ReactionLike = itemView.findViewById<ImageView>(R.id.ReactionLike)
        var ReactionHeart = itemView.findViewById<ImageView>(R.id.ReactionHeart)
        var ReactionWow = itemView.findViewById<ImageView>(R.id.ReactionInLove)
        var LikeTxt = itemView.findViewById<TextView>(R.id.LikeTxt)
        var HeartTxt = itemView.findViewById<TextView>(R.id.HeartTxt)
        var WowTxt = itemView.findViewById<TextView>(R.id.WowTxt)
        var Frame = itemView.findViewById<ConstraintLayout>(R.id.ReactionFrame)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.recycler_layout,parent,false))
    }

    override fun getItemCount(): Int {
        return arrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Name.text = arrayList[position].Name
        holder.Description.text = arrayList[position].Description
        holder.LikeCount.text = arrayList[position].LikeCount.toString()

        setBtnState(holder,position)

        Glide.with(mContext)
                .asBitmap()
                .load(arrayList[position].ProImage)
                .into(holder.ProPic)

        Glide.with(mContext)
                .asBitmap()
                .load(arrayList[position].ImgUrl)
                .into(holder.Pic)


        holder.LikeBtn.setOnClickListener {
            HideReactionLayout(holder)

            val IsLike = arrayList[position].IsLiked
            val IsReaction = arrayList[position].ReactionType

            if (arrayList[position].IsLiked == "0"){
                LikeUserPost(IsLike,IsReaction,"1",position)
            }else{
                var LikeCount = arrayList[position].LikeCount
                LikeCount --

                arrayList[position].IsLiked = "0"
                arrayList[position].ReactionType = ""
                arrayList[position].LikeCount = LikeCount
                notifyItemChanged(position)
            }
        }

        holder.ReactionLike.setOnClickListener {
            HideReactionLayout(holder)

            val IsLike: String = arrayList[position].IsLiked
            val IsReaction: String = arrayList[position].ReactionType
            LikeUserPost(IsLike, IsReaction, "1", position)
        }

        holder.ReactionHeart.setOnClickListener {
            HideReactionLayout(holder)

            val IsLike: String = arrayList[position].IsLiked
            val IsReaction: String = arrayList[position].ReactionType
            LikeUserPost(IsLike, IsReaction, "2", position)
        }

        holder.ReactionWow.setOnClickListener {
            HideReactionLayout(holder)

            val IsLike: String = arrayList[position].IsLiked
            val IsReaction: String = arrayList[position].ReactionType
            LikeUserPost(IsLike, IsReaction, "3", position)
        }

        holder.LikeBtn.setOnLongClickListener {
            ShowReactionFrame(holder)
            ListenForGesture(holder, position)
            true
        }

        mRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                when (newState) {
                    AbsListView.OnScrollListener.SCROLL_STATE_FLING -> {
                        HideReactionLayout(holder)
                    }
                    AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL -> {
                        HideReactionLayout(holder)
                    }
                    else -> {
                        HideReactionLayout(holder)
                    }
                }
            }
        })

    }

    private fun setBtnState(holder: MyViewHolder, position: Int) {
        if (arrayList[position].IsLiked == "1") {
            when (arrayList[position].ReactionType) {
                "", "1" -> holder.LikeBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.like))
                "2" -> holder.LikeBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.heart))
                "3" -> holder.LikeBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.in_love))
            }
        } else {
            holder.LikeBtn.setImageDrawable(ContextCompat.getDrawable(mContext,R.drawable.unlike))
        }
    }

    private fun LikeUserPost(isLike: String, isReaction: String, reaction: String, position: Int) {
        if (isLike == "0" && isReaction == ""){

            var LikeCount = arrayList[position].LikeCount
            LikeCount ++

            when (reaction) {
                "1" -> {
                    arrayList[position].IsLiked = "1"
                    arrayList[position].ReactionType = "1"
                    arrayList[position].LikeCount = LikeCount
                    notifyItemChanged(position)
                }
                "2" -> {
                    arrayList[position].IsLiked = "1"
                    arrayList[position].ReactionType = "2"
                    arrayList[position].LikeCount = LikeCount
                    notifyItemChanged(position)
                }
                "3" -> {
                    arrayList[position].IsLiked = "1"
                    arrayList[position].ReactionType = "3"
                    arrayList[position].LikeCount = LikeCount
                    notifyItemChanged(position)
                }
            }

        } else if (isLike == "1" && isReaction != "") { // Already Liked but to change the reaction type
            when (reaction) {
                "1" -> {
                    arrayList[position].IsLiked = "1"
                    arrayList[position].ReactionType = "1"
                    notifyItemChanged(position)
                }
                "2" -> {
                    arrayList[position].IsLiked = "1"
                    arrayList[position].ReactionType = "2"
                    notifyItemChanged(position)
                }
                "3" -> {
                    arrayList[position].IsLiked = "1"
                    arrayList[position].ReactionType = "3"
                    notifyItemChanged(position)
                }
            }
        }
    }

    private fun HideReactionLayout(holder: MyViewHolder) {
        holder.Frame
                .animate()
                .alpha(0f)
                .translationYBy(50f)
                .translationY(0f)
                .setDuration(100)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        holder.Frame.visibility = View.INVISIBLE
                    }
                })

        holder.ReactionLike.animate().translationYBy(50f).translationY(0f).duration = 100
        holder.ReactionHeart.animate().translationYBy(50f).translationY(0f).duration = 100
        holder.ReactionWow.animate().translationYBy(50f).translationY(0f).duration = 100

        holder.LikeTxt.animate().alpha(0f).duration = 0
        holder.WowTxt.animate().alpha(0f).duration = 0
        holder.HeartTxt.animate().alpha(0f).duration =0

        holder.LikeTxt.alpha = 0f
        holder.WowTxt.alpha = 0f
        holder.HeartTxt.alpha = 0f
    }

    private fun ShowReactionFrame(holder: MyViewHolder) {
        holder.Frame
                .animate()
                .alpha(1f)
                .translationYBy(0f)
                .translationY(-50f)
                .setDuration(100)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        holder.Frame.visibility = View.VISIBLE
                    }
                })

        holder.ReactionLike.animate().translationYBy(50f).translationY(0f).duration = 100
        holder.ReactionHeart.animate().translationYBy(50f).translationY(0f).duration = 100
        holder.ReactionWow.animate().translationYBy(50f).translationY(0f).duration = 100

        holder.LikeTxt.animate().translationYBy(50f).translationY(0f).duration = 100
        holder.WowTxt.animate().translationYBy(50f).translationY(0f).duration = 100
        holder.HeartTxt.animate().translationYBy(50f).translationY(0f).duration = 100

        holder.LikeTxt.alpha = 0f
        holder.WowTxt.alpha = 0f
        holder.HeartTxt.alpha = 0f
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun ListenForGesture(holder: MyViewHolder, position: Int) {
        holder.LikeBtn.setOnTouchListener { v, event ->
            val X = event.x.toInt()
            val Y = event.y.toInt()
            val EventAction = event.action

            when (EventAction) {
                MotionEvent.ACTION_DOWN -> FingerMoved = false

                MotionEvent.ACTION_MOVE ->   //Log.d("MOVEMENT","X: "+X+" Y: "+Y);
                    if (X in 200..250) {
                        FingerMoved = true
                        MovedToReaction = 1
                        PopUpAnimation(holder, MovedToReaction)

                    } else if (X in 300..380) {
                        FingerMoved = true
                        MovedToReaction = 2
                        PopUpAnimation(holder, MovedToReaction)

                    } else if (X >= 430) {
                        FingerMoved = true
                        MovedToReaction = 3
                        PopUpAnimation(holder, MovedToReaction)

                    }

                MotionEvent.ACTION_UP -> if (FingerMoved) {
                    HideReactionLayout(holder)
                    FingerMoved = false
                    val IsLike: String = arrayList[position].IsLiked
                    val IsReaction: String = arrayList[position].ReactionType
                    when (MovedToReaction) {
                        1 -> LikeUserPost(IsLike, IsReaction, "1", position)
                        2 -> LikeUserPost(IsLike, IsReaction, "2", position)
                        3 -> LikeUserPost(IsLike, IsReaction, "3", position)
                    }
                }
            }
            false
        }

    }

    private fun PopUpAnimation(holder: HomeAdapter.MyViewHolder, Reaction: Int) {
        when (Reaction) {
            1 -> {
                holder.ReactionLike.animate().translationYBy(0f).translationY(-50f).duration = 100
                holder.ReactionHeart.animate().translationYBy(50f).translationY(0f).duration = 100
                holder.ReactionWow.animate().translationYBy(50f).translationY(0f).duration = 100
                holder.LikeTxt.animate().alpha(1f).translationYBy(0f).translationY(-50f).duration = 100
                holder.WowTxt.animate().alpha(0f).translationYBy(50f).translationY(0f).duration = 100
                holder.HeartTxt.animate().alpha(0f).translationYBy(50f).translationY(0f).duration = 100
            }
            2 -> {
                holder.ReactionLike.animate().translationYBy(50f).translationY(0f).duration = 100
                holder.ReactionHeart.animate().translationYBy(0f).translationY(-50f).duration = 100
                holder.ReactionWow.animate().translationYBy(50f).translationY(0f).duration = 100
                holder.LikeTxt.animate().alpha(0f).translationYBy(50f).translationY(0f).duration = 100
                holder.HeartTxt.animate().alpha(1f).translationYBy(0f).translationY(-50f).duration = 100
                holder.WowTxt.animate().alpha(0f).translationYBy(50f).translationY(0f).duration = 100
            }
            3 -> {
                holder.ReactionLike.animate().translationYBy(50f).translationY(0f).duration = 100
                holder.ReactionHeart.animate().translationYBy(50f).translationY(0f).duration = 100
                holder.ReactionWow.animate().translationYBy(0f).translationY(-50f).duration = 100
                holder.LikeTxt.animate().alpha(0f).translationYBy(50f).translationY(0f).duration = 100
                holder.HeartTxt.animate().alpha(0f).translationYBy(50f).translationY(0f).duration = 100
                holder.WowTxt.animate().alpha(1f).translationYBy(0f).translationY(-50f).duration = 100
            }
        }

    }
}