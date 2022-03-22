package com.toofifty.goaltracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("goaltracker")
public interface GoalTrackerConfig extends Config {
    @ConfigItem(
            keyName = "goalTrackerData",
            name = "",
            description = ""
    )
    default String goalTrackerData() {
        return "";
    }

    @ConfigItem(
            keyName = "goalTrackerData",
            name = "",
            description = ""
    )
    void goalTrackerData(String str);
}
