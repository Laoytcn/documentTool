package ui;

import factory.DocumentFactory;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import service.DocumentService;
import utils.WaitUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.util.List;
import java.util.Objects;

public class MainFrame extends JFrame {

    private final String[] excelItems = {"WORD", "PDF", "PPT", "HTML", "JSON", "MARKDOWN"};
    private final String[] pdfItems = {"WORD", "XML", "EXCEL", "PPT", "HTML"};
    private final String[] pptItems = {"HTML", "HTML5", "PDF"};
    private final String[] wordItems = {"TEXT", "PDF"};

    private JPanel contentPane;

    public MainFrame() {
        //创建窗口
        JFrame frame = this;
        //窗口标题
        frame.setTitle("文件转换工具v1.0 Laoyt.cn");

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(0, 0));
        setContentPane(contentPane);

        //更换logo
        //绝对路径
//        Image icon = Toolkit.getDefaultToolkit().getImage("./src/main/java/Java/img/icon.jpg");
        //相对路径
        Image icon = Toolkit.getDefaultToolkit().getImage(".\\image\\icon.ico");
        //将图片添加到窗口
        frame.setIconImage(icon);
//        frame.setIconImage(icon.getImage());
        //窗口大小
        frame.setSize(480, 350);
        //窗口大小不可改变
        frame.setResizable(false);
        //获取屏幕的大小
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        //设置窗口居中
        int x = (int) (screenSize.getWidth() / 2 - frame.getWidth() / 2);
        int y = (int) (screenSize.getHeight() / 2 - frame.getHeight() / 2);
        //将屏幕中心位置设置到窗口中
        frame.setLocation(x, y);
        //用户关闭时会关闭这个对象窗口
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        //用户关闭时会关闭所有窗口
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //创建一个面板,并且将这个面板添加到窗口里面去
        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.NORTH);
        //将面板添加到窗口
        frame.add(panel);
        //取消默认的布局管理
        panel.setLayout(null);
        //标签
        //输入文件
        JLabel impLabel = new JLabel();
        //标签名字
        impLabel.setText("输入文件");
        //标签位置和大小
        impLabel.setBounds(20, 30, 100, 20);
        //将标签添加到面板
        panel.add(impLabel);

        //请选择转换类型
        JLabel typeLabel = new JLabel();
        //标签名字
        typeLabel.setText("请选择转换类型");
        //标签位置和大小
        typeLabel.setBounds(20, 60, 100, 20);
        //将标签添加到面板
        panel.add(typeLabel);


        //按钮
        //输入文件按钮
        JButton impButton = new JButton();
        //按钮名字
        impButton.setText("选择文件");
        //按钮位置和大小
        impButton.setBounds(130, 30, 100, 20);
        //将按钮添加到面板
        panel.add(impButton);


        //类型Combobox
        JComboBox typeCombobox = new JComboBox<>();
        //按钮位置和大小
        typeCombobox.setBounds(130, 60, 100, 20);
        //将按钮添加到面板
        panel.add(typeCombobox);


        //文本框
        //输入文件文本
        //内容，列数，setColumns(15)方法也可设置列数
        JTextField impJTextField = new JTextField("");
        //设置文本是否可编辑
        impJTextField.setEditable(true);
        //获得文本
        impJTextField.getText();
        //为不能编辑，但仍然可以选中内容进行复制等操作
        impJTextField.setEditable(false);
        //为不能使用，文本框以反白显示，不可选择文本也不可编辑
        //tuJTextField.setEnable(false);
        //文本框位置和大小
        impJTextField.setBounds(240, 30, 200, 20);
        //将文本况添加到面板
        panel.add(impJTextField);

        //事件
        //输入文件: 选择按钮事件
        impButton.addActionListener(e -> {
            //打开文件
            JFileChooser fileChooser = new JFileChooser();
            //设置只能选择文件夹
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            //显示文件选择框
            int result = fileChooser.showDialog(null, "选择文件");
            //不可多选
            //fileChooser.setMultiSelectionEnabled(false);//默认false
            fileChooser.setDialogTitle("Please choose a path");
            //使用文件类获取选择器选择的文件
            File f = fileChooser.getSelectedFile();
            String path = "";
            if (!Objects.isNull(f)) {
                //返回路径名
                path = f.getAbsolutePath();
            }
            handlerCombobox(path, typeCombobox);
            //设置到文本框
            impJTextField.setText(path);
        });


        //开始按钮
        JButton startButton = new JButton();
        //按钮名字
        startButton.setText("开始");
        //按钮位置和大小
        startButton.setBounds(240, 60, 65, 20);
        //将按钮添加到面板
        panel.add(startButton);
        //按下开始向后端传送
        startButton.addActionListener(e -> {
            try {
                String text = impJTextField.getText();
                if (StringUtils.isEmpty(text)) {
                    JOptionPane.showMessageDialog(frame, "请选择文件！");
                    return;
                }

                Object selectedItem = typeCombobox.getSelectedItem();
                if (Objects.isNull(selectedItem)) {
                    JOptionPane.showMessageDialog(frame, "请选择转换的类型！");
                    return;
                }
                String type = (String) selectedItem;
                if (startBtn(text, type)) {
                    JOptionPane.showMessageDialog(frame, "转换成功！");
                } else {
                    JOptionPane.showMessageDialog(frame, "转换失败！");
                }

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.getMessage());
            }
        });

        StringBuilder textBuilder = new StringBuilder();
        textBuilder.append("<html>");
        textBuilder.append("<span>说明：</span><BR>\n");
        textBuilder.append("<span>1、可以将文件拖拽至此界面。</span><BR>\n");
        textBuilder.append("<span>2、转换后的文件在源文件路径下。</span><BR>\n");
        textBuilder.append("<span>3、Excel支持：WORD，PDF，PPT，HTML，JSON，MARKDOWN。</span><BR>\n");
        textBuilder.append("<span>4、PDF支持：WORD，XML，EXCEL，PPT，HTML。</span><BR>\n");
        textBuilder.append("<span>5、PPT支持：HTML，HTML5，PDF。</span><BR>\n");
        textBuilder.append("<span>6、WORD支持：TEXT，PDF。</span><BR>");
        textBuilder.append("</html>");


        JLabel fileLabel = new JLabel();
        fileLabel.setText(textBuilder.toString());
        fileLabel.setBounds(20, 100, 390, 200);
        panel.add(fileLabel);

        panel.setTransferHandler(new TransferHandler() {

            @Override
            public boolean canImport(TransferHandler.TransferSupport support) {
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @SuppressWarnings("unchecked")
            @Override
            public boolean importData(TransferHandler.TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }

                Transferable transferable = support.getTransferable();
                try {
                    java.util.List<File> files = (List<File>) transferable.getTransferData(DataFlavor.javaFileListFlavor);
                    if (files.size() != 1) {
                        JOptionPane.showMessageDialog(null, "只能拖拽一个文件");
                        return false;
                    }

                    String filePath = files.get(0).getAbsolutePath();
                    impJTextField.setText(filePath);

                    handlerCombobox(filePath, typeCombobox);
                    return true;
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                    return false;
                }
            }
        });


        //窗口可见
        frame.setVisible(true);
    }


    private boolean startBtn(String text, String type) {
        DocumentFactory factory = new DocumentFactory();
        DocumentService documentService = factory.create(text);
        if (Objects.isNull(documentService)) {
            return false;
        }

        //旋转等待显示
        final WaitUtil waitUtil = new WaitUtil();
        SwingWorker<String, Void> sw = new SwingWorker<String, Void>() {

            StringBuffer sb = new StringBuffer();

            @Override
            protected String doInBackground() throws Exception {
                //-------------模拟任务开始---------------
                documentService.convertFile(text, type);
                //--------------模拟任务结束--------------
                return sb.toString();
            }

            @Override
            protected void done() {
                //将耗时任务执行完得到的结果移至done来进行处理，处理完关闭旋转等待框
                try {
                    String result = get();
                    System.out.println(result);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                // 关闭旋转等待框
                if(waitUtil != null) {
                    waitUtil.dispose();
                }
            }
        };
        sw.execute();
        waitUtil.setVisible(true);  //将旋转等待框WaitUnit设置为可见



        return true;
    }

    private String lastSuffix;

    private void handlerCombobox(String path, final JComboBox typeCombobox) {
        if (StringUtils.isEmpty(path)) {
            return;
        }
        String suffix = FilenameUtils.getExtension(path);
        if (StringUtils.equals(suffix, lastSuffix)) {
            return;
        }
        lastSuffix = suffix;
        typeCombobox.removeAllItems();

        String[] items = new String[]{};
        if (StringUtils.equals(suffix, "pdf")) {
            items = pdfItems;
        } else if (StringUtils.equals(suffix, "xlsx") || StringUtils.equals(suffix, "xls")) {
            items = excelItems;
        } else if (StringUtils.equals(suffix, "ppt") || StringUtils.equals(suffix, "pptx")) {
            items = pptItems;
        } else if (StringUtils.equals(suffix, "doc") || StringUtils.equals(suffix, "docx")) {
            items = wordItems;
        }

        for (String item : items) {
            typeCombobox.addItem(item);
        }
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() -> new MainFrame().setVisible(true));
    }
}
