package com.gmail.muskankataria2408.shaktiworks;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;

class RealmMigrations implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {

    }

    @Override
    public int hashCode() {
        return 37;
    }

    @Override
    public boolean equals(Object o) {
        return (o instanceof RealmMigrations);
    }
}
