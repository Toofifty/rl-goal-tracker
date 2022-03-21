package com.toofifty.goaltracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("goaltracker")
public interface GoalTrackerConfig extends Config {
    @ConfigItem(
            keyName = "goaltrackerData",
            name = "",
            description = ""
    )
    default String goaltrackerData() {
        return "";
    }

    @ConfigItem(
            keyName = "goaltrackerData",
            name = "",
            description = ""
    )
    void goaltrackerData(String str);
}
