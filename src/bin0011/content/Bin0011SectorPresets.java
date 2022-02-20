package bin0011.content;

import mindustry.ctype.*;
import mindustry.type.*;
import bin0011.content.*;
import bin0011.maps.planet.*;
import bin0011.content.Bin0011Planets;

public class Bin0011SectorPresets implements ContentList {
    public static SectorPreset
    dis00;

    @Override
    public void load(){
        //reg displan
        daciteFields = new SectorPreset("dacite-fields", Bin0011Planets.displan, 15){{
            localizedName = "Dacite Fields";
            addStartingItems = true;
            startWaveMultiplier = 2f;
            captureWave = 30;
            difficulty = 3;
        }};
        // end displan
    }
}
