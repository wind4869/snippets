package com.wind4869;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.util.DimensionService;
import com.intellij.openapi.wm.ex.WindowManagerEx;
import com.intellij.ui.components.JBList;
import com.intellij.ui.popup.util.ItemWrapper;
import com.intellij.ui.popup.util.MasterDetailPopupBuilder;
import com.intellij.util.ArrayUtilRt;
import com.wind4869.bookmarks.Bookmark;
import com.wind4869.bookmarks.BookmarkItem;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * SnippetsAction, Main action
 *
 * @author wind4869
 * @since 1.0.0
 */
public class SnippetsAction extends AnAction implements MasterDetailPopupBuilder.Delegate {
    private static final String DIMENSION_SERVICE_KEY = "snippets";
    private static final String TITLE = "Snippets";
    private JBPopup myPopup;

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getProject();
        if (project == null) {
            return;
        }

        if (myPopup != null && myPopup.isVisible()) {
            myPopup.cancel();
            return;
        }

        DefaultListModel<BookmarkItem> snippets = SnippetsManager.getInstance(project).getSnippets();
        JBPopup popup = new MasterDetailPopupBuilder(project)
                .setDelegate(this)
                .setAddDetailViewToEast(true)
                .setList(new JBList<>(snippets))
                .setDetailView(new MyDetailView(project))
                .setDimensionServiceKey(DIMENSION_SERVICE_KEY)
                .setPopupTuner(builder -> builder.setCloseOnEnter(false).setCancelOnClickOutside(false))
                .setDoneRunnable(() -> {
                    if (myPopup != null) myPopup.cancel();
                }).createMasterDetailPopup();

        myPopup = popup;

        Point location = DimensionService.getInstance().getLocation(DIMENSION_SERVICE_KEY, project);
        if (location != null) {
            Window window = WindowManagerEx.getInstanceEx().getMostRecentFocusedWindow();
            if (window != null) {
                popup.showInScreenCoordinates(window, location);
                return;
            }
        }
        popup.showInBestPositionFor(event.getDataContext());
    }

    @Override
    public void update(@NotNull AnActionEvent event) {
        event.getPresentation().setEnabledAndVisible(event.getProject() != null);
    }

    @Override
    public String getTitle() {
        return TITLE;
    }

    @Override
    public void handleMnemonic(KeyEvent e, Project project, JBPopup popup) {
    }

    @Override
    @Nullable
    public JComponent createAccessoryView(Project project) {
        return null;
    }

    @Override
    public Object[] getSelectedItemsInTree() {
        return ArrayUtilRt.EMPTY_OBJECT_ARRAY;
    }

    @Override
    public void itemChosen(ItemWrapper item, Project project, JBPopup popup, boolean withEnterOrDoubleClick) {
        if (item instanceof BookmarkItem && withEnterOrDoubleClick) {
            Bookmark bookmark = ((BookmarkItem) item).getBookmark();
            popup.cancel();
            bookmark.navigate(true);
        }
    }

    @Override
    public void removeSelectedItemsInTree() {
    }
}
