package com.moringaschool.gamify.models;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;


@Parcel
public class GameSearchResponse {

    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("title")
    @Expose
    public String title;
    @SerializedName("thumbnail")
    @Expose
    public String thumbnail;
    @SerializedName("short_description")
    @Expose
    public String shortDescription;
    @SerializedName("game_url")
    @Expose
    public String gameUrl;
    @SerializedName("genre")
    @Expose
    public String genre;
    @SerializedName("platform")
    @Expose
    public String platform;
    @SerializedName("publisher")
    @Expose
    public String publisher;
    @SerializedName("developer")
    @Expose
    public String developer;
    @SerializedName("release_date")
    @Expose
    public String releaseDate;
    @SerializedName("freetogame_profile_url")
    @Expose
    public String freetogameProfileUrl;
    @SerializedName("pushId")
    @Expose
    public String pushId;

    /**
     * No args constructor for use in serialization
     * 
     */
    public GameSearchResponse() {
    }

    /**
     * 
     * @param thumbnail
     * @param releaseDate
     * @param genre
     * @param publisher
     * @param gameUrl
     * @param developer
     * @param id
     * @param shortDescription
     * @param title
     * @param platform
     * @param freetogameProfileUrl
     */
    public GameSearchResponse(Integer id, String title, String thumbnail, String shortDescription, String gameUrl, String genre, String platform, String publisher, String developer, String releaseDate, String freetogameProfileUrl, List<GameSearchResponse> games) {
        super();
        this.id = id;
        this.title = title;
        this.thumbnail = thumbnail;
        this.shortDescription = shortDescription;
        this.gameUrl = gameUrl;
        this.genre = genre;
        this.platform = platform;
        this.publisher = publisher;
        this.developer = developer;
        this.releaseDate = releaseDate;
        this.freetogameProfileUrl = freetogameProfileUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getGameUrl() {
        return gameUrl;
    }

    public void setGameUrl(String gameUrl) {
        this.gameUrl = gameUrl;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getFreetogameProfileUrl() {
        return freetogameProfileUrl;
    }

    public void setFreetogameProfileUrl(String freetogameProfileUrl) {
        this.freetogameProfileUrl = freetogameProfileUrl;
    }
    public String getPushId(){
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
