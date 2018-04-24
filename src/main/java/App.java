import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class App extends JFrame implements ActionListener {
    final String path = "E:/Desktop/Working";
    final String name = "";
    final float delay = 0.1f;

    Recorder recorder = new Recorder(path, delay);
    Encoder encoder = new Encoder();

    JPanel panel =  new JPanel();

    JButton startButton = new JButton("Start Recording");
    JButton stopButton = new JButton("Stop Recording");

    public App() {
        setSize(new Dimension(300,100));
        setLayout(new BorderLayout());

        startButton.setActionCommand("startRecording");
        stopButton.setActionCommand("stopRecording");
        stopButton.setEnabled(false);

        startButton.addActionListener(this);
        stopButton.addActionListener(this);

        panel.add(startButton);
        panel.add(stopButton);

        add(panel);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if("startRecording".equals(e.getActionCommand())) {
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
            recorder.startRecording();
            new Thread(new Runnable() {
                public void run() {
                    while (recorder.isRecording()) {
                        recorder.update();
                    }
                }
            }).start();
        }
        if("stopRecording".equals(e.getActionCommand())) {
            stopButton.setEnabled(false);
            recorder.stopRecording();
            new Thread(new Runnable() {
                public void run() {
                    try {
                        encoder.encodeSequence(new File(path), new File(path + ".mp4"));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    startButton.setEnabled(true);
                }
            }).start();
        }
    }
}

