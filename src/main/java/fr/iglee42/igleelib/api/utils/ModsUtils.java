package fr.iglee42.igleelib.api.utils;

import fr.iglee42.igleelib.common.init.ModBlock;
import fr.iglee42.igleelib.common.init.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

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
    public static void placeGhostBlock(ServerLevel level, BlockPos pos, BlockState blockState, int dispearTime){
        level.setBlockAndUpdate(pos, ModBlock.GHOST_BLOCK.get().defaultBlockState());
        level.getBlockEntity(pos, ModBlockEntities.GHOST_BLOCK.get()).ifPresent(g -> {
            g.setStockedBlock(blockState);
            g.setDispearTime(dispearTime);
        });
    }

    public static void saveStringToBuffer(FriendlyByteBuf buf , String s){
        buf.writeInt(s.length());
        for (char c : s.toCharArray()){
            buf.writeChar(c);
        }
    }

    public static String readStringFromBuffer(FriendlyByteBuf buf){
        int length = buf.readInt();
        char[] word = new char[length];
        for (int i = 0; i < length; i++){
            word[i] = buf.readChar();
        }
        return String.valueOf(word);
    }
    public static <T extends ParticleOptions> void spawnParticle(T particleType, ServerLevel level , Vec3 fromPos, Vec3 goPos, int count) {
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

    public static void debugSign(Level level, BlockPos pos, String... lines){
        if (level.getBlockEntity(pos.west()) instanceof SignBlockEntity s){
            for (int i = 0; i < lines.length; i++) {
                if (i < 3) break;
                s.setMessage(i,new TextComponent(lines[i]));
            }
        }
    }



}
