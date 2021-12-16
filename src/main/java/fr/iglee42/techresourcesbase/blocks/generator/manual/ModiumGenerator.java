package fr.iglee42.techresourcesbase.blocks.generator.manual;

import fr.iglee42.techresourcesbase.blocks.generator.GeneratorBase;
import fr.iglee42.techresourcesbase.init.ModBlock;
import fr.iglee42.techresourcesbase.items.Gessence;
import fr.iglee42.techresourcesbase.tiles.generator.manual.ModiumGeneratorTile;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class ModiumGenerator extends GeneratorBase {
    public ModiumGenerator() {
        super(Properties.of(Material.METAL, MaterialColor.COLOR_LIGHT_GREEN).harvestTool(ToolType.PICKAXE).harvestLevel(2).requiresCorrectToolForDrops().strength(4.0F, 6.0F).sound(SoundType.METAL));
    }

    @Override
    public List<ItemStack> getDrops(BlockState p_220076_1_, LootContext.Builder p_220076_2_) {
        List<ItemStack> stack = new ArrayList<>();
        stack.add(new ItemStack(ModBlock.MODIUM_GENERATOR.get()));
        return stack;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new ModiumGeneratorTile();
    }
    @Override
    public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.getBlockEntity(pos) instanceof ModiumGeneratorTile) {
            ModiumGeneratorTile te = (ModiumGeneratorTile) worldIn.getBlockEntity(pos);
            assert te != null;
            if (te.hasGessence()){
                if (player.getMainHandItem().getItem() == Items.AIR){
                    if (player.isCrouching()){
                        InventoryHelper.dropItemStack(worldIn,pos.getX(),pos.above().getY(),pos.getZ(),new ItemStack(te.getGessence().getGessence().get(),1));
                        te.setGessence(null);
                        return ActionResultType.SUCCESS;
                    } else {
                        if (te.getDelay() <= 0){
                            te.setDelay(5);
                            InventoryHelper.dropItemStack(worldIn,pos.getX(),pos.above().getY(),pos.getZ(),new ItemStack(te.getGessence().getItem(),16));
                            return ActionResultType.SUCCESS;
                        } else {
                            player.displayClientMessage(new StringTextComponent("§cYou can't click (".substring(1) + te.getDelay() + ")"), true);
                            return ActionResultType.FAIL;
                        }
                    }
                }

            }
            if (Gessence.isGessence(player.getMainHandItem())){
                te.setGessence(((Gessence)player.getMainHandItem().getItem()).getType());
                player.getMainHandItem().setCount(player.getMainHandItem().getCount() -1);
                return ActionResultType.CONSUME;
            }
        }
        return ActionResultType.PASS;
    }





}
