// src/boardapp/UpdateDialog.java
package boardapp;

import javax.swing.*;
import java.awt.*;

public class UpdateDialog extends JDialog {
    private final BoardDAO dao;
    private final Board board;
    private JTextField txtTitle;
    private JTextField txtWriter;
    private JTextArea  txtContent;

    public UpdateDialog(Frame owner, BoardDAO dao, Board board) {
        super(owner, "게시물 수정", true);
        this.dao   = dao;
        this.board = board;

        // 기존 값을 불러와 편집 가능하게 세팅
        txtTitle   = new JTextField(board.getTitle());
        txtWriter  = new JTextField(board.getWriter());
        txtContent = new JTextArea(board.getContent(), 5, 20);

        JPanel p = new JPanel(new GridLayout(3, 1, 5, 5));
        p.add(labeled("제목",   txtTitle));
        p.add(labeled("글쓴이", txtWriter));
        p.add(new JScrollPane(txtContent));

        // 저장 버튼: Board 객체에 반영 후 DAO.update() 호출
        JButton ok = new JButton("저장");
        ok.addActionListener(e -> {
            board.setTitle(  txtTitle.getText());
            board.setWriter( txtWriter.getText());
            board.setContent(txtContent.getText());
            dao.update(board);
            dispose();
        });

        JButton cancel = new JButton("취소");
        cancel.addActionListener(e -> dispose());

        JPanel ps = new JPanel();
        ps.add(ok);
        ps.add(cancel);

        getContentPane().add(p,  BorderLayout.CENTER);
        getContentPane().add(ps, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(owner);
    }

    private JPanel labeled(String label, Component comp) {
        JPanel p = new JPanel(new BorderLayout(5,5));
        p.add(new JLabel(label), BorderLayout.WEST);
        p.add(comp,              BorderLayout.CENTER);
        return p;
    }
}
