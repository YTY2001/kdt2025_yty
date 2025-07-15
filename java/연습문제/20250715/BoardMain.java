// src/boardapp/BoardMain.java
package boardapp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BoardMain extends JFrame {
    private final BoardDAO dao = new BoardDAO();
    private final DefaultTableModel tableModel;
    private final JTable jTable;

    public BoardMain() {
        super("게시판 리스트");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // 모델에 dbId(숨김), 번호, 제목, 글쓴이, 날짜, 조회수 컬럼 설정
        tableModel = new DefaultTableModel(
            new String[]{"dbId", "번호", "제목", "글쓴이", "날짜", "조회수"}, 0
        );
        jTable = new JTable(tableModel);
        // dbId 컬럼 숨기기
        jTable.removeColumn(jTable.getColumnModel().getColumn(0));

        // 행 클릭 시 상세 정보 다이얼로그
        jTable.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    int viewRow = jTable.getSelectedRow();
                    if (viewRow != -1) {
                        int modelRow = jTable.convertRowIndexToModel(viewRow);
                        int dbId = (int) tableModel.getValueAt(modelRow, 0);
                        new ViewDialog(BoardMain.this, dao, dbId).setVisible(true);
                        refreshTable();
                    }
                }
            }
        });

        add(new JScrollPane(jTable), BorderLayout.CENTER);

        // 추가 버튼
        JButton btnInsert = new JButton("추가");
        btnInsert.addActionListener(e -> {
            new InsertDialog(this, dao).setVisible(true);
            refreshTable();
        });
        JPanel south = new JPanel();
        south.add(btnInsert);
        add(south, BorderLayout.SOUTH);

        setSize(600, 450);
        setLocationRelativeTo(null);
        refreshTable();
        setVisible(true);
    }

    /** 테이블 갱신 및 번호 초기화 */
    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Board> list = dao.findAll();
        int no = 1;
        for (Board b : list) {
            tableModel.addRow(new Object[]{
                b.getId(),   // 숨겨진 DB id
                no++,        // 화면에 표시할 순번
                b.getTitle(),
                b.getWriter(),
                b.getDate(),
                b.getHit()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BoardMain::new);
    }
}
