package me.frxq15.boostersx.datafactory;

import me.frxq15.boostersx.BoostersX;
import me.frxq15.boostersx.datafactory.sql.SQLGPlayerFactory;
import me.frxq15.boostersx.datafactory.yml.YMLGPlayerFactory;

public class DataFactoryHandler {
    private DataFactory dataFactory;

    public DataFactoryHandler(BoostersX plugin) {
        switch(plugin.getSettings().getStorageType()) {
            case MYSQL:
                this.dataFactory = new SQLGPlayerFactory(plugin, plugin.getSQLManager());
                break;
            case YML:
                this.dataFactory = new YMLGPlayerFactory(plugin);
                break;
            default:
                this.dataFactory = new YMLGPlayerFactory(plugin);
                //unsupported type
                break;
        }
    }
    public DataFactory getFactory() {
        return this.dataFactory;
    }
}
