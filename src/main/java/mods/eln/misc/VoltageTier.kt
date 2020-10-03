package mods.eln.misc

import mods.eln.Eln
import net.minecraft.util.ResourceLocation
import net.minecraftforge.client.IItemRenderer
import org.lwjgl.opengl.GL11

enum class VoltageTier(val voltageLevel: String, val voltage: Double) {
    NEUTRAL("neutral", 0.0),
    TTL("ttl", 5.0),
    LOW("low", 12.0),
    LOW_HOUSEHOLD("low_household", 120.0),
    HIGH_HOUSEHOLD("high_household", 240.0),
    INDUSTRIAL("industrial", 480.0),
    SUBURBAN_GRID("suburban_grid", 13_200.0),
    DISTRIBUTION_GRID("distribution_grid", 55_000.0),
    HIGH_TENSION_GRID("high_tension_grid", 125_000.0);

    fun drawIconBackground(type: IItemRenderer.ItemRenderType) {
        if (!Eln.noVoltageBackground && type == IItemRenderer.ItemRenderType.INVENTORY || type == IItemRenderer.ItemRenderType.FIRST_PERSON_MAP) {
            UtilsClient.drawIcon(type, ResourceLocation("eln", "textures/voltages/$voltageLevel.png"))
        }
    }

    fun fromVoltage(voltage: Double): VoltageTier {
        return when {
            voltage <= NEUTRAL.voltage -> NEUTRAL
            voltage <= TTL.voltage -> TTL
            voltage <= LOW.voltage -> LOW
            voltage <= LOW_HOUSEHOLD.voltage -> LOW_HOUSEHOLD
            voltage <= HIGH_HOUSEHOLD.voltage -> HIGH_HOUSEHOLD
            voltage <= INDUSTRIAL.voltage -> INDUSTRIAL
            voltage <= SUBURBAN_GRID.voltage -> SUBURBAN_GRID
            voltage <= DISTRIBUTION_GRID.voltage -> DISTRIBUTION_GRID
            voltage <= HIGH_TENSION_GRID.voltage -> HIGH_TENSION_GRID
            else -> NEUTRAL
        }
    }

    open fun setGLColor() {
        when (this) {
            NEUTRAL -> return
            TTL -> GL11.glColor3f(.80f, .87f, .82f)
            LOW -> GL11.glColor3f(.80f, .87f, .82f)
            LOW_HOUSEHOLD -> GL11.glColor3f(.96f, .80f, .56f)
            HIGH_HOUSEHOLD -> GL11.glColor3f(.96f, .80f, .56f)
            INDUSTRIAL -> GL11.glColor3f(.86f, .58f, .55f)
            else -> GL11.glColor3f(.55f, .74f, .85f)
        }
    }
}
