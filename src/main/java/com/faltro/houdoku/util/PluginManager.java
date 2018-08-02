package com.faltro.houdoku.util;

import com.faltro.houdoku.plugins.content.*;
import com.faltro.houdoku.plugins.info.AniList;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Manager and container for client plugins, which currently solely consists
 * of content sources. Generally, the instance of this class primarily exists
 * to hold and provide access to instances of the available plugins so that
 * multiple plugin instances are not necessary.
 */
public class PluginManager {
    private final ArrayList<ContentSource> contentSources;
    private final InfoSource infoSource;

    /**
     * Create the PluginManager.
     * <p>
     * This constructor creates instances of the available plugins, which are
     * manually defined here.
     */
    public PluginManager() {
        contentSources = new ArrayList<>();
        contentSources.addAll(Arrays.asList(
                new MangaDex(),
                new MangaHere(),
                new MangaSee(),
                new MangaTown(),
                new MangaPark()
                // add other sources here
        ));
        infoSource = new AniList(); // change to desired InfoSource
    }

    /**
     * Gets the ID of a content source instance.
     *
     * @param contentSource the ContentSource to get the id of
     * @return the ID of the contentSource's class.
     */
    private int getSourceId(ContentSource contentSource) {
        int result = -1;
        try {
            result = contentSource.getClass().getField("ID").getInt(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Gets the ContentSource instance whose class has the given ID.
     *
     * @param id the ContentSource ID (the static ID field of the
     *           implementing plugin class)
     * @return the ContentSource instance whose class has the given ID.
     */
    public ContentSource getSource(int id) {
        return contentSources.stream().filter(
                contentSource -> getSourceId(contentSource) == id
        ).findFirst().orElse(null);
    }

    public ArrayList<ContentSource> getContentSources() {
        return contentSources;
    }

    public InfoSource getInfoSource() {
        return infoSource;
    }
}
