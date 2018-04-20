package com.elkcreek.rodneytressler.intermediateandroid.ui.WeeklyForecaseView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.elkcreek.rodneytressler.intermediateandroid.R;
import com.elkcreek.rodneytressler.intermediateandroid.repository.apis.DarkSkyApi;
import com.elkcreek.rodneytressler.intermediateandroid.utils.Icons;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rodneytressler on 4/20/18.
 */

public class DailyForecastAdapter extends RecyclerView.Adapter<DailyForecastAdapter.ViewHolder> {

    private List<DarkSkyApi.Days> daysList;

    public DailyForecastAdapter(List<DarkSkyApi.Days> daysList) {
        this.daysList = daysList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_daily_forecast, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindDays(daysList.get(position));
    }

    @Override
    public int getItemCount() {
        return daysList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.text_date)
        protected TextView dayDate;

        @BindView(R.id.text_emoji)
        protected TextView dayEmoji;

        @BindView(R.id.text_item_summary)
        protected TextView daySummary;

        @BindView(R.id.text_item_temp_high)
        protected TextView dayHigh;

        @BindView(R.id.text_item_temp_low)
        protected TextView dayLow;

        @BindView(R.id.text_item_precip_chance)
        protected TextView dayPrecipChance;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindDays(DarkSkyApi.Days days) {
            dayEmoji.setText(showIcon(days.getIcon()));
            daySummary.setText(days.getSummary());
            dayHigh.setText(days.highTempString());
            dayLow.setText(days.lowTempString());
            dayPrecipChance.setText(days.precipChanceString());
        }

        private String showIcon(String icon) {
            int uniCodeIcon = Icons.getWeather(icon);
            String emoji = new String(Character.toChars(uniCodeIcon));
            return emoji;
        }
    }
}
