// src/boardapp/ViewDialog.java
package boardapp;

import javax.swing.*;
import java.awt.*;

public class ViewDialog extends JDialog {
    public ViewDialog(Frame owner, BoardDAO dao, int id) {
        super(owner, "게시물 보기", true);

        // 글 조회 (조회수 증가 포함)
        Board b = dao.findById(id);
        if (b == null) {
            JOptionPane.showMessageDialog(owner, "글을 찾을 수 없습니다.");
            dispose();
            return;
        }

        // 읽기 전용 필드 구성
        JTextField txtTitle  = new JTextField(b.getTitle());
        JTextField txtWriter = new JTextField(b.getWriter());
        JTextArea  txtContent= new JTextArea(b.getContent(), 5, 20);
        txtTitle .setEditable(false);
        txtWriter.setEditable(false);
        txtContent.setEditable(false);

        JPanel center = new JPanel(new GridLayout(3, 1, 5, 5));
        center.add(labeled("제목",   txtTitle));
        center.add(labeled("글쓴이", txtWriter));
        center.add(new JScrollPane(txtContent));

        // 버튼: 수정, 삭제, 닫기
        JButton btnUpdate = new JButton("수정");
        btnUpdate.addActionListener(e -> {
            new UpdateDialog(owner, dao, b).setVisible(true);
            dispose();
        });

        JButton btnDelete = new JButton("삭제");
        btnDelete.addActionListener(e -> {
            if (JOptionPane.showConfirmDialog(
                    this,
                    "정말 삭제하시겠습니까?",
                    "삭제 확인",
                    JOptionPane.YES_NO_OPTION
                ) == JOptionPane.YES_OPTION) {
                dao.delete(id);
                dispose();
            }
        });

        JButton btnClose = new JButton("닫기");
        btnClose.addActionListener(e -> dispose());

        JPanel south = new JPanel();
        south.add(btnUpdate);
        south.add(btnDelete);
        south.add(btnClose);

        getContentPane().add(center, BorderLayout.CENTER);
        getContentPane().add(south,  BorderLayout.SOUTH);
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
