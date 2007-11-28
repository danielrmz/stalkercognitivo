import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.Dimension;

public class AttributesTable extends JTable {
	private static final long serialVersionUID = -4058326085312364394L;
	public AttributesModel sm = new AttributesModel();
	
	public AttributesTable(int width, int height){
		this.setModel(this.sm);
	}
	
	public void setData(Object[][] data){
		this.sm.data = data;
		this.sm.fireTableDataChanged();
	}
	
	class AttributesModel extends AbstractTableModel {
		private static final long serialVersionUID = 1L;
		public String[] columnNames = {"Atributo",
                "Peso Anterior","Peso Actual"};
		public Object[][] data;

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public void setValueAt(Object value, int row, int col) {
            data[row][col] = value;
            fireTableCellUpdated(row, col);
        }
    }
}