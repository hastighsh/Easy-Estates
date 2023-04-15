package UI.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class Forcasting extends EastPanel implements ActionListener { ;
    JComboBox<String> methodsList;

    @Override
    public void actionPerformed(ActionEvent e) {
        MainUI main = MainUI.getInstance();

            JComboBox<String> item = new JComboBox<>();
            JTextField month = new JTextField();
            JComboBox<String> visual = new JComboBox();
            visual.addItem("chart");
            visual.addItem("table");
            JButton button = new JButton("submit");
            JLabel type = new JLabel("type");
            window.setPreferredSize(new Dimension(600, 100));
            for (MainUI.Node node : main.getResult()) {
                item.addItem(node.getLocation());
            }
            item.setPreferredSize(new Dimension(120, 20));
            month.setPreferredSize(new Dimension(80, 20));
            JLabel label1 = new JLabel("City: ");
            JLabel label2 = new JLabel("Month: ");
            window.add(label1);
            window.add(item);
            window.add(label2);
            window.add(month);
            window.add(type);
            window.add(visual);
            window.add(button);
            window.setLayout(new FlowLayout());
            window.pack();
            window.setVisible(true);

            button.addActionListener(f -> { // action listener for submit button
                String city = "";
                window.dispose();
                int month1 = 0;
                if (!month.getText().equals("")) {
                    month1 = Integer.parseInt(month.getText());
                }
                ArrayList<Double> temp = new ArrayList<>();
                for (MainUI.Node node : main.getResult()) {
                    if (node.getLocation().equals(item.getSelectedItem())) {
                        temp = main.dataHandler.merge(node);
                        city = node.getLocation();
                    }
                }
                if (month1 <= 0) {
                    main.makeDialogBox("Month must be a positive number.");
                } else {
                    if (main.bar != null) {
                        main.east.remove(main.barChart);
                    }
                    if (main.tableF != null) {
                        main.east.remove(main.tableF);
                    }
                    //east.remove(bar);
                    main.forForecasting(main.log.forecast(temp,
                                    month1,
                                    (String) Objects.requireNonNull(methodsList.getSelectedItem())),
                            city,
                            (String) Objects.requireNonNull(visual.getSelectedItem()));
                }
            });
    }

}
