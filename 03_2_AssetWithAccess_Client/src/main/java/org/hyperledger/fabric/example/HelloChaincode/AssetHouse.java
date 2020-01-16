package org.hyperledger.fabric.example.HelloChaincode;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class AssetHouse {

    public String houseId;
    public Integer nrOfRooms;
    public String addressCountry;
    public String addressCity;
    public String addressStreet;
    public Integer streetNr;

    public String toJSON () {
        Gson gson = new Gson();
        return gson.toJson(this);
    }

    // factory function
    public static AssetHouse createAssetHouse(String houseString) {
       Gson gson = new Gson();
       AssetHouse act = gson.fromJson(houseString, AssetHouse.class);

       return act;
    }
}
