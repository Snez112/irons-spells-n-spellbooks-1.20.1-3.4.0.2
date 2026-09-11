package io.redspace.ironsspellbooks.config;

import io.redspace.ironsspellbooks.api.registry.SpellRegistry;
import io.redspace.ironsspellbooks.api.spells.AbstractSpell;

import java.util.List;

public final class SpellDiscovery {
    public static List<AbstractSpell> getSpellsForConfig() {
        return SpellRegistry.getAllSpells();
    }
}