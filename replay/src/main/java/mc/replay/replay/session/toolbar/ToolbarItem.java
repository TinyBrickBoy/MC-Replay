package mc.replay.replay.session.toolbar;

import lombok.Getter;
import mc.replay.common.utils.item.nbt.ItemStackNBT;
import mc.replay.replay.session.ReplayPlayer;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public abstract class ToolbarItem {

    private final String id;
    private final int inventorySlot;
    private final Function<ReplayPlayer, ItemStack> playerItemStack;
    private final ItemStack staticItemStack;
    protected Consumer<ReplayPlayer> onClick = (player) -> {};

    protected ToolbarItem(String id, int inventorySlot, Function<ReplayPlayer, ItemStack> playerItemStack) {
        this.id = id;
        this.inventorySlot = inventorySlot;
        this.playerItemStack = playerItemStack;
        this.staticItemStack = null;
    }

    protected ToolbarItem(String id, int inventorySlot, ItemStack staticItemStack) {
        this.id = id;
        this.inventorySlot = inventorySlot;
        this.playerItemStack = null;
        this.staticItemStack = staticItemStack;
    }

    public final void give(ReplayPlayer player) {
        ItemStack stack = null;
        if (this.staticItemStack != null) {
            stack = this.staticItemStack.clone();
        } else if (this.playerItemStack != null) {
            stack = this.playerItemStack.apply(player).clone();
        }

        if (stack != null) {
            stack = ItemStackNBT.setString(stack, "TOOLBAR_ITEM", this.id);
            player.player().getInventory().setItem(this.inventorySlot, stack);
        }
    }
}