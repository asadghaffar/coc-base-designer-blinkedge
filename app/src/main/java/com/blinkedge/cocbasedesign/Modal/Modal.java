package com.blinkedge.cocbasedesign.Modal;

import com.blinkedge.cocbasedesign.API.API;

public class Modal {

    int hybridItemId;
    int trophyItemId;
    int farmingItemId;
    int itemId;
    boolean trophyFavoriteItems;
    boolean favouriteItems;
    boolean farmingFavoriteItems;
    boolean hybridFavoriteItems;
    int townHallId;
    String townHallImage;
    String townHallNumber;
    String VillageAllBases;
    String villageBaseLink;

    String builderBaseLink;
    int builderHallId;
    String builderBaseImage;
    String builderBaseTitle;
    int builderBasesItemId;

    public String getBuilderBaseLink() {
        return builderBaseLink;
    }

    public void setBuilderBaseLink(String builderBaseLink) {
        this.builderBaseLink = builderBaseLink;
    }


    public int getBuilderBasesItemId() {
        return builderBasesItemId;
    }

    public void setBuilderBasesItemId(int builderBasesItemId) {
        this.builderBasesItemId = builderBasesItemId;
    }

    public String getvillageBaseLink() {
        return villageBaseLink;
    }

    public void setvillageBaseLink(String baseLink) {
        this.villageBaseLink = baseLink;
    }

    public boolean isTrophyFavoriteItems() {
        return trophyFavoriteItems;
    }

    public void setTrophyFavoriteItems(boolean trophyFavoriteItems) {
        this.trophyFavoriteItems = trophyFavoriteItems;
    }

    public int getTrophyItemId() {
        return trophyItemId;
    }

    public void setTrophyItemId(int trophyItemId) {
        this.trophyItemId = trophyItemId;
    }

    public boolean isHybridFavoriteItems() {
        return hybridFavoriteItems;
    }

    public void setHybridFavoriteItems(boolean hybridFavoriteItems) {
        this.hybridFavoriteItems = hybridFavoriteItems;
    }

    public int getHybridItemId() {
        return hybridItemId;
    }

    public void setHybridItemId(int hybridItemId) {
        this.hybridItemId = hybridItemId;
    }

    public boolean isFarmingFavoriteItems() {
        return farmingFavoriteItems;
    }

    public void setFarmingFavoriteItems(boolean farmingFavoriteItems) {
        this.farmingFavoriteItems = farmingFavoriteItems;
    }

    public int getFarmingItemId() {
        return farmingItemId;
    }

    public void setFarmingItemId(int farmingItemId) {
        this.farmingItemId = farmingItemId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public boolean isFavouriteItems() {
        return favouriteItems;
    }

    public void setFavouriteItems(boolean favouriteItems) {

        this.favouriteItems = favouriteItems;
    }

    // catHome_id
    public int getTownHallId() {
        return townHallId;
    }

    public void setTownHallId(int townHallId) {
        this.townHallId = townHallId;
    }

    //home_image
    public String getTownHallImage() {
        return townHallImage;
    }

    public void setTownHallImage(String townHallImage) {
        this.townHallImage = API.IMAGE_BASE_URL + townHallImage;
    }

    // home_description
    public String getTownHallNumber() {
        return townHallNumber;
    }

    public void setTownHallNumber(String townHallNumber) {
        this.townHallNumber = townHallNumber;
    }

    // catHome_id
    public int getBuilderHallId() {
        return builderHallId;
    }

    public void setBuilderHallId(int builderHallId) {
        this.builderHallId = builderHallId;
    }

    //home_image
    public String getBuilderBaseImage() {
        return API.IMAGE_BASE_URL + builderBaseImage;
    }

    public void setBuilderBaseImage(String builderBaseImage) {
        this.builderBaseImage = builderBaseImage;
    }

    // home_description
    public String getBuilderBaseTitle() {
        return builderBaseTitle;
    }

    public void setBuilderBaseTitle(String builderBaseTitle) {
        this.builderBaseTitle = builderBaseTitle;
    }

    public String getVillageAllBases() {
        return API.IMAGE_BASE_URL + VillageAllBases;
    }

    public void setVillageAllBases(String villageAllBases) {
        VillageAllBases = villageAllBases;
    }


}
