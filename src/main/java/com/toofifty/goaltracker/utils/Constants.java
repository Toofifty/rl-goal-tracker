package com.toofifty.goaltracker.utils;

import com.toofifty.goaltracker.models.enums.Status;
import net.runelite.client.ui.ColorScheme;

import java.awt.*;
import java.util.Map;

public class Constants {
    public static final Map<Status, Color> STATUS_TO_COLOR = Map.of(
            Status.NOT_STARTED, ColorScheme.PROGRESS_ERROR_COLOR,
            Status.IN_PROGRESS, ColorScheme.PROGRESS_INPROGRESS_COLOR,
            Status.COMPLETED, ColorScheme.PROGRESS_COMPLETE_COLOR
    );
}
