package com.toofifty.goaltracker;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("goaltracker")
public interface GoalTrackerConfig extends Config
{
    @ConfigItem(keyName = "goalTrackerData", name = "", description = "", hidden = true)
    default String goalTrackerData()
    {
        return "";
    }

    @ConfigItem(keyName = "goalTrackerData", name = "", description = "", hidden = true)
    void goalTrackerData(String str);

    @ConfigItem(keyName = "goalTrackerItemCache", name = "", description = "", hidden = true)
    default String goalTrackerItemCache()
    {
        return "";
    }

    @ConfigItem(keyName = "goalTrackerItemCache", name = "", description = "", hidden = true)
    void goalTrackerItemCache(String str);
}
