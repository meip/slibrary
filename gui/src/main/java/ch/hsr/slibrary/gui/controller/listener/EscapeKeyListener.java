package ch.hsr.slibrary.gui.controller.listener;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public abstract class EscapeKeyListener implements KeyListener {
    @Override
    public void keyTyped(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            escapeAction();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    public abstract void escapeAction();
}
