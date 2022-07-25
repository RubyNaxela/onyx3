package com.rubynaxela.onyx.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

/**
 * The listener interface for receiving mouse drag events. The class that is interested in processing a mouse drag event
 * implements this interface. When the mouse drag event occurs, the {@link #mouseDragged(MouseEvent)} method is invoked.
 */
public interface MouseDragListener extends MouseMotionListener {

    @Override
    default void mouseMoved(MouseEvent e) {
    }
}
