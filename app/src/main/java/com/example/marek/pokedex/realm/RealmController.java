package com.example.marek.pokedex.realm;

import com.example.marek.pokedex.data.Pokemon;
import com.example.marek.pokedex.data.Stat;


import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/** Realm doesn't support Lists, so this class contains methods that can convert simple Lists
 * to Strings and Strings to simple Lists back again*/
public class RealmController {

    public static final String REALM_NAME = "PokeRealm.realm";

    /**Converts Realm objects list into Pokemon object list*/
    public static List<Pokemon> convertFromRealm(RealmResults<PokeRealm> results){

        List<Pokemon> pokeList = new ArrayList<>();

        for(int i=0; i<results.size(); i++){

            String favPokeSprite = results.get(i).getmSprite();
            String favPokeName = results.get(i).getmName();
            String favPokeSpecies = results.get(i).getmSpecies();
            int favPokeHeight = results.get(i).getmHeight();
            int favPokeWeight = results.get(i).getmWeight();
            int favPokeId = results.get(i).getmId();
            List<String> favPokeForms = realmToPoke(results.get(i).getmForms());
            List<String> favPokeAbilities = realmToPoke(results.get(i).getmAbilities());
            List<Stat> favPokeStats = realmToPoke(results.get(i).getmStats());

            pokeList
                    .add(new Pokemon(
                            favPokeWeight, favPokeAbilities, favPokeForms, favPokeHeight, favPokeId, favPokeName, favPokeSpecies, favPokeSprite, favPokeStats));
        }

        return pokeList;
    }

    /**Converts Strings stored in Realm list into Lists*/
    private static List realmToPoke(String string) {
        if (!string.contains(":")) {
            List<String> stringList = new ArrayList<>();

            String[] strTab = string.replace('[', ' ').replace(']', ' ').trim().split(",");

            for (int i = 0; i < strTab.length; i++) {
                stringList.add(strTab[i].trim());
            }
            return stringList;
        } else {
            List<Stat> stats = new ArrayList<>();

            String[] strTab = string.replace('[', ' ').replace(']', ' ').trim().split(",");

            for(int i = 0; i<strTab.length; i++){
                String[] statTab = strTab[i].split(":");
                Stat pokeStat = new Stat(statTab[0].trim(), Integer.valueOf(statTab[1].trim()));
                stats.add(pokeStat);
            }
            return stats;
        }
    }

    /**Converts Pokemon object into Realm object, generally by converting Lists into Strings*/
    public static void convertToRealm(PokeRealm pokeRealm, Pokemon pokemon){

        pokeRealm.setmAbilities(pokemon.getAbilities().toString());
        pokeRealm.setmForms(pokemon.getForms().toString());
        pokeRealm.setmStats(pokemon.getStats().toString());
        pokeRealm.setmId(pokemon.getId());
        pokeRealm.setmHeight(pokemon.getHeight());
        pokeRealm.setmName(pokemon.getName());
        pokeRealm.setmWeight(pokemon.getWeight());
        pokeRealm.setmSprite(pokemon.getSprite());
        pokeRealm.setmSpecies(pokemon.getSpecies());
    }
}
