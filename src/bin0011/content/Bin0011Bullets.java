package bin0011.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.*;
import mindustry.graphics.*;

public class Bin0011Bullets implements ContentList{
    public static BulletType

    //artillery
    bul0001,

	//standard
    bul0000;

    @Override
    public void load(){
        bul0000 = new BasicBulletType(2.5f, 12){{
            width = 7f;
            height = 9f;
            lifetime = 75f;
            shootEffect = Fx.shootSmall;
            smokeEffect = Fx.shootSmallSmoke;
            ammoMultiplier = 2;
        }};
	}
}
