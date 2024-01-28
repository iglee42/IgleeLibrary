package fr.iglee42.igleelib.api.utils;

import fr.iglee42.igleelib.common.blocks.entity.GhostBlockEntity;
import fr.iglee42.igleelib.common.init.ModBlock;
import net.minecraft.block.BlockState;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


public class ModsUtils {

    public static String[] split(String base,String separator){
        String[] st = base.split(separator);
        String[] finale = new String[st.length];
        int i = 0;
        for (String s : st){
            String fi = s;
            if (s.endsWith(separator))fi = s.substring(s.length()-1);
            finale[i] = fi;
            i += 1;
        }
        return finale;
    }

    public static String getUpperName(String name,String wordSeparator) {
        String[] nm = split(name,wordSeparator);
        StringBuilder end = new StringBuilder();
        int i = 0;
        for (String n : nm) {
            i += 1;
            char fc = n.charAt(0);
            String fcs = String.valueOf(fc);
            String fs = fcs.toUpperCase() + n.substring(1);
            end.append(fs).append(i == nm.length ? "" : " ");
        }

        return end.toString();
    }

    public static void placeGhostBlock(ServerWorld level, BlockPos pos, BlockState blockState, int dispearTime){
        level.setBlockAndUpdate(pos, ModBlock.GHOST_BLOCK.get().defaultBlockState());
        if (level.getBlockEntity(pos) instanceof GhostBlockEntity){
            GhostBlockEntity g = (GhostBlockEntity) level.getBlockEntity(pos);
            g.setStockedBlock(blockState);
            g.setDispearTime(dispearTime);
        }
    }

    public static void saveStringToBuffer(PacketBuffer buf , String s){
        buf.writeInt(s.length());
        for (char c : s.toCharArray()){
            buf.writeChar(c);
        }
    }

    public static String readStringFromBuffer(PacketBuffer buf){
        int length = buf.readInt();
        char[] word = new char[length];
        for (int i = 0; i < length; i++){
            word[i] = buf.readChar();
        }
        return String.valueOf(word);
    }
    public static <T extends IParticleData> void spawnParticle(T particleType, ServerWorld level , Vector3d fromPos, Vector3d goPos, int count) {
        if (level == null || level.isClientSide())
            return;


        double x = fromPos.x();
        double y = fromPos.y();
        double z = fromPos.z();

        double velX = goPos.x() - fromPos.x();
        double velY = goPos.y() - fromPos.y();
        double velZ = goPos.z() - fromPos.z();

        level.sendParticles(particleType, x, y, z, count, velX, velY, velZ, 0.09D);
    }

    public static void debugSign(World level, BlockPos pos, String... lines){
        if (level.getBlockEntity(pos.west()) instanceof SignTileEntity s){
            for (int i = 0; i < lines.length; i++) {
                if (i < 3) break;
                s.setMessage(i,new StringTextComponent(lines[i]));
            }
        }
    }



}
