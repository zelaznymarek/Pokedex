package com.example.marek.pokedex.app;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import com.example.marek.pokedex.data.Pokemon;
import com.example.marek.pokedex.R;
import com.example.marek.pokedex.realm.PokeRealm;
import com.example.marek.pokedex.realm.RealmController;
import com.example.marek.pokedex.utils.MySingleton;

import org.parceler.Parcels;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**Shows single pokemon with details*/

public class PokemonViewer extends Activity {

    @BindView(R.id.poke_icon)
    NetworkImageView pokeIcon;
    @BindView(R.id.poke_name)
    TextView tvPokeName;
    @BindView(R.id.tv_species_val)
    TextView tvSpecies;
    @BindView(R.id.tv_height_val)
    TextView tvHeight;
    @BindView(R.id.tv_weight_val)
    TextView tvWeight;
    @BindView(R.id.tv_forms_val)
    TextView tvForms;
    @BindView(R.id.tv_abilities_val)
    TextView tvAbilities;
    @BindView(R.id.tv_speed_val)
    TextView tvSpeed;
    @BindView(R.id.tv_special_def__val)
    TextView tvSpecialDef;
    @BindView(R.id.tv_special_att_val)
    TextView tvSpecialAttack;
    @BindView(R.id.tv_defense__val)
    TextView tvDefense;
    @BindView(R.id.tv_attack_val)
    TextView tvAttack;
    @BindView(R.id.tv_hp_val)
    TextView tvHP;
    @BindView(R.id.fab_fav_add_remove)
    FloatingActionButton fabFavAddRemove;

    private Pokemon mPokemon;
    private ImageLoader mImageLoader;
    private RealmResults<PokeRealm> mResult;
    private Bundle mExtras;
    private Realm mRealm;
    private boolean mInFavs = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon_viewer);
        ButterKnife.bind(this);

        mImageLoader = MySingleton.getInstance(this).getImageLoader();

        mRealm = Realm.getInstance(new RealmConfiguration.Builder(this)
                .name(RealmController.REALM_NAME)
                .build()
        );

        mExtras = getIntent().getExtras();
        mPokemon = Parcels.unwrap(mExtras.getParcelable("pokemon"));

        /**using parceled Pokemon object if started from MainActivity or pokemon id to get pokemon
         * from database if started from favorites*/
        if(mPokemon == null){
            int pokemonId = mExtras.getInt("pokemonId");
            mRealm.beginTransaction();
            mResult = mRealm.where(PokeRealm.class).equalTo("mId",pokemonId).findAll();
            mRealm.commitTransaction();
            mPokemon = RealmController.convertFromRealm(mResult).get(0);
        }

        /**check if pokemon is in favorites already*/
        mResult = mRealm.where(PokeRealm.class).equalTo("mId",mPokemon.getId()).findAll();
        if(!mResult.isEmpty()){
            fabFavAddRemove.setImageResource(R.drawable.ic_remove);
            mInFavs = true;

        } else {
            fabFavAddRemove.setImageResource(R.drawable.ic_add);
            mInFavs = false;
        }

        setFields();


    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        mRealm.close();
    }

    @OnClick(R.id.fab_fav_add_remove)
    public void submit(){

        if(!mInFavs) {
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm myRealm) {

                    PokeRealm pokeRealm = myRealm.createObject(PokeRealm.class);

                    RealmController.convertToRealm(pokeRealm, mPokemon);

                }
            }, new Realm.Transaction.Callback() {
                @Override
                public void onSuccess() {
                    Toast.makeText(PokemonViewer.this, R.string.added_to_fav, Toast.LENGTH_SHORT).show();
                    fabFavAddRemove.setImageResource(R.drawable.ic_remove);
                    mInFavs = true;
                }

                @Override
                public void onError(Exception e) {
                    Toast.makeText(PokemonViewer.this, R.string.in_fav, Toast.LENGTH_SHORT).show();
                    mRealm.cancelTransaction();
                }
            });
        } else {
            mRealm.beginTransaction();

            mResult = mRealm.where(PokeRealm.class).equalTo("mId", mPokemon.getId()).findAll();
            if(!mResult.isEmpty()){
                mResult.get(0).removeFromRealm();
            }

            mRealm.commitTransaction();
            Toast.makeText(PokemonViewer.this, R.string.removed_from_favs, Toast.LENGTH_SHORT).show();
            fabFavAddRemove.setImageResource(R.drawable.ic_add);
            mInFavs = false;

        }

    }

    public void setFields(){
        TextView[] statFields = {tvSpeed, tvSpecialDef, tvSpecialAttack, tvDefense, tvAttack, tvHP};

        pokeIcon.setImageUrl(mPokemon.getSprite(), mImageLoader);
        tvPokeName.setText(mPokemon.getName());
        tvSpecies.setText(mPokemon.getSpecies());
        tvHeight.setText(String.valueOf(mPokemon.getHeight()));
        tvWeight.setText(String.valueOf(mPokemon.getWeight()));
        tvForms.setText(mPokemon.getForms().toString().replace('[', ' ').replace(']', ' ').trim());
        tvAbilities.setText(mPokemon.getAbilities().toString().replace('[', ' ').replace(']', ' ').trim());

        for(int i=0; i<statFields.length; i++) {
            statFields[i].setText(String.valueOf(mPokemon.getStats().get(i).getStatValue()));
        }
    }

    /**back key starts proper activity depending on from witch it was started*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)  {
        if (keyCode == KeyEvent.KEYCODE_BACK ) {

            String from = mExtras.getString("from");
            if(from.equals("main")){
                Intent intent = new Intent(PokemonViewer.this, MainActivity.class);
                startActivity(intent);
            } else if (from.equals("list")) {
                mRealm.beginTransaction();
                mResult = mRealm.where(PokeRealm.class).findAllSorted("name");
                mRealm.commitTransaction();
                List<Pokemon> pokemonList = RealmController.convertFromRealm(mResult);
                Intent intent = new Intent(PokemonViewer.this, MyPokemons.class);
                intent.putExtra("pokemonList", Parcels.wrap(pokemonList));
                startActivity(intent);
            }

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }



}
