package ma15.brickcollector;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dan on 31/03/15.
 */
public class BrickSet implements Parcelable {
    private int id = -1;
    // mandatory
    private String title = "";

    // empty string signals no cover provided yet
    private String cover = "";
    Bitmap cover_pic = null;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public Bitmap getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(Bitmap cover_pic) {
        this.cover_pic = cover_pic;
    }

    public BrickSet() {

    }

    private BrickSet(Parcel in) {
        id = in.readInt();
        title = in.readString();
        cover = in.readString();

		/*
		 * MAGIC happens here => ....!:.asdfed.sd<q2sadaf
		 */
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeString(cover);
    }

    public static final Parcelable.Creator<BrickSet> CREATOR = new Parcelable.Creator<BrickSet>() {
        public BrickSet createFromParcel(Parcel in) {
            return new BrickSet(in);
        }

        public BrickSet[] newArray(int size) {
            return new BrickSet[size];
        }
    };

    @Override
    public String toString() {
        return "BrickSet [id=" + id + ", title=" + title + ", cover=" + cover + "]";
    }

}
