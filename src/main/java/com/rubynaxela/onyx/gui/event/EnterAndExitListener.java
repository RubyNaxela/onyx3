package com.rubynaxela.onyx.gui.event;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * The listener interface for receiving mouse enter and exit events. The class that is interested in processing a mouse
 * enter or exit event implements this interface. When the enter event occurs, the {@link #mouseEntered(MouseEvent)}
 * method is invoked. When the exit event occurs, the {@link #mouseExited(MouseEvent)} method is invoked.
 */
public interface EnterAndExitListener extends MouseListener {

    @Override
    default void mouseClicked(MouseEvent e) {
    }

    @Override
    default void mousePressed(MouseEvent e) {
    }

    @Override
    default void mouseReleased(MouseEvent e) {
    }
}
