# Disenchanter

[![modrinth](https://img.shields.io/badge/-modrinth-gray?style=for-the-badge&labelColor=green&labelWidth=15&logo=appveyor&logoColor=white)](https://modrinth.com/mod/disenchanter)
[![curseforge](https://img.shields.io/badge/-CurseForge-gray?style=for-the-badge&logo=curseforge&labelColor=orange)](https://www.curseforge.com/minecraft/mc-mods/disenchanter)
[![release](https://img.shields.io/github/v/release/glisco03/disenchanter?logo=github&style=for-the-badge)](https://github.com/glisco03/disenchanter/releases)
[![discord](https://img.shields.io/discord/825828008644313089?label=wisp%20forest&logo=discord&logoColor=white&style=for-the-badge)](https://discord.gg/xrwHKktV2d)

A Fabric mod that adds a Disenchanting Table, allowing you to salvage enchantments from items and transfer them to books. Use various catalysts to control how many enchantments are recovered and whether the item survives the process!

---

## 📖 Overview

The **Disenchanter** adds a simple but powerful block that lets you extract enchantments from items and transfer them to enchanted books. By default, extracting enchantments destroys the item, but using special **Catalysts** gives you more control over the disenchanting process:

- Recover **multiple enchantments** at once
- **Preserve the original item** (at the cost of durability)
- Extract **all enchantments** with maximum efficiency
- Target **specific enchantment levels**

Perfect for managing enchantments in your survival world without wasting precious resources!

---

## 🎮 How to Use

### Basic Setup
1. **Craft the Disenchanter** (recipe available in-game)
2. Place it down like any other block
3. Right-click to open the GUI

### Disenchanting Process
1. Place your **enchanted item** in the left slot
2. Add a **Book** in the middle slot
3. *(Optional)* Add a **Catalyst** in the top slot
4. Place **nothing** in the output slot (it must be empty)
5. Click the **Disenchant** button

The enchanted book will appear in the output slot, and the item/catalyst will be consumed according to the catalyst's behavior.

---

## 🔮 Catalysts

Catalysts modify how disenchanting works. Each catalyst has unique properties:

| Catalyst | Amount | Effect | Item Survives? |
|----------|--------|--------|----------------|
| **None** (default) | - | Extract **1 random** enchantment | ❌ No |
| **Emerald** | 1 | Extract **up to 2 random** enchantments | ❌ No |
| **Diamond** | 1 | Extract **first + up to 2 random** enchantments (3 total max) | ❌ No |
| **Ender Pearl** | 1 | Extract **1 random** enchantment | ✅ Yes (-500 durability) |
| **Amethyst Shard** | 4 | Extract **only the first** enchantment | ✅ Yes (removes all enchants) |
| **Heart of the Sea** | 1 | Extract **all enchantments** at **level - 1** | ❌ No |
| **Bottle o' Enchanting** | 1 | Extract **all max-level** enchantments | ❌ No |
| **Nether Star** | 1 | Extract **ALL enchantments** at **full level** | ✅ Yes (removes all enchants) |

### Catalyst Details

- **No Catalyst**: Basic disenchanting - extracts one random enchantment, item is destroyed
- **Emerald**: Get up to 2 random enchantments from the item
- **Diamond**: Guarantees the first enchantment plus up to 2 more random ones (up to 3 total)
- **Ender Pearl**: Preserves the item by removing one enchantment and adding 500 durability damage
- **Amethyst Shard** (×4): Removes all enchantments but only gives you the first one back; item survives
- **Heart of the Sea**: Extracts ALL enchantments but reduces each level by 1 (minimum level 1)
- **Bottle o' Enchanting**: Extracts only the highest-level enchantments from the item
- **Nether Star**: Ultimate catalyst - extracts ALL enchantments at full power and keeps the item intact!

---

## ⚙️ Configuration

The mod uses [owo-lib](https://github.com/wisp-forest/owo-lib) for configuration. Configuration options include:

- **Allow Disenchanting Without Catalyst**: Toggle whether disenchanting without a catalyst is allowed
- **Blacklist**: Define items that cannot be disenchanted

Access the config through **Mod Menu** (if installed) or edit the config file directly.

---

## 🛠️ Technical Details

### Requirements
- **Minecraft**: 1.21.9+
- **Fabric Loader**: 0.17.3+
- **Fabric API**: Latest version for 1.21.9
- **owo-lib**: 0.12.24+1.21.9
- **Java**: 21+

### Features
- ✨ Beautiful particle effects when disenchanting
- 🎨 Custom GUI with visual feedback
- 🔧 Fully configurable via config file
- 📚 REI/JEI integration for viewing catalyst recipes
- 🌐 Translations available in multiple languages

### Compatibility
- Works in **singleplayer** and **multiplayer**
- Server-side and client-side synchronization
- Compatible with most enchantment-related mods

---

## 🏗️ Building from Source

```bash
# Clone the repository
git clone https://github.com/glisco03/disenchanter.git
cd disenchanter

# Build with Gradle
./gradlew build

# Output JAR will be in build/libs/
```

---

## 📜 License

This project is licensed under the **MIT License**. See [LICENSE](LICENSE) for details.

---

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request. For major changes, please open an issue first to discuss what you would like to change.

---

## 💬 Support & Community

Join the [Wisp Forest Discord](https://discord.gg/xrwHKktV2d) for:
- Support and help
- Feature suggestions  
- Bug reports
- Community discussion

---

## 🙏 Credits

Created by [glisco](https://github.com/glisco03)

Part of the [Wisp Forest](https://github.com/wisp-forest) mod ecosystem.

---

### 🌟 Like this mod?

If you enjoy using Disenchanter, consider:
- ⭐ Starring the repository
- 📢 Sharing it with friends
- 💬 Joining our Discord community
- 🐛 Reporting bugs to help improve the mod
