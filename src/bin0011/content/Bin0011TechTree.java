package bin0011.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives.*;
import mindustry.type.ItemStack;
import bin0011.content.*;
import bin0011.content.Bin0011Items.*;
import bin0011.content.Bin0011Blocks.*;

public class Bin0011TechTree implements ContentList {
	static TechTree.TechNode context = null;

	private static void extendNode (UnlockableContent parent, Runnable children) {
		context = TechTree.all.find(t -> t.content == parent);
		children.run();
	}

	private static void node (UnlockableContent content, ItemStack[] requirements, Seq <Objective> objectives, Runnable children) {
		TechNode node = new TechNode(context, content, requirements);
		if (objectives != null) node.objectives = objectives;

		TechNode prev = context;
		context = node;
		children.run();
		context = prev;
	}

	private static void node (UnlockableContent content, ItemStack[] requirements, Seq <Objective> objectives) {
		node(content, requirements, objectives, () -> {
		});
	}

	private static void node (UnlockableContent content, Seq <Objective> objectives) {
		node(content, content.researchRequirements(), objectives, () -> {
		});
	}

	private static void node (UnlockableContent content, ItemStack[] requirements) {
		node(content, requirements, Seq.with(), () -> {
		});
	}

	private static void node (UnlockableContent content, ItemStack[] requirements, Runnable children) {
		node(content, requirements, null, children);
	}

	private static void node (UnlockableContent content, Seq <Objective> objectives, Runnable children) {
		node(content, content.researchRequirements(), objectives, children);
	}

	private static void node (UnlockableContent content, Runnable children) {
		node(content, content.researchRequirements(), children);
	}

	private static void node (UnlockableContent block) {
		node(block, () -> {
		});
	}

	private static void nodeProduce (UnlockableContent content, Seq <Objective> objectives, Runnable children) {
		node(content, content.researchRequirements(), objectives.and(new Produce(content)), children);
	}

	private static void nodeProduce (UnlockableContent content, Seq <Objective> objectives) {
		nodeProduce(content, objectives, () -> {
		});
	}

	private static void nodeProduce (UnlockableContent content, Runnable children) {
		nodeProduce(content, Seq.with(), children);
	}

	private static void nodeProduce (UnlockableContent content) {
		nodeProduce(content, Seq.with(), () -> {
		});
	}

	@Override
	public void load() {
		// reg item
		extendNode(Blocks.coreShard, () -> {
			nodeProduce(Bin0011Items.item00, () -> {
				nodeProduce(Bin0011Items.item01);
				nodeProduce(Bin0011Items.item11);
			});
		});
		// end item
		
		// reg block
		// reg crafters
		extendNode(Blocks.siliconSmelter, () -> {
			node(Bin0011Blocks.crafter0000, () -> {
				node(Bin0011Blocks.crafter0001, () -> {
					node(Bin0011Blocks.crafterLarge0000, () -> {
						node(Bin0011Blocks.crafterLarge0001, () -> {
							node(Bin0011Blocks.crafterLarge0010, () -> {
								node(Bin0011Blocks.crafterLarge0011);
									
                            				});
                        			});
                    			});
                		});
            		});
		});
		// end crafters
		// reg walls
		extendNode(Blocks.titaniumWall, () -> {
			node(Bin0011Blocks.wall00, () -> {
				node(Bin0011Blocks.wallLarge00, () -> {
					node(Bin0011Blocks.wall01, () -> {
						node(Bin0011Blocks.wallLarge01);
					});
				});
			});
		});
		// end walls
		// reg turret
		extendNode(Blocks.duo, () -> {
			node(Bin0011Blocks.turret0000, () -> {
				node(Bin0011Blocks.turret0001, () -> {
					node(Bin0011Blocks.turret0010);
				});
			});
		});
		extendNode(Blocks.lancer, () -> {
			node(Bin0011Blocks.turret0011, () -> {
				node(Bin0011Blocks.turret0100, () -> {
					node(Bin0011Blocks.turret0101);
				});
			});
		});
		extendNode(Blocks.segment, () -> {
			node(Bin0011Blocks.turret0110, () -> {
				node(Bin0011Blocks.turret0111);
			});
		});
		// end turret
		// reg power
		extendNode(Blocks.battery, () -> {
			node(Bin0011Blocks.node000, () -> {
				node(Bin0011Blocks.node001, () -> {
					node(Bin0011Blocks.gen0000, Seq.with(new Research(Bin0011Items.item10)), () -> {
						node(Bin0011Blocks.gen0001, Seq.with(new Research(Bin0011Items.item10)));
					});
				});
			});
		});
		
		extendNode(Blocks.steamGenerator, () -> {
			node(Bin0011Blocks.burner000, () -> {
				node(Bin0011Blocks.burner001);
			});
		});
		
		extendNode(Blocks.battery, () -> {
			node(Bin0011Blocks.battery00, () -> {
				node(Bin0011Blocks.battery01);
			});
		});

		extendNode(Blocks.solarPanel, () -> {
			node(Bin0011Blocks.panel00, () -> {
				node(Bin0011Blocks.panel01);
			});
		});
		// end power
		// end block
	}
}
