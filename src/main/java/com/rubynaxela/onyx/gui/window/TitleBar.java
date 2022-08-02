package com.rubynaxela.onyx.gui.window;

import com.rubynaxela.jadeite.awt.event.ClickListener;
import com.rubynaxela.jadeite.awt.event.MouseDragListener;
import com.rubynaxela.jadeite.awt.event.PressAndReleaseListener;
import com.rubynaxela.jadeite.awt.graphics.CircleShape;
import com.rubynaxela.onyx.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Represents a window title bar containing the title label and window control buttons.
 */
@SuppressWarnings("FieldCanBeLocal")
public class TitleBar extends JPanel implements PressAndReleaseListener, MouseDragListener {

    private final Window window;
    private final JPanel buttons;
    private final TitleBarButton close, minimize;
    private final JLabel title;
    private Point relativeCursorPos;

    /**
     * Creates the title bar. This constructor should be called before the window title is set.
     *
     * @param window reference to the window
     */
    public TitleBar(@NotNull Window window) {

        this.window = window;
        setBackground(new Color(0, 0, 0, 128));
        setLayout(new BorderLayout());

        final var layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(8);
        layout.setVgap(10);
        buttons = new JPanel();
        buttons.setLayout(layout);
        buttons.setBackground(Colors.TRANSPARENT);

        close = new TitleBarButton(Colors.MACOS_RED);
        if (window instanceof Frame) close.addClickListener(e -> System.exit(0));
        else if (window instanceof final Dialog dialog) close.addClickListener(e -> dialog.dispose());
        buttons.add(close);

        minimize = new TitleBarButton(Colors.MACOS_YELLOW);
        if (window instanceof final JFrame frame) {
            minimize.addClickListener(e -> frame.setState(JFrame.ICONIFIED));
            buttons.add(minimize);
        }

        add(buttons, BorderLayout.WEST);

        title = new JLabel();
        if (window instanceof final Frame frame) title.setText(frame.getTitle());
        else if (window instanceof final Dialog dialog) title.setText(dialog.getTitle());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD));
        add(title, BorderLayout.CENTER);

        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        relativeCursorPos = e.getPoint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        relativeCursorPos = null;
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        final Point absoluteCursorPos = e.getLocationOnScreen();
        window.setLocation(absoluteCursorPos.x - relativeCursorPos.x, absoluteCursorPos.y - relativeCursorPos.y);
    }

    /**
     * Represents a circle button located on the window title bar. Used as
     * the close and minimize button. The size of this button is 12x12.
     */
    private static class TitleBarButton extends JPanel {

        private final CircleShape buttonCircle;

        /**
         * Creates a title bar button.
         *
         * @param color the button's color
         */
        public TitleBarButton(@NotNull Color color) {
            buttonCircle = new CircleShape(6);
            buttonCircle.setColor(color);
            setBackground(Colors.TRANSPARENT);
            setPreferredSize(new Dimension(12, 12));
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            buttonCircle.draw((Graphics2D) g);
        }

        /**
         * Adds the specified {@link ClickListener} to receive mouse click events from this component.
         *
         * @param listener the mouse click listener
         */
        public void addClickListener(@NotNull ClickListener listener) {
            addMouseListener(listener);
        }
    }
}
