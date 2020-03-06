package com.dinosaurfactory.android;

public class Category {

    private int namecode;
    private int imageId;
    private String category;

    public static final Category[] categories = {
            new Category(R.string.dino,R.drawable.dinosaur,"dino"),
            new Category(R.string.stone,R.drawable.stone,"stone"),
            new Category(R.string.biz,R.drawable.biz_ring,"biz")
    };

    private Category(int namecode, int imageId, String category){
        this.namecode = namecode;
        this.imageId = imageId;
        this.category = category;

    }

    public int getName(){ return namecode;}
    public int getImageId(){ return imageId;}
    public String getCategory(){return category;}
}
