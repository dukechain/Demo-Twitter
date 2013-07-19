package inter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import cn.edu.ecnu.Tweet;

import com.cloudgarden.resource.SWTResourceManager;

public class ResultPanel extends org.eclipse.swt.widgets.Composite
{

    ArrayList<String> titleList;

    ArrayList<String> imagePathList;

    ArrayList<String> contextList;

    ArrayList<Image> imageList; 
    
    ArrayList<ItemGui> itemList;
    
    ArrayList<Tweet> tweetList;

    private ScrolledComposite sc1;

    private Composite c1;

    {
        // Register as a resource user - SWTResourceManager will
        // handle the obtaining and disposing of resources
        SWTResourceManager.registerResourceUser(this);
    }

    public ResultPanel(Composite parent, int style)
    {
        super(parent, style);
        titleList = new ArrayList<String>();
        imagePathList = new ArrayList<String>();
        contextList = new ArrayList<String>();
        itemList = new ArrayList<ItemGui>();
        imageList = new ArrayList<Image>();
        tweetList = new ArrayList<Tweet>();
//        loadTitleList("titleList.txt");
//        loadList("list.txt");

        initGUI();
    }

    /**
     * Initializes the GUI.
     */
    private void initGUI()
    {
        try
        {

            this.setLayout(new FillLayout());

            this.setSize(new org.eclipse.swt.graphics.Point(480, 800));
            this.setBackground(SWTResourceManager.getColor(192, 192, 192));

            // Composite resultPanel = new Composite(this, SWT.NULL);

            sc1 = new ScrolledComposite(this, SWT.HORIZONTAL | SWT.V_SCROLL | SWT.BORDER);
            sc1.setAlwaysShowScrollBars(true);
            sc1.setExpandVertical(true);
            sc1.setExpandHorizontal(true);
            // sc1.setLayout(new FillLayout(org.eclipse.swt.SWT.HORIZONTAL));
            c1 = new Composite(sc1, SWT.NONE);
            sc1.setContent(c1);
            c1.setBackground(SWTResourceManager.getColor(240, 240, 240));

            GridLayout layout = new GridLayout();
            layout.verticalSpacing = 2;
            c1.setLayout(layout);

            for (int i = 0; i < titleList.size(); ++i)
            {

                String title = titleList.get(i);
                String imagePath = imagePathList.get(i);
                String context = contextList.get(i);

                ItemGui elegui = new ItemGui(c1, SWT.NULL, "", title, imagePath, context);
                itemList.add(elegui);
            }

            sc1.setMinSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

            c1.addPaintListener(new PaintListener()
            {

                @Override
                public void paintControl(PaintEvent arg0)
                {

                    GC gc = arg0.gc;
                    gc.setBackground(SWTResourceManager.getColor(0, 0, 0));

                    for (ItemGui item : itemList)
                    {

                        Rectangle rect = item.getBounds();
                        Rectangle rect1 = new Rectangle(rect.x - 2, rect.y - 2, rect.width + 4, rect.height + 4);
                        gc.fillRectangle(rect1);

                        addControlListener(new ControlAdapter()
                        {

                            public void controlResized(ControlEvent e)
                            {
                                super.controlResized(e);
                            }
                        });

                    }

                }
            });

            this.layout();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void loadTitleList(String fileName)
    {

        String line = null;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null)
            {

                titleList.add(line);
            }

            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void loadList(String fileName)
    {

        String line = null;
        String imagePath;
        String context;
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            while ((line = br.readLine()) != null)
            {

                // title = line;
                imagePath = line;
                context = br.readLine();

                // titleList.add(title);
                imagePathList.add(imagePath);
                contextList.add(context);
            }

            br.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    
    public void updateResultList(Tweet t)
    {
        tweetList.add(0, t);
        
        int n = tweetList.size() - 1;

        for (int i = 0; i < n; ++i)
        {
            ItemGui ig = itemList.get(i);
            Tweet it = tweetList.get(i);
            ig.updateContent(it.getUserName(), 
                    it.getUserImage(), 
                    it.getTweetContent(), 
                    it.getTweetId(), 
                    it.getTweetPosttime());
        }

        Tweet it = tweetList.get(n);
        ItemGui nig = new ItemGui(c1, SWT.NULL, "", 
                it.getUserName(), 
                it.getUserImage(), 
                it.getTweetContent());
        
        nig.updateContent(it.getUserName(), 
                it.getUserImage(), 
                it.getTweetContent(), 
                it.getTweetId(), 
                it.getTweetPosttime());

        itemList.add(nig);
        sc1.setMinSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        c1.layout(true);
        c1.redraw();
        sc1.layout(true);

    }

    public void updateResultList(final String at, final String ac, final String ai)
    {

        titleList.add(0, at);
        contextList.add(0, ac);
        imagePathList.add(0, ai);

        int n = titleList.size() - 1;

        for (int i = 0; i < n; ++i)
        {
            ItemGui ig = itemList.get(i);
            ig.updateContent(titleList.get(i), imagePathList.get(i), contextList.get(i));
        }

        ItemGui nig = new ItemGui(c1, SWT.NULL, "", titleList.get(n), imagePathList.get(n), contextList.get(n));

        itemList.add(nig);
        sc1.setMinSize(c1.computeSize(SWT.DEFAULT, SWT.DEFAULT));

        c1.layout(true);
        c1.redraw();
        sc1.layout(true);

    }

    public void clear() {
        for(ItemGui item : itemList) {
            item.dispose();
        }
        itemList.clear();
        tweetList.clear();
        System.gc();
    }
    
    /**
     * Auto-generated main method to display this org.eclipse.swt.widgets.Composite inside a new Shell.
     */
    public static void main(String[] args)
    {
        Display display = Display.getDefault();
        Shell shell = new Shell(display);
        ResultPanel inst = new ResultPanel(shell, SWT.NULL);
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
        }
        shell.open();
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

}
