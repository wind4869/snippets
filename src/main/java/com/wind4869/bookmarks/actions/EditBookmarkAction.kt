package com.wind4869.bookmarks.actions;

import com.wind4869.bookmarks.BookmarkManager
import com.wind4869.bookmarks.actions.ToggleBookmarkAction.getBookmarkInfo
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.project.DumbAwareAction
import javax.swing.JComponent

class EditBookmarkAction : DumbAwareAction() {
    override fun actionPerformed(e: AnActionEvent) {
        val project = e.project ?: return
        val bookmark = getBookmarkInfo(e)?.bookmarkAtPlace ?: return
        val contextComponent = e.getData(PlatformDataKeys.CONTEXT_COMPONENT) as? JComponent ?: return
        BookmarkManager.getInstance(project).editDescription(bookmark, contextComponent)
    }

    override fun update(e: AnActionEvent) {
        val info = getBookmarkInfo(e)
        e.presentation.isEnabledAndVisible = info != null && info.bookmarkAtPlace != null
    }
}
