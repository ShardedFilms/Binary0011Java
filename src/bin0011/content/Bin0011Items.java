package bin0011.content;

import arc.graphics.Color;
import mindustry.ctype.ContentList;
import mindustry.type.Item;

public class Bin0011Items implements ContentList {
	public static Item 
		item00,
		item01, item10, item11;

	@Override
	public void load() {
		item00 = new Item("item00", Color.valueOf("4A4B53")) {{
			localizedName = "item00";
			cost = 0;
		}};

		item01 = new Item("item01", Color.valueOf("4A4B53")) {{
			localizedName = "item01";
			cost = 0;
		}};
		item10 = new Item("item10", Color.valueOf("B0BAC0")) {{
			localizedName = "item10";
			cost = 0;
			explosiveness = 0.6f;
			charge = 0.2f;
		}};
		item11 = new Item("item11", Color.valueOf("B0BAC0")) {{
			localizedName = "item11";
			cost = 0;
			hardness = 1;
		}};
	}
}
