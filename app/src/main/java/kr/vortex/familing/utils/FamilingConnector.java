package kr.vortex.familing.utils;

import android.content.Context;
import android.content.SharedPreferences;

import retrofit.RequestInterceptor;
import retrofit.RestAdapter;

/**
 * Created by kotohana-laptop on 15. 7. 20.
 */
public class FamilingConnector {

    private static FamilingService instance;
    private static SharedPreferences sharedPreferences;
    private static String apikey;

    public static FamilingService getInstance() {
        if(instance != null) return instance;
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://familing.kkiro.kr")
                .setRequestInterceptor(new RequestInterceptor() {
                    @Override
                    public void intercept(RequestFacade request) {
                        // Inject API Key
                        request.addQueryParam("apikey", apikey);
                    }
                })
                .build();
        instance = restAdapter.create(FamilingService.class);
        return instance;
    }

    public static void setApiKey(String key) {
        apikey = key;
    }

    public static String getApiKey() {
        return apikey;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        if(sharedPreferences == null) {
            sharedPreferences = context.getSharedPreferences("authkeys", 0);
        }
        return sharedPreferences;
    }

    public static void loadApiKey(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
       apikey = sharedPreferences.getString("apikey", "");
    }

    public static void saveApiKey(Context context, String key) {
        apikey = key;
        saveApiKey(context);
    }

    public static void saveApiKey(Context context){
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        sharedPreferences.edit().putString("apikey", apikey).commit();
    }

}
