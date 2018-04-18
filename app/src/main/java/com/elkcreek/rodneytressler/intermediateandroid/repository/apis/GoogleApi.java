package com.elkcreek.rodneytressler.intermediateandroid.repository.apis;

import com.elkcreek.rodneytressler.intermediateandroid.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rodneytressler on 4/18/18.
 */

public interface GoogleApi {
    @GET("json?api_key=" + BuildConfig.API_KEY_GOOGLEGEO)
    Observable<ResponseGoogleAddress> getAddress(@Query("address") String address);

    class ResponseGoogleAddress {
        @SerializedName("results")
        @Expose private List<AddressInformation> addressInformation;

        public List<AddressInformation> getAddressInformation() {
            return addressInformation;
        }
    }

    class AddressInformation {
        @SerializedName("formatted_address")
        @Expose private String formattedAddress;

        @SerializedName("geometry")
        @Expose private GoogleGeometry googleGeometry;

        public String getFormattedAddress() {
            return formattedAddress;
        }

        public GoogleGeometry getGoogleGeometry() {
            return googleGeometry;
        }
    }

    class GoogleGeometry {
        @SerializedName("location")
        @Expose private GoogleLocation googleLocation;

        public GoogleLocation getGoogleLocation() {
            return googleLocation;
        }
    }

    class GoogleLocation {
        @SerializedName("lat")
        @Expose private double latitude;

        @SerializedName("lng")
        @Expose private double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
