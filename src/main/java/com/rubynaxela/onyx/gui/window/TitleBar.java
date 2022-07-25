package com.rubynaxela.onyx.gui.window;

import com.rubynaxela.onyx.gui.animation.CircleShape;
import com.rubynaxela.onyx.gui.event.ClickListener;
import com.rubynaxela.onyx.gui.event.MouseDragListener;
import com.rubynaxela.onyx.gui.event.PressAndReleaseListener;
import com.rubynaxela.onyx.util.Colors;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Represents the window title bar containing the title label and window control buttons.
 */
public class TitleBar extends JPanel implements PressAndReleaseListener, MouseDragListener {

    private final JFrame window;
    private final JPanel buttons;
    private final TitleBarButton close, minimize;
    private final JLabel title;
    private Point relativeCursorPos;

    /**
     * Creates the title bar. This constructor should be called before the window title is set.
     *
     * @param window reference to the window
     */
    public TitleBar(@NotNull JFrame window) {

        this.window = window;
        setBackground(new Color(0, 0, 0, 128));
        setPreferredSize(new Dimension(1152, 28));
        setLayout(new BorderLayout());

        final var layout = new FlowLayout(FlowLayout.LEFT);
        layout.setHgap(8);
        layout.setVgap(10);
        buttons = new JPanel();
        buttons.setLayout(layout);
        buttons.add(close = new TitleBarButton(Colors.MACOS_RED));
        buttons.add(minimize = new TitleBarButton(Colors.MACOS_YELLOW));
        buttons.setBackground(Colors.TRANSPARENT);
        add(buttons, BorderLayout.WEST);

        title = new JLabel(window.getTitle());
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(title.getFont().deriveFont(Font.BOLD));
        add(title, BorderLayout.CENTER);

        addMouseListener(this);
        addMouseMotionListener(this);

        close.addClickListener(e -> System.exit(0));
        minimize.addClickListener(e -> window.setState(JFrame.ICONIFIED));
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
