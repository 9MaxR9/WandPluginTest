package com.maximeruys.spels;

import java.util.HashMap;
import java.util.Map;

public class SpellManager {

    public static Map<String, Spell> SPELLS = new HashMap<String, Spell>();

    public SpellManager(){
        AvadaKedavraSpell avadaKadavraSpell = new AvadaKedavraSpell();
        SPELLS.put(avadaKadavraSpell.name(), avadaKadavraSpell);
    }

    public static Spell getSpellByName(String name){
        for(String spellName: SPELLS.keySet()){
            if(spellName.equalsIgnoreCase(name)){
                return SPELLS.get(name);
            }
        }
        return null;
    }

}
