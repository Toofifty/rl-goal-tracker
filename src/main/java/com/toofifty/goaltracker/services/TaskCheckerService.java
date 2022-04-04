package com.toofifty.goaltracker.services;

import com.toofifty.goaltracker.ItemCache;
import com.toofifty.goaltracker.goal.ItemTask;
import com.toofifty.goaltracker.goal.ManualTask;
import com.toofifty.goaltracker.goal.QuestTask;
import com.toofifty.goaltracker.goal.SkillLevelTask;
import com.toofifty.goaltracker.goal.SkillXpTask;
import com.toofifty.goaltracker.goal.Task;
import com.toofifty.goaltracker.goal.TaskStatus;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import org.apache.commons.lang3.NotImplementedException;

public class TaskCheckerService
{
    @Inject
    private Client client;

    @Inject
    private ItemCache itemCache;

    public TaskStatus check(Task task)
    {
        if (task instanceof ManualTask) {
            return check((ManualTask) task);
        }

        if (task instanceof SkillLevelTask) {
            return check((SkillLevelTask) task);
        }

        if (task instanceof SkillXpTask) {
            return check((SkillXpTask) task);
        }

        if (task instanceof QuestTask) {
            return check((QuestTask) task);
        }

        if (task instanceof ItemTask) {
            return check((ItemTask) task);
        }

        throw new NotImplementedException("Missing task check implementation");
    }

    public TaskStatus check(ManualTask task)
    {
        return task.setResult(task.isDone()
            ? TaskStatus.COMPLETED
            : TaskStatus.NOT_STARTED);
    }

    public TaskStatus check(SkillLevelTask task)
    {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return task.getResult();
        }

        return task.setResult(
            client.getRealSkillLevel(task.getSkill()) >= task.getLevel()
                ? TaskStatus.COMPLETED
                : TaskStatus.NOT_STARTED);
    }

    public TaskStatus check(SkillXpTask task)
    {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return task.getResult();
        }

        return task.setResult(
            client.getSkillExperience(task.getSkill()) >= task.getXp()
                ? TaskStatus.COMPLETED
                : TaskStatus.NOT_STARTED);
    }

    public TaskStatus check(QuestTask task)
    {
        if (client.getGameState() != GameState.LOGGED_IN ||
            !client.isClientThread()) {
            return task.getResult();
        }

        return task.setResult(TaskStatus.fromQuestState(task.getQuest().getState(client)));
    }

    public TaskStatus check(ItemTask task)
    {
        task.setAcquired(Math.min(
            itemCache.getTotalQuantity(task.getItemId()),
            task.getQuantity()));

        return task.setResult(
            task.getAcquired() >= task.getQuantity()
                ? TaskStatus.COMPLETED
                : (task.getAcquired() > 0
                    ? TaskStatus.IN_PROGRESS
                    : TaskStatus.NOT_STARTED));
    }
}
