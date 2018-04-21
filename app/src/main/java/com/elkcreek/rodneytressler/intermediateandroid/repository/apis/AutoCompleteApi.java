package com.elkcreek.rodneytressler.intermediateandroid.repository.apis;

import com.elkcreek.rodneytressler.intermediateandroid.BuildConfig;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by rodneytressler on 4/20/18.
 */

public interface AutoCompleteApi {
    @GET("json?key=" + BuildConfig.API_KEY_GOOGLE_AUTOCOMPLETE)
    Observable<AutoCompleteResponse> getAutoCompleteResponse(@Query("input") String input);

    class AutoCompleteResponse {
        @SerializedName("predictions")
        @Expose private List<Predictions> predictionsList;

        public List<Predictions> getPredictionsList() {
            return predictionsList;
        }
    }

    class Predictions {
        @SerializedName("description")
        @Expose private String description;

        public String getDescription() {
            return description;
        }
    }
}
