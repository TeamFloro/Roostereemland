package nl.floro.roostereemland.api;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by robert-jan on 14-12-14.
 */
public class RoostereemlandApiClient {
    private static final String BASE_URL = "http://www.roostereemland.nl/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, boolean rooster, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        if (rooster) {
            client.get(getAbsoluteUrl("dagrooster/" + url), params, responseHandler);
        } else {
            client.get(getAbsoluteUrl(url), params, responseHandler);
        }
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        System.out.println(BASE_URL + relativeUrl);
        return BASE_URL + relativeUrl;
    }
}

