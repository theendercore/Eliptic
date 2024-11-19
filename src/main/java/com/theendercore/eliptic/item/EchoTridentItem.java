package com.theendercore.eliptic.item;

import com.theendercore.eliptic.init.ELPConfig;
import com.theendercore.eliptic.init.ELPData;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class EchoTridentItem extends TridentItem {
    private final int cooldown = 0;

    public EchoTridentItem() {
        super((new Item.Settings()).group(ItemGroup.COMBAT).maxDamage(250).rarity(Rarity.RARE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        BlockPos playerPos = player.getBlockPos();
        BlockPos blockBelowPos = playerPos.down();
        BlockState blockBelowState = world.getBlockState(blockBelowPos);
        if (ELPData.getEcho(player)) {
            if (player instanceof ServerPlayerEntity sPlayer) {
                ELPData.setEcho(sPlayer, false);
                ELPData.setEcho(sPlayer, false);
            }
            return TypedActionResult.consume(stack);
        } else if (!blockBelowState.isAir()) {
            RegistryKey<World> dim = world.getRegistryKey();
            if (dim != World.END && world.getBlockState(blockBelowPos).getBlock() != Blocks.AIR) {
                player.setPos(playerPos.getX(), playerPos.getY() + ELPConfig.TP_HEIGHT.get(), playerPos.getZ());
                if (player instanceof ServerPlayerEntity sPlayer) {
                    sPlayer.networkHandler.requestTeleportAndDismount(playerPos.getX(), playerPos.getY() + ELPConfig.TP_HEIGHT.get(), playerPos.getZ(), player.getYaw(), player.getPitch());
                    ELPData.setEcho(sPlayer, true);
                    ELPData.setOverlay(sPlayer, true);
                    stack.damage(1, sPlayer, (it) -> {
                    });
                }
                return TypedActionResult.consume(stack);
            }
            return TypedActionResult.pass(stack);
        } else {
            return super.use(world, player, hand);
        }
    }

   /* public InteractionResultHolder<ItemStack> m_7203_(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.m_21120_(hand);
        BlockPos playerPos = player.m_20183_();
        BlockPos blockBelowPos = playerPos.m_7495_();
        BlockState blockBelowState = world.m_8055_(blockBelowPos);
        if (this.cooldown == 0 && player.getPersistentData().m_128471_("echo")) {
            int riptideLevel = EnchantmentHelper.m_44843_(Enchantments.f_44957_, itemstack);
            Vec3 lookDirection = player.m_20154_();
            double d0 = lookDirection.f_82479_;
            double d1 = lookDirection.f_82480_;
            double d2 = lookDirection.f_82481_;
            Vec3 motion = player.m_20184_();
            float f = Mth.m_14116_((float) (motion.f_82479_ * motion.f_82479_ + motion.f_82481_ * motion.f_82481_));
            float f1 = 1.2F + 0.2F * f + (float) riptideLevel * 0.2F;
            player.f_19864_ = true;
            player.m_20256_(motion.m_82520_(d0 * (double) f1, d1 * (double) f1 + 1.0, d2 * (double) f1));
            itemstack.m_41622_(1, player, (p_220038_1_) -> {
                p_220038_1_.m_21190_(player.m_7655_());
            });
            world.m_6263_((Player) null, player.m_20185_(), player.m_20186_(), player.m_20189_(), SoundEvents.f_12517_, player.m_5720_(), 1.0F, 1.0F);
            player.m_204079_(20);
            player.m_36335_().m_41524_(this, 10);
            return InteractionResultHolder.m_19090_(itemstack);
        } else if (!blockBelowState.m_60795_()) {
            EchotridentRightclickedProcedure.execute(world, (double) playerPos.m_123341_(), (double) playerPos.m_123342_(), (double) playerPos.m_123343_(), player, itemstack);
            return InteractionResultHolder.m_19090_(itemstack);
        } else {
            return super.m_7203_(world, player, hand);
        }
    }*/
}

