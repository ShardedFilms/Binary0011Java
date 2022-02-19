package bin0011;

import arc.*;
import arc.util.*;
import mindustry.*;
import mindustry.content.*;
import mindustry.game.EventType.*;
import mindustry.gen.*;
import mindustry.mod.*;
import mindustry.ui.dialogs.*;

import bin0011.content.*;

public class bin0011 extends Mod{
	public bin0011(){}
	// load mod content
	@Override
	public void loadContent(){
		new Bin0011Items().load();
		new Bin0011Blocks().load();
		new Bin0011TechTree().load();
		new Bin0011Planets().load();
// bullet type bakal gw bikin nanti
//		new Bin0011Bullets().load();
	}
}
