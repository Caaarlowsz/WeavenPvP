package br.com.skyprogrammer.cophenix.zenixpvp.utilitaries.constructor;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_7_R4.entity.CraftSnowball;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

import br.com.skyprogrammer.cophenix.zenixpvp.Handler;
import net.minecraft.server.v1_7_R4.EntityFishingHook;
import net.minecraft.server.v1_7_R4.EntityHuman;
import net.minecraft.server.v1_7_R4.EntitySnowball;
import net.minecraft.server.v1_7_R4.Packet;
import net.minecraft.server.v1_7_R4.PacketPlayOutEntityDestroy;

public class GrapplerHook extends EntityFishingHook {
	private Snowball instanceOfSnowBall;
	private EntitySnowball instanceofEntitySnowBall;
	public int publicInteger;
	public EntityHuman instanceOfEntityHuman;
	public Entity instanceOfEntity;
	public boolean lastControllerDead;
	public boolean isHooked;

	public void Grappler(final Handler instanceOfHandler) {
	}

	public GrapplerHook(final World world, final EntityHuman entityhuman) {
		super((net.minecraft.server.v1_7_R4.World) ((CraftWorld) world).getHandle(), entityhuman);
		this.instanceOfEntityHuman = entityhuman;
	}

	protected void c() {
	}

	public void h() {
		this.lastControllerDead = this.instanceofEntitySnowBall.dead;
		for (final Entity entity : this.instanceofEntitySnowBall.world.getWorld().getEntities()) {
			if (!(entity instanceof Firework) && entity.getEntityId() != this.getBukkitEntity().getEntityId()
					&& entity.getEntityId() != this.instanceOfEntityHuman.getBukkitEntity().getEntityId()
					&& entity.getEntityId() != this.instanceofEntitySnowBall.getBukkitEntity().getEntityId()) {
				if (entity.getLocation()
						.distance(this.instanceofEntitySnowBall.getBukkitEntity().getLocation()) >= 2.0) {
					if (!(entity instanceof Player)) {
						continue;
					}
					((Player) entity).getEyeLocation()
							.distance(this.instanceofEntitySnowBall.getBukkitEntity().getLocation());
				} else {
					this.instanceofEntitySnowBall.die();
					this.instanceOfEntity = entity;
					this.isHooked = true;
					this.locX = entity.getLocation().getX();
					this.locY = entity.getLocation().getY();
					this.locZ = entity.getLocation().getZ();
					this.motX = 0.0;
					this.motY = 0.04;
					this.motZ = 0.0;
				}
			}
		}
		try {
			this.locX = this.instanceOfEntity.getLocation().getX();
			this.locY = this.instanceOfEntity.getLocation().getY();
			this.locZ = this.instanceOfEntity.getLocation().getZ();
			this.motX = 0.0;
			this.motY = 0.04;
			this.motZ = 0.0;
			this.isHooked = true;
		} catch (Exception e) {
			if (this.instanceofEntitySnowBall.dead) {
				this.isHooked = true;
			}
			this.locX = this.instanceofEntitySnowBall.locX;
			this.locY = this.instanceofEntitySnowBall.locY;
			this.locZ = this.instanceofEntitySnowBall.locZ;
		}
	}

	public void die() {
	}

	public void spawn(final Location location) {
		this.instanceOfSnowBall = (Snowball) this.instanceOfEntityHuman.getBukkitEntity()
				.launchProjectile(Snowball.class);
		this.instanceofEntitySnowBall = ((CraftSnowball) this.instanceOfSnowBall).getHandle();
		final PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(
				new int[] { this.instanceofEntitySnowBall.getId() });
		Player[] arrayOfPlayer;
		for (int j = (arrayOfPlayer = Bukkit.getOnlinePlayers()).length, i = 0; i < j; ++i) {
			final Player p = arrayOfPlayer[i];
			((CraftPlayer) p).getHandle().playerConnection.sendPacket((Packet) packet);
		}
		((CraftWorld) location.getWorld()).getHandle().addEntity((net.minecraft.server.v1_7_R4.Entity) this);
	}

	public void remove() {
		super.die();
	}

	public boolean isHooked() {
		return this.isHooked;
	}

	public void setHookedEntity(final Entity nodamage) {
		this.instanceOfEntity = nodamage;
	}
}
