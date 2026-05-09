package dev.emi.trinkets;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dev.emi.trinkets.TrinketsMain;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;

public class TrinketsConfig {

    private static final Path CONFIG_PATH = FabricLoader.getInstance().getConfigDir().resolve("trinkets.json");
    private static final Map<String, Integer> slotAmounts = new LinkedHashMap<>();

    private static final String DEFAULT_CONFIG = """
            {
              "_comment": [
                "Trinkets 饰品栏 - 配置文件",
                "",
                "【作用】",
                "  修改已有槽位的数量，无需制作数据包，无需创建文件夹。",
                "",
                "【使用方法】",
                "  1. 修改下方 slot_amounts 中对应的数值",
                "  2. 保存此文件",
                "  3. 在游戏内执行 /reload 命令即可立即生效",
                "     或者重新进入世界",
                "",
                "【格式说明】",
                "  \\"槽组/槽名\\": 数量",
                "",
                "【修改示例】",
                "  \\"hand/ring\\": 3        → 戒指槽位从1个增加到3个",
                "  \\"hand/glove\\": 2       → 手套槽位从1个增加到2个",
                "  \\"chest/back\\": 2       → 背部槽位从1个增加到2个",
                "  \\"legs/belt\\": 2        → 腰带槽位从1个增加到2个",
                "  \\"head/hat\\": 0         → 禁用帽子槽位",
                "",
                "【槽组对照表】",
                "  head    = 头部  (hat帽子, face面部)",
                "  chest   = 胸部  (necklace项链, back背部, cape披风)",
                "  legs    = 腿部  (belt腰带)",
                "  feet    = 脚部  (shoes鞋子, aglet鞋带)",
                "  hand    = 主手  (glove手套, ring戒指)",
                "  offhand = 副手  (glove手套, ring戒指)",
                "",
                "【注意事项】",
                "  · 只支持修改已在实体分配中启用的槽位",
                "  · 数量减少时，多余槽位中的物品会自动掉落",
                "  · 数量增加时，新增的空槽位会正常显示在GUI中",
                "  · 设为零等同于禁用该槽位类型",
                "  · 删除某一行不会恢复默认值，需要手动改回原数值",
                "  · 如果不小心改错了，删除此文件后 /reload 会重新生成默认配置",
                "",
                "下方为全部内置槽位及其默认数量："
              ],
              "slot_amounts": {
                "head/hat": 1,
                "head/face": 1,
                "chest/necklace": 1,
                "chest/back": 1,
                "chest/cape": 1,
                "legs/belt": 1,
                "feet/shoes": 1,
                "feet/aglet": 1,
                "hand/glove": 1,
                "hand/ring": 1,
                "offhand/glove": 1,
                "offhand/ring": 1
              }
            }
            """;

    static {
        // 预填充默认值
        slotAmounts.put("head/hat", 1);
        slotAmounts.put("head/face", 1);
        slotAmounts.put("chest/necklace", 1);
        slotAmounts.put("chest/back", 1);
        slotAmounts.put("chest/cape", 1);
        slotAmounts.put("legs/belt", 1);
        slotAmounts.put("feet/shoes", 1);
        slotAmounts.put("feet/aglet", 1);
        slotAmounts.put("hand/glove", 1);
        slotAmounts.put("hand/ring", 1);
        slotAmounts.put("offhand/glove", 1);
        slotAmounts.put("offhand/ring", 1);
    }

    /**
     * 获取指定槽位的配置覆盖数量
     * @return 配置的数量，若无覆盖则返回 -1
     */
    public static int getOverrideAmount(String group, String slot) {
        return slotAmounts.getOrDefault(group + "/" + slot, -1);
    }

    /**
     * 加载配置文件。首次运行自动创建默认配置。
     */
    public static void load() {
        if (!Files.exists(CONFIG_PATH)) {
            createDefaultConfig();
        } else {
            readConfig();
        }
    }

    private static void readConfig() {
        try {
            String content = Files.readString(CONFIG_PATH, StandardCharsets.UTF_8);
            JsonObject json = JsonParser.parseString(content).getAsJsonObject();
            if (json.has("slot_amounts")) {
                JsonObject amounts = json.getAsJsonObject("slot_amounts");
                for (Map.Entry<String, JsonElement> entry : amounts.entrySet()) {
                    try {
                        int amount = entry.getValue().getAsInt();
                        if (amount >= 0 && amount <= 100) {
                            slotAmounts.put(entry.getKey(), amount);
                            TrinketsMain.LOGGER.info("[trinkets] 配置覆盖: {} → {}", entry.getKey(), amount);
                        } else {
                            TrinketsMain.LOGGER.warn("[trinkets] 忽略无效数量 {}: {} (范围 0-100)", entry.getKey(), amount);
                        }
                    } catch (NumberFormatException e) {
                        TrinketsMain.LOGGER.warn("[trinkets] 忽略无效值 {}: 不是数字", entry.getKey());
                    }
                }
            }
        } catch (Exception e) {
            TrinketsMain.LOGGER.error("[trinkets] 配置文件读取失败，使用默认值: {}", e.getMessage());
        }
    }

    private static void createDefaultConfig() {
        try {
            Files.createDirectories(CONFIG_PATH.getParent());
            Files.writeString(CONFIG_PATH, DEFAULT_CONFIG, StandardCharsets.UTF_8);
            TrinketsMain.LOGGER.info("[trinkets] 已在 config/trinkets.json 创建默认配置文件");
        } catch (IOException e) {
            TrinketsMain.LOGGER.error("[trinkets] 无法创建配置文件: {}", e.getMessage());
        }
    }
}
