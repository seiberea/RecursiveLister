import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RecursiveListerFrame extends JFrame
{
    private JPanel mainPanel, titlePanel, displayPanel, buttonPanel;
    private JLabel titleLabel;
    private JTextArea displayText;
    private JScrollPane displayScroll;
    private JButton startButton, quitButton;
    private ActionListener quit = new quitListener();
    private ActionListener start = new startListener();
    private JFileChooser chooser;
    private File startingFile;
    private ArrayList<String> files = new ArrayList<String>();

    RecursiveListerFrame()
    {
        setTitle("Recursive File Lister");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        mainPanel = new JPanel();
        titlePanel = new JPanel();
        displayPanel = new JPanel();
        buttonPanel = new JPanel();
        titleLabel = new JLabel("Recursive File Lister");
        displayText =  new JTextArea(30, 50);
        displayScroll = new JScrollPane(displayText);
        chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        startButton = new JButton("Start");
        startButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        startButton.addActionListener(start);
        quitButton = new JButton("Quit");
        quitButton.setFont(new Font("Comic Sans MS", Font.PLAIN, 25));
        quitButton.addActionListener(quit);

        add(mainPanel);
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(titlePanel, BorderLayout.NORTH);
        titlePanel.add(titleLabel);
        titleLabel.setFont(new Font("Times New Roman", Font.BOLD, 40));

        mainPanel.add(displayPanel, BorderLayout.CENTER);
        displayPanel.add(displayScroll);
        displayText.setFont(new Font("Times New Roman", Font.PLAIN, 20));

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        buttonPanel.add(startButton);
        buttonPanel.add(quitButton);
    }

    private class startListener implements ActionListener
    {
        public void actionPerformed(ActionEvent AE)
        {
            files.clear();
            displayText.setText("");
            File workingDir = new File("C:\\");
            chooser.setCurrentDirectory(workingDir);
            if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                startingFile = chooser.getSelectedFile();
                files = find(startingFile);
                for(int i = 0; i < files.size(); i++)
                {
                    displayText.append(files.get(i));
                    displayText.append("\n");
                }
            }
        }
    }
    private class quitListener implements ActionListener
    {
        public void actionPerformed(ActionEvent AE)
        {
            System.exit(0);
        }
    }

    public File[] FileFinder(File startingDirectory)
    {
        File children[];
        children = startingDirectory.listFiles();
        return children;
    }

    public ArrayList<String> find(File startingDirectory)
    {
        File children[] = FileFinder(startingDirectory);
        for(File child : children)
        {
            if(child.isDirectory())
            {
                find(child);
            }
            else
            {
                files.add(child.getAbsoluteFile().toString());
            }
        }
        return files;
    }
}
