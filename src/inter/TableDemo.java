package inter;

import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import com.cloudgarden.resource.SWTResourceManager;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class TableDemo extends Dialog{

	private Table table = null;
	
	protected Shell shell;
	protected Object result;

	ArrayList<String> columnTitle;
	ArrayList<ArrayList<String>> itemList;
	ArrayList<Integer> widths;
	int imageIdx;

	public TableDemo(Shell parent) {
		super(parent, SWT.RESIZE);
	}

	/**
	 * 打开控件
	 * @return
	 */
	public Object open() {
		createContents();
		shell.setLayout(new FillLayout());
		shell.setText("Result");
		
		// createTable(shell);

		createTable(columnTitle, itemList, imageIdx);
		shell.pack();
		shell.open();
		shell.layout();

		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
		return result;
	}

	protected void createContents() {
		shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL | SWT.RESIZE);
//	    shell = new Shell(getParent(), SWT.NULL);
		shell.setText("SWT Dialog");
	}

	/**
	 * 设置table数据
	 * @param columnTitle	列名称
	 * @param itemList	行数据
	 * @param imageIdx	图片的列id
	 */
	public void setData(ArrayList<String> columnTitle,
			ArrayList<ArrayList<String>> itemList, int imageIdx) {
		this.columnTitle = columnTitle;
		this.itemList = itemList;
		this.imageIdx = imageIdx;
		this.widths = null;
	}
	
	
	public void setWidth(ArrayList<Integer> widths) {
	    this.widths = widths;
	}
	
	/**
	 * 创建table 
	 * @param columnTitle
	 * @param itemList
	 * @param imageIdx
	 */
	private void createTable(ArrayList<String> columnTitle,
			ArrayList<ArrayList<String>> itemList, int imageIdx) {
		table = new Table(shell, SWT.MULTI | SWT.FULL_SELECTION);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.setSize(400, 600);
		
		Font titleFont = SWTResourceManager.getFont("微软雅黑", 10, SWT.BOLD);
		Font contextFont = SWTResourceManager.getFont("微软雅黑", 12, 0, false,
				false);

		table.setFont(titleFont);

		TableColumn col0 = new TableColumn(table, SWT.CENTER);
		col0.setText("");
		col0.setWidth(0);
		col0.setResizable(false);

		
		for (int i=0;i<columnTitle.size();++i) {
		    String str = columnTitle.get(i);
		    int width = 70;
		    if( widths != null ) width = widths.get(i);
			TableColumn col = new TableColumn(table, SWT.CENTER);
			col.setText(str);
			col.setWidth(width);
		}

		Color bg = Display.getDefault().getSystemColor(SWT.COLOR_GRAY);
		int idx = 0;
		for (ArrayList<String> itemData : itemList) {
			TableItem item = new TableItem(table, SWT.LEFT);
			item.setFont(contextFont);
			idx++;
			if (idx % 2 == 0)
				item.setBackground(bg);
			for (int i = 0; i < itemData.size(); ++i) {
				if (i != imageIdx) {
					item.setText(i + 1, itemData.get(i));
				} else {
					item.setImage(i + 1, new Image(null, itemData.get(i)));
				}
			}
		}

	}
}
