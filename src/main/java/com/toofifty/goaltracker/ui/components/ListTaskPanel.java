package com.toofifty.goaltracker.ui.components;

import com.toofifty.goaltracker.utils.ReorderableList;
import com.toofifty.goaltracker.models.task.Task;
import java.util.function.Consumer;
import javax.swing.JMenuItem;

public class ListTaskPanel extends ListItemPanel<Task>
{
    private final JMenuItem indentItem = new JMenuItem("Indent");
    private final JMenuItem unindentItem = new JMenuItem("Unindent");

    private Consumer<Task> indentedListener;
    private Consumer<Task> unindentedListener;

    public ListTaskPanel(ReorderableList<Task> list, Task item)
    {
        super(list, item);

        indentItem.addActionListener(e -> {
            // Indent all of the items children
            var index = list.indexOf(item);
            for (int i = index + 1; i < list.size(); i++) {
                var child = list.get(i);

                System.out.println(String.format("%s >= %s", item.getIndentLevel(), child.getIndentLevel()));
                // If a child is less indented then this item assume its a parent node and break
                if (item.getIndentLevel() >= child.getIndentLevel()) break;

                child.indent();
            }

            item.indent();
            this.indentedListener.accept(item);
        });

        unindentItem.addActionListener(e -> {
            // Unindent all of the items children
            var index = list.indexOf(item);
            for (int i = index + 1; i < list.size(); i++) {
                var child = list.get(i);

                System.out.println(String.format("%s >= %s", item.getIndentLevel(), child.getIndentLevel()));
                // If a child is less indented then this item assume its a parent node and break
                if (item.getIndentLevel() >= child.getIndentLevel()) break;

                child.unindent();;
            }

            item.unindent();
            this.unindentedListener.accept(item);
        });
    }

    @Override
    public void refreshMenu()
    {
        popupMenu.removeAll();
        if (!list.isFirst(item)) {
            popupMenu.add(moveUp);
        }
        if (!list.isLast(item)) {
            popupMenu.add(moveDown);
        }
        if (!list.isFirst(item)) {
            popupMenu.add(moveToTop);
        }
        if (!list.isLast(item)) {
            popupMenu.add(moveToBottom);
        }

        var previousItem = list.getPreviousItem(item);
        
        if (item.isNotFullyIndented() && previousItem != null && previousItem.getIndentLevel() >= item.getIndentLevel()) {
            popupMenu.add(indentItem);
        }

        if (item.isIndented()) {
            popupMenu.add(unindentItem);
        }

        popupMenu.add(removeItem);
    }

    public void onIndented(Consumer<Task> indentedListener) {
        this.indentedListener = indentedListener;
    }

    public void onUnindented(Consumer<Task> unindentedListener) {
        this.unindentedListener = unindentedListener;
    }
}
