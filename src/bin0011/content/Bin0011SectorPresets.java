package bin0011.content;

import mindustry.ctype.*;
import mindustry.type.*;
import bin0011.content.*;
import bin0011.maps.planet.*;
import bin0011.content.Bin0011Planets.*;

public class Bin0011SectorPresets implements ContentList {
    public static SectorPreset
    dis00;

    @Override
    public void load(){
        // reg displan
        // gw sengaja pake base 16 biar gw bisa bikin >16 misi tanpa harus khawatir angkanya bakal sampe max value (kalo bas 16 max valuenya 16^digit -> 16^2 = 256
        dis00 = new SectorPreset("dis00", Bin0011Planets.displan, 15){{
            localizedName = "dis00";
			addStartingItems = true;
            captureWave = 20;
            difficulty = 2;
			alwaysUnlocked = true;
        }};
        // end displan
    }
}
//aa
