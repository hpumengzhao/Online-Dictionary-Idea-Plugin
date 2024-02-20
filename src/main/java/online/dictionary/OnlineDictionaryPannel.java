package online.dictionary;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class OnlineDictionaryPannel extends JPanel {
    public OnlineDictionaryPannel() {
        //表单
        JPanel formPanel  = new JPanel();
        JTextField wordTextField = new JTextField();
        JButton lookUpButton = new JButton("查询");
        formPanel.setLayout(new BorderLayout());
        formPanel.add(wordTextField, BorderLayout.CENTER);
        formPanel.add(lookUpButton, BorderLayout.EAST);

        JLabel resultLabel = new JLabel();
        resultLabel.setHorizontalAlignment(JLabel.LEFT);
        resultLabel.setVerticalAlignment(JLabel.TOP);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setLayout(new BorderLayout());
        add(formPanel, BorderLayout.NORTH);
        add(resultLabel, BorderLayout.CENTER);

        lookUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String word = wordTextField.getText();
                String result;
                try {
                    if (word.isBlank()) {
                        result = "<font color=red>请输入要查询的内容</font>";
                    } else {
                        Document document = Jsoup.connect("https://cn.bing.com/dict/search?q=" + word).get();
                        String text = document.select("div.qdef div.hd_area").first().toString();
                        String translated = document.select("div.qdef ul").first().toString();
                        result = text + translated;
                    }
                } catch (IOException ex) {
                    result = "<font color=red>网络异常</font>";
                } catch (NullPointerException ex) {
                    result = "<font color=red>数据异常，单词查询失败</font>";
                }

                resultLabel.setText("<html>" + result + "</html>");
            }
        });

        wordTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    lookUpButton.doClick();
                }
            }
        });
    }
}
