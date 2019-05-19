package com.wggy.prune.info1113;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Demo {
    private JFrame frame;
    private JTable table;
    private JScrollPane scrollPane;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Demo window = new Demo();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Demo() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 582, 437);
        frame.getContentPane().setLayout(null);
        scrollPane = new JScrollPane();
        scrollPane.setBounds(14, 13, 536, 337);
        frame.getContentPane().add(scrollPane);
        JButton button = new JButton("\u67E5\u627E"); // 查找
        button.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Demo1 s = new Demo1();
                try {
                    if (table != null) {// 先判断表模型是否为空,如果不是则先清除表
                        JFrame f = new JFrame();
                        f.remove(table);
                    }
                    table = s.search();// 表赋值
                    table.setAutoCreateRowSorter(true);
                    scrollPane.setViewportView(table);// 添加滚动条
                } catch (Exception ex) {
                }
            }
        });
        button.setBounds(260, 366, 63, 23);
        frame.getContentPane().add(button);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                frame.dispose();
            }
        });
        frame.setVisible(true);
    }

    class Demo1 {
        private String[] columnNames = { "Planet", "Radius", "Moons", "Gaseous", "Color" };
        private Object[][] cells = { { "Mercury", 2440.0, 0, false, Color.YELLOW },
                { "Venus", 6052.0, 0, false, Color.YELLOW }, { "Earth", 6378.0, 1, false, Color.BLUE },
                { "Mars", 3397.0, 2, false, Color.RED }, { "Jupiter", 71492.0, 16, true, Color.ORANGE },
                { "Saturn", 60268.0, 18, true, Color.ORANGE }, { "Uranus", 25559.0, 17, true, Color.BLUE },
                { "Neptune", 24766.0, 8, true, Color.BLUE }, { "Pluto", 1137.0, 1, false, Color.BLACK } };

        public JTable search() {
            JTable JTable = new JTable(cells, columnNames);// 创建表格
            return JTable;
        }
    }
}
