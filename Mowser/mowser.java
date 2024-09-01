import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class mowser extends JFrame
{
    //Setting Some Key objects
    private JTextField urlfield;
    private JEditorPane displayArea;
    boolean toggle = true;
    JPanel optmenu = new JPanel();
    ImageIcon logo = new ImageIcon("logo.png");
    ImageIcon SetLogo = new ImageIcon("SetLogo.png");

    public mowser() throws IOException
    {
    //setting up more objects
        JLabel shl = new JLabel("Search-History");
        JTextArea dsh = new JTextArea();
        displayArea = new JEditorPane();
        displayArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(displayArea);
    //JButton Config
        JButton search = new JButton("Search");
        JButton opt = new JButton();
        ImageIcon cog = scaleIcon(SetLogo, 30, 30);
        opt.setIcon(cog);
        opt.setBorderPainted(false);
        opt.setBackground(new Color(236, 155, 171));
        opt.setFocusPainted(false);
        opt.setPreferredSize(new Dimension(30, 30));
        search.setBorderPainted(false);
        search.setBackground(new Color(155, 212, 236));
        search.setFocusPainted(false);
    //JFrame Config
        setTitle("Mowser");
        setIconImage(logo.getImage());
        setSize(881,700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    //JButton Events
        urlfield = new JTextField(50);
        search.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String url = urlfield.getText();
                try
                {
                    
                    displayArea.setPage(url);
                    String H = null;
                    FileReader rsh = new FileReader("search_his.txt");
                    int i;
                    while((i=rsh.read()) != -1)
                    {
                        H += (char)i;
                    }
                    rsh.close();
                    FileWriter Fw = new FileWriter("search_his.txt");
                    String SH = H+"\n"+url+"\n";
                    Fw.write(SH.replace("null"," "));
                    Fw.close();
                } 
                catch (IOException ex) {
                    displayArea.setText("Error loading page: " + ex.getMessage());
                }
            }
        });

        opt.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                scrollPane.setVisible(false);
                optmenu.setVisible(true);
                try 
                {
                 if(toggle)
                 {
                    FileReader rsh = new FileReader("search_his.txt");
                    String sh = null;
                    int i;
                    while((i=rsh.read()) != -1)
                    {
                        sh += (char)i;
                    }
                    rsh.close();
                    dsh.setText("\n"+sh.replace("null",""));
                    toggle = false;
                 }
                 else
                 {
                 scrollPane.setVisible(true);
                 optmenu.setVisible(false);
                 toggle = true;
                 }
                } 
                catch (IOException e1) 
                {
                 e1.printStackTrace();
                }
            }
        });
    //JPane&JEditiorPane config
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(new Color(236, 155, 171));
        topPanel.add(urlfield);
        urlfield.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 17));
        topPanel.add(search);
        topPanel.add(opt);

        optmenu.setVisible(false);
        optmenu.setBackground(new Color(163, 199, 212));
        optmenu.setLayout(new BoxLayout(optmenu, BoxLayout.Y_AXIS));
        optmenu.add(shl);
        optmenu.add(dsh);
        
        dsh.setText("");
        dsh.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 15));
        dsh.setEditable(false);
        dsh.setBackground(new Color(163, 199, 212));
        dsh.setAlignmentX(Component.CENTER_ALIGNMENT);

        shl.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 18));
        shl.setForeground(new Color(15, 15, 15));
        shl.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(optmenu, BorderLayout.EAST);
        add(mainPanel);
    }
    // To Scale Icon To opt Size
    private ImageIcon scaleIcon(ImageIcon icon, int width, int height)
    {
        Image img = icon.getImage();
        Image scaledImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImg);
    }

    public static void main(String[] args)
    {
    // Starting The Browser
        SwingUtilities.invokeLater(() ->
        {
            mowser browser;
            try
            {
                browser = new mowser();
                browser.setVisible(true);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}