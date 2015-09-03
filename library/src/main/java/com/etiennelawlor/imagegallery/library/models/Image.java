package com.etiennelawlor.imagegallery.library.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Yesid Lazaro on 9/1/15.
 */
public class Image implements Parcelable{
    private boolean fromInternet;
    private String url;

    public Image(boolean fromInternet, String url) {
        this.fromInternet = fromInternet;
        this.url = url;
    }

    public boolean isFromInternet() {
        return fromInternet;
    }

    public void setIsFromInternet(boolean fromInternet) {
        this.fromInternet = fromInternet;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
    protected Image(Parcel in) {
        fromInternet = in.readByte() != 0x00;
        url = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (fromInternet ? 0x01 : 0x00));
        dest.writeString(url);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel in) {
            return new Image(in);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };

}
