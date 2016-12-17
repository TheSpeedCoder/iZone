package net.techguard.izone.managers;

import net.techguard.izone.configuration.ConfigManager;
import net.techguard.izone.util.ParticleEffect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

/**
 * Class:
 *
 * @author TryHardDood
 */
public class Visualizer {
	private Player   player;
	private Location loc1;
	private Location loc2;

	public Visualizer(Player player, Location loc1, Location loc2) {
		this.player = player;
		this.loc1 = loc1;
		this.loc2 = loc2;
	}

	public void visualize() {
		int minx = Math.min(getLoc1().getBlockX(), getLoc2().getBlockX());
		int maxx = Math.max(getLoc1().getBlockX(), getLoc2().getBlockX());

		int miny = Math.min(getLoc1().getBlockY(), getLoc2().getBlockY());
		int maxy = Math.max(getLoc1().getBlockY(), getLoc2().getBlockY());

		int minz = Math.min(getLoc1().getBlockZ(), getLoc2().getBlockZ());
		int maxz = Math.max(getLoc1().getBlockZ(), getLoc2().getBlockZ());

		Block b;
		World w = getLoc1().getWorld();

		Location loc = getPlayer().getLocation();
		loc.add((double) ConfigManager.getViewDistance(), (double) ConfigManager.getViewDistance(), (double) ConfigManager.getViewDistance());

		for (int x = minx; x <= maxx; x++)
		{
			for (int y = miny; y <= maxy; y++)
			{
				for (int z = minz; z <= maxz; z++)
				{
					if (loc.getX() < x || loc.getY() < y || loc.getZ() < z)
					{
						break;
					}

					b = w.getBlockAt(x, y, z);
					if (isOutline(b.getLocation(), minx, maxx, miny, maxy, minz, maxz))
					{
						ParticleEffect particleEffect = ParticleEffect.valueOf(ConfigManager.getParticle());
						if (particleEffect == null)
						{
							System.out.println("ERROR! Invalid particles in iZone/config.yml");
							return;
						}
						particleEffect.display((float)x, (float)y, (float)z, 0, 1, b.getLocation(), player);
					}
				}
			}
		}
	}

	private boolean isOutline(Location l, int xmin, int xmax, int ymin, int ymax, int zmin, int zmax) {
		int x = l.getBlockX();
		int y = l.getBlockY();
		int z = l.getBlockZ();
		return (x == xmin || x == xmax || y == ymin || y == ymax || z == zmin || z == zmax);
	}

	public Player getPlayer() {
		return player;
	}

	public Location getLoc2() {
		return loc2;
	}

	public Location getLoc1() {
		return loc1;
	}
}
