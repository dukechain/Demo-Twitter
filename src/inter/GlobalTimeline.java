package inter;



import javax.swing.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import cn.edu.ecnu.GlobalTimelineGenerator;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using
 * Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class GlobalTimeline extends org.eclipse.swt.widgets.Composite
{
    private ResultPanel resultPanel;

    // private CtrlPanel ctrlPanel;

    private Menu menu1;

    private MenuItem endItem;

    private MenuItem startItem;

    private Menu fileMenu;

    private MenuItem fileMenuItem;

    private Timer tt = null;

    private GlobalTimelineGenerator globalTimeline;
    
    /**
     * Auto-generated main method to display this org.eclipse.swt.widgets.Composite inside a new Shell.
     */
    public static void main(String[] args)
    {
        showGUI();

    }

    /**
     * Overriding checkSubclass allows this class to extend org.eclipse.swt.widgets.Composite
     */
    protected void checkSubclass()
    {
    }

    /**
     * Auto-generated method to display this org.eclipse.swt.widgets.Composite inside a new Shell.
     */
    public static void showGUI()
    {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);
        final GlobalTimeline inst = new GlobalTimeline(shell, SWT.NULL);
        Point size = inst.getSize();
        shell.setLayout(new FillLayout());
        shell.layout();
        if (size.x == 0 && size.y == 0)
        {
            inst.pack();
            shell.pack();
        }
        else
        {
            Rectangle shellBounds = shell.computeTrim(0, 0, size.x, size.y);
            shell.setSize(shellBounds.width, shellBounds.height);
            shell.setText("Generator");
            shell.setImage(new Image(null, "image/image.jpg"));
        }
        shell.open();

        // TimerTask task = new TimerTask() {
        //
        // @Override
        // public void run() {
        // int index = (new Random()).nextInt(3);
        // if (index < 0)
        // index += 3;
        // //
        // inst.resultPanel.itemList.get(0).updateImage(
        // Integer.toString(index) + ".jpg");
        // }
        // };
        // (new Timer()).schedule(task, 1000, 500);

        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }

       if( inst.globalTimeline != null ) {
           inst.globalTimeline.end();
       }
    }

    public GlobalTimeline(org.eclipse.swt.widgets.Composite parent, int style)
    {
        super(parent, style);
        initGUI();
    }

    private void initGUI()
    {
        try
        {
            RowLayout thisLayout = new RowLayout(org.eclipse.swt.SWT.HORIZONTAL);
            thisLayout.center = true;
            this.setLayout(new FillLayout());
            this.setSize(570, 600);
            {

                menu1 = new Menu(getShell(), SWT.BAR);
                getShell().setMenuBar(menu1);
                {
                    fileMenuItem = new MenuItem(menu1, SWT.CASCADE);
                    fileMenuItem.setText("Run");
                    {
                        fileMenu = new Menu(fileMenuItem);
                        {
                            startItem = new MenuItem(fileMenu, SWT.CASCADE);
                            startItem.setText("Start");

                            startItem.addListener(SWT.Selection, new Listener()
                            {

                                @Override
                                public void handleEvent(Event arg0)
                                {

                                    new Runnable()
                                    {
                                        
                                        @Override
                                        public void run()
                                        {

                                             globalTimeline = new GlobalTimelineGenerator();
                                            
                                            globalTimeline.gen(resultPanel);
                                            
                                        }
                                    }.run();
                                    
                                    /*if (tt != null)
                                    {
                                        tt.stop();
                                        tt = null;
                                        System.gc();
                                    }
                                    tt = new Timer(1000, new ActionListener()
                                    {

                                        @Override
                                        public void actionPerformed(ActionEvent arg0)
                                        {
                                            Display.getDefault().syncExec(new Runnable()
                                            {
                                                public void run()
                                                {
                                                    int index = (new Random()).nextInt(3);
                                                    if (index < 0)
                                                        index += 3;
                                                    resultPanel.updateResultList("AddTitle", "null", Integer.toString(index) + ".jpg");
                                                    // resultPanel.itemList.get(0).updateImage(
                                                    // Integer.toString(index)
                                                    // +
                                                    // ".jpg");
                                                    // System.out.println(index
                                                    // +
                                                    // ".jpg");
                                                }
                                            });

                                        }
                                    });
                                    tt.start();*/
                                }
                            });

                        }
                        {
                            endItem = new MenuItem(fileMenu, SWT.CASCADE);
                            endItem.setText("End");

                            endItem.addListener(SWT.Selection, new Listener()
                            {

                                @Override
                                public void handleEvent(Event arg0)
                                {

//                                    if (tt != null)
//                                    {
                                        if( globalTimeline != null ) {
                                        globalTimeline.end();
                                        globalTimeline = null;
                                        }
//                                        tt.stop();
//                                        tt = null;
//                                        System.gc();
//                                    }
                                }
                            });

                        }
                        fileMenuItem.setMenu(fileMenu);
                    }
                }

                // ctrlPanel = new CtrlPanel(this, SWT.NULL);
                // System.out.println(ctrlPanel.getSize());
                //				RowData resultPanelLData = new RowData(570,
                //						this.getSize().y - 20);
                resultPanel = new ResultPanel(this, SWT.NULL);
                //				resultPanel.setLayoutData(resultPanelLData);
                //				resultPanel.setSize(570, this.getSize().y - 10);
            }
            this.layout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
