package editor;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


public class popUpData {
	public int currentTimeInSecond = 0;
	public data dataToSet;
	public JFrame frame;
	JTextField audioText;
	JTextField videoText;
	JTextField rateText;
	maskEditor editor;
	data lastData;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					popUpData window = new popUpData(0,null,null);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public popUpData(int second, maskEditor editor, data lastData) {
		currentTimeInSecond = second;
		this.dataToSet = new data();
		this.editor = editor;
		this.lastData = lastData;
		if (lastData != null && (lastData.startInSeconds + lastData.dt > currentTimeInSecond))
		{
		    JFrame frame2 = new JFrame("Add point after dt!");
		    JOptionPane.showMessageDialog(frame2, "You can't add point before last point \"DT\" finishes");
			frame2.setVisible(false);

		    return;
		}
		else{
		initialize();
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		int height = 15;
		int width = 900;
		
		int margin_height = 5;
		int margin_width = 5;
		
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		if (lastData != null && (lastData.startInSeconds + lastData.dt > currentTimeInSecond))
		{
		    JFrame frame2 = new JFrame("Add point after dt!");
		    JOptionPane.showMessageDialog(frame2, "You can't add point before last point \"DT\" finishes");
			frame.setVisible(false);
			frame2.setVisible(false);

		    return;
		}
		
		int countHeight = 0;
		
		// audio text
		JLabel audioLabel = new JLabel("Audio speed:");
		audioText = new JTextField("1.0");
		audioLabel.setBounds(0, countHeight, width, height);
		countHeight+=height+margin_height;
		audioText.setBounds(0, countHeight, width, height);
		countHeight+=height+margin_height;
		frame.getContentPane().add(audioLabel);
		frame.getContentPane().add(audioText);
		
		// video speed
		JLabel videoLabel = new JLabel("Video speed:");
		videoText = new JTextField("1.0");
		videoLabel.setBounds(0, countHeight, width, height);
		countHeight+=height+margin_height;
		videoText.setBounds(0, countHeight, width, height);
		countHeight+=height+margin_height;
		frame.getContentPane().add(videoLabel);
		frame.getContentPane().add(videoText);
				
		// video speed
		JLabel functionRateLabel = new JLabel("Function of user rate:");
		rateText = new JTextField("x");
		functionRateLabel.setBounds(0, countHeight, width, height);
		countHeight+=height+margin_height;
		rateText.setBounds(0, countHeight, width, height);
		countHeight+=height+margin_height;
		frame.getContentPane().add(functionRateLabel);
		frame.getContentPane().add(rateText);
		
		// buttos
		
		JButton syncWait = new JButton("Wait for each other");
		JButton syncDontWait = new JButton("Don't wait for each other");
		JButton syncOptional = new JButton("follows in DT");
		syncWait.setBounds(0, countHeight, width/3-margin_width, height);
		syncDontWait.setBounds(width/3, countHeight, width/3-margin_width, height);
		syncOptional.setBounds(2*width/3, countHeight, width/3-margin_width, height);
		countHeight+=height+margin_height;

		// buttons handler
		syncWait.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateData(data.SyncType.SyncTypeWait, 0);
			}
		});

		syncDontWait.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				updateData(data.SyncType.SyncTypeDontWait, 0);
			}
		});
		
		syncOptional.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
			    JFrame frame = new JFrame("InputDialog Example #1");

			    String dt = JOptionPane.showInputDialog(frame, "Please input DT:");
			    float dtFloat = Float.parseFloat(dt);
			    
			    updateData( data.SyncType.SyncTypeDT, dtFloat);			    
			}
		});
		
		frame.getContentPane().add(syncWait);
		frame.getContentPane().add(syncDontWait);
		frame.getContentPane().add(syncOptional);

		frame.setBounds(100, 100, width, countHeight+25);

	}
	
	void updateData( data.SyncType type, float dt)
	{
		dataToSet.audioFunc = audioText.getText();
		dataToSet.videoFunc = videoText.getText();
		dataToSet.type = type;
		dataToSet.startInSeconds = currentTimeInSecond;
		dataToSet.dt = dt;
		dataToSet.userRateFunction = rateText.getText();
		
		editor.finishAdding(dataToSet);
		frame.setVisible(false);
	}
	
}
