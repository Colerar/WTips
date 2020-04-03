# WTips
## 介绍
本 Tips 插件功能强大, 目前支持底部显示, 准备更新 Boss Bar 与 计分板. 同时, 本插件支持所有使用 EasyAPI 插件的 Format .
## 安装
您可在 Release 中下载打包好的 .jar 文件, 再将其放入 plugins 文件夹. 同时, 请参见 依赖 章,
## 使用
您可在 plugins/EasyAPI 目录下找到 integrateConfig.yml .
您可以修改其中的:
- WTips.tips : 您的底部显示, 支持任何使用 EasyAPI 的插件. 最后有 Format 全表.
- WTips.tips.task.delay : 底部显示的间隔. 单位: Tick
- WTips.tips.task.duration : 底部显示的持续时间. 单位: Tick
- WTips.tips.task.showtype : 显示方式, 目前可以为 TIP 或 POPUP.
- EasyAPImodule.WTips.WTips : 是否打开本插件.
## 依赖
本插件依赖于:
- [KotlinLib](https://nukkitx.com/resources/kotlinlib.48/)
- [EasyAPI](https://github.com/WetABQ/EasyAPI-Nukkit)
## Formatter
### 基础 Formatter
- %player_name% : 玩家名称
- %player_display_name% : 玩家显示名
- %player_uuid% : 玩家UUID
- %player_ip% : 玩家IP
- %player_device% : 玩家设备
- %player_health% : 玩家血量
- %player_max_health% : 玩家最大血量
- %player_food_level% : 玩家饥饿值
- %player_max_food_level% : 玩家最大饥饿值
- %player_level% : 玩家等级
- %player_speed% : 玩家速度
- %player_direction% : 玩家视角
- %player_position% : 玩家位置
- %player_exp% : 玩家经验值
- %player_exp_level% : 玩家经验等级
- %player_exp_to_next% : 下一等级经验
- %player_hand_item% : 手中物品
- %player_hand_item_meta% : 手中物品 Meta 值
- %player_x% : X
- %player_y% : Y
- %player_z% : Z
- %player_gm% : 玩家游戏模式
- %player_op% : 玩家是否为OP
- %player_ping% : 玩家的延迟
- %server_online_player : 服务器在线玩家数
- %server_motd% : 服务器MOTD
- %server_max_online% : 服务器最大在线
- %server_ram_used% : 服务器已用内存
- %server_ram_free% : 服务器闲置内存
- %server_ram_total% : 服务器总内存
- %server_cores% : 服务器核心数
- %server_tps% : 服务器TPS
- %server_uptime% : 服务器运行时间
- %date% : 日期
- %date_year% : 年
- %date_month% : 月
- %date_day% : 日
- %time% : 时间
- %time_hour% : 时 
- %time_minute% : 分
- %time_second% : 秒
- %player_ecomoney% : 玩家金钱(Economy API)
### 外部 Formatter (使用 EasyAPI)
- %wprefix_prefix% : 玩家使用的 WPrefix 称号.
