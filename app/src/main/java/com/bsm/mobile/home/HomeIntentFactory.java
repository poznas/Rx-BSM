package com.bsm.mobile.home;

import android.content.Context;
import android.content.Intent;

import com.bsm.mobile.login.LoginActivity;

import java.util.HashMap;

import static com.bsm.mobile.Constants.*;

public class HomeIntentFactory {

    public static HashMap<String, Intent> getPrivilegeIntentMap(Context context){
        return new HashMap<String, Intent>(){{
            put(BRAND_WIZARDS, new Intent(context, LoginActivity.class));
            put(BRAND_SM_INFO, new Intent(context, LoginActivity.class));
            put(BRAND_MC_INFO, new Intent(context, LoginActivity.class));
            put(BRAND_CALENDAR, new Intent(context, LoginActivity.class));
            put(BRAND_MAIN_COMPETITION, new Intent(context, LoginActivity.class));
            put(BRAND_BET, new Intent(context, LoginActivity.class));
            put(BRAND_MEDAL, new Intent(context, LoginActivity.class));
            put(BRAND_ZONGLER, new Intent(context, LoginActivity.class));
            put(BRAND_REPORT, new Intent(context, LoginActivity.class));
            put(BRAND_JUDGE, new Intent(context, LoginActivity.class));
            put(BRAND_PROF_RATE, new Intent(context, LoginActivity.class));
        }};
    }

    public static HashMap<String, Intent> getTeamIntentMap(Context context){
        return new HashMap<String, Intent>(){{
            put(TEAM_CORMEUM, new Intent(context, LoginActivity.class).putExtra(KEY_TEAM, TEAM_CORMEUM));
            put(TEAM_SENSUM, new Intent(context, LoginActivity.class).putExtra(KEY_TEAM, TEAM_SENSUM));
            put(TEAM_MUTINIUM, new Intent(context, LoginActivity.class).putExtra(KEY_TEAM, TEAM_MUTINIUM));
        }};
    }
}