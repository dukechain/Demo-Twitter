package inter;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.cloudgarden.resource.SWTResourceManager;

/**
* This code was edited or generated using CloudGarden's Jigloo
* SWT/Swing GUI Builder, which is free for non-commercial
* use. If Jigloo is being used commercially (ie, by a corporation,
* company or business for any purpose whatever) then you
* should purchase a license for each developer using Jigloo.
* Please visit www.cloudgarden.com for details.
* Use of Jigloo implies acceptance of these licensing terms.
* A COMMERCIAL LICENSE HAS NOT BEEN PURCHASED FOR
* THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED
* LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
*/
public class CtrlPanel extends org.eclipse.swt.widgets.Composite {

	{
		//Register as a resource user - SWTResourceManager will
		//handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	private Composite composite2;
	private Label label1;
	private Composite composite3;
	public Button button1;
	private Label label6;
	private Label label5;
	private Text text5;
	private Label label4;
	private Text text4;
	private Label label3;
	private Text text3;
	private Label attr3;
	private Text text2;
	private Label label2;
	private Text text1;
    private Label label1danwei;
    private Label label2danwei;
    private Label label3danwei;
    private Label label4danwei;
    private Label label5danwei;



	/**
	* Auto-generated main method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void main(String[] args) {
		showGUI();
	}
	
	/**
	* Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
	*/	
	protected void checkSubclass() {
	}
	
	/**
	* Auto-generated method to display this 
	* org.eclipse.swt.widgets.Composite inside a new Shell.
	*/
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		CtrlPanel inst = new CtrlPanel(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if(size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
			shell.setSize(705, 227);
		} else {
			Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
			shell.setSize(shellBounds.width, shellBounds.height);
		}
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch())
				display.sleep();
		}
	}

	public CtrlPanel(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);
		initGUI();
	}

	public ArrayList<String> getParameter(){
	    ArrayList<String> ret = new ArrayList<String>();
	    
	    ret.add(text1.getText());
	    ret.add(text2.getText());
	    ret.add(text3.getText());
	    ret.add(text4.getText());
	    
	    return ret;
	}
	
	public void outputPenalty(String out) {
	    text5.setText(out);
	}
	
	
	private void initGUI() {
		try {
			RowLayout thisLayout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
			thisLayout.spacing = 40;
			thisLayout.fill = true;
			thisLayout.center = true;
			this.setLayout(thisLayout);
			this.setSize(500, 125);
			{
				label5 = new Label(this, SWT.NONE);
				RowData label5LData = new RowData(120,119);
				label5.setLayoutData(label5LData);
				label5.setText("label5");
				label5.setImage(new Image(null,"image/image.jpg"));
			}
			{
				composite3 = new Composite(this, SWT.NONE);
				GridLayout composite3Layout = new GridLayout();
				composite3Layout.makeColumnsEqualWidth = true;
				RowData composite3LData = new RowData(100, 119);
				composite3LData.width = 100;
				composite3LData.height = 119;
				composite3.setLayoutData(composite3LData);
				composite3.setLayout(composite3Layout);
				composite3.setSize(99, 119);
				{
					label6 = new Label(composite3, SWT.NONE);
					GridData label6LData = new GridData();
					label6.setLayoutData(label6LData);
					label6.setText("xxxxx");
					label6.setFont(SWTResourceManager.getFont("微软雅黑",12,1,false,false));
				}
				{
					button1 = new Button(composite3, SWT.PUSH | SWT.CENTER);
					GridData button1LData = new GridData();
					button1.setLayoutData(button1LData);
					button1.setText("Refresh");
					
					button1.addListener(SWT.Selection, new Listener() {
						public void handleEvent(Event e) {
							
						}
					});
					
				}
			}
			{
				composite2 = new Composite(this, SWT.NONE);
				GridLayout composite2Layout = new GridLayout();
				composite2Layout.makeColumnsEqualWidth = false;
				composite2Layout.numColumns = 3;
//				composite2Layout.marginLeft = 10;
				RowData composite2LData = new RowData(250, 140);
				composite2LData.width = 250;
				composite2LData.height = 140;
				composite2.setLayoutData(composite2LData);
				composite2.setLayout(composite2Layout);
				composite2.setSize(249, 139);
				{
					label1 = new Label(composite2, SWT.NONE);
					GridData label1LData = new GridData();
					label1LData.widthHint = 120;
					label1LData.heightHint = 18;
					label1.setLayoutData(label1LData);
					label1.setText("Latency Bound");
				}
				{
					text1 = new Text(composite2, SWT.NONE);
					GridData text1LData = new GridData();
					text1LData.widthHint = 50;
					text1LData.heightHint = 18;
					text1.setLayoutData(text1LData);
					text1.setText("100");
				}
				{
				    label1danwei = new Label(composite2, SWT.NONE);
                    GridData label1LDatadanwei = new GridData();
                    label1LDatadanwei.widthHint = 25;
                    label1LDatadanwei.heightHint = 18;
                    label1danwei.setLayoutData(label1LDatadanwei);
                    label1danwei.setText("ms");
				}
				{
					label2 = new Label(composite2, SWT.NONE);
					GridData label2LData = new GridData();
					label2LData.widthHint = 120;
					label2LData.heightHint = 18;
					label2.setLayoutData(label2LData);
					label2.setText("Staleness Bound");
				}
	        
				{
					text2 = new Text(composite2, SWT.NONE);
					GridData value2LData = new GridData();
					value2LData.widthHint = 50;
					value2LData.heightHint = 18;
					text2.setLayoutData(value2LData);
					text2.setText("60");
				}
			    {
                    label2danwei = new Label(composite2, SWT.NONE);
                    GridData label2LDatadanwei = new GridData();
                    label2LDatadanwei.widthHint = 25;
                    label2LDatadanwei.heightHint = 18;
                    label2danwei.setLayoutData(label2LDatadanwei);
                    label2danwei.setText("s");
                }
				{
					attr3 = new Label(composite2, SWT.NONE);
					GridData attr3LData = new GridData();
					attr3LData.widthHint = 120;
					attr3LData.heightHint = 18;
					attr3.setLayoutData(attr3LData);
					attr3.setText("QoS Preference");
				}
				{
					text3 = new Text(composite2, SWT.NONE);
					GridData text3LData = new GridData();
					text3LData.widthHint = 50;
					text3LData.heightHint = 18;
					text3.setLayoutData(text3LData);
					text3.setText("0.5");
				}
                {
                    label3danwei = new Label(composite2, SWT.NONE);
                    GridData label3LDatadanwei = new GridData();
                    label3LDatadanwei.widthHint = 25;
                    label3LDatadanwei.heightHint = 18;
                    label3danwei.setLayoutData(label3LDatadanwei);
                    label3danwei.setText("");
                }
				{
					label3 = new Label(composite2, SWT.NONE);
					GridData label3LData = new GridData();
					label3LData.widthHint = 120;
					label3LData.heightHint = 18;
					label3.setLayoutData(label3LData);
					label3.setText("Query Weight");
				}
				{
					text4 = new Text(composite2, SWT.NONE);
					GridData text4LData = new GridData();
					text4LData.widthHint = 50;
					text4LData.heightHint = 18;
					text4.setLayoutData(text4LData);
					text4.setText("1");
				}
                {
                    label4danwei = new Label(composite2, SWT.NONE);
                    GridData label4LDatadanwei = new GridData();
                    label4LDatadanwei.widthHint = 25;
                    label4LDatadanwei.heightHint = 18;
                    label4danwei.setLayoutData(label4LDatadanwei);
                    label4danwei.setText("");
                }
				{
					label4 = new Label(composite2, SWT.NONE);
					GridData label4LData = new GridData();
					label4LData.widthHint = 120;
					label4LData.heightHint = 18;
					label4.setLayoutData(label4LData);
					label4.setText("Penalty");
				}
				{
					text5 = new Text(composite2, SWT.NONE);
					GridData text5LData = new GridData();
					text5LData.widthHint = 50;
					text5LData.heightHint = 18;
					text5.setLayoutData(text5LData);
					text5.setText("");
					text5.setEditable(false);
				}
                {
                    label5danwei = new Label(composite2, SWT.NONE);
                    GridData label5LDatadanwei = new GridData();
                    label5LDatadanwei.widthHint = 25;
                    label5LDatadanwei.heightHint = 18;
                    label5danwei.setLayoutData(label5LDatadanwei);
                    label5danwei.setText("");
                }
			}
			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
