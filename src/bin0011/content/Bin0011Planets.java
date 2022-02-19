package bin0011.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.graphics.g3d.*;
import mindustry.type.*;
import mindustry.content.*;

import bin0011.maps.planet.*;

public class Bin0011Planets implements ContentList {
	public static Planet displan;

	@Override
	public void load() {
		displan = new Planet("displan", Planets.sun, 2, 1.15f) {{
			localizedName = "Displan";
			generator = new DisplanPlanetGenerator();
			meshLoader = () -> new HexMesh(this, 6);
			atmosphereColor = Color.valueOf("9f9f9f");
			atmosphereRadIn = 0.02f;
			atmosphereRadOut = 0.15f;
			startSector = 0;
			alwaysUnlocked = true;
		}};
	}
}
