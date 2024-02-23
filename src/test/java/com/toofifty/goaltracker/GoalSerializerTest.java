package com.toofifty.goaltracker;

import com.google.common.io.Resources;
import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.enums.TaskType;
import com.toofifty.goaltracker.models.Goal;
import com.toofifty.goaltracker.models.task.*;
import com.toofifty.goaltracker.utils.ReorderableList;
import net.runelite.api.Quest;
import net.runelite.api.Skill;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GoalSerializerTest {
    GoalSerializer serializer = new GoalSerializer();

    @Test
    public void deserialize_should_parse_successfully() throws IOException {
        //noinspection UnstableApiUsage
        String json = Resources.toString(Resources.getResource("data.json"), Charset.defaultCharset());

        List<Goal> goals = serializer.deserialize(json);

        assertEquals(1, goals.size());
        assertEquals("My Goal", goals.get(0).getDescription());
        assertEquals(-1, goals.get(0).getDisplayOrder());
        assertEquals(1, goals.get(0).getTasks().size());

        assertEquals(ManualTask.class, goals.get(0).getTasks().get(0).getClass());

        ManualTask task = (ManualTask) goals.get(0).getTasks().get(0);

        assertEquals(TaskType.MANUAL, task.getType());
        assertEquals(Status.COMPLETED, task.getStatus());
        assertTrue(task.isNotified());
        assertTrue(task.isDone());
        assertEquals(0, task.getIndentLevel());
        assertEquals("Do all the things!", task.getDescription());
    }

    @Test
    public void deserialize_should_support_other_task_types() throws IOException {
        //noinspection UnstableApiUsage
        String json = Resources.toString(Resources.getResource("complex.json"), Charset.defaultCharset());

        List<Goal> goals = serializer.deserialize(json);

        assertEquals(1, goals.size());
        assertEquals("My Goal", goals.get(0).getDescription());
        assertEquals(-1, goals.get(0).getDisplayOrder());
        assertEquals(5, goals.get(0).getTasks().size());

        assertEquals(ManualTask.class, goals.get(0).getTasks().get(0).getClass());
        assertEquals(0, goals.get(0).getTasks().get(0).getIndentLevel());
        assertEquals(SkillLevelTask.class, goals.get(0).getTasks().get(1).getClass());
        assertEquals(1, goals.get(0).getTasks().get(1).getIndentLevel());

        SkillLevelTask skillLevelTask = (SkillLevelTask) goals.get(0).getTasks().get(1);

        assertEquals(99, skillLevelTask.getLevel());
        assertEquals(Skill.ATTACK, skillLevelTask.getSkill());
        assertEquals(1, skillLevelTask.getIndentLevel());

        assertEquals(SkillXpTask.class, goals.get(0).getTasks().get(2).getClass());

        SkillXpTask skillXpTask = (SkillXpTask) goals.get(0).getTasks().get(2);

        assertEquals(1234, skillXpTask.getXp());
        assertEquals(Skill.ATTACK, skillXpTask.getSkill());
        assertEquals(2, skillXpTask.getIndentLevel());

        assertEquals(QuestTask.class, goals.get(0).getTasks().get(3).getClass());

        QuestTask questTask = (QuestTask) goals.get(0).getTasks().get(3);

        assertEquals(Quest.ANIMAL_MAGNETISM, questTask.getQuest());
        assertEquals(1, questTask.getIndentLevel());

        assertEquals(ItemTask.class, goals.get(0).getTasks().get(4).getClass());

        ItemTask itemTask = (ItemTask) goals.get(0).getTasks().get(4);

        assertEquals(314, itemTask.getItemId());
        assertEquals(4, itemTask.getAcquired());
        assertEquals(10, itemTask.getQuantity());
        assertEquals("Feather", itemTask.getItemName());
        assertEquals(0, itemTask.getIndentLevel());
    }

    @Test
    public void serialize_should_convert_goals_to_json() throws IOException {
        //noinspection UnstableApiUsage
        String expectedJson = Resources.toString(Resources.getResource("data.json"), Charset.defaultCharset());

        List<Goal> goals = Arrays.asList(
            Goal.builder()
                .description("My Goal")
                .tasks(ReorderableList.from(Arrays.asList(
                    ManualTask.builder()
                        .description("Do all the things!")
                        .status(Status.COMPLETED)
                        .notified(true)
                        .indentLevel(0)
                        .build())))
                .build()
        );

        assertEquals(expectedJson, serializer.serialize(goals, true));
        
    }

    @Test
    public void serialize_should_support_other_task_types() throws IOException {
        //noinspection UnstableApiUsage
        String expectedJson = Resources.toString(Resources.getResource("complex.json"), Charset.defaultCharset());

        List<Goal> goals = Arrays.asList(
                Goal.builder()
                        .description("My Goal")
                        .tasks(ReorderableList.from(Arrays.asList(
                                ManualTask.builder()
                                        .description("Do all the things!")
                                        .status(Status.COMPLETED)
                                        .notified(true)
                                        .indentLevel(0)
                                        .build(),
                                SkillLevelTask.builder()
                                        .status(Status.IN_PROGRESS)
                                        .notified(false)
                                        .indentLevel(1)
                                        .level(99)
                                        .skill(Skill.ATTACK)
                                        .build(),
                                SkillXpTask.builder()
                                        .status(Status.NOT_STARTED)
                                        .notified(false)
                                        .indentLevel(2)
                                        .xp(1234)
                                        .skill(Skill.ATTACK)
                                        .build(),
                                QuestTask.builder()
                                        .status(Status.NOT_STARTED)
                                        .notified(false)
                                        .indentLevel(1)
                                        .quest(Quest.ANIMAL_MAGNETISM)
                                        .build(),
                                ItemTask.builder()
                                        .status(Status.NOT_STARTED)
                                        .notified(false)
                                        .indentLevel(0)
                                        .quantity(10)
                                        .acquired(4)
                                        .itemId(314)
                                        .itemName("Feather")
                                        .build()
                        ))).build()
        );

        assertEquals(expectedJson, serializer.serialize(goals, true));
    }
}