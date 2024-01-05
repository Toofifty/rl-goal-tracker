package com.toofifty.goaltracker.services;

import com.toofifty.goaltracker.GoalTrackerPlugin;
import com.toofifty.goaltracker.models.task.*;
import net.runelite.api.Client;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.util.ImageUtil;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class TaskIconService
{
    public static final ImageIcon CROSS_MARK_ICON;
    public static final ImageIcon CHECK_MARK_ICON;
    public static final ImageIcon QUEST_ICON;
    public static final ImageIcon QUEST_COMPLETE_ICON;
    public static final ImageIcon UNKNOWN_ICON;

    static {
        CROSS_MARK_ICON = new ImageIcon(
                ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/cross_mark.png")
        );
        CHECK_MARK_ICON = new ImageIcon(
                ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/check_mark.png")
        );
        QUEST_ICON = new ImageIcon(
                ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/quest_icon.png")
        );
        QUEST_COMPLETE_ICON = new ImageIcon(
                ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/quest_complete.png")
        );
        UNKNOWN_ICON = new ImageIcon(
                ImageUtil.loadImageResource(GoalTrackerPlugin.class, "/question_mark.png")
        );
    }

    @Inject
    private ItemManager itemManager;

    @Inject
    private SkillIconManager skillIconManager;

    @Inject
    private Client client;

    public ImageIcon get(Task task)
    {
        if (task instanceof ManualTask) {
            return get((ManualTask) task);
        }

        if (task instanceof QuestTask) {
            return get((QuestTask) task);
        }

        BufferedImage image = null;
        if (task instanceof SkillLevelTask) {
            image = get((SkillLevelTask) task);
        } else if (task instanceof SkillXpTask) {
            image = get((SkillXpTask) task);
        } else if (task instanceof ItemTask) {
            image = get((ItemTask) task);
        }

        if (image != null) {
            return new ImageIcon(image.getScaledInstance(16, 16, 32));
        }

        return UNKNOWN_ICON;
    }

    public ImageIcon get(ManualTask task)
    {
        return task.isDone() ? CHECK_MARK_ICON : CROSS_MARK_ICON;
    }

    public BufferedImage get(SkillLevelTask task)
    {
        return skillIconManager.getSkillImage(task.getSkill());
    }

    public BufferedImage get(SkillXpTask task)
    {
        return skillIconManager.getSkillImage(task.getSkill());
    }

    public ImageIcon get(QuestTask task)
    {
        return task.isDone() ? QUEST_COMPLETE_ICON : QUEST_ICON;
    }

    public BufferedImage get(ItemTask task)
    {
        if (task.getCachedIcon() == null && client.isClientThread()) {
            task.setCachedIcon(itemManager.getImage(task.getItemId()));
        }

        return task.getCachedIcon();
    }
}
