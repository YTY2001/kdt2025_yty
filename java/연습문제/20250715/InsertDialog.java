// src/boardapp/InsertDialog.java
package boardapp;

import javax.swing.*;
import java.awt.*;

public class InsertDialog extends JDialog {
    private final BoardDAO dao;
    private JTextField txtTitle;
    private JTextField txtWriter;
    private JTextArea  txtContent;

    public InsertDialog(Frame owner, BoardDAO dao) {
        super(owner, "게시물 작성", true);
        this.dao = dao;

        // 입력 필드
        txtTitle   = new JTextField();
        txtWriter  = new JTextField();
        txtContent = new JTextArea(5, 20);

        JPanel center = new JPanel(new GridLayout(3, 1, 5, 5));
        center.add(labeled("제목",   txtTitle));
        center.add(labeled("글쓴이", txtWriter));
        center.add(new JScrollPane(txtContent));

        // 버튼: 저장, 취소
        JButton btnSave = new JButton("저장");
        btnSave.addActionListener(e -> {
            // DAO.insert 호출
            dao.insert(new Board(
                txtTitle.getText(),
                txtWriter.getText(),
                txtContent.getText()
            ));
            dispose();
        });

        JButton btnCancel = new JButton("취소");
        btnCancel.addActionListener(e -> dispose());

        JPanel south = new JPanel();
        south.add(btnSave);
        south.add(btnCancel);

        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(south,  BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    // 라벨 + 컴포넌트 조합 패널
    private JPanel labeled(String label, Component comp) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(comp,              BorderLayout.CENTER);
        return p;
    }
}
