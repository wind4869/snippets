package com.wind4869;

import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.ui.popup.util.DetailViewImpl;
import com.intellij.util.ui.JBUI;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * MyDetailView, Custom DetailView
 *
 * @author wind4869
 * @since 1.0.0
 */
public class MyDetailView extends DetailViewImpl {
    MyDetailView(Project project) {
        super(project);
    }

    @NotNull
    @Override
    protected Editor createEditor(@Nullable Project project, Document document, VirtualFile file) {
        Editor editor = super.createEditor(project, document, file);
        editor.setBorder(JBUI.Borders.empty());
        return editor;
    }
}
