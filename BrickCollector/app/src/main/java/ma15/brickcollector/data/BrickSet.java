package ma15.brickcollector.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dan on 31/03/15.
 */
public class BrickSet implements Parcelable {
    private String setID = "";
    private String number = "";
    private String numberVariant = "";
    private String name = "";
    private String year = "";
    private String theme = "";
    private String subtheme = "";
    private String pieces = "";
    private String minifigs = "";

    private String imageFilename = "";
    private String thumbnailURL = "";
    private String largeThumbnailURL = "";
    private String bricksetURL = "";
    private String owned = "";
    private String wanted = "";
    private String qtyOwned = "";
    private String ownedByTotal = "";
    private String wantedByTotal = "";
    private String UKRetailPrice = "";
    private String USRetailPrice = "";
    private String CARetailPrice = "";
    private String EURetailPrice = "";
    private String rating = "";
    private String reviewCount = "";

    private String packagingType = "";
    private String availability = "";
    private String instructionsCount = "";
    private String additionalImageCount = "";

    /* Getter, Setter */

    public String getSetID() {
        return setID;
    }

    public void setSetID(String setID) {
        this.setID = setID;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumberVariant() {
        return numberVariant;
    }

    public void setNumberVariant(String numberVariant) {
        this.numberVariant = numberVariant;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getSubtheme() {
        return subtheme;
    }

    public void setSubtheme(String subtheme) {
        this.subtheme = subtheme;
    }

    public String getPieces() {
        return pieces;
    }

    public void setPieces(String pieces) {
        this.pieces = pieces;
    }

    public String getMinifigs() {
        return minifigs;
    }

    public void setMinifigs(String minifigs) {
        this.minifigs = minifigs;
    }

    public String getImageFilename() {
        return imageFilename;
    }

    public void setImageFilename(String imageFilename) {
        this.imageFilename = imageFilename;
    }

    public String getThumbnailURL() {
        return thumbnailURL;
    }

    public void setThumbnailURL(String thumbnailURL) {
        this.thumbnailURL = thumbnailURL;
    }

    public String getLargeThumbnailURL() {
        return largeThumbnailURL;
    }

    public void setLargeThumbnailURL(String largeThumbnailURL) {
        this.largeThumbnailURL = largeThumbnailURL;
    }

    public String getBricksetURL() {
        return bricksetURL;
    }

    public void setBricksetURL(String bricksetURL) {
        this.bricksetURL = bricksetURL;
    }

    public String isOwned() {
        return owned;
    }

    public void setOwned(String owned) {
        this.owned = owned;
    }

    public String isWanted() {
        return wanted;
    }

    public void setWanted(String wanted) {
        this.wanted = wanted;
    }

    public String getQtyOwned() {
        return qtyOwned;
    }

    public void setQtyOwned(String qtyOwned) {
        this.qtyOwned = qtyOwned;
    }

    public String getOwnedByTotal() {
        return ownedByTotal;
    }

    public void setOwnedByTotal(String ownedByTotal) {
        this.ownedByTotal = ownedByTotal;
    }

    public String getWantedByTotal() {
        return wantedByTotal;
    }

    public void setWantedByTotal(String wantedByTotal) {
        this.wantedByTotal = wantedByTotal;
    }

    public String getUKRetailPrice() {
        return UKRetailPrice;
    }

    public void setUKRetailPrice(String UKRetailPrice) {
        this.UKRetailPrice = UKRetailPrice;
    }

    public String getUSRetailPrice() {
        return USRetailPrice;
    }

    public void setUSRetailPrice(String USRetailPrice) {
        this.USRetailPrice = USRetailPrice;
    }

    public String getCARetailPrice() {
        return CARetailPrice;
    }

    public void setCARetailPrice(String CARetailPrice) {
        this.CARetailPrice = CARetailPrice;
    }

    public String getEURetailPrice() {
        return EURetailPrice;
    }

    public void setEURetailPrice(String EURetailPrice) {
        this.EURetailPrice = EURetailPrice;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReviewCount() {
        return reviewCount;
    }

    public void setReviewCount(String reviewCount) {
        this.reviewCount = reviewCount;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getInstructionsCount() {
        return instructionsCount;
    }

    public void setInstructionsCount(String instructionsCount) {
        this.instructionsCount = instructionsCount;
    }

    public String getAdditionalImageCount() {
        return additionalImageCount;
    }

    public void setAdditionalImageCount(String additionalImageCount) {
        this.additionalImageCount = additionalImageCount;
    }

    public static Creator<BrickSet> getCreator() {
        return CREATOR;
    }

    @Override
    public String toString() {
        return "BrickSet{" +
                "setID='" + setID + '\'' +
                ", number='" + number + '\'' +
                ", numberVariant='" + numberVariant + '\'' +
                ", name='" + name + '\'' +
                ", year='" + year + '\'' +
                ", theme='" + theme + '\'' +
                ", subtheme='" + subtheme + '\'' +
                ", pieces='" + pieces + '\'' +
                ", minifigs='" + minifigs + '\'' +
                ", imageFilename='" + imageFilename + '\'' +
                ", thumbnailURL='" + thumbnailURL + '\'' +
                ", largeThumbnailURL='" + largeThumbnailURL + '\'' +
                ", bricksetURL='" + bricksetURL + '\'' +
                ", owned=" + owned +
                ", wanted=" + wanted +
                ", qtyOwned='" + qtyOwned + '\'' +
                ", ownedByTotal='" + ownedByTotal + '\'' +
                ", wantedByTotal='" + wantedByTotal + '\'' +
                ", UKRetailPrice='" + UKRetailPrice + '\'' +
                ", USRetailPrice='" + USRetailPrice + '\'' +
                ", CARetailPrice='" + CARetailPrice + '\'' +
                ", EURetailPrice='" + EURetailPrice + '\'' +
                ", rating='" + rating + '\'' +
                ", reviewCount='" + reviewCount + '\'' +
                ", packagingType='" + packagingType + '\'' +
                ", availability='" + availability + '\'' +
                ", instructionsCount='" + instructionsCount + '\'' +
                ", additionalImageCount='" + additionalImageCount + '\'' +
                '}';
    }

    public BrickSet(String setID, String number, String numberVariant, String name, String year, String theme, String subtheme, String pieces, String minifigs, String imageFilename, String thumbnailURL, String largeThumbnailURL, String bricksetURL, String owned, String wanted, String qtyOwned, String ownedByTotal, String wantedByTotal, String UKRetailPrice, String USRetailPrice, String CARetailPrice, String EURetailPrice, String rating, String reviewCount, String packagingType, String availability, String instructionsCount, String additionalImageCount) {
        this.setID = setID;
        this.number = number;
        this.numberVariant = numberVariant;
        this.name = name;
        this.year = year;
        this.theme = theme;
        this.subtheme = subtheme;
        this.pieces = pieces;
        this.minifigs = minifigs;
        this.imageFilename = imageFilename;
        this.thumbnailURL = thumbnailURL;
        this.largeThumbnailURL = largeThumbnailURL;
        this.bricksetURL = bricksetURL;
        this.owned = owned;
        this.wanted = wanted;
        this.qtyOwned = qtyOwned;
        this.ownedByTotal = ownedByTotal;
        this.wantedByTotal = wantedByTotal;
        this.UKRetailPrice = UKRetailPrice;
        this.USRetailPrice = USRetailPrice;
        this.CARetailPrice = CARetailPrice;
        this.EURetailPrice = EURetailPrice;
        this.rating = rating;
        this.reviewCount = reviewCount;
        this.packagingType = packagingType;
        this.availability = availability;
        this.instructionsCount = instructionsCount;
        this.additionalImageCount = additionalImageCount;
    }

    public BrickSet() {

    }

    private BrickSet(Parcel in) {
        this.setID = in.readString();
        this.number = in.readString();
        this.numberVariant = in.readString();
        this.name = in.readString();
        this.year = in.readString();
        this.theme = in.readString();
        this.subtheme = in.readString();
        this.pieces = in.readString();
        this.minifigs = in.readString();
        this.imageFilename = in.readString();
        this.thumbnailURL = in.readString();
        this.largeThumbnailURL = in.readString();
        this.bricksetURL = in.readString();
        this.owned = in.readString();
        this.wanted = in.readString();
        this.qtyOwned = in.readString();
        this.ownedByTotal = in.readString();
        this.wantedByTotal = in.readString();
        this.UKRetailPrice = in.readString();
        this.USRetailPrice = in.readString();
        this.CARetailPrice = in.readString();
        this.EURetailPrice = in.readString();
        this.rating = in.readString();
        this.reviewCount = in.readString();
        this.packagingType = in.readString();
        this.availability = in.readString();
        this.instructionsCount = in.readString();
        this.additionalImageCount = in.readString();

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
        dest.writeString(setID);
        dest.writeString(number);
        dest.writeString(numberVariant);
        dest.writeString(name);
        dest.writeString(year);
        dest.writeString(theme);
        dest.writeString(subtheme);
        dest.writeString(pieces);
        dest.writeString(minifigs);
        dest.writeString(imageFilename);
        dest.writeString(thumbnailURL);
        dest.writeString(largeThumbnailURL);
        dest.writeString(bricksetURL);
        dest.writeString(owned);
        dest.writeString(wanted);
        dest.writeString(qtyOwned);
        dest.writeString(ownedByTotal);
        dest.writeString(wantedByTotal);
        dest.writeString(UKRetailPrice);
        dest.writeString(USRetailPrice);
        dest.writeString(CARetailPrice);
        dest.writeString(EURetailPrice);
        dest.writeString(rating);
        dest.writeString(reviewCount);
        dest.writeString(packagingType);
        dest.writeString(availability);
        dest.writeString(instructionsCount);
        dest.writeString(additionalImageCount);
    }

    public static final Parcelable.Creator<BrickSet> CREATOR = new Parcelable.Creator<BrickSet>() {
        public BrickSet createFromParcel(Parcel in) {
            return new BrickSet(in);
        }

        public BrickSet[] newArray(int size) {
            return new BrickSet[size];
        }
    };

    /*
    @Override
    public String toString() {
        return "BrickSet [id=" + id + ", title=" + title + ", cover=" + cover + "]";
    }*/

}
