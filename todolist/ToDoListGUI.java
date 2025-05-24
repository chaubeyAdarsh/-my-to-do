

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;

public class ToDoListGUI extends JFrame {
    private TaskManager manager = new TaskManager();
    private DefaultListModel<String> listModel = new DefaultListModel<>();
    private JList<String> taskList = new JList<>(listModel);

    public ToDoListGUI() {
        super("To-Do List");
        manager.loadTasks();
        for (Task task : manager.getTasks()) {
            listModel.addElement(task.toString());
        }

        JTextField inputField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton deleteButton = new JButton("Delete");
        JButton toggleButton = new JButton("Mark Done/Undone");

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.add(inputField, BorderLayout.CENTER);
        panel.add(addButton, BorderLayout.EAST);

        JPanel buttons = new JPanel();
        buttons.add(deleteButton);
        buttons.add(toggleButton);

        add(panel, BorderLayout.NORTH);
        add(new JScrollPane(taskList), BorderLayout.CENTER);
        add(buttons, BorderLayout.SOUTH);

        addButton.addActionListener(e -> {
            String title = inputField.getText().trim();
            if (!title.isEmpty()) {
                manager.addTask(title);
                listModel.addElement("[ ] " + title);
                inputField.setText("");
            }
        });

        deleteButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index >= 0) {
                manager.deleteTask(index);
                listModel.remove(index);
            }
        });

        toggleButton.addActionListener(e -> {
            int index = taskList.getSelectedIndex();
            if (index >= 0) {
                manager.toggleTask(index);
                listModel.set(index, manager.getTasks().get(index).toString());
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                manager.saveTasks();
                System.exit(0);
            }
        });

        setSize(400, 400);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
