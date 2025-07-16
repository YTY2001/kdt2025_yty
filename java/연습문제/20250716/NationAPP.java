package ch12.sec01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

public class NationAPP extends JFrame {
    private final JButton btnSort;
    private JTable jTable;
    private JComboBox<String> cmbOrder;
    private List<Nation> listNation;

    public NationAPP() {
        setTitle("국가 정보");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 데이터 초기화
        listNation = new ArrayList<>(Nation.nations);

        // 테이블 생성 및 추가
        add(new JScrollPane(makeTable()), BorderLayout.CENTER);

        // 하단 패널: 정렬 옵션 및 버튼
        JPanel panel = new JPanel();
        String[] orders = {"국가별", "인구수", "GDP순위"};
        cmbOrder = new JComboBox<>(orders);
        panel.add(cmbOrder);

        btnSort = new JButton("정렬");
        btnSort.addActionListener(e -> sortAndRefresh());
        panel.add(btnSort);
        add(panel, BorderLayout.SOUTH);

        setSize(600, 300);
        setVisible(true);
    }

    // 클릭 시 정렬 후 테이블 갱신
    private void sortAndRefresh() {
        String criteria = (String) cmbOrder.getSelectedItem();
        switch (criteria) {
            case "국가별":
                listNation.sort(Comparator.comparing(Nation::getName));
                break;
            case "인구수":
                listNation.sort(Comparator.comparingDouble(Nation::getPopulation).reversed());
                break;
            case "GDP순위":
                listNation.sort(Comparator.comparingInt(Nation::getGdpRank));
                break;
        }
        // 모델 초기화 및 재추가
        DefaultTableModel model = (DefaultTableModel) jTable.getModel();
        model.setRowCount(0);
        int idx = 1;
        for (Nation n : listNation) {
            model.addRow(new Object[] {
                idx++, n.getName(), n.getType(), n.getPopulation(), n.getGdpRank()
            });
        }
    }

    private JTable makeTable() {
        if (jTable == null) {
            jTable = new JTable();
            DefaultTableModel model = (DefaultTableModel) jTable.getModel();
            model.addColumn("번호");
            model.addColumn("국가명");
            model.addColumn("유형");
            model.addColumn("인구수(백만)");
            model.addColumn("GDP순위");

            // 초기 데이터 로딩
            int idx = 1;
            for (Nation n : listNation) {
                model.addRow(new Object[] {
                    idx++, n.getName(), n.getType(), n.getPopulation(), n.getGdpRank()
                });
            }

            // 중앙 정렬 렌더러 설정
            CenterTableCellRenderer centerRenderer = new CenterTableCellRenderer();
            for (int i = 0; i < model.getColumnCount(); i++) {
                jTable.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        return jTable;
    }

    // 테이블 셀 중앙 정렬 렌더러
    public class CenterTableCellRenderer extends JLabel implements TableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value,
                boolean isSelected, boolean hasFocus,
                int row, int column) {
            setText(value != null ? value.toString() : "");
            setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 12));
            setHorizontalAlignment(CENTER);
            setOpaque(true);
            setBackground(isSelected ? Color.YELLOW : Color.WHITE);
            return this;
        }
    }

    public static void main(String[] args) {
        new NationAPP();
    }
}
