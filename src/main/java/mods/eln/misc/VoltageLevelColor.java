package mods.eln.misc;

import mods.eln.Eln;
import mods.eln.sixnode.genericcable.GenericCableDescriptor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.IItemRenderer;
import org.lwjgl.opengl.GL11;

public enum VoltageLevelColor {
    None(null),
    Neutral("neutral"),
    SignalVoltage("signal"),
    LowVoltage("low"),
    MediumVoltage("medium"),
    HighVoltage("high"),
    VeryHighVoltage("veryhigh"),
    Grid("grid"),
    HighGrid("highgrid"),
    Thermal("thermal");

    VoltageLevelColor(final String voltageLevel) {
        this.voltageLevel = voltageLevel;
    }

    public void drawIconBackground(IItemRenderer.ItemRenderType type) {
        if (!Eln.noVoltageBackground && voltageLevel != null &&
            type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.FIRST_PERSON_MAP) {
            UtilsClient.drawIcon(type, new ResourceLocation("eln", "textures/voltages/" + voltageLevel + ".png"));
        }
    }

    private final String voltageLevel;

    public static VoltageLevelColor fromVoltage(double voltage) {
        return null;
    }

    public static VoltageLevelColor fromCable(GenericCableDescriptor descriptor) {
        if (descriptor != null) {
            return fromVoltage(descriptor.electricalNominalVoltage);
        } else {
            return None;
        }
    }

    public void setGLColor() {
        switch (this) {
            case None:
            case Neutral:
                break;

                // TODO: Remove this tier.
            case SignalVoltage:
                GL11.glColor3f(.80f, .87f, .82f);
                break;

            case LowVoltage:
                GL11.glColor3f(.55f, .84f, .68f);
                break;

            case MediumVoltage:
                GL11.glColor3f(.96f, .80f, .56f);
                break;

            case HighVoltage:
                GL11.glColor3f(.86f, .58f, .55f);
                break;

                // TODO: Remove this tier.
            case VeryHighVoltage:
                GL11.glColor3f(.55f, .74f, .85f);
                break;

        }
    }
}

