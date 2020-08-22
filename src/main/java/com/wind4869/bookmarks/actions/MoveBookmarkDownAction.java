package com.wind4869.bookmarks.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeBundle;
import com.wind4869.bookmarks.BookmarkItem;
import com.wind4869.bookmarks.BookmarkManager;
import com.intellij.ide.ui.UISettings;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonShortcuts;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ListUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

class MoveBookmarkDownAction extends DumbAwareAction {
    private final Project myProject;
    private final JList<BookmarkItem> myList;

    MoveBookmarkDownAction(Project project, JList<BookmarkItem> list) {
        super(IdeBundle.message("action.bookmark.move.down"), null, AllIcons.Actions.MoveDown);
        setEnabledInModalContext(true);
        myProject = project;
        myList = list;
        registerCustomShortcutSet(CommonShortcuts.MOVE_DOWN, list);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        int modelSize = myList.getModel().getSize();
        if (modelSize == 0 || !BookmarksAction.notFiltered(myList) || UISettings.getInstance().getSortBookmarks()) {
            e.getPresentation().setEnabled(false);
        } else {
            int lastIndex = modelSize - 1;
            if (myList.getModel().getElementAt(lastIndex) == null) lastIndex--;
            e.getPresentation().setEnabled(BookmarksAction.getSelectedBookmarks(myList).size() == 1 && myList.getSelectedIndex() < lastIndex);
        }
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        ListUtil.moveSelectedItemsDown(myList);
        BookmarkManager.getInstance(myProject).moveBookmarkDown(BookmarksAction.getSelectedBookmarks(myList).get(0));
    }
}