package com.mobiles.msm.contentprovider;

import ckm.simple.sql_provider.UpgradeScript;
import ckm.simple.sql_provider.annotation.ProviderConfig;
import ckm.simple.sql_provider.annotation.SimpleSQLConfig;

/**
 * Created by vaibhav on 19/11/16.
 */
@SimpleSQLConfig(
        name = "ProductProvider",
        authority = "com.mobiles.msm.authority",
        database = "product.db",
        version = 1)
public class ProductProviderConfig implements ProviderConfig {
    @Override
    public UpgradeScript[] getUpdateScripts() {
        return new UpgradeScript[0];
    }
}
