package com.wind4869.bookmarks.actions;

import com.intellij.icons.AllIcons;
import com.wind4869.bookmarks.Bookmark;
import com.wind4869.bookmarks.BookmarkItem;
import com.wind4869.bookmarks.BookmarkManager;
import com.intellij.lang.LangBundle;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.ui.ListUtil;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.List;

class DeleteBookmarkAction extends DumbAwareAction {
    private final Project myProject;
    private final JList<? extends BookmarkItem> myList;

    DeleteBookmarkAction(Project project, JList<? extends BookmarkItem> list) {
        super(LangBundle.messagePointer("action.DeleteBookmarkAction.delete.text"),
                LangBundle.messagePointer("action.delete.current.bookmark.description"), AllIcons.General.Remove);
        setEnabledInModalContext(true);
        myProject = project;
        myList = list;
        registerCustomShortcutSet(CustomShortcutSet.fromString("DELETE", "BACK_SPACE"), list);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(BookmarksAction.getSelectedBookmarks(myList).size() > 0);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        List<Bookmark> bookmarks = BookmarksAction.getSelectedBookmarks(myList);
        ListUtil.removeSelectedItems(myList);

        for (Bookmark bookmark : bookmarks) {
            BookmarkManager.getInstance(myProject).removeBookmark(bookmark);
        }
    }
}