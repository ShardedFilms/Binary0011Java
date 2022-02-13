package bin0011.content;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.struct.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.ctype.*;
import mindustry.game.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.entities.bullet.*;
import mindustry.world.*;
import mindustry.world.blocks.*;
import mindustry.world.blocks.distribution.*;
import mindustry.world.blocks.units.*;
import mindustry.world.blocks.production.*;
import mindustry.world.blocks.defense.*;
import mindustry.world.blocks.defense.turrets.*;
import mindustry.world.meta.*;
import mindustry.world.draw.*;
import mindustry.*;

import bin0011.content.*;

import static mindustry.type.ItemStack.with;

public class Bin0011Blocks implements ContentList {
	public static Block 
		
		
		// defense
		wall00, wallLarge00,
		// turrets
		turret0000, turret0001, turret0010, turret0011,
		// production
		crft0000;

	@Override
	public void load() {
		// reg defense
		wall00 = new Wall("wall00") {{
			requirements(Category.defense, with(
				Bin0011Items.item00, 6
			));
			localizedName = "wall00";
			size = 1;
			health = 400;
		}};
		wallLarge00 = new Wall("wall-large00") {{
			requirements(Category.defense, with(
				Bin0011Items.item00, 12
			));
			localizedName = "wallLarge00";
			size = 2;
			health = 1600;
		}};
		// end defense
		// reg turret
		turret0000 = new ItemTurret("turret0000"){{
			requirements(Category.turret, with(Bin0011Items.item00, 28), true);
            		ammo(
				Bin0011Items.item00, Bullets.standardCopper,
				Bin0011Items.item01, Bullets.standardHoming,
				Bin0011Items.item11, Bullets.missileSurge
            		);
			localizedName = "turret0000";
            		spread = 4f;
           		shots = 2;
          		alternate = true;
           		reloadTime = 17.5f;
           		restitution = 0.03f;
           		range = 152;
           		shootCone = 15f;
           		ammoUseEffect = Fx.casing1;
           		health = 250;
           	 	inaccuracy = 2f;
           		rotateSpeed = 10f;

            		limitRange(0f);
        	}};
        	turret0001 = new ItemTurret("turret0001"){{
            		requirements(Category.turret, with(Bin0011Items.item00, 32, Bin0011Items.item01, 15));
            		ammo(
				Bin0011Items.item00, Bullets.artilleryDense,
				Bin0011Items.item01, Bullets.artilleryHoming
			);
			localizedName = "turret0001";
            		targetAir = false;
            		reloadTime = 50f;
            		recoilAmount = 1f;
           	 	range = 256f;
            		inaccuracy = 0.5f;
            		shootCone = 10f;
            		health = 325;
            		shootSound = Sounds.bang;
            		limitRange(0f);
        	}};
		turret0010 = new PowerTurret("turret0010"){{
			requirements(Category.turret, with(Bin0011Items.item00, 75, Bin0011Items.item01, 45, Bin0011Items.item10, 30));
            		localizedName = "turret0010";
			range = 192f;
            		chargeTime = 40f;
            		chargeMaxDelay = 25f;
            		chargeEffects = 9;
            		recoilAmount = 1.5f;
            		reloadTime = 50f;
            		cooldown = 0.05f;
            		powerUse = 4.7f;
            		shootShake = 2f;
            		shootEffect = Fx.shootBig;
            		smokeEffect = Fx.smokeCloud;
            		chargeEffect = Fx.none;
            		chargeBeginEffect = Fx.none;
            		size = 2;
            		health = 290 * size * size;
            		targetAir = true;
            		shootSound = Sounds.laser;
			
			shootType = new LaserBulletType(140){{
                		colors = new Color[]{Pal.darkestGray.a(1f), Pal.darkishGray, Color.white};
                		hitEffect = Fx.hitLancer;
                		hitSize = 6;
                		lifetime = 18f;
                		drawSize = 400f;
                		collidesAir = true;
                		length = 192f;
                		ammoMultiplier = 2f;
            		}};
        	}};
        	turret0011 = new LaserTurret("turret0011"){{
            		requirements(Category.turret, with(Bin0011Items.item00, 75, Bin0011Items.item01, 65, Bin0011Items.item10, 35));
            		shootEffect = Fx.shootBigSmoke2;
            		shootCone = 40f;
            		recoilAmount = 1f;
            		size = 2;
            		shootShake = 2f;
            		range = 240f;
            		reloadTime = 90f;
            		firingMoveFract = 0.5f;
           		shootDuration = 480f;
            		powerUse = 5f;
            		shootSound = Sounds.laserbig;
            		loopSound = Sounds.beam;
            		loopSoundVolume = 2f;

            		shootType = new ContinuousLaserBulletType(20){{
                		colors = new Color[]{Pal.darkestGray.a(1f), Pal.darkerGray, Pal.darkishGray, Color.white};
				length = 240f;
                		hitEffect = Fx.hitMeltdown;
                		hitColor = Pal.gray;
                		status = StatusEffects.shocked;
                		drawSize = 32f;

                		incendChance = 1f;
                		incendSpread = 7f;
                		incendAmount = 2;
            		}};
            		health = 225 * size * size;
        	}};

		// end turret
		// reg production
		crft0000 = new GenericCrafter("crft0000") {{
			requirements(Category.crafting, with(
				Items.copper, 60,
				Items.lead, 35
			));
			size = 3;
			health = 280 * size * size;
			itemCapacity = 50;
			craftTime = 60f;
			craftEffect = Fx.smelt;
			drawer = new DrawSmelter(Color.valueOf("FFFFFF"));
			consumes.items(with(
				Items.copper, 2,
				Items.coal, 1
			));
			consumes.power(1.2f);
			outputItem = new ItemStack(Bin0011Items.item00, 1);
		}};
		// end production
	}
}

