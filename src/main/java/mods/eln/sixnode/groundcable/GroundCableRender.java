package mods.eln.sixnode.groundcable;

import mods.eln.Eln;
import mods.eln.cable.CableRenderDescriptor;
import mods.eln.misc.Direction;
import mods.eln.node.six.SixNodeDescriptor;
import mods.eln.node.six.SixNodeElementRender;
import mods.eln.node.six.SixNodeEntity;

public class GroundCableRender extends SixNodeElementRender {

    GroundCableDescriptor descriptor;

    public GroundCableRender(SixNodeEntity tileEntity, Direction side, SixNodeDescriptor descriptor) {
        super(tileEntity, side, descriptor);
        this.descriptor = (GroundCableDescriptor) descriptor;
    }

    @Override
    public void draw() {
        super.draw();
        if (side.isY()) {
            front.glRotateOnX();
        }
        descriptor.draw();
    }

    public CableRenderDescriptor getCableRender(mods.eln.misc.LRDU lrdu) {
        return Eln.uninsulatedHighCurrentRender;
    }
}
