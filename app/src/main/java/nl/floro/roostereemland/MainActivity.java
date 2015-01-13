package nl.floro.roostereemland;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import java.util.ArrayList;

import nl.floro.roostereemland.api.RoostereemlandApiClient;
//import nl.floro.roostereemland.fragments.RoosterFragment;


public class MainActivity extends ActionBarActivity {


    static final ArrayList<String> Klassen = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        /*
        * Hardcoded strings... -_-
        *
        * Floris implement hashmaps hier aub XD.
        * Key, Value
        * "Klas1", "c000012.htm"
        *
        * Met als uitzondering, "-"
        * Dit is de Homepage.
        *
        *
        * EDIT:
        * Hoeft niet, we kunnen een Array gebruiken en dan de position van de klasnaam gebruiken...
        * Maaarrr.. als jij een hashmap wil maken ;p
        *
        */


//        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        final RoosterFragment roosterFragment = new RoosterFragment();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Klassen);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//        transaction.add(R.id.fragment_holder, roosterFragment).commit();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                getRooster(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public void getRooster(int klasPositie) {
        /*
        * 51/c/c00012.htm
        * 51/c/c00002.htm
        *
        * Todo:
        * Fix dat hij voor een klas onder de tien er een 0 voor zet en daarna niks.
        * Ik wil dit niet fixen met een slordige if else of een case...
        */

        final TextView textView = (TextView) findViewById(R.id.SiteText);
        RoostereemlandApiClient.get("51/c/c0000" + klasPositie + ".htm", null, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                textView.setText(Html.fromHtml(new String(responseBody)));
                System.out.println(new String(responseBody));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                textView.setText(new String(responseBody));
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}




/*
* Todo:
* - Make rooster work in fragment
* - Settings
* - Parse homepage info
* - enz.
* */