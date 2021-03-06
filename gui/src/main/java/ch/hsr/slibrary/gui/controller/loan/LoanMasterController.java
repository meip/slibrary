package ch.hsr.slibrary.gui.controller.loan;

import ch.hsr.slibrary.gui.controller.listener.LoanTableListener;
import ch.hsr.slibrary.gui.controller.system.ComponentController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailController;
import ch.hsr.slibrary.gui.controller.system.MasterDetailControllerDelegate;
import ch.hsr.slibrary.gui.form.LoanDetail;
import ch.hsr.slibrary.gui.form.LoanMaster;
import ch.hsr.slibrary.gui.util.*;
import ch.hsr.slibrary.spa.Library;
import ch.hsr.slibrary.spa.Loan;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class LoanMasterController extends ComponentController implements Observer, LoanDetailControllerDelegate, MasterDetailControllerDelegate, LoanListingControllerDelegate, NotificationResponder {


    private MasterDetailController masterDetailController;
    private LoanMaster loanMaster;
    private Library library;
    private Map<Loan, ComponentController> loanControllerMap = new HashMap<>();
    private ListSelectionModel listSelectionModel;

    public LoanMasterController(String title, LoanMaster component, Library lib) {
        super(title);
        this.component = component;
        loanMaster = component;
        this.library = lib;
    }

    public MasterDetailController getMasterDetailController() {
        return masterDetailController;
    }

    public void setMasterDetailController(MasterDetailController masterDetailController) {
        this.masterDetailController = masterDetailController;
        masterDetailController.setDelegate(this);
    }

    @Override
    public void initialize() {
        NotificationCenter.getInstance().addResponder(MessageID.SHOW_LOAN_DETAIL_MESSAGE, this);
        initializeButtonListeners();
        initializeTable();
        initializeFilters();
        initializeObserving();
        updateUI();
    }

    private void initializeButtonListeners() {
        loanMaster.getShowSelctionButton().setEnabled(false);
        loanMaster.getShowSelctionButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int index : loanMaster.getLoanTable().getSelectedRows()) {
                    index = loanMaster.getLoanTable().convertRowIndexToModel(index);
                    Loan loan = library.getLoans().get(index);
                    presentLoan(loan);
                }
            }
        });

        final LoanMasterController self = this;
        loanMaster.getNewLoanButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewLoanDetailController controller = new NewLoanDetailController("Neue Ausleihe erfassen", new LoanDetail(), library);
                controller.setDelegate(self);
                controller.setMasterDetailController(self.masterDetailController);
                masterDetailController.addDetailController(controller);
                masterDetailController.setSelectedDetailController(controller);
            }
        });

        loanMaster.getNewLoanButton().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()),
                "NEWLOAN");
        loanMaster.getNewLoanButton().getActionMap().put("NEWLOAN", new AbstractAction() {
            public void actionPerformed(ActionEvent evt) {
                loanMaster.getNewLoanButton().doClick();
                System.out.println("NEWLOAN action");
            }
        });
    }

    private void initializeTable() {


        TableHelper.setAlternatingRowStyle(loanMaster.getLoanTable());
        loanMaster.getLoanTable().setModel(new AbstractTableModel() {
            private final int ICON_COLUMN = 0;
            private String[] columnNames = {"Status", "Exemplar-ID", "Titel", "Ausgeliehen bis", "Ausgeliehen an"};

            @Override
            public int getRowCount() {
                return library.getLoans().size();
            }

            @Override
            public int getColumnCount() {
                return columnNames.length;
            }


            @Override
            public String getColumnName(int columnIndex) {
                return columnNames[columnIndex];
            }


            @Override
            public Object getValueAt(int rowIndex, int columnIndex) {
                Loan loan = library.getLoans().get(rowIndex);
                switch (columnIndex) {
                    case ICON_COLUMN:
                        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource((loan.isLent()) ? "error_10x10.png" : "correct_10x10.png"));
                        icon.setDescription(LoanUtil.loanStatus(loan.getDaysOverdue()));
                        return icon;
                    case 1:
                        return loan.getCopy().getInventoryNumber();
                    case 2:
                        return loan.getCopy().getTitle().getName();
                    case 3:
                        return LoanUtil.getReturnDate(loan);
                    case 4:
                        return loan.getCustomer().getSurname() + " " + loan.getCustomer().getName();
                    default:
                        return "";
                }
            }

            public Class getColumnClass(int column) {
                Class clazz = String.class;//getValueAt(0, column).getClass();
                switch (column) {
                    case ICON_COLUMN:
                        clazz = Icon.class;
                        break;
                }
                return clazz;
            }
        });
        loanMaster.getLoanTable().getColumnModel().getColumn(0).setMaxWidth(100);
        loanMaster.getLoanTable().getColumnModel().getColumn(1).setMaxWidth(100);
        loanMaster.getLoanTable().getColumnModel().getColumn(3).setMaxWidth(250);
        loanMaster.getLoanTable().getColumnModel().getColumn(3).setPreferredWidth(250);
        loanMaster.getLoanTable().getColumnModel().getColumn(4).setMaxWidth(250);
        loanMaster.getLoanTable().getColumnModel().getColumn(4).setPreferredWidth(250);
        //loanMaster.getLoanTable().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

        listSelectionModel = loanMaster.getLoanTable().getSelectionModel();
        listSelectionModel.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                updateLabels();
                updateButtons();
            }
        });
        loanMaster.getLoanTable().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    JTable jtable = (JTable) e.getSource();
                    int index = jtable.convertRowIndexToModel(jtable.getSelectedRow());
                    Loan loan = library.getLoans().get(index);
                    presentLoan(loan);
                }
            }
        });
    }

    private void initializeObserving() {
        for (Loan loan : library.getLoans()) {
            loan.addObserver(this);
        }
    }


    private void initializeFilters() {
        LoanTableListener listener = new LoanTableListener(library, loanMaster.getOnlyOverdueCheckbox(), loanMaster.getOnlyLentCheckbox(), loanMaster.getSearchField(), loanMaster.getLoanTable());
        loanMaster.getOnlyOverdueCheckbox().addItemListener(listener);
        loanMaster.getOnlyLentCheckbox().addItemListener(listener);
        loanMaster.getSearchField().addKeyListener(listener);
    }


    private LoanDetailController createControllerForLoan(Loan loan) {
        return new LoanDetailController("Ausleihe: " + loan.getCopy().getTitle().getName(), new LoanDetail(), loan, library);
    }

    private void presentLoan(Loan loan) {
        if (!loanControllerMap.containsKey(loan)) {
            LoanDetailController controller = createControllerForLoan(loan);
            controller.getLoanListingController().setListingDelegate(this);
            controller.setDelegate(this);
            controller.setMasterDetailController(this.masterDetailController);
            loanControllerMap.put(loan, controller);
        }
        ComponentController controller = loanControllerMap.get(loan);
        if (!masterDetailController.containsDetailController(controller)) {
            masterDetailController.addDetailController(controller);
        }
        masterDetailController.setSelectedDetailController(controller);
    }

    private void removeControllerFromMap(ComponentController controller) {
        if (controller.getClass() == LoanDetailController.class) {
            ((LoanDetailController) controller).getLoanListingController().setListingDelegate(null);
        }
        List<Loan> loansToRemove = new LinkedList<>();
        for (Loan loan : loanControllerMap.keySet()) {
            if (loanControllerMap.get(loan) == controller) {
                loansToRemove.add(loan);
            }
        }
        for (Loan loan : loansToRemove) {
            loanControllerMap.remove(loan);
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        updateUI();
    }

    private void updateUI() {
        updateLabels();
        updateButtons();
        updateTable();
        loanMaster.getOnlyLentCheckbox().setSelected(loanMaster.getOnlyLentCheckbox().isSelected());
        loanMaster.getOnlyOverdueCheckbox().setSelected(loanMaster.getOnlyOverdueCheckbox().isSelected());
    }

    private void updateLabels() {
        loanMaster.getLoanCountLabel().setText(new Integer(library.getLoans().size()).toString());
        loanMaster.getCurrentLoanLabel().setText(new Integer(library.getLentOutBooks().size()).toString());
        loanMaster.getOverdueLoanLabel().setText(new Integer(library.getOverdueLoans().size()).toString());
    }

    private void updateButtons() {
        loanMaster.getShowSelctionButton().setEnabled(loanMaster.getLoanTable().getSelectedRowCount() > 0);
    }

    private void updateTable() {
        loanMaster.getLoanTable().updateUI();
    }

    @Override
    public void willRemoveDetailController(ComponentController detailController) {

    }

    @Override
    public void didRemoveDetailController(ComponentController detailController) {
        removeControllerFromMap(detailController);
    }

    @Override
    public void willSelectDetailController(ComponentController detailController) {

    }

    @Override
    public void didSelectDetailController(ComponentController detailController) {
        detailController.setFocus();
    }

    @Override
    public void controllerDidChangeTitle(ComponentController controller) {

    }

    @Override
    public void detailControllerDidCancel(LoanDetailController loanDetailController) {
        removeControllerFromMap(loanDetailController);
        masterDetailController.removeDetailController(loanDetailController);
    }

    @Override
    public void detailControllerDidSave(LoanDetailController loanDetailController, boolean shouldClose) {
        if (shouldClose) {
            removeControllerFromMap(loanDetailController);
            masterDetailController.removeDetailController(loanDetailController);
        }
        updateLabels();
        //Need to call it explicit cause RowFilters on loanTable
        ((AbstractTableModel) loanMaster.getLoanTable().getModel()).fireTableDataChanged();
    }

    @Override
    public void loanListingControllerDidSelectLoans(LoanListingController controller, List<Loan> loans) {
        for (Loan loan : loans) {
            presentLoan(loan);
        }
    }

    @Override
    public void receiveNotification(Notification notification) {
        if (notification.messageID.equals(MessageID.SHOW_LOAN_DETAIL_MESSAGE)) {
            presentLoan((Loan) notification.messageBody);
        }
    }
}
