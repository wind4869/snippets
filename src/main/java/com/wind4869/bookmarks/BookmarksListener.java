package com.wind4869.bookmarks;

import com.intellij.util.messages.Topic;
import org.jetbrains.annotations.NotNull;

public interface BookmarksListener {
  Topic<BookmarksListener> TOPIC = Topic.create("Bookmarks", BookmarksListener.class);

  default void bookmarkAdded(@NotNull Bookmark b) { }

  default void bookmarkRemoved(@NotNull Bookmark b) { }

  default void bookmarkChanged(@NotNull Bookmark b) { }

  default void bookmarksOrderChanged() { }
}