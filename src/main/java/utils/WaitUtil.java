package utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 旋转等待的dialog
 */
public class WaitUtil extends JDialog {
    private static final long serialVersionUID = 6987303361741568128L;
    private final JPanel contentPanel = new JPanel();


    /**
     * Create the dialog.
     */
    public WaitUtil() {
        setBounds(0, 0, 232, 94);
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(null);
        {
            JLabel lblLoading = new JLabel("正在转换中请稍后...");
            lblLoading.setForeground(Color.DARK_GRAY);
            lblLoading.setOpaque(false);
            lblLoading.setIcon(Imgs.getImage("../loading.gif"));
            lblLoading.setFont(new Font("宋体", Font.PLAIN, 15));
            lblLoading.setBounds(0, 0, 252, 94);
            contentPanel.add(lblLoading);
        }

        setModalityType(ModalityType.APPLICATION_MODAL);    //置顶显示
        setUndecorated(true);   //禁用或启用此窗体的装饰(true:禁用；false:启用)
        setLocationRelativeTo(null);    //设置窗口相对于指定组件的位置(null表示置于屏幕的中央)
    }
}
