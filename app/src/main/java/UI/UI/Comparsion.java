package UI.UI;

import LogicAndComparsion.Location;
import LogicAndComparsion.StatsComparison;
import LogicAndComparsion.Time;
import LogicAndComparsion.TimeSeries;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class Comparsion extends EastPanel implements ActionListener{
    MainUI main;
    @Override
    public void actionPerformed(ActionEvent e) {
          main = MainUI.getInstance();
        if(main.getResult().size()==0){
            String dia = "add a city first";
            main.makeDialogBox(dia);
        }
        else {

            JComboBox<String> city1= new JComboBox<>();
            JComboBox<String> fromTime = new JComboBox<>(main.years);
            JComboBox<String> toTime = new JComboBox<>(main.years);
            for (MainUI.Node node : main.getResult()) {
                city1.addItem(node.getLocation());
            }
            JLabel city1Label = new JLabel("City:");
            JLabel fromL = new JLabel("form:");
            JLabel toL = new JLabel("to: ");
            JButton submit = new JButton("submit");
            window.add(city1Label);
            window.add(city1);
            window.add(fromL);
            window.add(fromTime);
            window.add(toL);
            window.add(toTime);
            window.add(submit);
            window.setLayout(new FlowLayout());
            window.pack();
            window.setVisible(true);
            submit.addActionListener(c -> {
                try {
                    Location location1 = new Location((String) city1.getSelectedItem());
                    Time start1 = new Time((String) main.fromList.getSelectedItem());        // user gives this (comes as parameter from UI call, logic.AddTimeSeries(place, startTime, endTime);
                    Time end1 = new Time((String) main.toList.getSelectedItem());
                    Time start2 = new Time((String) fromTime.getSelectedItem());
                    Time end2 = new Time((String) toTime.getSelectedItem());
                    int startTemp = Integer.parseInt(start2.getName());
                    int endTemp = Integer.parseInt(end2.getName());
                    if (start1.equals(start2) && end1.equals(end2)) {
                        main.makeDialogBox("please select two different time series");
                    } else if (startTemp >= endTemp) {
                        main.makeDialogBox("select correct time series please");
                    } else {
                        window.dispose();
                        ArrayList<Double> data1 = main.log.fetchData(location1, start1, end1);//  fetchData(place, startTime, endTime) returns data1, data2
                        System.out.println(data1);
                        ArrayList<Double> data2 = main.log.fetchData(location1, start2, end2);
                        System.out.println(data2);
                        TimeSeries tseries1 = new TimeSeries(data1, start1, end1);
                        TimeSeries tseries2 = new TimeSeries(data2, start1, end1);
                        StatsComparison sc = main.log.compareTimeSeries(tseries1, tseries2);
                        System.out.println(sc.getPValue());
                        System.out.println(sc.getConclusion());
                        main.east.remove(main.outputScrollPane);
                        main.createCompareFrame(sc.getPValue(), (String) city1.getSelectedItem(), sc.getConclusion(), main.east);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
        }
    }
}
