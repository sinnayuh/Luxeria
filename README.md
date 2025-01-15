# Luxeria ✨
A feature-rich Minecraft plugin that enhances SMP experience with custom home management, spawner control, and dynamic scoreboard statistics.

## Features

### Home Management
Easily manage multiple teleport locations with the following commands:
- `/sethome <name>` - Create a new home location
- `/home <name>` - Teleport to a saved home location
- `/listhome` - View all your saved home locations
- `/delhome <name>` - Remove a saved home location

### Spawner Control
Customize spawner activation radius to optimize server performance:
- `/inc getradius` - Check the current spawner activation range
- `/inc setradius <value>` - Modify the spawner activation range
- `/inc help` - Display available spawner commands

### Dynamic Scoreboard
Real-time server statistics display including:
- Online Players Count
- Total Entities Killed
- Player Deaths
- Raids Won

## Commands
| Command | Permission | Description |
|---------|------------|-------------|
| `/sethome <name>` | `luxeria.home.set` | Save a new home location |
| `/home <name>` | `luxeria.home.teleport` | Teleport to a saved home |
| `/listhome` | `luxeria.home.list` | Display all saved homes |
| `/delhome <name>` | `luxeria.home.delete` | Remove a saved home |
| `/inc getradius` | `luxeria.spawner.getradius` | View spawner activation range |
| `/inc setradius <value>` | `luxeria.spawner.setradius` | Set spawner activation range |

## Installation
1. Download the latest version of Luxeria.jar
2. Place the jar file in your server's `plugins` folder
3. Restart your server