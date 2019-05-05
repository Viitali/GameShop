package Presentacion.Ticket;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.AbstractTableModel;

import Negocio.SA.SAAbstractFactory;
import Presentacion.Controller.Controller;
import Presentacion.Controller.Event;
import Transfers.TEmployee;
import Transfers.TPlatform;
import Transfers.TProduct;
import Transfers.TTicket;

@SuppressWarnings("serial")
public class FormTicket extends JDialog {
	
	private JLabel _employeeId = new JLabel("Employee");
	private JLabel _products = new JLabel("Products");
	private JLabel _amount = new JLabel("Amount");
	private JComboBox<Object> _employeeElection = new JComboBox<Object>();
	private JComboBox<Object> _productsElection = new JComboBox<Object>();
	private JSpinner _numberOfproduct = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
	private JButton _add;
	private JButton _remove;
	private JButton _accept;
	private JButton _cancel;
	private AbstractTableModel model;
	private JTable _grid;
	private String[]_columnIds = {"ID", "Name", "Platform", "Amount"};
	private JScrollPane _jsp;
	
	private List<Object> _productsSelected = new ArrayList<Object>();
	
	public FormTicket(){
		this.setTitle("Add new ticket");
		this.setIconImage(new ImageIcon("resources/GameShopLogo.png").getImage());
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				closeDialog();
			}
		});
		
		this.setLayout(new FlowLayout());
		this.setSize(new Dimension(320, 290));
		this.setLocationRelativeTo(null);
		
		initComponents();
		fillRegisterTicketLists();
		okButtonAction();
		cancelButtonAction();
		addButtonAction();
		removeButtonAction();
	}
	
	private void cancelButtonAction() {
		_cancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				closeDialog();
			}
		});
	}
	
	private void okButtonAction() {
		_accept.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(_productsSelected.size() > 0) {
					// info ==> en [0] tenemos el ID del empleado y en [1] tenemos el nombre
					String[] info = ((String)(_employeeElection.getSelectedItem())).split(" - ");
					TTicket tt = new TTicket(Integer.parseInt(info[0]), _productsSelected);
					Double suma = (double) 0;
					for(Object t : _productsSelected)
						suma += ((TProduct)t).get_pvp();
					
					tt.set_finalPrice(suma);
					closeDialog();
					Controller.getInstance().action(tt, Event.REGISTER_TICKET);
				}
			}
		});
	}
	
	private void removeButtonAction() {
		_remove.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectedRow = _grid.getSelectedRow();
				if(selectedRow != -1) {
					int removeCant = (Integer)_numberOfproduct.getValue();
					int unitsOfSelectedProduct = ((TProduct)_productsSelected.get(selectedRow)).get_unitsProvided() -
												 ((TProduct)_productsSelected.get(selectedRow)).get_stock();
					if(unitsOfSelectedProduct >= removeCant) {
						((TProduct)_productsSelected.get(selectedRow)).set_unitsProvided(unitsOfSelectedProduct - removeCant);
						model.fireTableDataChanged();
					}
				}
			}
		});
	}
	
	private void addButtonAction() {
		_add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TProduct toAdd = new TProduct();
				// En [0] tenemos el ID, en [1] el nombre del producto y en [2] el tipo
				String[] info = ((String)(_productsElection.getSelectedItem())).split(" - ");
				toAdd.set_id(Integer.parseInt(info[0]));
				toAdd.set_name(info[1]);
				toAdd.set_type(info[2]);
				Integer unitsToSell = (Integer)_numberOfproduct.getValue();
				
				// Si existe en la BD un producto con ese id, nombre y tipo, nos devuelve todos sus datos
				TProduct all = (TProduct)SAAbstractFactory.getInstance().createSAProduct().readProduct(toAdd.get_id());
				if(all != null && /*!addItemToAnExistingProduct(toAdd) &&*/ all.get_stock() >= unitsToSell) {
					all.set_stock(all.get_stock() - unitsToSell);
					_productsSelected.add(all);
				}
				
				model.fireTableDataChanged();
			}
		});
	}
	
	private boolean addItemToAnExistingProduct(Object tpr) {
		boolean exit = false;
		TProduct tp = (TProduct)tpr;
		for(int i = 0; i < _productsSelected.size() && !exit; ++i) {
			if(((TProduct)_productsSelected.get(i)).get_id() == tp.get_id()) {
				((TProduct)_productsSelected.get(i)).set_unitsProvided(tp.get_unitsProvided() + ((TProduct)_productsSelected.get(i)).get_unitsProvided());
				exit = true;
			}
		}
		return exit;
	}
	
	private void fillRegisterTicketLists() {
		String type;
		// Rellenar la lista de los productos
		for(Object tp : SAAbstractFactory.getInstance().createSAProduct().readAllProducts()) {
			if(((TProduct) tp).get_type().equalsIgnoreCase(TProduct.game))
				type = TProduct.game;
			else
				type = TProduct.accessory;
			/* Aqui ponemos el id, nombre y el tipo para luego hacer SPLIT(" - ") y saber si es juego o accesorio */
			_productsElection.addItem(((TProduct) tp).get_id() + " - " + ((TProduct) tp).get_name() + " - " + type);
		}
		
		// Rellenar la lista de los empleados disponibles en la base de datos
		for(Object te : SAAbstractFactory.getInstance().createSAEmployee().readAllEmployees()) {
			_employeeElection.addItem(((TEmployee) te).get_id() + " - " + ((TEmployee) te).get_name());
		}
	}
	
	
	
	
	// METODOS PARA INICIALIZAR LOS COMPONENTES DE LA GUI
	private void initComponents() {
		_employeeId.setAlignmentX(Component.CENTER_ALIGNMENT);
		_products.setAlignmentX(Component.CENTER_ALIGNMENT);
		_amount.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		sizeComponent(_employeeElection, new Dimension(200, 20));
		sizeComponent(_productsElection, new Dimension(200, 20));
		sizeComponent(_numberOfproduct, new Dimension(50, 20));
		
		_add = new JButton("Add");
		sizeComponent(_add, new Dimension(75, 20));
		_remove = new JButton("Remove");
		sizeComponent(_remove, new Dimension(75, 20));
		
		initTable();
		
		_accept = new JButton("Accept");
		sizeComponent(_accept, new Dimension(75, 20));
		_cancel = new JButton("Cancel");
		sizeComponent(_cancel, new Dimension(75, 20));
		
		addComponentToDialog(_employeeId);
		this.add(Box.createRigidArea(new Dimension(10, 1)));
		addComponentToDialog(_employeeElection);
		this.add(Box.createRigidArea(new Dimension(20, 1)));
		addComponentToDialog(_products);
		this.add(Box.createRigidArea(new Dimension(13, 1)));
		addComponentToDialog(_productsElection);
		this.add(Box.createRigidArea(new Dimension(20, 1)));
		addComponentToDialog(_amount);
		addComponentToDialog(_numberOfproduct);
		addComponentToDialog(_add);
		addComponentToDialog(_remove);
		addComponentToDialog(_jsp);
		addComponentToDialog(_accept);
		addComponentToDialog(_cancel);
		this.setVisible(true);
	}
	
	protected void addComponentToDialog(JComponent c) {
		this.add(c);
	}
	
	protected void sizeComponent(JComponent c, Dimension d) {
		c.setPreferredSize(d);
		c.setMinimumSize(d);
		c.setMaximumSize(d);
		c.setAlignmentX(Component.CENTER_ALIGNMENT);
		c.setVisible(true);
	}
	
	private void initTable() {
		model = new AbstractTableModel() {
			@Override
			public int getColumnCount() {
				return _columnIds.length;
			}
			@Override
			public int getRowCount() {
				return _productsSelected.size() == 0 ? 0 : _productsSelected.size();
			}
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object o = null;
				
				switch(columnIndex){
				case 0:
					o = ((TProduct)_productsSelected.get(rowIndex)).get_id();
					break;
				case 1:
					o = ((TProduct)_productsSelected.get(rowIndex)).get_name();
					break;
				case 2:
					o = ((TPlatform)(SAAbstractFactory.getInstance().createSAPlatform().readPlatform(
							((TProduct)_productsSelected.get(rowIndex)).get_platformId()))).get_name();
					break;
				case 3:
					o = ((TProduct)_productsSelected.get(rowIndex)).get_unitsProvided() - 
							((TProduct)_productsSelected.get(rowIndex)).get_stock();
					break;
				}
				return o;
			}
			@Override
			public String getColumnName(int column) {
				return _columnIds[column];
			}
			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};
		
		_grid = new JTable(model);
		_jsp = new JScrollPane(_grid, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		sizeComponent(_grid, new Dimension(300, 150));
		sizeComponent(_jsp, new Dimension(300, 150));
		setColumnsWidth();
		_grid.setVisible(true);
	}
	
	private void setColumnsWidth() {
		columnWidth(0, 40);
		columnWidth(1, 140);
		columnWidth(2, 50);
		columnWidth(3, 50);
	}
	
	private void columnWidth(int column, int width) {
		_grid.getColumnModel().getColumn(column).setPreferredWidth(width);
		_grid.getColumnModel().getColumn(column).setMinWidth(width);
		_grid.getColumnModel().getColumn(column).setMaxWidth(width);
	}

	protected void closeDialog() {
		setVisible(false);
		dispose();
	}
}
