package com.theendercore.eliptic.item;

import com.theendercore.eliptic.init.ELPConfig;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.Rarity;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

import static com.theendercore.eliptic.init.ELPData.getEcho;
import static com.theendercore.eliptic.init.ELPData.setEcho;

public class EchoTridentItem extends TridentItem {
    public EchoTridentItem() {
        super(new Item.Settings().group(ItemGroup.COMBAT).maxDamage(250).rarity(Rarity.RARE));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        BlockPos pos = player.getBlockPos();
        BlockState stateBelow = world.getBlockState(pos.down());
        if (getEcho(player)) {
            int riptideLevel = EnchantmentHelper.getRiptide(stack);
            Vec3d motion = player.getVelocity();
            double f = MathHelper.sqrt((float) (motion.x * motion.x + motion.z * motion.z));
            double f1 = 1.2F + 0.2F * f + (float) riptideLevel * 0.2F;
            Vec3d dir = player.getRotationVector();
            player.velocityModified = true;
            player.setVelocity(motion.add(dir.x * f1, dir.y * f1 + 1.0, dir.z * f1));
            player.velocityModified = true;
            stack.damage(1, player, it -> it.sendToolBreakStatus(player.getActiveHand()));
            world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_TRIDENT_RIPTIDE_1, player.getSoundCategory(), 1.0F, 1.0F);
            player.useRiptide(20);
            player.getItemCooldownManager().set(this, 10);
            return TypedActionResult.success(stack);
        } else if (!stateBelow.isAir()) {
            RegistryKey<World> dim = world.getRegistryKey();
            if (dim != World.END && stateBelow.getBlock() != Blocks.AIR) {
                player.setPos(pos.getX(), pos.getY() + ELPConfig.TP_HEIGHT.get(), pos.getZ());
                if (player instanceof ServerPlayerEntity sPlayer) {
                    sPlayer.networkHandler.requestTeleportAndDismount(pos.getX(), pos.getY() + ELPConfig.TP_HEIGHT.get(), pos.getZ(), player.getYaw(), player.getPitch());
                    setEcho(sPlayer, true);
                    stack.damage(1, player, it -> it.sendToolBreakStatus(player.getActiveHand()));
                }
                return TypedActionResult.consume(stack);
            }
            return TypedActionResult.pass(stack);
        } else {
            return super.use(world, player, hand);
        }
    }
}

