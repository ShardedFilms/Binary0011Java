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

import bin0011.content.*;

import static mindustry.type.ItemStack.with;

public class Bin0011Blocks implements ContentList {
	public static Block 
		
		
		// defense
		wall00, wallLarge00,
		// turrets
		turret0000, turret0001, turret0010;

	@Override
	public void load() {
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
		turret0000 = new ItemTurret("turret0000"){{
			requirements(Category.turret, with(Bin0011Items.item00, 28), true);
            		ammo(
				Bin0011Items.item00, Bullets.standardCopper,
				Bin0011Items.item01, Bullets.standardHoming
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
            		range = 192f;
            		chargeTime = 40f;
            		chargeMaxDelay = 25f;
            		chargeEffects = 9;
            		recoilAmount = 1.5f;
            		reloadTime = 50f;
            		cooldown = 0.05f;
            		powerUse = 4.7f;
            		shootShake = 2f;
            		shootEffect = Fx.lancerLaserShoot;
            		smokeEffect = Fx.none;
            		chargeEffect = Fx.lancerLaserCharge;
            		chargeBeginEffect = Fx.lancerLaserChargeBegin;
            		size = 2;
            		health = 290 * size * size;
            		targetAir = true;
            		shootSound = Sounds.laser;
			
			shootType = new LaserBulletType(140){{
                		colors = new Color[]{Pal.darkerGray.a(0.4f), Pal.darkishGray, Color.white};
                		hitEffect = Fx.hitLancer;
                		hitSize = 6;
                		lifetime = 18f;
                		drawSize = 400f;
                		collidesAir = true;
                		length = 192f;
                		ammoMultiplier = 2f;
            		}};
        	}};
	}
}

