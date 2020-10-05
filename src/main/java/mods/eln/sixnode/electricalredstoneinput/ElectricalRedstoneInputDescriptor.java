package mods.eln.sixnode.electricalredstoneinput;

import mods.eln.misc.*;
import mods.eln.misc.Obj3D.Obj3DPart;
import mods.eln.node.six.SixNodeDescriptor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;

import java.util.Collections;
import java.util.List;

import static mods.eln.i18n.I18N.tr;

public class ElectricalRedstoneInputDescriptor extends SixNodeDescriptor {

    public float[] pinDistance;

    Obj3D obj;
    Obj3DPart main, led;

    public ElectricalRedstoneInputDescriptor(String name, Obj3D obj) {
        super(name, ElectricalRedstoneInputElement.class, ElectricalRedstoneInputRender.class);
        //obj = Eln.instance.obj.getObj(objName);
        this.obj = obj;
        if (obj != null) {
            main = obj.getPart("main");
            led = obj.getPart("led");

            pinDistance = Utils.getSixNodePinDistance(main);
        }

        voltageTier = VoltageTier.TTL;
    }

    void draw(int redstone) {
        //LRDU.Down.glRotateOnX();
        if (main != null) main.draw();

        float light = redstone / 15f;
        GL11.glColor4f(light, light, light, 1f);
        UtilsClient.drawLight(led);
    }

    @Override
    public void addInfo(@NotNull ItemStack itemStack, @NotNull EntityPlayer entityPlayer, @NotNull List list) {
        super.addInfo(itemStack, entityPlayer, list);
        Collections.addAll(list, tr("Converts Redstone signal\nto an electrical voltage.").split("\n"));
    }

    @Override
    public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public boolean handleRenderType(ItemStack item, ItemRenderType type) {
        return true;
    }

    @Override
    public boolean shouldUseRenderHelperEln(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
        return type != ItemRenderType.INVENTORY;
    }

    @Override
    public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
        if (type == ItemRenderType.INVENTORY) {
            super.renderItem(type, item, data);
        } else {
            draw(15);
        }
    }

    @Override
    public LRDU getFrontFromPlace(Direction side, EntityPlayer player) {
        return super.getFrontFromPlace(side, player).right();
    }
}
