package mods.eln.sim.nbt;

import mods.eln.Eln;
import mods.eln.misc.Utils;
import mods.eln.misc.VoltageTier;

public class NbtElectricalGateInputOutput extends NbtElectricalLoad {

    public NbtElectricalGateInputOutput(String name) {
        super(name);
        Eln.smallInsulationLowCurrentCopperCable.applyTo(this);
    }

    public String plot(String str) {
        return str + " " + Utils.plotVolt("", getU()) + Utils.plotAmpere("", getCurrent());
    }

    public boolean isInputHigh() {
        return getU() > VoltageTier.TTL.getVoltage() * 0.6;
    }

    public boolean isInputLow() {
        return getU() < VoltageTier.TTL.getVoltage() * 0.2;
    }

    public double getInputNormalized() {
        double norm = getU() * (1.0 / VoltageTier.TTL.getVoltage());
        if (norm < 0.0) norm = 0.0;
        if (norm > 1.0) norm = 1.0;
        return norm;
    }

    public double getInputBornedU() {
        double U = this.getU();
        if (U < 0.0) U = 0.0;
        if (U > VoltageTier.TTL.getVoltage()) U = VoltageTier.TTL.getVoltage();
        return U;
    }
}
