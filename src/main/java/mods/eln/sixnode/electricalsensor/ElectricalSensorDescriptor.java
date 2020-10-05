package mods.eln.sixnode.electricalsensor;

import mods.eln.Eln;
import mods.eln.misc.Direction;
import mods.eln.misc.LRDU;
import mods.eln.misc.Obj3D.Obj3DPart;
import mods.eln.misc.VoltageTier;
import mods.eln.node.six.SixNodeDescriptor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static mods.eln.i18n.I18N.tr;

public class ElectricalSensorDescriptor extends SixNodeDescriptor {

    boolean voltageOnly;
    Obj3DPart main;

    public ElectricalSensorDescriptor(
        String name, String modelName,
        boolean voltageOnly) {
        super(name, ElectricalSensorElement.class, ElectricalSensorRender.class);
        this.voltageOnly = voltageOnly;
        main = Eln.obj.getPart(modelName, "main");

        voltageTier = VoltageTier.TTL;
    }

    void draw() {
        if (main != null) main.draw();
    }

    @Override
    public void addInfo(@NotNull ItemStack itemStack, @NotNull EntityPlayer entityPlayer, @NotNull List list) {
        super.addInfo(itemStack, entityPlayer, list);
        if (voltageOnly) {
            list.add(tr("Measures voltage on cables."));
            list.add(tr("Has a signal output."));
        } else {
            list.add(tr("Measures electrical values on cables."));
            list.add(tr("Can measure Voltage/Power/Current"));
            list.add(tr("Has a signal output."));
        }
    }

    @Override
    public LRDU getFrontFromPlace(Direction side, EntityPlayer player) {
        return super.getFrontFromPlace(side, player).inverse();
    }
}
