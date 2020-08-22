package com.wind4869;

import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import com.wind4869.bookmarks.Bookmark;
import com.wind4869.bookmarks.BookmarkItem;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.io.File;
import java.nio.file.Paths;

/**
 * SnippetsManager, Main Manager
 *
 * @author wind4869
 * @since 1.0.0
 */
public class SnippetsManager {
    private static final String PATH = "/workspace/snippets-java";
    private final DefaultListModel<BookmarkItem> snippets;
    private final Project myProject;

    public static SnippetsManager getInstance(@NotNull Project project) {
        return ServiceManager.getService(project, SnippetsManager.class);
    }

    public SnippetsManager(@NotNull Project project) {
        snippets = new DefaultListModel<>();
        myProject = project;
        init();
    }

    public DefaultListModel<BookmarkItem> getSnippets() {
        return snippets;
    }

    private void init() {
        addSnippets(System.getProperty("user.home") + PATH);
    }

    private void addSnippets(String path) {
        File[] files = new File(path).listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            String absolutePath = file.getAbsolutePath();
            if (file.isDirectory()) {
                addSnippets(absolutePath);
            } else if (absolutePath.endsWith(".java")) {
                addSnippet(absolutePath);
            }
        }
    }

    private void addSnippet(String absolutePath) {
        VirtualFile file = VirtualFileManager.getInstance()
                .findFileByNioPath(Paths.get(absolutePath));
        if (file == null) {
            return;
        }
        Bookmark snippet = new Bookmark(myProject, file, -1, "");
        snippets.addElement(new BookmarkItem(snippet));
    }
}
