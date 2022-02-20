package bin0011.content;

import arc.struct.Seq;
import mindustry.content.*;
import mindustry.content.TechTree.TechNode;
import mindustry.ctype.ContentList;
import mindustry.ctype.UnlockableContent;
import mindustry.game.Objectives.*;
import mindustry.type.ItemStack;
import bin0011.content.*;

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
		extendNode(Items.copper, () -> {
			nodeProduce(Bin0011Items.item00);
		});
		extendNode(Items.sand, () -> {
			nodeProduce(Bin0011Items.item01);
		});
		extendNode(Items.lead, () -> {
			nodeProduce(Bin0011Items.item10);
			nodeProduce(Bin0011Items.item11);
		});

		extendNode(Blocks.titaniumWall, () -> {
			node(Bin0011Blocks.wall00, () -> {
				node(Bin0011Blocks.wallLarge00, () -> {
					node(Bin0011Blocks.wall01);
					node(Bin0011Blocks.wallLarge01);
				});
			});
		});
		extendNode(Blocks.arc, () -> {
			node(Bin0011Blocks.turret0000);
			node(Bin0011Blocks.turret0001);
		});
		extendNode(Blocks.lancer, () -> {
			node(Bin0011Blocks.turret0010);
			node(Bin0011Blocks.turret0011);
		});
		extendNode(Blocks.kiln, () -> {
			node(Bin0011Blocks.crft0000);
			node(Bin0011Blocks.crft0001);
			node(Bin0011Blocks.crft0010);
			node(Bin0011Blocks.crft0011);
		});
		extendNode(Blocks.combustionGenerator, () -> {
			node(Bin0011Blocks.node000);
			node(Bin0011Blocks.gen0000);
			node(Bin0011Blocks.gen0001);
		});
	}
}
