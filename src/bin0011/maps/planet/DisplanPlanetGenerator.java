package bin0011.maps.planet;

import arc.graphics.Color;
import arc.math.*;
import arc.math.geom.*;
import arc.struct.*;
import arc.util.Tmp;
import mindustry.ai.Astar;
import mindustry.ai.BaseRegistry.BasePart;
import mindustry.content.Blocks;
import mindustry.game.*;
import mindustry.maps.generators.BaseGenerator;
import mindustry.maps.planet.SerpuloPlanetGenerator;
import mindustry.type.Sector;
import mindustry.world.*;
import mindustry.world.blocks.environment.Floor;
import arc.graphics.*;
import arc.util.*;
import arc.util.noise.*;
import mindustry.ai.*;
import mindustry.ai.BaseRegistry.*;
import mindustry.content.*;
import mindustry.graphics.g3d.PlanetGrid.*;
import mindustry.maps.generators.*;
import mindustry.type.*;

import bin0011.content.*;

import static mindustry.Vars.*;

public class DisplanPlanetGenerator extends SerpuloPlanetGenerator {
	public DisplanPlanetGenerator() {
	}

	public int seed = 96;
	BaseGenerator basegen = new BaseGenerator();
	float scl = 6f;
	float waterOffset = 0.050f;

	Block[][] arr =
    {
    {Blocks.salt, Blocks.basalt, Blocks.darksand, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.basalt, Blocks.basalt},
    {Blocks.salt, Blocks.basalt, Blocks.stone, Blocks.stone, Blocks.salt, Blocks.basalt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.basalt, Blocks.stone, Blocks.stone},
    {Blocks.salt, Blocks.darksand, Blocks.stone, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.basalt},
    {Blocks.salt, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.stone},
    {Blocks.salt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.salt, Blocks.basalt, Blocks.darksand, Blocks.basalt, Blocks.darksand, Blocks.darksand, Blocks.basalt},
    {Blocks.salt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.charr, Blocks.charr, Blocks.charr, Blocks.charr, Blocks.dacite, Blocks.dacite},
    // dacite & charr spam
    {Blocks.salt, Blocks.salt, Blocks.salt, Blocks.salt, Blocks.basalt, Blocks.basalt, Blocks.basalt, Blocks.dacite, Blocks.dacite, Blocks.dacite, Blocks.charr, Blocks.charr, Blocks.darksand},
    {Blocks.salt, Blocks.salt, Blocks.dacite, Blocks.darksand, Blocks.darksand, Blocks.stone, Blocks.dacite, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.stone, Blocks.darksand},
    {Blocks.salt, Blocks.salt, Blocks.dacite, Blocks.darksand, Blocks.charr, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand},
    {Blocks.charr, Blocks.salt, Blocks.dacite, Blocks.stone, Blocks.charr, Blocks.charr, Blocks.darksand, Blocks.darksand, Blocks.basalt, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand},
    {Blocks.charr, Blocks.salt, Blocks.dacite, Blocks.dacite, Blocks.darksand, Blocks.charr, Blocks.charr, Blocks.darksand, Blocks.dacite, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand},
    {Blocks.charr, Blocks.salt, Blocks.dacite, Blocks.stone, Blocks.charr, Blocks.sand, Blocks.charr, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand, Blocks.darksand}
    };

    ObjectMap<Block, Block> dec = ObjectMap.of(
        Blocks.salt, Blocks.darksand,
        Blocks.salt, Blocks.darksand,
        Blocks.salt, Blocks.darksand,
        Blocks.darksandTaintedWater, Blocks.darksandTaintedWater
    );

    ObjectMap<Block, Block> tars = ObjectMap.of(
        Blocks.salt, Blocks.dacite,
        Blocks.salt, Blocks.dacite
    );

    float water = 2f / arr[0].length;
    float rawHeight(Vec3 position){
        position = Tmp.v33.set(position).scl(scl);
        return (Mathf.pow((float)Simplex.noise3d(seed, 7, 0.5f, 1f/3f, position.x, position.y, position.z), 2.3f) + waterOffset) / (1f + waterOffset);
    }

    @Override
    public void generateSector(Sector sector){

        //these always have bases
        if(sector.id == 154 || sector.id == 0){
            sector.generateEnemyBase = true;
            return;
        }

        Ptile tile = sector.tile;

        boolean any = false;
        float poles = Math.abs(tile.v.y);
        float noise = Noise.snoise3(tile.v.x, tile.v.y, tile.v.z, 0.001f, 0.58f);

        if(noise + poles/7.1 > 0.12 && poles > 0.23){
            any = true;
        }

        if(noise < 0.16){
            for(Ptile other : tile.tiles){
                var osec = sector.planet.getSector(other);

                //no sectors near start sector!
                if(
                    osec.id == sector.planet.startSector || //near starting sector
                    osec.generateEnemyBase && poles < 0.85 || //near other base
                    (sector.preset != null && noise < 0.11) //near preset
                ){
                    return;
                }
            }
        }

        sector.generateEnemyBase = any;
    }

    @Override
    public void postGenerate(Tiles tiles){
        if(sector.hasEnemyBase()){
            basegen.postGenerate();
        }
    }

    @Override
    public void generate() {

        class Room {
            int x, y, radius;
            ObjectSet<Room> connected = new ObjectSet<>();

            Room(int x, int y, int radius) {
                this.x = x;
                this.y = y;
                this.radius = radius;
                connected.add(this);
            }

            void connect(Room to) {
                if (connected.contains(to)) return;

                connected.add(to);
                float nscl = rand.random(20f, 60f);
                int stroke = rand.random(4, 12);
                brush(pathfind(x, y, to.x, to.y, tile -> (tile.solid() ? 5f : 0f) + noise(tile.x, tile.y, 1, 1, 1f / nscl) * 60, Astar.manhattan), stroke);
            }
        }

        cells(4);
        distort(10f, 12f);

        float constraint = 1.3f;
        float radius = width / 2f / Mathf.sqrt3;
        int rooms = rand.random(2, 5);
        Seq<Room> roomseq = new Seq<>();

        for (int i = 0; i < rooms; i++) {
            Tmp.v1.trns(rand.random(360f), rand.random(radius / constraint));
            float rx = (width / 2f + Tmp.v1.x);
            float ry = (height / 2f + Tmp.v1.y);
            float maxrad = radius - Tmp.v1.len();
            float rrad = Math.min(rand.random(9f, maxrad / 2f), 30f);
            roomseq.add(new Room((int) rx, (int) ry, (int) rrad));
        }

        //check positions on the map to place the player spawn. this needs to be in the corner of the map
        Room spawn = null;
        Seq<Room> enemies = new Seq<>();
        int enemySpawns = rand.random(1, Math.max((int) (sector.threat * 4), 1));
        int offset = rand.nextInt(360);
        float length = width / 2.55f - rand.random(13, 23);
        int angleStep = 5;
        int waterCheckRad = 5;
        for (int i = 0; i < 360; i += angleStep) {
            int angle = offset + i;
            int cx = (int) (width / 2 + Angles.trnsx(angle, length));
            int cy = (int) (height / 2 + Angles.trnsy(angle, length));

            int waterTiles = 0;

            //check for water presence
            for (int rx = -waterCheckRad; rx <= waterCheckRad; rx++) {
                for (int ry = -waterCheckRad; ry <= waterCheckRad; ry++) {
                    Tile tile = tiles.get(cx + rx, cy + ry);
                    if (tile == null || tile.floor().liquidDrop != null) {
                        waterTiles++;
                    }
                }
            }

            if (waterTiles <= 4 || (i + angleStep >= 360)) {
                roomseq.add(spawn = new Room(cx, cy, rand.random(8, 15)));

                for (int j = 0; j < enemySpawns; j++) {
                    float enemyOffset = rand.range(60f);
                    Tmp.v1.set(cx - width / 2, cy - height / 2).rotate(180f + enemyOffset).add(width / 2, height / 2);
                    Room espawn = new Room((int) Tmp.v1.x, (int) Tmp.v1.y, rand.random(8, 15));
                    roomseq.add(espawn);
                    enemies.add(espawn);
                }

                break;
            }
        }

        for (Room room : roomseq) {
            erase(room.x, room.y, room.radius);
        }

        int connections = rand.random(Math.max(rooms - 1, 1), rooms + 3);
        for (int i = 0; i < connections; i++) {
            roomseq.random(rand).connect(roomseq.random(rand));
        }

        for (Room room : roomseq) {
            spawn.connect(room);
        }

        cells(2);
        distort(10f, 6f);

        inverseFloodFill(tiles.getn(spawn.x, spawn.y));

        Seq<Block> ores = Seq.with(Blocks.oreCopper, Blocks.oreLead, Bin0011Blocks.oreItem00);
        float poles = Math.abs(sector.tile.v.y);
        float nmag = 0.75f;
        float scl = 1f;
        float addscl = 1.3f;

        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.25f*addscl){
            ores.add(Blocks.oreCoal);
        }

        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.25f*addscl){
            ores.add(Bin0011Blocks.oreItem10);
        }

        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x + 1, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.5f*addscl){
            ores.add(Blocks.oreTitanium);
        }

        if(Simplex.noise3d(seed, 2, 0.5, scl, sector.tile.v.x + 2, sector.tile.v.y, sector.tile.v.z)*nmag + poles > 0.7f*addscl){
            ores.add(Blocks.oreThorium);
        }

        if(rand.chance(0.25)){
            ores.add(Blocks.oreScrap);
        }

        FloatSeq frequencies = new FloatSeq();
        for (int i = 0; i < ores.size; i++) {
            frequencies.add(rand.random(-0.1f, 0.01f) - i * 0.01f + poles * 0.04f);
        }

        pass((x, y) -> {
            if (!floor.asFloor().hasSurface()) return;

            int offsetX = x - 4, offsetY = y + 23;
            for (int i = ores.size - 1; i >= 0; i--) {
                Block entry = ores.get(i);
                float freq = frequencies.get(i);
                if (Math.abs(0.5f - noise(offsetX, offsetY + i * 999, 2, 0.7, (40 + i * 2))) > 0.22f + i * 0.01 &&
                        Math.abs(0.5f - noise(offsetX, offsetY - i * 999, 1, 1, (30 + i * 4))) > 0.37f + freq) {
                    ore = entry;
                    break;
                }
            }

            if (ore == Blocks.oreScrap && rand.chance(0.33)) {
                floor = Blocks.darksand;
            }
        });

        trimDark();

        median(2);

        tech();

        //random stuff
        float difficulty = sector.threat;
        ints.clear();
        ints.ensureCapacity(width * height / 6);

        int ruinCount = rand.random(-2, 6);
        if (ruinCount > 0) {
            int padding = 25;

            //create list of potential positions
            for (int x = padding; x < width - padding; x++) {
                for (int y = padding; y < height - padding; y++) {
                    Tile tile = tiles.getn(x, y);
                    if (!tile.solid() && (tile.drop() != null || tile.floor().liquidDrop != null)) {
                        ints.add(tile.pos());
                    }
                }
            }

            ints.shuffle(rand);

            int placed = 0;
            float diffRange = 0.4f;
            //try each position
            for (int i = 0; i < ints.size && placed < ruinCount; i++) {
                int val = ints.items[i];
                int x = Point2.x(val), y = Point2.y(val);

                //do not overwrite player spawn
                if (Mathf.within(x, y, spawn.x, spawn.y, 18f)) {
                    continue;
                }

                float range = difficulty + rand.random(diffRange);

                Tile tile = tiles.getn(x, y);
                BasePart part = null;
                if (tile.overlay().itemDrop != null) {
                    part = bases.forResource(tile.drop()).getFrac(range);
                } else if (tile.floor().liquidDrop != null && rand.chance(0.05)) {
                    part = bases.forResource(tile.floor().liquidDrop).getFrac(range);
                } else if (rand.chance(0.05)) { //ore-less parts are less likely to occur.
                    part = bases.parts.getFrac(range);
                }

                //actually place the part
                if (part != null && BaseGenerator.tryPlace(part, x, y, Team.derelict, (cx, cy) -> {
                    Tile other = tiles.getn(cx, cy);
                    if (other.floor().hasSurface()) {
                        other.setOverlay(Blocks.oreScrap);
                        for (int j = 1; j <= 2; j++) {
                            for (Point2 p : Geometry.d8) {
                                Tile t = tiles.get(cx + p.x * j, cy + p.y * j);
                                if (t != null && t.floor().hasSurface() && rand.chance(j == 1 ? 0.4 : 0.2)) {
                                    t.setOverlay(Blocks.oreScrap);
                                }
                            }
                        }
                    }
                })) {
                    placed++;

                    int debrisRadius = Math.max(part.schematic.width, part.schematic.height) / 2 + 3;
                    Geometry.circle(x, y, tiles.width, tiles.height, debrisRadius, (cx, cy) -> {
                        float dst = Mathf.dst(cx, cy, x, y);
                        float removeChance = Mathf.lerp(0.05f, 0.5f, dst / debrisRadius);

                        Tile other = tiles.getn(cx, cy);
                        if (other.build != null && other.isCenter()) {
                            if (other.team() == Team.derelict && rand.chance(removeChance)) {
                                other.remove();
                            } else if (rand.chance(0.5)) {
                                other.build.health = other.build.health - rand.random(other.build.health * 0.9f);
                            }
                        }
                    });
                }
            }
        }

        Schematics.placeLaunchLoadout(spawn.x, spawn.y);

        for (Room espawn : enemies) {
            tiles.getn(espawn.x, espawn.y).setOverlay(Blocks.spawn);
        }

        if (sector.hasEnemyBase()) {
            basegen.generate(tiles, enemies.map(r -> tiles.getn(r.x, r.y)), tiles.get(spawn.x, spawn.y), state.rules.waveTeam, sector, difficulty);

            state.rules.attackMode = sector.info.attack = true;
        } else {
            state.rules.winWave = sector.info.winWave = 10 + 5 * (int) Math.max(difficulty * 10, 1);
        }

        float waveTimeDec = 0.4f;

        state.rules.waveSpacing = Mathf.lerp(60 * 65 * 2, 60f * 60f * 1f, Math.max(difficulty - waveTimeDec, 0f) / 0.8f);
        state.rules.waves = sector.info.waves = true;
        state.rules.enemyCoreBuildRadius = 600f;

        state.rules.spawns = Waves.generate(difficulty, new Rand(), state.rules.attackMode);
    }

    @Override
    public float getHeight(Vec3 position) {
        float height = rawHeight(position);
        return Math.max(height, water);
    }

    @Override
    public Color getColor(Vec3 position) {
        Block block = getBlock(position);
        return Tmp.c1.set(block.mapColor).a(1f - block.albedo);
    }

    public Block getBlock(Vec3 position){
        float height = rawHeight(position);
        Tmp.v31.set(position);
        position = Tmp.v33.set(position).scl(scl);
        float rad = scl;
        float temp = Mathf.clamp(Math.abs(position.y * 2f) / (rad));
        float tnoise = (float)Simplex.noise3d(seed, 7, 0.56, 1f/3f, position.x, position.y + 999f, position.z);
        temp = Mathf.lerp(temp, tnoise, 0.5f);
        height *= 1.2f;
        height = Mathf.clamp(height);

        float tar = (float)Simplex.noise3d(seed, 4, 0.55f, 1f/2f, position.x, position.y + 999f, position.z) * 0.3f + Tmp.v31.dst(0, 0, 1f) * 0.2f;

        Block res = arr[Mathf.clamp((int)(temp * arr.length), 0, arr[0].length - 1)][Mathf.clamp((int)(height * arr[0].length), 0, arr[0].length - 1)];
        if(tar > 0.5f){
            return tars.get(res, res);
        }else{
            return res;
        }
    }

    @Override
    public void genTile(Vec3 position, TileGen tile){
        tile.floor = getBlock(position);
        tile.block = tile.floor.asFloor().wall;

        if(Ridged.noise3d(1, position.x, position.y, position.z, 2, 22) > 0.31){
            tile.block = Blocks.air;
        }
    }
}
