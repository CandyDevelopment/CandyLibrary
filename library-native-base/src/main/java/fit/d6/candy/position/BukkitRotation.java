package fit.d6.candy.position;

import fit.d6.candy.api.position.Rotation;

public class BukkitRotation implements Rotation {

    private final float yaw;
    private final float pitch;

    public BukkitRotation(float yaw, float pitch) {
        this.yaw = yaw;
        this.pitch = pitch;
    }

    @Override
    public float getYaw() {
        return this.yaw;
    }

    @Override
    public float getPitch() {
        return this.pitch;
    }

}
