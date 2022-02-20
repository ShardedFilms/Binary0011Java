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
        /* naming system untuk sector beda dari naming system block. 
        gw gak pengen nama sector digitnya banyak karena pake base 2/binary. disini gw pake hexadecimal, base 16 */
        daciteFields = new SectorPreset("dacite-fields", Bin0011Planets.displan, 15){{
            localizedName = "Dacite Fields";
            addStartingItems = true;
            startWaveMultiplier = 2f
            captureWave = 30;
            difficulty = 3;
        }};
        // end displan
    }
}
