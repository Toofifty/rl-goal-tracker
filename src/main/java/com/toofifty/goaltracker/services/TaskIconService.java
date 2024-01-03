package com.toofifty.goaltracker.services;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.goal.ItemTask;
import com.toofifty.goaltracker.goal.ManualTask;
import com.toofifty.goaltracker.goal.QuestTask;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import com.toofifty.goaltracker.goal.SkillXpTask;
import com.toofifty.goaltracker.goal.Task;
import java.awt.image.BufferedImage;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.util.ImageUtil;
import org.apache.commons.lang3.NotImplementedException;

public class TaskIconService
{
    private static final BufferedImage CROSS_MARK_ICON;
    private static final BufferedImage QUEST_ICON;

    static {
        CROSS_MARK_ICON = ImageUtil.loadImageResource(
            GoalTrackerPlugin.class, "/cross_mark.png");
        QUEST_ICON = ImageUtil.loadImageResource(
            GoalTrackerPlugin.class, "/quest_icon.png");
    }

    @Inject
    private Client client;

    @Inject
    private ItemManager itemManager;

    @Inject
    private SkillIconManager skillIconManager;

    public BufferedImage get(Task task)
    {
        if (task instanceof ManualTask) {
            return get((ManualTask) task);
        }

        if (task instanceof SkillLevelTask) {
            return get((SkillLevelTask) task);
        }

        if (task instanceof SkillXpTask) {
            return get((SkillXpTask) task);
        }

        if (task instanceof QuestTask) {
            return get((QuestTask) task);
        }

        if (task instanceof ItemTask) {
            return get((ItemTask) task);
        }

        throw new NotImplementedException("Missing task icon implementation");
    }

    public BufferedImage get(ManualTask task)
    {
        return CROSS_MARK_ICON;
    }

    public BufferedImage get(SkillLevelTask task)
    {
        return skillIconManager.getSkillImage(task.getSkill());
    }

    public BufferedImage get(SkillXpTask task)
    {
        return skillIconManager.getSkillImage(task.getSkill());
    }

    public BufferedImage get(QuestTask task)
    {
        return QUEST_ICON;
    }

    public BufferedImage get(ItemTask task)
    {
        if (task.getCachedIcon() == null) {
            task.setCachedIcon(itemManager.getImage(task.getItemId()));
        }

        return task.getCachedIcon();
    }
}
