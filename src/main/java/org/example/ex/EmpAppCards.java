package org.example.ex;

import org.example.L03DBFactory;
import org.example.L05EmpDao;
import org.example.L05EmpDaoImp;
import org.example.L05EmpDto;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 * EMP 관리 UI (CardLayout)
 * - 조회 / 등록 / 수정 / 삭제 페이지를 개별 클래스로 분리
 * - JDBC/DAO는 전혀 없음. TODO 지점에 구현만 추가하면 됨.
 */
public class EmpAppCards {

    // 카드 이름 상수
    private static final String CARD_VIEW   = "VIEW";
    private static final String CARD_CREATE = "CREATE";
    private static final String CARD_UPDATE = "UPDATE";
    private static final String CARD_DELETE = "DELETE";

    private static JLabel status;           // 하단 상태바
    private JFrame frame;
    private JPanel cards;            // 중앙 카드 영역
    private ViewPage viewPage;       // 조회 페이지
    private CreatePage createPage;   // 등록 페이지
    private UpdatePage updatePage;   // 수정 페이지
    private DeletePage deletePage;   // 삭제 페이지

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new EmpAppCards().initUI());
    }

    private void initUI() {
        frame = new JFrame("EMP 관리 (Swing UI Only)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout(8, 8));

        // 상단 내비게이션
        JToolBar nav = new JToolBar();
        nav.setFloatable(false);
        JButton btnView   = new JButton("조회");
        JButton btnCreate = new JButton("등록");
        JButton btnUpdate = new JButton("수정");
        JButton btnDelete = new JButton("삭제");
        nav.add(btnView); nav.add(btnCreate); nav.add(btnUpdate); nav.add(btnDelete);
        frame.add(nav, BorderLayout.NORTH);

        // 중앙 카드 영역
        cards = new JPanel(new CardLayout());
        viewPage   = new ViewPage();
        createPage = new CreatePage();
        updatePage = new UpdatePage();
        deletePage = new DeletePage();

        cards.add(viewPage,   CARD_VIEW);
        cards.add(createPage, CARD_CREATE);
        cards.add(updatePage, CARD_UPDATE);
        cards.add(deletePage, CARD_DELETE);
        frame.add(cards, BorderLayout.CENTER);

        // 하단 상태바
        status = new JLabel("Ready");
        frame.add(status, BorderLayout.SOUTH);

        // 네비게이션 동작
        btnView.addActionListener(e -> showCard(CARD_VIEW));
        btnCreate.addActionListener(e -> showCard(CARD_CREATE));
        btnUpdate.addActionListener(e -> showCard(CARD_UPDATE));
        btnDelete.addActionListener(e -> showCard(CARD_DELETE));

        // 시작은 조회 탭
        showCard(CARD_VIEW);

        frame.setSize(760, 520);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void showCard(String name) {
        ((CardLayout) cards.getLayout()).show(cards, name);
        status.setText(name + " 페이지");
    }


    /* ---------------------------
       조회 페이지 (테이블 + 이름검색)
       --------------------------- */
    static class ViewPage extends JPanel {
        private final DefaultTableModel tableModel;
        private final JTable table;
        private final JTextField tfName;
        private final JButton btnLoadAll, btnSearch;

        ViewPage() {
            super(new BorderLayout(6,6));
            // 상단 검색 바
            JPanel searchBar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            searchBar.add(new JLabel("이름 검색:"));
            tfName = new JTextField(15);
            btnSearch = new JButton("검색");
            btnLoadAll = new JButton("전체 조회");
            searchBar.add(tfName);
            searchBar.add(btnSearch);
            searchBar.add(btnLoadAll);
            add(searchBar, BorderLayout.NORTH);

            // 중앙 테이블
            tableModel = new DefaultTableModel(new Object[]{"EMPNO","ENAME","DEPTNO","SAL","HIREDATE"}, 0) {
                @Override public boolean isCellEditable(int r, int c) { return false; }
            };
            table = new JTable(tableModel);
            add(new JScrollPane(table), BorderLayout.CENTER);

            // 이벤트 (JDBC는 TODO로 비워둠)
            btnLoadAll.addActionListener(e -> onLoadAll());
            btnSearch.addActionListener(e -> onSearch());
            tfName.addActionListener(e -> onSearch());
        }

        private void onLoadAll() {
            // TODO: 여기에서 JDBC로 전체 조회 후 model 채우기
            tableModel.setRowCount(0);
            // 예) model.addRow(new Object[]{empno, ename, deptno, sal, hiredate});
            L05EmpDao empDao= null;
            try {
                empDao = new L05EmpDaoImp(L03DBFactory.getConn());
                java.util.List<L05EmpDto> emps=empDao.findAll();
                for (L05EmpDto e : emps) {
                    Object [] row={ e.getEmpno(), e.getEname(), e.getDeptno(), e.getSal(), e.getHiredate()};
                    tableModel.addRow(row);
                }
                status.setText("전체 직원 " + emps.size() + "명 조회 완료");
            } catch (SQLException e) {
                e.printStackTrace();
                status.setText("조회 실패");
            }
        }

        private void onSearch() {
            String keyword = tfName.getText().trim();
            if (keyword.isEmpty()) {
                status.setText("이름을 입력하세요.");
                return;
            }
            // TODO: 여기에서 JDBC로 ENAME LIKE ? 검색 후 model 채우기

            tableModel.setRowCount(0);
            status.setText("[TODO] 이름 검색 구현: " + keyword);
        }

        // 외부에서 필요 시 호출할 수 있는 헬퍼 (선택)
        void clearTable() { tableModel.setRowCount(0); }
    }

    /* ---------------------------
       등록 페이지 (폼만 제공)
       --------------------------- */
    static class CreatePage extends JPanel {
        private final JTextField tfEmpno = new JTextField(10);
        private final JTextField tfEname = new JTextField(12);
        private final JTextField tfDeptno = new JTextField(8);
        private final JTextField tfSal = new JTextField(8);
        private final JTextField tfHiredate = new JTextField(10); // yyyy-MM-dd
        private final JButton btnSubmit = new JButton("등록");
        private final JButton btnClear = new JButton("초기화");

        CreatePage() {
            super(new BorderLayout(6,6));

            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints g = baseGbc();

            addRow(form, g, 0, "empno", tfEmpno);
            addRow(form, g, 1, "ename", tfEname);
            addRow(form, g, 2, "deptno", tfDeptno);
            addRow(form, g, 3, "sal", tfSal);
            addRow(form, g, 4, "hiredate(yyyy-MM-dd)", tfHiredate);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
            actions.add(btnSubmit);
            actions.add(btnClear);

            add(form, BorderLayout.NORTH);
            add(actions, BorderLayout.CENTER);

            // 이벤트 (JDBC는 TODO)
            btnSubmit.addActionListener(e -> {
                // TODO: INSERT JDBC
                status.setText("[TODO] 등록 구현");
            });
            btnClear.addActionListener(e -> clearForm());
        }

        private GridBagConstraints baseGbc() {
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(6,6,6,6);
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0;
            return c;
        }
        private void addRow(JPanel p, GridBagConstraints c, int row, String label, JComponent field) {
            GridBagConstraints l = (GridBagConstraints) c.clone();
            l.gridx = 0; l.gridy = row; l.weightx = 0;
            p.add(new JLabel(label), l);
            GridBagConstraints f = (GridBagConstraints) c.clone();
            f.gridx = 1; f.gridy = row; f.weightx = 1;
            p.add(field, f);
        }

        void clearForm() {
            tfEmpno.setText(""); tfEname.setText(""); tfDeptno.setText("");
            tfSal.setText(""); tfHiredate.setText("");
            status.setText("등록 폼 초기화");
        }
    }

    /* ---------------------------
       수정 페이지 (폼만 제공)
       --------------------------- */
    static class UpdatePage extends JPanel {
        private final JTextField tfEmpno = new JTextField(10);
        private final JTextField tfEname = new JTextField(12);
        private final JTextField tfDeptno = new JTextField(8);
        private final JTextField tfSal = new JTextField(8);
        private final JTextField tfHiredate = new JTextField(10); // yyyy-MM-dd
        private final JButton btnLoad = new JButton("불러오기");
        private final JButton btnApply = new JButton("수정 저장");

        UpdatePage() {
            super(new BorderLayout(6,6));

            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints g = baseGbc();

            addRow(form, g, 0, "empno(불러오기 대상)", tfEmpno);
            addRow(form, g, 1, "ename", tfEname);
            addRow(form, g, 2, "deptno", tfDeptno);
            addRow(form, g, 3, "sal", tfSal);
            addRow(form, g, 4, "hiredate(yyyy-MM-dd)", tfHiredate);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
            actions.add(btnLoad);
            actions.add(btnApply);

            add(form, BorderLayout.NORTH);
            add(actions, BorderLayout.CENTER);

            // 이벤트 (JDBC는 TODO)
            btnLoad.addActionListener(e -> {
                // TODO: SELECT by empno → 폼 채우기
                status.setText("[TODO] 수정 대상 불러오기");
            });
            btnApply.addActionListener(e -> {
                // TODO: UPDATE JDBC
                status.setText("[TODO] 수정 저장");
            });
        }

        private GridBagConstraints baseGbc() {
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(6,6,6,6);
            c.fill = GridBagConstraints.HORIZONTAL;
            return c;
        }
        private void addRow(JPanel p, GridBagConstraints c, int row, String label, JComponent field) {
            GridBagConstraints l = (GridBagConstraints) c.clone();
            l.gridx = 0; l.gridy = row; l.weightx = 0;
            p.add(new JLabel(label), l);
            GridBagConstraints f = (GridBagConstraints) c.clone();
            f.gridx = 1; f.gridy = row; f.weightx = 1;
            p.add(field, f);
        }
    }

    /* ---------------------------
       삭제 페이지 (폼만 제공)
       --------------------------- */
    static class DeletePage extends JPanel {
        private final JTextField tfEmpno = new JTextField(10);
        private final JButton btnDelete = new JButton("삭제");

        DeletePage() {
            super(new BorderLayout(6,6));

            JPanel form = new JPanel(new GridBagLayout());
            GridBagConstraints g = new GridBagConstraints();
            g.insets = new Insets(6,6,6,6);
            g.fill = GridBagConstraints.HORIZONTAL;

            GridBagConstraints l = (GridBagConstraints) g.clone();
            l.gridx = 0; l.gridy = 0;
            form.add(new JLabel("empno"), l);

            GridBagConstraints f = (GridBagConstraints) g.clone();
            f.gridx = 1; f.gridy = 0; f.weightx = 1;
            form.add(tfEmpno, f);

            JPanel actions = new JPanel(new FlowLayout(FlowLayout.LEFT));
            actions.add(btnDelete);

            add(form, BorderLayout.NORTH);
            add(actions, BorderLayout.CENTER);

            // 이벤트 (JDBC는 TODO)
            btnDelete.addActionListener(e -> {
                // TODO: DELETE JDBC
                status.setText("[TODO] 삭제 실행");
            });
        }
    }
}