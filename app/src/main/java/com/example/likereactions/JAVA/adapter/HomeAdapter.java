package com.example.likereactions.JAVA.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.likereactions.JAVA.models.DataModel;
import com.example.likereactions.R;

import java.util.ArrayList;



/**
 * Created by Arshad on 18,February,2021
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

    Context mContext;
    Activity mActivity;
    ArrayList<DataModel> arrayList;
    RecyclerView mRecyclerView;
    boolean FingerMoved;
    int MovedToReaction;

    public HomeAdapter(Context mContext, Activity mActivity, ArrayList<DataModel> arrayList, RecyclerView mRecyclerView) {
        this.mContext = mContext;
        this.mActivity = mActivity;
        this.arrayList = arrayList;
        this.mRecyclerView = mRecyclerView;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView ProPic,Pic;
        ImageButton LikeBtn,CommentBtn,ShareBtn;
        TextView Name,Description,LikeCount;

        // Reaction Layout Elements
        ImageView ReactionLike,ReactionHeart,ReactionWow;
        TextView LikeTxt,HearTxt,WowTxt;
        ConstraintLayout Frame;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ProPic = itemView.findViewById(R.id.profile_image);
            Pic = itemView.findViewById(R.id.recycler_Img);
            LikeBtn = itemView.findViewById(R.id.LikeBtn);
            CommentBtn = itemView.findViewById(R.id.CommentBtn);
            ShareBtn = itemView.findViewById(R.id.ShareBtn);
            Name = itemView.findViewById(R.id.profile_name);
            Description = itemView.findViewById(R.id.recycler_Dscr);
            LikeCount = itemView.findViewById(R.id.LikeCount);

            ReactionLike = itemView.findViewById(R.id.ReactionLike);
            ReactionHeart = itemView.findViewById(R.id.ReactionHeart);
            ReactionWow = itemView.findViewById(R.id.ReactionInLove);
            LikeTxt = itemView.findViewById(R.id.LikeTxt);
            HearTxt = itemView.findViewById(R.id.HeartTxt);
            WowTxt = itemView.findViewById(R.id.WowTxt);
            Frame = itemView.findViewById(R.id.ReactionFrame);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Name.setText(arrayList.get(position).getName());
        holder.Description.setText(arrayList.get(position).getDescription());
        holder.LikeCount.setText(String.valueOf(arrayList.get(position).getLikeCount()));

        setBtnState(holder,position);

        Glide.with(mContext)
                .asBitmap()
                .load(arrayList.get(position).getProImage())
                .into(holder.ProPic);

        Glide.with(mContext)
                .asBitmap()
                .load(arrayList.get(position).getImgUrl())
                .into(holder.Pic);

        holder.LikeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReactionLayout(holder);

                String IsLike = arrayList.get(position).getIsLiked();
                String IsReaction = arrayList.get(position).getReactionType();

                if (arrayList.get(position).getIsLiked().equals("0")) {
                    LikeUserPost(IsLike, IsReaction, "1", position);
                }else {
                    int LikeCount = arrayList.get(position).getLikeCount();
                    LikeCount--;

                    arrayList.get(position).setIsLiked("0");
                    arrayList.get(position).setReactionType("");
                    arrayList.get(position).setLikeCount(LikeCount);
                    notifyItemChanged(position);
                }
            }
        });

        holder.ReactionLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReactionLayout(holder);

                String IsLike = arrayList.get(position).getIsLiked();
                String IsReaction = arrayList.get(position).getReactionType();
                LikeUserPost(IsLike,IsReaction,"1",position);
            }
        });

        holder.ReactionHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReactionLayout(holder);

                String IsLike = arrayList.get(position).getIsLiked();
                String IsReaction = arrayList.get(position).getReactionType();
                LikeUserPost(IsLike,IsReaction,"2",position);
            }
        });

        holder.ReactionWow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HideReactionLayout(holder);

                String IsLike = arrayList.get(position).getIsLiked();
                String IsReaction = arrayList.get(position).getReactionType();
                LikeUserPost(IsLike,IsReaction,"3",position);
            }
        });

        holder.LikeBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ShowReactionFrame(holder);
                ListenForGesture(holder,position);
                return true;
            }
        });

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    HideReactionLayout(holder);
                } else if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    HideReactionLayout(holder);
                } else {
                    HideReactionLayout(holder);
                }
            }
        });

    }

    @SuppressLint("ClickableViewAccessibility")
    private void ListenForGesture(MyViewHolder holder, int position) {

        holder.LikeBtn.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int X = (int) event.getX();
                int Y = (int) event.getY();
                int EventAction = event.getAction();

                switch (EventAction){
                    case MotionEvent.ACTION_DOWN:
                        FingerMoved = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //Log.d("MOVEMENT","X: "+X+" Y: "+Y);
                        if (X >= 200 && X <= 250){
                            FingerMoved = true;
                            MovedToReaction = 1;
                            PopUpAnimation(holder,MovedToReaction);

                        }else if (X >= 300 && X <= 380){
                            FingerMoved = true;
                            MovedToReaction = 2;
                            PopUpAnimation(holder, MovedToReaction);

                        }else if (X >= 430){
                            FingerMoved = true;
                            MovedToReaction = 3;
                            PopUpAnimation(holder, MovedToReaction);

                        }

                        break;
                    case MotionEvent.ACTION_UP:
                        if (FingerMoved){
                            HideReactionLayout(holder);
                            FingerMoved = false;

                            String IsLike = arrayList.get(position).getIsLiked();
                            String IsReaction = arrayList.get(position).getReactionType();

                            switch (MovedToReaction){
                                case 1:
                                    LikeUserPost(IsLike,IsReaction,"1",position);
                                    break;
                                case 2:
                                    LikeUserPost(IsLike,IsReaction,"2",position);
                                    break;
                                case 3:
                                    LikeUserPost(IsLike,IsReaction,"3",position);
                                    break;

                            }
                        }
                        break;

                }

                return false;
            }
        });
    }

    private void PopUpAnimation(MyViewHolder holder, int Reaction) {
        switch (Reaction){
            case 1:
                holder.ReactionLike.animate().translationYBy(0).translationY(-50).setDuration(100);
                holder.ReactionHeart.animate().translationYBy(50).translationY(0).setDuration(100);
                holder.ReactionWow.animate().translationYBy(50).translationY(0).setDuration(100);

                holder.LikeTxt.animate().alpha(1f).translationYBy(0).translationY(-50).setDuration(100);
                holder.WowTxt.animate().alpha(0f).translationYBy(50).translationY(0).setDuration(100);
                holder.HearTxt.animate().alpha(0f).translationYBy(50).translationY(0).setDuration(100);
                break;

            case 2:
                holder.ReactionLike.animate().translationYBy(50).translationY(0).setDuration(100);
                holder.ReactionHeart.animate().translationYBy(0).translationY(-50).setDuration(100);
                holder.ReactionWow.animate().translationYBy(50).translationY(0).setDuration(100);

                holder.LikeTxt.animate().alpha(0f).translationYBy(50).translationY(0).setDuration(100);
                holder.HearTxt.animate().alpha(1f).translationYBy(0).translationY(-50).setDuration(100);
                holder.WowTxt.animate().alpha(0f).translationYBy(50).translationY(0).setDuration(100);
                break;
            case 3:
                holder.ReactionLike.animate().translationYBy(50).translationY(0).setDuration(100);
                holder.ReactionHeart.animate().translationYBy(50).translationY(0).setDuration(100);
                holder.ReactionWow.animate().translationYBy(0).translationY(-50).setDuration(100);

                holder.LikeTxt.animate().alpha(0f).translationYBy(50).translationY(0).setDuration(100);
                holder.HearTxt.animate().alpha(0f).translationYBy(50).translationY(0).setDuration(100);
                holder.WowTxt.animate().alpha(1f).translationYBy(0).translationY(-50).setDuration(100);
                break;
        }
    }

    private void LikeUserPost(String isLike,String isReaction, String reaction, int position) {
        if (isLike.equals("0") && isReaction.equals("")){ // No Like No Reaction

            int LikeCount = arrayList.get(position).getLikeCount();
            LikeCount++;

            switch (reaction){
                case "1":
                    arrayList.get(position).setIsLiked("1");
                    arrayList.get(position).setReactionType("1");
                    arrayList.get(position).setLikeCount(LikeCount);
                    notifyItemChanged(position);
                    break;
                case "2":
                    arrayList.get(position).setIsLiked("1");
                    arrayList.get(position).setReactionType("2");
                    arrayList.get(position).setLikeCount(LikeCount);
                    notifyItemChanged(position);
                    break;
                case "3":
                    arrayList.get(position).setIsLiked("1");
                    arrayList.get(position).setReactionType("3");
                    arrayList.get(position).setLikeCount(LikeCount);
                    notifyItemChanged(position);
                    break;
            }
        }else if (isLike.equals("1") && !isReaction.equals("")){ // Already Liked but to change the reaction type
            switch (reaction){
                case "1":
                    arrayList.get(position).setIsLiked("1");
                    arrayList.get(position).setReactionType("1");
                    notifyItemChanged(position);
                    break;
                case "2":
                    arrayList.get(position).setIsLiked("1");
                    arrayList.get(position).setReactionType("2");
                    notifyItemChanged(position);
                    break;
                case "3":
                    arrayList.get(position).setIsLiked("1");
                    arrayList.get(position).setReactionType("3");
                    notifyItemChanged(position);
                    break;
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void setBtnState(MyViewHolder holder, int position) {

        if (arrayList.get(position).getIsLiked().equals("1")){
            switch (arrayList.get(position).getReactionType()){
                case "":
                case "1":
                    holder.LikeBtn.setImageDrawable(mContext.getDrawable(R.drawable.like));
                    break;
                case "2":
                    holder.LikeBtn.setImageDrawable(mContext.getDrawable(R.drawable.heart));
                    break;
                case "3":
                    holder.LikeBtn.setImageDrawable(mContext.getDrawable(R.drawable.in_love));
                    break;
            }
        }else {
            holder.LikeBtn.setImageDrawable(mContext.getDrawable(R.drawable.unlike));
        }
    }

    private void HideReactionLayout(MyViewHolder holder) {
        holder.Frame
                .animate()
                .alpha(0f)
                .translationYBy(50)
                .translationY(0)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        holder.Frame.setVisibility(View.INVISIBLE);
                    }
                });

        holder.ReactionLike.animate().translationYBy(50).translationY(0).setDuration(100);
        holder.ReactionHeart.animate().translationYBy(50).translationY(0).setDuration(100);
        holder.ReactionWow.animate().translationYBy(50).translationY(0).setDuration(100);

        holder.LikeTxt.animate().alpha(0f).setDuration(0);
        holder.WowTxt.animate().alpha(0f).setDuration(0);
        holder.HearTxt.animate().alpha(0f).setDuration(0);

        holder.LikeTxt.setAlpha(0f);
        holder.WowTxt.setAlpha(0f);
        holder.HearTxt.setAlpha(0f);
    }

    private void ShowReactionFrame(MyViewHolder holder) {
        holder.Frame
                .animate()
                .alpha(1f)
                .translationYBy(0)
                .translationY(-50)
                .setDuration(100)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        holder.Frame.setVisibility(View.VISIBLE);
                    }
                });

        holder.ReactionLike.animate().translationYBy(50).translationY(0).setDuration(100);
        holder.ReactionHeart.animate().translationYBy(50).translationY(0).setDuration(100);
        holder.ReactionWow.animate().translationYBy(50).translationY(0).setDuration(100);

        holder.LikeTxt.animate().translationYBy(50).translationY(0).setDuration(100);
        holder.WowTxt.animate().translationYBy(50).translationY(0).setDuration(100);
        holder.HearTxt.animate().translationYBy(50).translationY(0).setDuration(100);

        holder.LikeTxt.setAlpha(0f);
        holder.WowTxt.setAlpha(0f);
        holder.HearTxt.setAlpha(0f);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

}
