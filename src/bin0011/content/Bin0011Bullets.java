package bin0011.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.graphics.*;
import mindustry.content.*;

import bin0011.content.*;

public class Bin0011Bullets implements ContentList{
    public static BulletType

    //artillery
    binaryArtilleryDense;

    @Override
    public void load(){

        artillery00 = new ArtilleryBulletType(3f, 20, "shell"){{
            hitEffect = Fx.flakExplosion;
            knockback = 0.8f;
            lifetime = 80f;
            width = height = 11f;
            collidesTiles = false;
            splashDamageRadius = 30f * 2f;
            splashDamage = 45f;
            reloadMultiplier = 1.2f;
            ammoMultiplier = 3f;
            homingPower = 1.8f;
            homingRange = 50f;
        }};
    }
}
