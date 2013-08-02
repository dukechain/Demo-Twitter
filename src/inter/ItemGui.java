package inter;

import java.util.Random;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

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
public class ItemGui extends org.eclipse.swt.widgets.Composite {
	private Composite composite1;
	private Composite composite2;
	private Label label1;
	private Label label5;
	private Label label4;
	private Label label3;
	private Composite composite3;
	private Text text1;
	private Label label2;
	private Composite group1;
//	private String groupName;
	private String title;
	private String imagePath;
	private Image image;
	private String context;
	{
		// Register as a resource user - SWTResourceManager will
		// handle the obtaining and disposing of resources
		SWTResourceManager.registerResourceUser(this);
	}

	/**
	 * Auto-generated main method to display this
	 * org.eclipse.swt.widgets.Composite inside a new Shell.
	 */
	public static void main(String[] args) {
		showGUI();
	}

	/**
	 * Overriding checkSubclass allows this class to extend
	 * org.eclipse.swt.widgets.Composite
	 */
	protected void checkSubclass() {
	}

	/**
	 * Auto-generated method to display this org.eclipse.swt.widgets.Composite
	 * inside a new Shell.
	 */
	public static void showGUI() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		ItemGui inst = new ItemGui(shell, SWT.NULL);
		Point size = inst.getSize();
		shell.setLayout(new FillLayout());
		shell.layout();
		if (size.x == 0 && size.y == 0) {
			inst.pack();
			shell.pack();
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

	public ItemGui(org.eclipse.swt.widgets.Composite parent, int style) {
		super(parent, style);

//		groupName = "";
		title = "数据挖掘与数据分析";
		context = "【研究人员制成360TB光盘 寿命长达100万年】根据国外媒体报道，南安普敦大学光电研究中心和埃因霍温科技大学的研究人员日前联手开发出了一种拥有360TB存储空间的玻璃光盘，其存储寿命更是长达100万年。";
		imagePath = "2.jpg";

		initGUI();
	}

	public ItemGui(org.eclipse.swt.widgets.Composite parent, int style,
			String name, String title, String imagePath, String context) {

		super(parent, style);
//		this.groupName = name;
		this.title = title;
		this.imagePath = imagePath;
		this.context = context;
		this.image = null;
		initGUI();
	}

	public ItemGui(Composite parent, int style, String name, String title, Image image, String context)
    {
	    super(parent, style);
        this.title = title;
        this.image = image;
        this.context = context;

        initGUI();
    }

    private void initGUI() {
		try {

			FillLayout thisLayout = new FillLayout(
					org.eclipse.swt.SWT.HORIZONTAL);
			this.setLayout(thisLayout);
			this.setSize(550, 90);
			this.setEnabled(false);
			this.setFont(SWTResourceManager
					.getFont("微软雅黑", 11, 2, false, false));
			{
				group1 = new Composite(this, SWT.NULL);
				RowLayout group1Layout = new RowLayout(
						org.eclipse.swt.SWT.HORIZONTAL);
				group1.setLayout(group1Layout);
				// RowLayout group1Layout = new
				// RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
				group1Layout.fill = true;
				// group1.setText(groupName);
				group1.setSize(550, 80);
				{
					composite1 = new Composite(group1, SWT.NONE);
					GridLayout composite1Layout = new GridLayout();
					composite1Layout.makeColumnsEqualWidth = true;
					RowData composite1LData = new RowData(53, 80);
					composite1LData.width = 53;
					composite1LData.height = 80;
					composite1.setLayoutData(composite1LData);
					composite1.setLayout(composite1Layout);
					composite1.setSize(53, 80);
					{
						label1 = new Label(composite1, SWT.NONE);
						label1.setText("label1");
						GridData label1LData = new GridData();
						label1.setLayoutData(label1LData);
						// label1.setImage(SWTResourceManager.getImage("2.jpg"));
						if( image != null ){
						      label1.setImage(image);
						}else 
						label1.setImage(new Image(Display.getDefault(),
								imagePath));
						label1.setSize(53, 60);
					}
				}
				{
					composite2 = new Composite(group1, SWT.NONE);
					RowLayout composite2Layout = new RowLayout(
							org.eclipse.swt.SWT.HORIZONTAL);
					composite2Layout.fill = true;
					composite2Layout.spacing = 3;
					RowData composite2LData = new RowData(470, 80);
					composite2LData.width = 470;
					composite2LData.height = 80;
					composite2.setLayoutData(composite2LData);
					composite2.setLayout(composite2Layout);
					composite2.setSize(470, 79);
					{
						label2 = new Label(composite2, SWT.NONE);
						RowData label2LData = new RowData();
						label2LData.width = 285;
						label2LData.height = 17;
						label2.setLayoutData(label2LData);
						// label2.setText("数据挖掘与数据分析");

						label2.setText(title);
						label2.setFont(SWTResourceManager.getFont("微软雅黑", 11,
								1, false, false));
					}
					{
						label5 = new Label(composite2, SWT.NONE);
						RowData label5LData = new RowData();
						label5LData.width = 161;
						label5LData.height = 17;
						label5.setLayoutData(label5LData);
						Random r = new Random();
						label5.setText("M" + Integer.toString(r.nextInt()));
						label5.setAlignment(SWT.RIGHT);
					}
					{
						text1 = new Text(composite2, SWT.MULTI | SWT.WRAP);
						RowData text1LData = new RowData(445, 40);
						text1LData.width = 445;
						text1LData.height = 40;
						text1.setLayoutData(text1LData);
						// text1.setText("【研究人员制成360TB光盘 寿命长达100万年】根据国外媒体报道，南安普敦大学光电研究中心和埃因霍温科技大学的研究人员日前联手开发出了一种拥有360TB存储空间的玻璃光盘，其存储寿命更是长达100万年。");
						text1.setText(context);
						text1.setSize(450, 40);
					}
					{
						composite3 = new Composite(composite2, SWT.NONE);

						RowData comp3LData = new RowData(330, 20);
						comp3LData.width = 330;
						comp3LData.height = 20;
						FillLayout composite3Layout = new FillLayout(
								org.eclipse.swt.SWT.HORIZONTAL);
						composite3.setLayout(composite3Layout);
						composite3.setLayoutData(comp3LData);
						composite3.setSize(330, 19);
						composite3Layout.spacing = 30;
						{
							label3 = new Label(composite3, SWT.NONE);
							label3.setText("Post time: 7-10");
							label3.setForeground(SWTResourceManager.getColor(0,
									0, 227));
							label3.setFont(SWTResourceManager.getFont("微软雅黑",
									8, 2, false, false));
						}
						/*{
							label4 = new Label(composite3, SWT.NONE);
							label4.setText("Latency: 100ms");
							label4.setForeground(SWTResourceManager.getColor(0,
									0, 227));
							label4.setFont(SWTResourceManager.getFont("微软雅黑",
									8, 2, false, false));
						}*/
					}
				}
			}

			this.layout();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateImage(final String str) {
		Display.getDefault().syncExec(new Runnable() {
			public void run() {
				label1.setImage(new Image(null, str));
			}
		});

	}

	public void updateContent(String string, String string2, String string3) {
		
		label1.setImage(new Image(null, string2));
		label2.setText(string);
		text1.setText(string3);
		label5.setText("M" + Integer.toString((new Random()).nextInt()));
	}

    public void updateContent(String title, Image image2, String context,String mid, String postTime)
    {
        label1.setImage(image2);
        label2.setText(title);
        text1.setText(context);
        label5.setText(mid);
        label3.setText(postTime);
    }

}
