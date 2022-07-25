package com.rubynaxela.onyx.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The listener interface for receiving mouse click events. The class that is interested in processing a mouse click event
 * implements this interface. When the mouse click event occurs, the {@link #mouseClicked(MouseEvent)} method is invoked.
 */
public interface ClickListener extends MouseListener {

    /**
     * @deprecated Use {@link PressAndReleaseListener} or {@link MouseListener} instead.
     */
    @Deprecated
    @Override
    default void mousePressed(MouseEvent e) {
    }

    /**
     * @deprecated Use {@link PressAndReleaseListener} or {@link MouseListener} instead.
     */
    @Deprecated
    @Override
    default void mouseReleased(MouseEvent e) {
    }

    /**
     * @deprecated Use {@link MouseListener} instead.
     */
    @Deprecated
    @Override
    default void mouseEntered(MouseEvent e) {
    }

    /**
     * @deprecated Use {@link MouseListener} instead.
     */
    @Deprecated
    @Override
    default void mouseExited(MouseEvent e) {
    }
}
