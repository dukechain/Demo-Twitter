package inter;


import java.util.ArrayList;
import java.util.List;

import javax.swing.Timer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;

import cn.edu.ecnu.GlobalTimelineGenerator;
import cn.edu.ecnu.HomeTimelineQuery;
import cn.edu.ecnu.Penalty;
import cn.edu.ecnu.Tweet;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose whatever) then you should purchase a license for each developer using
 * Jigloo. Please visit www.cloudgarden.com for details. Use of Jigloo implies acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class HomeTimeLine extends org.eclipse.swt.widgets.Composite
{
    private ResultPanel resultPanel;

     private CtrlPanel ctrlPanel;

    private Menu menu1;

    private MenuItem endItem;

    private MenuItem startItem;

    private Menu fileMenu;

    private MenuItem fileMenuItem;

    private Timer tt = null;

    private GlobalTimelineGenerator globalTimeline;
    
    private Composite ins;
    
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
        final HomeTimeLine inst = new HomeTimeLine(shell, SWT.NULL);
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
            shell.setText("TimeLine");
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

    public HomeTimeLine(org.eclipse.swt.widgets.Composite parent, int style)
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
            this.setLayout(thisLayout);
            this.setSize(570, 900);
            {

                ctrlPanel = new CtrlPanel(this, SWT.NULL);
                System.out.println(ctrlPanel.getSize());
                ctrlPanel.button1.addListener(SWT.Selection, new Listener()
                {
                    
                    @Override
                    public void handleEvent(Event arg0)
                    {
                        List<String> par = ctrlPanel.getParameter();
                        System.out.println(par);

                        HomeTimelineQuery timeline = new HomeTimelineQuery(par);
                        List<Tweet> ret = timeline.query();
  
                        resultPanel.clear();

                        long panelstart= System.currentTimeMillis();
                        for (Tweet t : ret)
                        {
                            resultPanel.updateResultList(t);
                        }
                        long panelend= System.currentTimeMillis();
                        
                        System.out.println("panel painting time: "+(panelend-panelstart)+"ms");
                        
                        
//                        ctrlPanel.outputPenalty(Double.toString(
//                                timeline.getTotalPenalty()));
                        
                        //////
                        ArrayList<String> columnTitle;
                        ArrayList<ArrayList<String>> itemList;
                        int imageIdx;
                        columnTitle = new ArrayList<String>();
                        columnTitle.add("Name");
                        columnTitle.add("Image");
                        columnTitle.add("Arrival Time");
                        columnTitle.add("Finished Time");
                        columnTitle.add("Unapplied Update Arrival Time");
                        columnTitle.add("QoS Penalty");
                        columnTitle.add("QoD Penalty");
                        columnTitle.add("Total Penalty");

                        ArrayList<Integer> widths = new ArrayList<Integer>();
                        widths.add(70);
                        widths.add(57);
                        widths.add(140);
                        widths.add(140);
                        widths.add(80);
                        widths.add(110);
                        widths.add(110);
                        widths.add(110);
                        
                        itemList = new ArrayList<ArrayList<String>>();
                        
                        List<Penalty> penalties = timeline.getpenalties();
                        
                        for (Penalty penalty : penalties)
                        {
                            itemList.add(penalty.getItem());
                        }
                        /*for (int i = 0; i < 3; ++i) {
                            ArrayList<String> item = new ArrayList<String>();
                            item.add("lee");
                            item.add("image/"+Integer.toString(i) + ".jpg");
                            item.add("10");
                            item.add("10");
                            item.add("10");
                            itemList.add(item);
                        }
                        for (int i = 0; i < 3; ++i) {
                            ArrayList<String> item = new ArrayList<String>();
                            item.add("lee");
                            item.add("image/"+Integer.toString(i) + ".jpg");
                            item.add("10");
                            item.add("10");
                            item.add("10");
                            itemList.add(item);
                        }*/
                        imageIdx = 1;
                        TableDemo td = new TableDemo(Display.getCurrent().getActiveShell());
                        td.setData(columnTitle, itemList, imageIdx);
                        td.setWidth(widths);
                        td.open();
                    }
                });
                
                
                RowData resultPanelLData = new RowData(570, this.getSize().y - 20);
                
                ins = new Composite(this, SWT.NULL);
                ins.setLayout(new FillLayout());
                ins.setLayoutData(resultPanelLData);
                resultPanel = new ResultPanel(ins, SWT.NULL);
            }
            this.layout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}
