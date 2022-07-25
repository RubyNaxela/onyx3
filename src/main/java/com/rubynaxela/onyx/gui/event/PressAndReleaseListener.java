package com.rubynaxela.onyx.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The listener interface for receiving mouse press and release events. The class that is interested in processing a mouse
 * press or release event implements this interface. When the press event occurs, the {@link #mousePressed(MouseEvent)}
 * method is invoked. When the release event occurs, the {@link #mouseReleased(MouseEvent)} method is invoked.
 */
public interface PressAndReleaseListener extends MouseListener {

    /**
     * @deprecated Use {@link ClickListener} or {@link MouseListener} instead.
     */
    @Deprecated
    @Override
    default void mouseClicked(MouseEvent e) {
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
