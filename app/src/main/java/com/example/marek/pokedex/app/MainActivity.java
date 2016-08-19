package com.example.marek.pokedex.app;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.KeyEvent;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import com.example.marek.pokedex.data.Pokemon;
import com.example.marek.pokedex.R;
import com.example.marek.pokedex.data.Stat;
import com.example.marek.pokedex.realm.PokeRealm;
import com.example.marek.pokedex.realm.RealmController;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

import java.util.ArrayList;
import java.util.List;

/** MainActivity is used to search or view favorites pokemons*/

public class MainActivity extends Activity {

    @BindView(R.id.tf_search)
    EditText tfSearch;
    @BindView(R.id.fab_show_fav)
    FloatingActionButton fabShowFav;

    private final String mUrl = "http://pokeapi.co/api/v2/pokemon/";
    private final String mSlash = "/";
    private final String mPokeNotFound = "Pokemon not found";
    private List<Pokemon> mPokemonList;
    private Pokemon mPokemon;
    private Realm mRealm;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mRealm = Realm.getInstance(new RealmConfiguration.Builder(this)
                .name(RealmController.REALM_NAME)
                .build()
        );

        mPokemonList = new ArrayList<>();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    @OnClick(R.id.b_search)
    public void submitSearch(){

        String searchUrl = mUrl + tfSearch.getText().toString() + mSlash;
        getPokemon(searchUrl);
    }

    @OnClick(R.id.fab_show_fav)
    public void showFavorites(){

        RotateAnimation rotate;

        rotate = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(300);
        rotate.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                mRealm.beginTransaction();
                RealmResults<PokeRealm> results = mRealm.where(PokeRealm.class).findAllSorted("name");
                mRealm.commitTransaction();
                mPokemonList = RealmController.convertFromRealm(results);
                Intent intent = new Intent(MainActivity.this,MyPokemons.class);
                intent.putExtra("pokemonList", Parcels.wrap(mPokemonList));
                startActivity(intent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        fabShowFav.startAnimation(rotate);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            this.finishAffinity();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    /**Parses json using Volley*/
    public void getPokemon(String url){

        showPd();

        RequestQueue queue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        List<String> abilityList = new ArrayList<>();
                        List<Stat> statList = new ArrayList<>();
                        List<String> formList = new ArrayList<>();


                        try {
                            JSONArray formsArray = response.getJSONArray("forms");

                            for(int i=0; i<formsArray.length(); i++) {
                                JSONObject formName = formsArray.getJSONObject(i);
                                formList.add(formName.getString("name"));
                            }

                            JSONArray abilitiesArray = response.getJSONArray("abilities");

                            for(int i=0; i<abilitiesArray.length(); i++){
                                JSONObject abilities = abilitiesArray.getJSONObject(i);
                                JSONObject ability = abilities.getJSONObject("ability");
                                abilityList.add(ability.getString("name"));

                            }


                            JSONArray statsArray = response.getJSONArray("stats");

                            for(int i=0; i<statsArray.length(); i++){
                                JSONObject stats = statsArray.getJSONObject(i);
                                JSONObject stat = stats.getJSONObject("stat");
                                String statName = stat.getString("name");
                                int statValue = stats.getInt("base_stat");
                                statList.add(new Stat(statName, statValue));

                            }

                            String pokeName = response.getString("name");

                            int weight = response.getInt("weight");

                            JSONObject sprites = response.getJSONObject("sprites");
                            String sprite = sprites.getString("front_default");

                            int height = response.getInt("height");

                            JSONObject species = response.getJSONObject("species");
                            String spec = species.getString("name");

                            int id = response.getInt("id");

                            mPokemon = new Pokemon(weight, abilityList, formList, height, id, pokeName, spec, sprite, statList);

                            hidePd();

                            showPoke();



                        }catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },

                new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        hidePd();
                        Toast.makeText(MainActivity.this, mPokeNotFound, Toast.LENGTH_LONG).show();
                        VolleyLog.d("Error: ", error.getMessage());

                    }
                });



        queue.add(jsonObjectRequest);


    }

    public void showPoke(){
        Intent intent = new Intent(MainActivity.this, PokemonViewer.class);
        Bundle extras = new Bundle();
        extras.putParcelable("pokemon", Parcels.wrap(Pokemon.class, mPokemon));
        extras.putString("from", "main");
        intent.putExtras(extras);
        startActivity(intent);
    }

    public void showPd(){
        if(mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Searching...");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setCanceledOnTouchOutside(false);
            mProgressDialog.show();
        }
    }

    public void hidePd(){
        mProgressDialog.dismiss();
        mProgressDialog = null;
    }


}
