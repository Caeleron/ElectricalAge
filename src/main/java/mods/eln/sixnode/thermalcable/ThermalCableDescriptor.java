package mods.eln.sixnode.thermalcable;

import mods.eln.Eln;
import mods.eln.cable.CableRenderDescriptor;
import mods.eln.misc.Utils;
import mods.eln.misc.VoltageTier;
import mods.eln.node.six.SixNodeDescriptor;
import mods.eln.sim.ThermalLoad;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

import static mods.eln.i18n.I18N.tr;

public class ThermalCableDescriptor extends SixNodeDescriptor {
    double thermalRp = 1, thermalRs = 1, thermalC = 1;
    double thermalWarmLimit, thermalCoolLimit;
    double thermalStdT, thermalStdPower;
    double thermalStdDrop, thermalStdLost;
    double thermalTao;

    String description = "todo cable";

    public CableRenderDescriptor render;

    public static final ThermalCableDescriptor[] list = new ThermalCableDescriptor[256];

    public ThermalCableDescriptor(String name,
                                  double thermalWarmLimit, double thermalCoolLimit,
                                  double thermalStdT, double thermalStdPower,
                                  double thermalStdDrop, double thermalStdLost,
                                  double thermalTao,
                                  CableRenderDescriptor render,
                                  String description) {
        super(name, ThermalCableElement.class, ThermalCableRender.class);

        this.description = description;
        this.render = render;

        this.thermalWarmLimit = thermalWarmLimit;
        this.thermalCoolLimit = thermalCoolLimit;
        this.thermalStdT = thermalStdT;
        this.thermalStdPower = thermalStdPower;
        this.thermalStdDrop = thermalStdDrop;
        this.thermalStdLost = thermalStdLost;
        this.thermalTao = thermalTao;

        thermalRs = thermalStdDrop / 2 / thermalStdPower;
        thermalRp = thermalStdT / thermalStdLost;
        thermalC = Eln.simulator.getMinimalThermalC(thermalRs, thermalRp);
        if (!Eln.simulator.checkThermalLoad(thermalRs, thermalRp, thermalC)) {
            Utils.println("Bad thermalCable setup");
            while (true) ;
        }
        voltageTier = VoltageTier.NEUTRAL;
    }

    public static ThermalCableDescriptor getDescriptorFrom(ItemStack itemStack) {
        return list[(itemStack.getItemDamage() >> 8) & 0xFF];
    }

    public void setThermalLoad(ThermalLoad thermalLoad) {
        thermalLoad.Rp = thermalRp;
        thermalLoad.Rs = thermalRs;
        thermalLoad.C = thermalC;
    }

    @Override
    public void addInfo(@NotNull ItemStack itemStack, @NotNull EntityPlayer entityPlayer, @NotNull List list) {
        super.addInfo(itemStack, entityPlayer, list);

        list.add(tr("Max. temperature: %1$°C", Utils.plotValue(thermalWarmLimit)));
        list.add(tr("Serial resistance: %1$K/W", Utils.plotValue(thermalRs * 2)));
        list.add(tr("Parallel resistance: %1$K/W", Utils.plotValue(thermalRp)));
        list.add("");
        Collections.addAll(list, tr("Low serial resistance\n => High conductivity.").split("\n"));
        Collections.addAll(list, tr("High parallel resistance\n => Low power dissipation.").split("\n"));
    }
}
