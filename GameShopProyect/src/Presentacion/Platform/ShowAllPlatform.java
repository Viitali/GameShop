/**
 * 
 */
package Presentacion.Platform;

import Presentacion.View.ShowAll;
import Transfers.TPlattform;

import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

public class ShowAllPlatform extends ShowAll {

	private static final long serialVersionUID = 1L;
	private String[] _columnsId = { "ID", "Name"};
	AbstractTableModel model;
	private List<Object> _platforms;

	public ShowAllPlatform(String nameIdentificator, List<Object> l) {
		super(nameIdentificator);
		_platforms = l;
		this.initComponents();
	}

	private void initComponents() {
		model = new AbstractTableModel() {

			@Override
			public int getColumnCount() {
				return _columnsId.length;
			}

			@Override
			public int getRowCount() {
				return _platforms == null ? 0 : _platforms.size();
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Object o = null;
				
				switch(columnIndex){
				case 0:
					o = ((TPlattform)_platforms.get(rowIndex)).get_id();
					break;
					
				case 1:
					o = ((TPlattform)_platforms.get(rowIndex)).get_name();
					break;
				}
				return o;
			}

			@Override
			public String getColumnName(int column) {
				return _columnsId[column];
			}

			@Override
			public boolean isCellEditable(int row, int col) {
				return false;
			}
		};

		_grid = new JTable(model);
		_grid.setVisible(true);
		this.add(_grid);

		this.add(new JScrollPane(_grid,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED));
		
	}
	
	@Override
	public void update(List<Object> l) {
		if(this._platforms == null || (l != null && l.size() != this._platforms.size())) {
			this._platforms = l;
			this.model.fireTableDataChanged();
		}
	}

}