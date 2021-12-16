package fr.iglee42.techresourcesbase.blocks.properties;

import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

public enum ConnectedBlockType implements IStringSerializable {

    SINGLE("single"),
    TOP("top"),
    CENTER("center"),
    BOTTOM("bottom");

    private final String name;

    ConnectedBlockType(String name) {
        this.name = name;
    }


    public static ConnectedBlockType getTypeForConnections(boolean connectUp, boolean connectDown, Direction.Axis axis) {
        ConnectedBlockType connectedBlockType = ConnectedBlockType.SINGLE;

        if (connectUp && connectDown) {
            connectedBlockType = ConnectedBlockType.CENTER;
        } else if (connectUp) {
            connectedBlockType = ConnectedBlockType.BOTTOM;
        } else if (connectDown) {
            connectedBlockType = ConnectedBlockType.TOP;
        }

        if (axis == Direction.Axis.Z) {
            if (connectedBlockType == ConnectedBlockType.BOTTOM) {
                connectedBlockType = ConnectedBlockType.TOP;
            } else if (connectedBlockType == ConnectedBlockType.TOP) {
                connectedBlockType = ConnectedBlockType.BOTTOM;
            }
        }

        return connectedBlockType;
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }
}