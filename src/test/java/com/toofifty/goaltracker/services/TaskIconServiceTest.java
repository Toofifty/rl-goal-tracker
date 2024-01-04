package com.toofifty.goaltracker.services;

import com.toofifty.goaltracker.models.enums.Status;
import com.toofifty.goaltracker.models.task.*;
import net.runelite.api.Client;
import net.runelite.api.Skill;
import net.runelite.client.game.ItemManager;
import net.runelite.client.game.SkillIconManager;
import net.runelite.client.util.AsyncBufferedImage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class TaskIconServiceTest {
    @Mock
    private ItemManager itemManager;

    @Mock
    private SkillIconManager skillIconManager;

    @Mock
    private Client client;

    @InjectMocks
    TaskIconService service;

    @Mock
    AsyncBufferedImage image;

    @BeforeEach
    public void init() {
        when(image.getScaledInstance(anyInt(), anyInt(), anyInt())).thenReturn(image);
    }

    @Test
    void get_shouldSupportManualTasks() {
        Task task = ManualTask.builder().status(Status.NOT_STARTED).build();

        assertEquals(TaskIconService.CROSS_MARK_ICON, service.get(task));
    }

    @Test
    void get_shouldSupportCompletedManualTasks() {
        Task task = ManualTask.builder().status(Status.COMPLETED).build();

        assertEquals(TaskIconService.CHECK_MARK_ICON, service.get(task));
    }

    @Test
    void get_shouldSupportNotStartedQuestTasks() {
        Task task = QuestTask.builder().status(Status.NOT_STARTED).build();

        assertEquals(TaskIconService.QUEST_ICON, service.get(task));
    }

    @Test
    void get_shouldSupportCompletedQuestTasks() {
        Task task = QuestTask.builder().status(Status.COMPLETED).build();

        assertEquals(TaskIconService.QUEST_COMPLETE_ICON, service.get(task));
    }

    @Test
    void get_shouldSupportSkillLevelTasks() {
        when(skillIconManager.getSkillImage(Skill.ATTACK)).thenReturn(image);

        Task task = SkillLevelTask.builder().skill(Skill.ATTACK).build();

        assertEquals(ImageIcon.class, service.get(task).getClass());
        verify(skillIconManager).getSkillImage(Skill.ATTACK);
    }

    @Test
    void get_shouldSupportSkillXPTasks() {
        when(skillIconManager.getSkillImage(Skill.ATTACK)).thenReturn(image);

        Task task = SkillXpTask.builder().skill(Skill.ATTACK).build();

        assertEquals(ImageIcon.class, service.get(task).getClass());
        verify(skillIconManager).getSkillImage(Skill.ATTACK);
    }

    @Test
    void get_shouldSupportItemTasks() {
        when(client.isClientThread()).thenReturn(true);
        when(itemManager.getImage(314)).thenReturn(image);

        Task task = ItemTask.builder().itemId(314).build();

        assertEquals(ImageIcon.class, service.get(task).getClass());
        verify(itemManager).getImage(314);
    }

    @Test
    void get_shouldSupportItemTasksNotBeingReady() {
        when(client.isClientThread()).thenReturn(false);
        when(itemManager.getImage(314)).thenReturn(image);

        Task task = ItemTask.builder().itemId(314).build();

        assertEquals(ImageIcon.class, service.get(task).getClass());
        verify(itemManager, never()).getImage(314);
    }
}