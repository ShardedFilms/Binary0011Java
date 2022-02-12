package bin0011.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.graphics.*;

public class Bin0011Bullets implements ContentList{
    public static BulletType

    //artillery
    art0000, art0001,

	//standard
    std0000, std0001;

    @Override
    public void load(){
    	// reg standard
		std0000 = new BasicBulletType(2.5f, 12){{
            width = 7f;
            height = 9f;
            lifetime = 75f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            ammoMultiplier = 2;
        }};
		std0001 = new BasicBulletType(3f, 16, "bullet"){{
            width = 7f;
            height = 9f;
            homingPower = 0.5f;
            reloadMultiplier = 3.5f;
            ammoMultiplier = 5;
            lifetime = 75f;
        }};
		// end reg
		// reg artillery
        art0000 = new ArtilleryBulletType(3f, 23, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 11f;
            collidesTiles = false;
            splashDamageRadius = 25f * 0.8f;
            splashDamage = 35f;
        }};
        art0001 = new ArtilleryBulletType(3f, 27, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 11f;
            collidesTiles = false;
            splashDamageRadius = 25f * 0.8f;
            splashDamage = 39f;
            reloadMultiplier = 1.2f;
            ammoMultiplier = 3f;
            homingPower = 1.5f;
            homingRange = 50f;
        }};
		// end reg
	}
}
