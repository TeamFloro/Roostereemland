package nl.floro.roostereemland;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import nl.floro.roostereemland.api.RoostereemlandApiClient;


public class MainActivity extends ActionBarActivity {


    static final ArrayList<String> Klassen = new ArrayList<>();
    static final ArrayList<String> Docenten = new ArrayList<>();
    static final ArrayList<String> Lokalen = new ArrayList<>();
    static final Calendar kalender = new GregorianCalendar();
    static String p;
    SharedPreferences sharedPrefs;
    String lastUsed;
    String klas;
    String docent;
    Spinner spinnerKlas;
    Spinner spinnerDocent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getRoosterMededelingen();
        sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.drawable.ic_launcher);
        toolbar.setSubtitle("voor alle leerlingen op HNE!");
        spinnerDocent = (Spinner) findViewById(R.id.spinner1);
        spinnerKlas = (Spinner) findViewById(R.id.spinner);
        final WebView webView = (WebView) findViewById(R.id.webView);
        AdView adView = (AdView) this.findViewById(R.id.adView);
        ArrayAdapter<String> adapterDocenten = new ArrayAdapter<>(this, R.layout.spinner_item, Docenten);
        ArrayAdapter<String> adapterKlassen = new ArrayAdapter<>(this, R.layout.spinner_item, Klassen);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice(AdRequest.DEVICE_ID_EMULATOR).addTestDevice("A1312D94E3AE6ED57755EAAB4C76AECC").build();
        Klassen.addAll(Arrays.asList("-", "eg1a", "ehv1bCE", "ehv1cCE", "ehv1d", "ehv1e", "ehv1fSp", "emh1g", "emh1h", "emh1iSp", "eg2a", "eg2b", "ehv2cCE", "ehv2d", "ehv2e", "ehv2f", "ehv2g", "ehv2h", "em2a", "em2b", "em2cSp", "eg3aiP", "eg3b", "ev3c", "ev3d", "ev3eSp", "eh3a", "eh3b", "eh3c", "eh3d", "eh3eSp", "em3a", "em3b", "em3c", "evg4a", "evg4b", "evg4c", "eh4a", "eh4b", "eh4c", "eh4d", "em4a", "em4b", "em4c", "evg5a", "evg5b", "evg5c", "eh5a", "eh5b", "eh5c", "eh5d", "evg6a", "evg6b"));
        Docenten.addAll(Arrays.asList("-", "Akn", "Ama", "Ate", "Bbw", "Bdw", "Bke", "Blb", "Bne", "Bpd", "Bre", "Brn", "Brs", "Brw", "Bss", "Bvg", "Bzd", "Ccg", "Ctn", "Dhg", "Dkb", "Dma", "Dmn", "Dnt", "Egl", "Enn", "Esf", "Faw", "Fns", "Fsf", "Ggw", "Gns", "Grb", "Grk", "Gsg", "Gtf", "Hah", "Hdb", "Heb", "Hgd", "Hge", "Hke", "Hld", "Hmd", "Hpa", "Hpe", "Hse", "Hte", "Hwg", "Hwi", "Jnn", "Joe", "Jot", "Jsl", "Khd", "Kld", "Klm", "Kom", "Krf", "Kru", "Kte", "Ktn", "Kvw", "Leg", "Lhw", "Lmw", "Lrb", "Mdg", "Mdn", "Mes", "Mhf", "Mlf", "Mnn", "Mrl", "Nvm", "Obe", "Olm", "Osn", "Osw", "Pif", "Pjs", "Pre", "Ptd", "Rcs", "Rif", "Rig", "Rlw", "Rml", "Rui", "Rvm", "Sbt", "Sch", "Sgn", "Shd", "Sis", "Skt", "Srn", "Sta", "Swn", "Ten", "Tmn", "Tmw", "Vds", "Vec", "Ves", "Vew", "Vhk", "Viw", "Vkb", "Vkn", "Vnl", "Vrt", "Vsk", "Wbe", "Wma", "Wmg", "Wms"));
        Lokalen.addAll(Arrays.asList("000", "005", "006", "007", "024", "034", "035", "036", "037", "103", "104", "116", "117", "118", "119", "122", "123", "124", "126", "127", "128", "129", "130", "202", "203", "204", "205", "208", "210", "215", "216", "217", "218", "219", "221", "222", "224", "225", "227", "228", "303", "304", "307", "309", "310", "311", "313", "401", "402", "403", "N1", "N10", "N2", "N6", "N7", "N8", "N9", "S1", "S2", "StZ", "StZ-", "T1", "T2", "T3", "T4"));
        lastUsed = sharedPrefs.getString("lastUsed", null);
        klas = sharedPrefs.getString("klas", null);
        docent = sharedPrefs.getString("docent", null);

        setSupportActionBar(toolbar);

        adView.loadAd(adRequest);
        webView.getSettings();
        webView.setBackgroundColor(Color.TRANSPARENT);

        adapterKlassen.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapterDocenten.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKlas.setAdapter(adapterKlassen);
        spinnerDocent.setAdapter(adapterDocenten);

        spinnerKlas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String partURL = "/c/c0000";
                if (position == 0) {
                    return;
                }
                if (position >= 10) {
                    partURL = "/c/c000";
                }


                webView.loadUrl("http://www.roostereemland.nl/dagrooster/" + getWeek() + partURL + position + ".htm");
                webView.setBackgroundColor(Color.TRANSPARENT);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerDocent.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                String partURL = "/t/t0000";
                if (position == 0) {
                    return;
                }
                if (position >= 10) {
                    partURL = "/t/t000";
                }

                if (position >= 100) {
                    partURL = "/t/t00";
                }


                webView.loadUrl("http://www.roostereemland.nl/dagrooster/" + getWeek() + partURL + position + ".htm");
                webView.setBackgroundColor(Color.TRANSPARENT);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if (lastUsed != null) {
            switch (lastUsed) {
                case "klas":
                    if (klas != null) {
                        spinnerDocent.setSelection(0);
                        spinnerKlas.setSelection(Integer.valueOf(klas));
                    }
                    break;
                case "docent":
                    if (docent != null) {
                        spinnerKlas.setSelection(0);
                        spinnerDocent.setSelection(Integer.valueOf(docent));
                    }
                    break;
            }
        }
    }

    public String getWeek() {
        String week = kalender.get(Calendar.WEEK_OF_YEAR) + "";
        if (kalender.get(Calendar.WEEK_OF_YEAR) < 10) {
            week = String.format("%02d", kalender.get(Calendar.WEEK_OF_YEAR));

        }
        return week;
    }

    public void getRoosterMededelingen() {
        final TextView mededelingen = (TextView) findViewById(R.id.textView1);
        RoostereemlandApiClient.get("", false, null, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Element roosterDoc = Jsoup.parse(new String(responseBody)).body();
                // Eigenschappen, laatst gewijzigde kip:
                // <font uze="2" face="Arial Unicode MS" color="#93678">
                p = roosterDoc.select("font[size=4]").text();
                if (p.contains("Roosterwijzer")) {
                    char[] d;
                    d = p.toCharArray();
                    System.out.println(d[0]);

                    for (int x = 0; x < d.length; x++) {
                        if (d[x] == 'R' && d[x + 1] == 'o' && d[x + 2] == 'o' && d[x + 3] == 's' && d[x + 4] == 't' && d[x + 5] == 'e' && d[x + 6] == 'r' && d[x + 7] == 'w' && d[x + 8] == 'i' && d[x + 9] == 'j' && d[x + 10] == 'z' && d[x + 11] == 'e' && d[x + 12] == 'r') {
                            p = p.substring(0, x);
                            mededelingen.setText(p);
                            mededelingen.setTextColor(Color.parseColor("#F44336"));
                            break;

                        }
                    }
                } else System.out.println("hoisdfdsf opent m noiit");


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println(new String(responseBody));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(getApplicationContext(), Settings.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first
        lastUsed = sharedPrefs.getString("lastUsed", null);
        klas = sharedPrefs.getString("klas", null);
        docent = sharedPrefs.getString("docent", null);

        if (lastUsed != null) {
            switch (lastUsed) {
                case "klas":
                    if (klas != null) {
                        spinnerDocent.setSelection(0);
                        spinnerKlas.setSelection(Integer.valueOf(klas));
                    }
                    break;
                case "docent":
                    if (docent != null) {
                        spinnerKlas.setSelection(0);
                        spinnerDocent.setSelection(Integer.valueOf(docent));
                    }
                    break;
            }
            System.out.println("hij update nu toch?" + klas);
        }
    }
}

