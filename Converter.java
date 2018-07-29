import javax.swing.JFrame;
import java.io.File;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.JScrollPane;
import java.util.ArrayList;
import java.awt.Dimension;
import java.util.List;
import java.util.Scanner;
import javax.swing.tree.TreeNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Node;
import org.apache.commons.io.FileUtils;

public class Converter extends JFrame
{
    private JTree tree;
    private String test = "";
    private static String fo ="";

    private int parentNode;

    public static void main(String[] args)
    {
        //takes html file as arguement in command line
        fo = args[0];
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Converter();
            }
        });
    }

    public Converter() {
    // create window
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setTitle("Parser:Html to HyperTree");
    this.setMinimumSize(new Dimension(300, 400));
    this.setExtendedState(3);
    try {
        test = FileUtils.readFileToString(new File(fo));
        System.out.println("File contents:\n\n "+ test);
    }
    catch(Exception e){
      e.printStackTrace();
    }
    // create tree and root node
    this.tree = new JTree();
    final DefaultMutableTreeNode ROOT = new DefaultMutableTreeNode("ROOT");

    // create model
    DefaultTreeModel treeModel = new DefaultTreeModel(ROOT);
    tree.setModel(treeModel);

    // add scrolling tree to window
    this.add(new JScrollPane(tree));

    // parse document (can be cleaned too)
    Document doc = Jsoup.parse(test);
    // Cleaner cleaner = new Cleaner(Whitelist.simpleText());
    // doc = cleaner.clean(doc);

    // walk the document tree recursivly
    traverseRecursivly(doc.getAllElements().first(), ROOT);
    this.execPrime();
    this.execHead();
    this.execTail();

    System.out.println("Hypertree generated.");
    this.expandAllNodes(tree);
    this.pack();
    this.setLocationRelativeTo(null);
    this.setVisible(true);
}

public void execTail(){
    Tail gt=new Tail();
        gt.set_str(this.test);
        gt.set_i(1);
        gt.dis_gtree();
}

public void execPrime(){
  Prime gt=new Prime();
        gt.set_str(this.test);
        gt.set_i(1);
        gt.dis_gtree();
}

public void execHead(){
  Head gt1=new Head();
        gt1.set_str(this.test);
        gt1.set_i(1);
        gt1.dis_gtree();
}

private void traverseRecursivly(Node docNode, DefaultMutableTreeNode treeNode) {
    // iterate child nodes:
    for (Node nextChildDocNode : docNode.childNodes()) {
        // create leaf:
        DefaultMutableTreeNode nextChildTreeNode = new DefaultMutableTreeNode(nextChildDocNode.nodeName());
        // add child to tree:
        treeNode.add(nextChildTreeNode);
        // do the same for this child's child nodes:
        traverseRecursivly(nextChildDocNode, nextChildTreeNode);
    }
}

// can be removed ...
private void expandAllNodes(JTree tree) {
    int j = tree.getRowCount();
    int i = 0;
    while (i < j) {
        tree.expandRow(i);
        i += 1;
        j = tree.getRowCount();
    }
  }
}
