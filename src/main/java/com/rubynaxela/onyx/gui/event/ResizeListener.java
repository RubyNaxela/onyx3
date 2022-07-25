package com.rubynaxela.onyx.gui.event;

import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

/**
 * The listener interface for receiving component resize events. The class that is interested
 * in processing a component resize event implements this interface. When the component
 * resize event occurs, the {@link #componentResized(ComponentEvent)} method is invoked.
 */
public interface ResizeListener extends ComponentListener {

    @Override
    default void componentMoved(ComponentEvent e) {
    }

    @Override
    default void componentShown(ComponentEvent e) {
    }

    @Override
    default void componentHidden(ComponentEvent e) {
    }
}
