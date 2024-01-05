package com.toofifty.goaltracker.services;

import com.toofifty.goaltracker.ItemCache;
import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.task.*;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.StatChanged;

import javax.inject.Inject;

public class TaskUpdateService
{
    @Inject
    private Client client;

    @Inject
    private ItemCache itemCache;

    public boolean update(Task task) {
        switch (task.getType()) {
            case SKILL_LEVEL: return this.update((SkillLevelTask) task);
            case SKILL_XP: return this.update((SkillXpTask) task);
            case QUEST: return this.update((QuestTask) task);
            case ITEM: return this.update((ItemTask) task);
        }

        return false;
    }

    /**
     * Returns true if an update has occurred
     * @param task the skill level task to validate
     */
    public boolean update(SkillLevelTask task)
    {
        if (client.getGameState() != GameState.LOGGED_IN) return false;

        return this.update(task, client.getRealSkillLevel(task.getSkill()));
    }

    /**
     * Returns true if an update has occurred
     * @param task the skill level task to validate
     * @param event the stat changed event this update is tied to
     */
    public boolean update(SkillLevelTask task, StatChanged event)
    {
        if (event.getSkill() != task.getSkill()) return false;

        return this.update(task, event.getLevel());
    }

    /**
     * Returns true if an update has occurred
     * @param task the skill level task to validate
     */
    public boolean update(SkillLevelTask task, int level)
    {
        Status oldStatus = task.getStatus();

        task.setStatus(
                level >= task.getLevel()
                        ? Status.COMPLETED
                        : Status.NOT_STARTED
        );

        return oldStatus != task.getStatus();
    }

    /**
     * Returns true if an update has occurred
     * @param task the skill level task to validate
     */
    public boolean update(SkillXpTask task)
    {
        if (client.getGameState() != GameState.LOGGED_IN) return false;

        return this.update(task, client.getSkillExperience(task.getSkill()));
    }

    /**
     * Returns true if an update has occurred
     * @param task the skill level task to validate
     * @param event the stat changed event this update is tied to
     */
    public boolean update(SkillXpTask task, StatChanged event)
    {
        if (event.getSkill() != task.getSkill()) return false;

        return this.update(task, event.getXp());
    }

    /**
     * Returns true if an update has occurred
     * @param task the skill xp task to validate
     */
    public boolean update(SkillXpTask task, int xp)
    {
        Status oldStatus = task.getStatus();

        task.setStatus(
                xp >= task.getXp()
                    ? Status.COMPLETED
                    : Status.NOT_STARTED
        );

        return oldStatus != task.getStatus();
    }

    /**
     * Returns true if an update has occurred
     * @param task the quest task to validate
     */
    public boolean update(QuestTask task)
    {
        if (client.getGameState() != GameState.LOGGED_IN || !client.isClientThread()) return false;

        Status oldStatus = task.getStatus();

        task.setStatus(Status.fromQuestState(task.getQuest().getState(client)));

        return oldStatus != task.getStatus();
    }

    /**
     * Returns true if an update has occurred
     * @param task the item task to validate
     */
    public boolean update(ItemTask task)
    {
        int oldAcquired = task.getAcquired();
        Status oldStatus = task.getStatus();

        task.setAcquired(Math.min(itemCache.getTotalQuantity(task.getItemId()), task.getQuantity()));

        task.setStatus(
            task.getAcquired() >= task.getQuantity()
                ? Status.COMPLETED
                : (task.getAcquired() > 0
                    ? Status.IN_PROGRESS
                    : Status.NOT_STARTED));

        return oldAcquired != task.getAcquired() || oldStatus != task.getStatus();
    }
}
