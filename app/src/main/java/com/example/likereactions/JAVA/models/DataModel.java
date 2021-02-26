package com.example.likereactions.JAVA.models;

/**
 * Created by Arshad on 18,February,2021
 */
public class DataModel {
    String Name;
    String ProImage;
    String Description;
    int LikeCount;
    String ImgUrl;
    String IsLiked;
    String ReactionType;

    public DataModel(String name, String proImage, String description, int likeCount, String imgUrl, String isLiked, String reactionType) {
        Name = name;
        ProImage = proImage;
        Description = description;
        LikeCount = likeCount;
        ImgUrl = imgUrl;
        IsLiked = isLiked;
        ReactionType = reactionType;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getProImage() {
        return ProImage;
    }

    public void setProImage(String proImage) {
        ProImage = proImage;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public int getLikeCount() {
        return LikeCount;
    }

    public void setLikeCount(int likeCount) {
        LikeCount = likeCount;
    }

    public String getImgUrl() {
        return ImgUrl;
    }

    public void setImgUrl(String imgUrl) {
        ImgUrl = imgUrl;
    }

    public String getIsLiked() {
        return IsLiked;
    }

    public void setIsLiked(String isLiked) {
        IsLiked = isLiked;
    }

    public String getReactionType() {
        return ReactionType;
    }

    public void setReactionType(String reactionType) {
        ReactionType = reactionType;
    }
}
