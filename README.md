# Trinkets MRQ

基于 [Trinkets](https://github.com/emilyploszaj/trinkets) 的改进版本，一个数据驱动的 Minecraft Fabric 饰品栏模组。

**适用版本**：Minecraft 1.20.1 | Fabric

---

## 快速开始

> 想多戴几个戒指？想系两条腰带？不用做数据包，改一个文件就行。

1. 将 JAR 放入 `.minecraft/mods/`，启动游戏
2. 打开 `.minecraft/config/trinkets.json`
3. 修改数值，例如把 `"hand/ring"` 从 `1` 改成 `3`
4. 保存文件，游戏内输入 `/reload`
5. 完成

---

## 与原版的区别

| 功能 | 原版 Trinkets | Trinkets MRQ |
|---|---|---|
| 修改槽位数量 | 需要制作数据包（多个 JSON 文件 + 目录结构） | **编辑一个配置文件即可** |
| 中文支持 | 无 | 配置文件全中文注释 |
| 生效方式 | 数据包放入 world/datapacks + `/reload` | 改配置 + `/reload` |
| 新手友好度 | 需要了解 Minecraft 数据包机制 | 打开文件改数字就行 |

**核心改进**：内置配置文件系统，用户无需创建数据包、无需理解命名空间和目录结构。模组首次启动时自动在 `config/trinkets.json` 生成带中文注释的配置文件，修改后 `/reload` 即可生效。

---

## 配置文件详解

### 文件位置

```
.minecraft/
└── config/
    └── trinkets.json    ← 就是这个文件
```

首次启动模组后自动生成。如果文件被误删，下次启动时自动重新生成默认配置。

### 完整配置文件内容

```json
{
  "_comment": [
    "Trinkets 饰品栏 - 配置文件",
    "",
    "【使用方法】",
    "  1. 修改下方 slot_amounts 中对应的数值",
    "  2. 保存此文件",
    "  3. 在游戏内执行 /reload 命令即可立即生效",
    "",
    "【修改示例】",
    "  \"hand/ring\": 3        → 戒指槽位从1个增加到3个",
    "  \"hand/glove\": 2       → 手套槽位从1个增加到2个",
    "  \"chest/back\": 2       → 背部槽位从1个增加到2个",
    "  \"legs/belt\": 2        → 腰带槽位从1个增加到2个",
    "  \"head/hat\": 0         → 禁用帽子槽位",
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
    "  · 删除此文件后 /reload 会重新生成默认配置"
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
```

### 槽位对照表

| 配置键 | 所属槽组 | 中文名称 | 用途说明 | 默认数量 |
|---|---|---|---|---|
| `head/hat` | head（头部） | 帽子 | 头盔类饰品 | 1 |
| `head/face` | head（头部） | 面部 | 眼镜、面具类饰品 | 1 |
| `chest/necklace` | chest（胸部） | 项链 | 项链、护身符类饰品 | 1 |
| `chest/back` | chest（胸部） | 背部 | 背包、鞘翅类 | 1 |
| `chest/cape` | chest（胸部） | 披风 | 披风类饰品 | 1 |
| `legs/belt` | legs（腿部） | 腰带 | 腰带类饰品 | 1 |
| `feet/shoes` | feet（脚部） | 鞋子 | 鞋类饰品 | 1 |
| `feet/aglet` | feet（脚部） | 鞋带 | 鞋带类饰品 | 1 |
| `hand/glove` | hand（主手） | 手套 | 手套类饰品 | 1 |
| `hand/ring` | hand（主手） | 戒指 | 戒指类饰品 | 1 |
| `offhand/glove` | offhand（副手） | 手套（副手） | 副手手套饰品 | 1 |
| `offhand/ring` | offhand（副手） | 戒指（副手） | 副手戒指饰品 | 1 |

### 注意事项

| 场景 | 行为 |
|---|---|
| 数量从 1 改成 3 | 新增 2 个空槽位，GUI 中显示 3 个槽 |
| 数量从 3 改成 1 | 多余槽位的物品自动掉落，不会消失 |
| 设为 0 | 该槽位类型完全禁用，已有物品自动掉落 |
| 配置写错（非数字） | 该行被忽略，使用默认值 |
| 配置写错（超出范围） | 忽略该值（有效范围 0-100） |
| 删除配置文件 | 下次启动自动生成默认配置 |
| 槽位未生效 | 确认该槽位已被实体分配启用（需其他模组或数据包配合） |

---

## 数据包方式（高级用户）

如果需要**新增槽位类型**（而非仅修改已有槽位的数量），或需要**将槽位分配给非玩家实体**，仍需使用数据包方式。

### 数据包结构

```
datapack/
├── pack.mcmeta                       ← pack_format: 15 (MC 1.20.1)
└── data/
    └── trinkets/
        ├── slots/
        │   └── <group>/
        │       ├── group.json         ← 槽组定义（slot_id, order）
        │       └── <slot_name>.json   ← 槽位类型定义
        ├── entities/
        │   └── <your_id>.json         ← 实体槽位分配
        └── tags/
            └── items/
                └── <group>/
                    └── <slot_name>.json ← 物品标签（可放入的物品）
```

### 槽组定义 (group.json)

```json
{
  "slot_id": 5,
  "order": 0
}
```

| 字段 | 说明 |
|---|---|
| `slot_id` | MC 内部槽位 ID，决定了槽组在 GUI 中的位置（head=5, chest=6, legs=7, feet=8, offhand=45, hand=-1） |
| `order` | 组内排序，越小越靠前 |

### 槽位类型定义 (<slot>.json)

```json
{
  "icon": "trinkets:gui/slots/ring",
  "order": 0,
  "amount": 1,
  "validator_predicates": ["trinkets:tag"],
  "quick_move_predicates": ["trinkets:all"],
  "tooltip_predicates": ["trinkets:all"],
  "drop_rule": "default"
}
```

| 字段 | 默认值 | 说明 |
|---|---|---|
| `icon` | 必填 | 槽位图标纹理路径（省略 `textures/` 前缀和 `.png` 后缀） |
| `order` | 0 | 组内排序 |
| `amount` | 1 | 该槽位的默认数量（可被配置文件覆盖） |
| `validator_predicates` | `["trinkets:tag"]` | 验证物品能否放入的谓词 |
| `quick_move_predicates` | `["trinkets:all"]` | Shift+点击快速移动的谓词 |
| `tooltip_predicates` | `["trinkets:all"]` | 提示文本显示的谓词 |
| `drop_rule` | `default` | 死亡掉落规则：`keep`/`drop`/`destroy`/`default` |

### 谓词说明

| 谓词 ID | 行为 |
|---|---|
| `trinkets:all` | 始终允许 |
| `trinkets:none` | 始终禁止 |
| `trinkets:tag` | 仅允许带有 `trinkets:<group>/<slot>` 物品标签的物品 |
| `trinkets:relevant` | 仅允许对该槽位提供属性修饰符的物品 |

### 实体分配

```json
{
  "replace": false,
  "entities": ["player"],
  "slots": [
    "chest/back",
    "hand/ring",
    "head/hat"
  ]
}
```

| 字段 | 说明 |
|---|---|
| `replace` | `true` 覆盖低优先级数据包，`false`（默认）合并 |
| `entities` | 实体 ID 数组，如 `"player"`、`"minecraft:zombie"` |
| `slots` | 槽位数组，格式为 `"group/slot"` |

更多细节请参考 [原版 Trinkets Wiki](https://github.com/emilyploszaj/trinkets/wiki/Home)。

---

## 两种方式对比

| | 配置文件方式 | 数据包方式 |
|---|---|---|
| **适用场景** | 修改已有槽位数量 | 新增槽位类型、分配槽位给实体 |
| **文件数量** | 1 个 | 3+ 个 |
| **难度** | 简单 | 中等 |
| **需要 /reload** | 是 | 是 |
| **需要理解命名空间** | 否 | 是 |
| **支持中文注释** | 是 | 否 |

---

## 故障排查

| 问题 | 解决方法 |
|---|---|
| 改配置后没变化 | 确认已执行 `/reload`，检查 JSON 语法是否正确 |
| 槽位根本没出现 | 该槽位可能未被实体分配启用，需要数据包或其他模组配合 |
| 配置被重置 | 检查是否误删了 `config/trinkets.json`，或 JSON 格式错误导致使用默认值 |
| 物品掉落了 | 槽位数量减少时多出的物品会自动掉落，这是正常行为 |

---

## 原版介绍

Trinkets 为 Minecraft 添加了饰品栏槽位系统。默认包含 6 个槽组（头部、胸部、腿部、脚部、副手、主手），每个槽组可以添加不同种类的槽位。模组的 UI 直观易用，致力于减少界面杂乱感。

---

## 致谢

- 原版 Trinkets 作者：[Emily Ploszaj](https://github.com/emilyploszaj)
- 原版仓库：[emilyploszaj/trinkets](https://github.com/emilyploszaj/trinkets)
- 许可证：MIT
