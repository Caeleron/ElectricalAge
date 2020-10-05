package mods.eln.transparentnode.electricalfurnace;

import mods.eln.Eln;
import mods.eln.misc.IFunction;
import mods.eln.misc.VoltageTier;
import mods.eln.node.transparent.TransparentNodeDescriptor;
import mods.eln.sim.ThermalLoad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static mods.eln.i18n.I18N.tr;

public class ElectricalFurnaceDescriptor extends TransparentNodeDescriptor {

    public IFunction PfT, thermalPlostfT;
    public double thermalC;
    //public double thermalRp;
    //ThermalLoadInitializer thermal;

    public ElectricalFurnaceDescriptor(String name, IFunction PfT, IFunction thermalPlostfT, double thermalC) {
        super(name, ElectricalFurnaceElement.class, ElectricalFurnaceRender.class);
        this.PfT = PfT;
        this.thermalPlostfT = thermalPlostfT;
        this.thermalC = thermalC;

        voltageTier = VoltageTier.NEUTRAL;
    }

    public void applyTo(ThermalLoad load) {
        load.set(Double.POSITIVE_INFINITY, thermalPlostfT.getValue(0), thermalC);
    }

    public void refreshTo(ThermalLoad load, double conductionFactor) {
        double Rp = (load.Tc / thermalPlostfT.getValue(load.Tc)) / conductionFactor;
        if (Rp < 0.1) Rp = 0.1;
        load.setRp(Rp);
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type == ItemRenderType.INVENTORY) {
            super.renderItem(type, item, data);
        } else {
            Eln.obj.draw("ElectricFurnace", "furnace");
        }
    }

    @Override
    public void addInfo(@NotNull ItemStack itemStack, @NotNull EntityPlayer entityPlayer, @NotNull List list) {
        super.addInfo(itemStack, entityPlayer, list);
        Collections.addAll(list, tr("Similar to a vanilla furnace,\nbut heats with electricity.").split("\n"));
    }
}
