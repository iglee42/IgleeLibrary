package fr.iglee42.igleelib.api.client.screenModules;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.Rectangle2d;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public abstract class InfoArea extends AbstractGui {
    protected final Rectangle2d area;

    protected InfoArea(Rectangle2d area) {
        this.area = area;
    }

    public abstract void draw(MatrixStack transform);
}