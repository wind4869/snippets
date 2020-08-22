package com.wind4869.bookmarks.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeBundle;
import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.ToggleAction;
import org.jetbrains.annotations.NotNull;

class ToggleSortBookmarksAction extends ToggleAction {
    ToggleSortBookmarksAction() {
        super(IdeBundle.message("action.bookmark.toggle.sort"), null, AllIcons.ObjectBrowser.Sorted);
        setEnabledInModalContext(true);
    }

    @Override
    public boolean isSelected(@NotNull AnActionEvent e) {
        return UISettings.getInstance().getSortBookmarks();
    }

    @Override
    public void setSelected(@NotNull AnActionEvent e, boolean state) {
        UISettings.getInstance().setSortBookmarks(state);
        UISettings.getInstance().fireUISettingsChanged();
    }
}