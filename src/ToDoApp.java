import javax.swing.*;//import swing components 
import java.awt.*; //provide layout managers and basic gui tools(borderlayout)
import java.awt.event.*; //contains the actionevent and actionlistener which we use to handle button clicks

public class ToDoApp extends JFrame implements ActionListener
//extends JFrame means that you can call JFrame methods directly like setSize(), setVisible() etc
//implements ActionListener :the class promises to provide actionPerformed method to handle action events
 {
    //taskfield , tasklistmodel ,taskjlist ,addbutton ,deletebutton are declared at the classs level so both the constructors and actionperformed can access them .if they were local variables in the constructor, actionperformed wouldnt see them
    private JTextField taskField;
    private DefaultListModel<String> taskListModel;
    private JList<String> taskJList;
    private JButton addButton, deleteButton;

    public ToDoApp() {
        super("TO-DO LIST APP");
        setSize(400, 400); //standard window configuration
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout()); //borderlayout divides the container into 5 areas:north,south,east,west,center.we use north for input area ,center for task list and south for delete button

        JPanel topPanel = new JPanel();//top panel to hold task field and add button
        taskField = new JTextField(20);//20 is the number of columns (width) of the text field
        addButton = new JButton("Add Task");//button to add task
        addButton.addActionListener(this);//when the button is clicked ,it generates an action event which is handled by this class(actionperformed method)

        topPanel.add(taskField);
        topPanel.add(addButton);
        add(topPanel, BorderLayout.NORTH);

        taskListModel = new DefaultListModel<>();//swings separates model from view .defaultlistmodel holds the strings
        taskJList = new JList<>(taskListModel);//jlist shows them.when u update the model ,the view updates automatically
        add(new JScrollPane(taskJList), BorderLayout.CENTER);//if list is long ,scrollpane adds scrollbars

        JPanel bottomPanel = new JPanel();//bottom panel to hold delete button
        deleteButton = new JButton("Delete Task");//delete button
        deleteButton.addActionListener(this); // when delete button is clicked ,actionperformed method handles it
        bottomPanel.add(deleteButton);// add delete button to bottom panel
        add(bottomPanel, BorderLayout.SOUTH);// add bottom panel to the frame

        setLocationRelativeTo(null);//center the window on the screen
        setVisible(true); //make the window visible
    }

    @Override
    public void actionPerformed(ActionEvent e) 
    //actionperformed(actionevent e) when a registred component is activated swing invokes this method with a  actionevent
    //e.getSource() returns the object that generated the event (the button that was clicked)
    {
        if (e.getSource() == addButton) {
            String task = taskField.getText().trim();//reads the text from the taskfield and removes leading/trailing spaces
            if (!task.isEmpty())//check if task is not empty
            {
                taskListModel.addElement(task); //add the task to the model
                taskField.setText("");//clear the input field after adding
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a task.");
            }
        } else if (e.getSource() == deleteButton) {
            int selectedIndex = taskJList.getSelectedIndex();//returns index of selected item in the list or -1 if none is selected
            if (selectedIndex != -1) {
                taskListModel.remove(selectedIndex);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a task to delete.");
            }
        }
    }
    //Swing is single threaded all gui updates must happens on the event dispatch thread(EDT).If we create or update gui components from other threads it can lead to unpredictable behavior or crashes.Hence we use SwingUtilities.invokeLater to ensure that the gui is created on the EDT
    public static void main(String[] args) {
        SwingUtilities.invokeLater(ToDoApp::new);//queues the gui creation to run on the EDT .this avoids subtle threading bugs and is considered best practices
    }
}