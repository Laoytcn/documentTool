import ui.MainFrame;

import java.awt.*;

/**
 * @description: 启动类
 */
public class DocumentUtilApplication {
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
