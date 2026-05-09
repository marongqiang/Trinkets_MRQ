# Trinkets MRQ

基于 [Trinkets](https://github.com/emilyploszaj/trinkets) 的改进版本，一个数据驱动的 Minecraft Fabric 饰品栏模组。

---

## 与原版的区别

| 功能 | 原版 Trinkets | Trinkets MRQ |
|---|---|---|
| 修改槽位数量 | 需要制作数据包（3+ 个 JSON 文件） | **编辑一个配置文件即可** |
| 中文支持 | 无 | 配置文件全中文注释 |
| 生效方式 | 数据包 + `/reload` | 改配置 + `/reload` |

**核心改进**：内置配置文件系统（`config/trinkets.json`），用户无需创建数据包、无需理解命名空间，直接修改一个文件即可调整槽位数量。

---

## 配置文件使用方法

### 第一步：找到配置文件

首次启动模组后，会在 `.minecraft/config/` 目录下自动生成 `trinkets.json` 文件。

### 第二步：修改槽位数量

打开 `config/trinkets.json`，找到 `slot_amounts` 部分，修改对应槽位的数值：

```json
"slot_amounts": {
  "head/hat": 1,           // ← 帽子槽位，改成 2 就能戴两顶帽子
  "hand/ring": 3,          // ← 戒指槽位，改成 3 就能戴三个戒指
  "chest/back": 2,         // ← 背部槽位，改成 2 就能背两个东西
  "hand/glove": 2,         // ← 手套槽位，改成 2 就能戴两副手套
  "legs/belt": 2           // ← 腰带槽位，改成 2 就能系两条腰带
}
```

### 第三步：生效

在游戏内输入 `/reload` 或重新进入世界即可生效。

### 所有可用的槽位

| 配置键 | 所属组 | 中文名 | 默认数量 |
|---|---|---|---|
| `head/hat` | 头部 | 帽子 | 1 |
| `head/face` | 头部 | 面部 | 1 |
| `chest/necklace` | 胸部 | 项链 | 1 |
| `chest/back` | 胸部 | 背部 | 1 |
| `chest/cape` | 胸部 | 披风 | 1 |
| `legs/belt` | 腿部 | 腰带 | 1 |
| `feet/shoes` | 脚部 | 鞋子 | 1 |
| `feet/aglet` | 脚部 | 鞋带 | 1 |
| `hand/glove` | 主手 | 手套 | 1 |
| `hand/ring` | 主手 | 戒指 | 1 |
| `offhand/glove` | 副手 | 手套 | 1 |
| `offhand/ring` | 副手 | 戒指 | 1 |

### 注意事项

- **数量减少时**：多余槽位中的物品会自动掉落，不会丢失
- **数量增加时**：新增的空槽位会正常显示在饰品栏 GUI 中
- **设为 0**：等同于禁用该槽位类型
- **改错后恢复**：删除 `config/trinkets.json` 文件，重进游戏会自动生成默认配置
- **只支持修改已有槽位**：不能通过配置文件新增不存在的槽位类型，如有此需求请使用数据包方式

---

## 数据包方式（高级用户）

如果需要**新增槽位类型**（而非修改已有槽位的数量），仍需使用数据包方式。

### 数据包结构

```
datapack/
├── pack.mcmeta
└── data/
    └── trinkets/
        ├── slots/
        │   └── <group>/
        │       ├── group.json          ← 槽组定义（slot_id, order）
        │       └── <slot_name>.json    ← 槽位定义（icon, amount, 谓词）
        ├── entities/
        │   └── <your_id>.json          ← 实体分配（哪些实体拥有哪些槽）
        └── tags/
            └── items/
                └── <group>/
                    └── <slot_name>.json ← 物品标签（哪些物品能放入该槽）

```

### 槽位 JSON 示例

```json
{
  "icon": "modid:gui/slots/my_slot",
  "order": 0,
  "amount": 2,
  "validator_predicates": ["trinkets:tag"],
  "quick_move_predicates": ["trinkets:all"],
  "tooltip_predicates": ["trinkets:all"],
  "drop_rule": "default"
}
```

### 实体分配 JSON 示例

```json
{
  "entities": ["player"],
  "slots": [
    "chest/back",
    "hand/ring",
    "head/hat"
  ]
}
```

更多细节请参考[原版 Trinkets Wiki](https://github.com/emilyploszaj/trinkets/wiki/Home)。

---

## 原版介绍

Trinkets 为 Minecraft 添加了饰品栏槽位系统。默认包含 6 个槽组（头部、胸部、腿部、脚部、副手、主手），每个槽组可以添加不同种类的槽位。模组的 UI 直观易用，致力于减少界面杂乱感。

---

## 致谢

- 原版 Trinkets 作者：[Emily Ploszaj](https://github.com/emilyploszaj)
- 原版仓库：[emilyploszaj/trinkets](https://github.com/emilyploszaj/trinkets)
- 许可证：MIT
