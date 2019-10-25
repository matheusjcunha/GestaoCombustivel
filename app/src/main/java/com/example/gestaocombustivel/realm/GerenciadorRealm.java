package com.example.gestaocombustivel.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class GerenciadorRealm implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        for(long versao = oldVersion; versao < newVersion; versao++){
            migrar(realm, versao, versao + 1);
        }
    }

    private void migrar(DynamicRealm realm, long versaoVelha, long versaoNova){
        if(versaoVelha == 0 && versaoNova == 1){
            RealmSchema schema = realm.getSchema();
            RealmObjectSchema abastecimentoSchema = schema.get("Abastecimento");
            abastecimentoSchema.addField( "caminhoFoto", String.class );
        }
    }

}
