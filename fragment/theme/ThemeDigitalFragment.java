package com.pomohouse.launcher.fragment.theme;

import android.database.ContentObserver;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pomohouse.launcher.R;
import com.pomohouse.launcher.base.BaseThemeFragment;
import com.pomohouse.launcher.di.module.ThemeFragmentPresenterModule;
import com.pomohouse.launcher.fragment.theme.presenter.IThemeFragmentPresenter;
import com.pomohouse.launcher.lock_screen.LockScreenService;
import com.pomohouse.launcher.manager.notifications.INotificationManager;
import com.pomohouse.launcher.manager.settings.SettingPrefManager;
import com.pomohouse.launcher.manager.theme.ThemePrefModel;
import com.pomohouse.launcher.models.EventDataInfo;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

/**
 * Created by Admin on 9/26/2016 AD.
 */

public class ThemeDigitalFragment extends BaseThemeFragment implements IThemeFragmentView {

    @BindView(R.id.container)
    RelativeLayout container;
    @BindView(R.id.tvHour)
    AppCompatTextView tvHour;
    @BindView(R.id.tvMinute)
    AppCompatTextView tvMinute;

    @BindView(R.id.tvHour2)
    AppCompatTextView tvHour2;
    @BindView(R.id.tvMinute2)
    AppCompatTextView tvMinute2;

    @BindView(R.id.tvAmPm)
    AppCompatTextView tvAmPm;

    @BindView(R.id.tvDay)
    AppCompatTextView tvDay;
    @BindView(R.id.tvDate)
    AppCompatTextView tvDate;

    @BindView(R.id.ivCallNotification)
    AppCompatImageView ivCallNotification;

    @BindView(R.id.ivMessageNotification)
    AppCompatImageView ivMessageNotification;

    @BindView(R.id.ivStatusDNotification)
    AppCompatImageView ivStatusDNotification;
    @Inject
    IThemeFragmentPresenter presenter;

    @Inject
    INotificationManager notificationManager;

    private ThemePrefModel theme;

    //yangyu add
    private String currentTimeFormat = "24";
//    private ContentResolver mContentResolver;

    private ContentObserver timeFormatObserver = new ContentObserver(new Handler()) {
        @Override
        public void onChange(boolean selfChange) {
            currentTimeFormat = Settings.System.getString(getActivity().getContentResolver(), Settings.System.TIME_12_24);
            Timber.e("Current Time Format : " + currentTimeFormat);
            if (currentTimeFormat.equals("24")) {
                LockScreenService.is24HourFormat = true;
            } else if (currentTimeFormat.equals("12")) {
                LockScreenService.is24HourFormat = false;
            }
        }
    };

    public static ThemeDigitalFragment newInstance(ThemePrefModel themeId) {
        ThemeDigitalFragment fragment = new ThemeDigitalFragment();
        Bundle args = new Bundle();
        args.putParcelable(THEME, themeId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        theme = getArguments().getParcelable(THEME);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onCheckNotificationIcon(notificationManager);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Timber.e(THEME + ":" + theme.getPosition());
        return inflater.inflate(R.layout.fragment_theme_5, container, false);/*
        if (theme == null)
            theme = new ThemePrefModel();
        switch (theme.getThemeId()) {
            case 1:
                return inflater.inflate(R.layout.fragment_theme_1, container, false);
            case 2:
                return inflater.inflate(R.layout.fragment_theme_2, container, false);
            case 4:
                return inflater.inflate(R.layout.fragment_theme_4, container, false);
            case 5:
                return inflater.inflate(R.layout.fragment_theme_5, container, false);
            case 7:
                return inflater.inflate(R.layout.fragment_theme_7, container, false);
            default:
                return inflater.inflate(R.layout.fragment_theme_5, container, false);
        }*/
    }
    /*SettingPrefManager settingPrefManager;
    TimeZone tz;
    private Calendar calendar;*/

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onInitial();
        Drawable drawable;
        if (theme.getPosition() == 1)
            drawable = getResources().getDrawable(R.drawable.theme_box_bg_1);
        else if (theme.getPosition() == 2)
            drawable = getResources().getDrawable(R.drawable.theme_box_bg_2);
        else drawable = getResources().getDrawable(R.drawable.theme_box_bg_3);

        ((ImageView) view.findViewById(R.id.boxDate)).setImageDrawable(drawable);

        TextView tvPoral = view.findViewById(R.id.tvPoral);
        if (tvPoral != null) tvPoral.setTextColor(Color.parseColor(theme.getDosColor()));
        int resId = getResources().getIdentifier(theme.getBackground(), "drawable", getActivity().getPackageName());
        container.setBackgroundResource(resId);
        tvDate.setTextColor(Color.parseColor(theme.getDataColor()));
        tvDay.setTextColor(Color.parseColor(theme.getDayNameColor()));
        tvHour.setTextColor(Color.parseColor(theme.getHourColor()));
        tvHour2.setTextColor(Color.parseColor(theme.getHourColor()));
        tvMinute.setTextColor(Color.parseColor(theme.getMinuteColor()));
        tvMinute2.setTextColor(Color.parseColor(theme.getMinuteColor()));
        presenter.onCheckNotificationIcon(notificationManager);

        tvAmPm.setVisibility(View.INVISIBLE);

        //settingPrefManager = new SettingPrefManager(getContext());

        currentTimeFormat = Settings.System.getString(getActivity().getContentResolver(), Settings.System.TIME_12_24);
        /*calendar = Calendar.getInstance();
        if (settingPrefManager.getSetting().isAutoTimezone()) {
            calendar.setTime(new Date());
        } else {
            Toast.makeText(getActivity(),"Theme Digital Screen "+settingPrefManager.getSetting().getTimeZoneGMT(),Toast.LENGTH_SHORT).show();
            tz = TimeZone.getTimeZone(settingPrefManager.getSetting().getTimeZoneGMT());
            calendar = Calendar.getInstance(tz);
        }*/

        //updateTime(calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE));
        getActivity().getContentResolver().registerContentObserver(Settings.System.getUriFor(Settings.System.TIME_12_24), false, timeFormatObserver);
        if (currentTimeFormat == null) LockScreenService.is24HourFormat = true;
        else if (currentTimeFormat.equals("24")) LockScreenService.is24HourFormat = true;
        else if (currentTimeFormat.equals("12")) LockScreenService.is24HourFormat = false;
    }


    private static final int UPDATE_HOUR = 1;
    private static final int UPDATE_MINUTE = 2;
    private static final int UPDATE_DAY = 3;
    private static final int UPDATE_DATE = 4;
    private final Handler _handler = new Handler(msg -> {
        String time, day, date;
        switch (msg.what) {
            case UPDATE_HOUR:
                time = (String) msg.obj;
                tvHour.setText(String.valueOf(time.charAt(0)));
                tvHour2.setText(String.valueOf(time.charAt(1)));
                break;
            case UPDATE_MINUTE:
                time = (String) msg.obj;
                tvMinute.setText(String.valueOf(time.charAt(0)));
                tvMinute2.setText(String.valueOf(time.charAt(1)));
                break;
            case UPDATE_DAY:
                day = (String) msg.obj;
                tvDay.setText(day.toUpperCase() + " ");
                break;
            case UPDATE_DATE:
                date = (String) msg.obj;
                tvDate.setText(date.toUpperCase());
                break;
        }
        return true;
    });

    @Override
    public void updateTime(int hour, int minute) {

        String hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
        String minuteStr = minute < 10 ? "0" + minute : String.valueOf(minute);
        tvAmPm.setText(hour >= 12 ? getResources().getString(R.string.text_pm) : getResources().getString(R.string.text_am));
        if (currentTimeFormat == null) tvAmPm.setVisibility(View.INVISIBLE);
        else if (currentTimeFormat.equals("24")) {
            tvAmPm.setVisibility(View.INVISIBLE);
        } else if (currentTimeFormat.equals("12")) {
            tvAmPm.setVisibility(View.VISIBLE);
            hour = hour < 13 ? hour : (hour - 12);
            hourStr = hour < 10 ? "0" + hour : String.valueOf(hour);
        }
        Timber.i(hourStr + ":" + minuteStr);
        _handler.sendMessage(_handler.obtainMessage(UPDATE_HOUR, hourStr));
        _handler.sendMessage(_handler.obtainMessage(UPDATE_MINUTE, minuteStr));
    }

    @Override
    protected void updateDate(int month, int day, int dayName) {
        _handler.sendMessage(_handler.obtainMessage(UPDATE_DAY, dayOfWeek[dayName > 0 ? dayName - 1 : dayName]));
        _handler.sendMessage(_handler.obtainMessage(UPDATE_DATE, day + " " + monthOfYear[month]));
    }

    @Override
    public void eventReceived(EventDataInfo event) {
        presenter.eventReceived(event);
    }

    @Override
    protected List<Object> injectModules() {
        return Collections.singletonList(new ThemeFragmentPresenterModule(this));
    }

    @Override
    public void updateTimezone() {
        super.timezoneChange();
    }


    @Override
    public void notificationVolumeMute(boolean isShow) {
        ivStatusDNotification.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void notificationMessage(boolean isShow) {
        ivMessageNotification.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void notificationCall(boolean isShow) {
        ivCallNotification.setVisibility(isShow ? View.VISIBLE : View.INVISIBLE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        getActivity().getContentResolver().unregisterContentObserver(timeFormatObserver);
    }
}