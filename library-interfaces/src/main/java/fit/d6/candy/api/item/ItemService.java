package fit.d6.candy.api.item;

import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

public interface ItemService extends Service {

    @NotNull
    ItemManager getItemManager();

    @NotNull
    static ItemService getService() {
        return CandyLibrary.getLibrary().getService(ItemService.class);
    }

}