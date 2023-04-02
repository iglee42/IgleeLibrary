package fr.iglee42.igleelib.api.client.screenModules;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.renderer.Rectangle2d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Arrays;
import java.util.List;

/*
 *  BluSunrize
 *  Copyright (c) 2021
 *
 *  This code is licensed under "Blu's License of Common Sense"
 *  Details can be found in the license file in the root folder of this project
 */
public class EnergyModule extends InfoArea {
    private final IEnergyStorage energy;

    public EnergyModule(int xMin, int yMin)  {
        this(xMin, yMin, null,8,64);
    }

    public EnergyModule(int xMin, int yMin, IEnergyStorage energy)  {
        this(xMin, yMin, energy,8,64);
    }

    public EnergyModule(int xMin, int yMin, IEnergyStorage energy, int width, int height)  {
        super(new Rectangle2d(xMin, yMin, width, height));
        this.energy = energy;
    }

    public List<ITextComponent> getTooltips() {
        return Arrays.asList(new StringTextComponent(energy.getEnergyStored() + "/" + energy.getMaxEnergyStored() + " FE"));
    }

    @Override
    public void draw(MatrixStack transform) {
        final int height = area.getHeight();
        int stored = (int)(height*(energy.getEnergyStored()/(float)energy.getMaxEnergyStored()));
        fillGradient(
                transform,
                area.getX(), area.getY()+(height-stored),
                area.getX() + area.getWidth(), area.getY() +area.getHeight(),
                0xffb51500, 0xff600b00
        );
    }
}