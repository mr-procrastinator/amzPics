package org.deco.amazingpics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Andrey on 6/25/15.
 */
public class FeedData {
    public String link;
    @SerializedName("images")
    public ImagesBlock imagesBlock;
   //public String caption;

    public static class ImagesBlock{
        @SerializedName("low_resolution")
        public Image lowResolution;
        @SerializedName("standard_resolution")
        public Image standardResolution;
        @Expose
        public Image thumbnail;
    }

    public static class Image{
        public int height;
        public int widht;
        public String url;
    }
}
