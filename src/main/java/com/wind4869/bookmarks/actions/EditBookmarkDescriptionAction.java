package com.wind4869.bookmarks.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.IdeBundle;
import com.wind4869.bookmarks.Bookmark;
import com.wind4869.bookmarks.BookmarkItem;
import com.wind4869.bookmarks.BookmarkManager;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CustomShortcutSet;
import com.intellij.openapi.project.DumbAwareAction;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.util.SystemInfo;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

class EditBookmarkDescriptionAction extends DumbAwareAction {
    private final Project myProject;
    private final JList<? extends BookmarkItem> myList;
    private JBPopup myPopup;

    EditBookmarkDescriptionAction(Project project, JList<? extends BookmarkItem> list) {
        super(IdeBundle.message("action.bookmark.edit.description"), IdeBundle.message("action.bookmark.edit.description.description"), AllIcons.Actions.Edit);
        setEnabledInModalContext(true);
        myProject = project;
        myList = list;
        registerCustomShortcutSet(new CustomShortcutSet(KeyStroke.getKeyStroke(SystemInfo.isMac ? "meta ENTER" : "control ENTER")), list);
    }

    @Override
    public void update(@NotNull AnActionEvent e) {
        e.getPresentation().setEnabled(myPopup != null && myPopup.isVisible() && BookmarksAction.getSelectedBookmarks(myList).size() == 1);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (myPopup == null || !myPopup.isVisible()) {
            return;
        }
        Bookmark bookmark = BookmarksAction.getSelectedBookmarks(myList).get(0);
        myPopup.setUiVisible(false);

        BookmarkManager.getInstance(myProject).editDescription(bookmark, myList);

        if (myPopup != null && !myPopup.isDisposed()) {
            myPopup.setUiVisible(true);
            myPopup.setSize(myPopup.getContent().getPreferredSize());
        }
    }

    public void setPopup(JBPopup popup) {
        myPopup = popup;
    }
}